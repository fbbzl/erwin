package org.fz.erwin.stream;

import org.fz.erwin.lang.Vars;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author fengbinbin
 * @version 1.0
 * @since 2020/7/20 15:31
 */
public class StreamForks<T> {

    private final Stream<T>                           stream;
    private final Map<Object, Function<Stream<T>, ?>> forks = LinkedHashMap.newLinkedHashMap(16);

    public StreamForks(Stream<T> stream) {
        this.stream = Vars.requireNotNull(stream, "stream can not be null");
    }

    public static <T> StreamForks<T> of(Collection<T> collection) {
        Vars.requireNotNull(collection, "collection can not be null");
        return new StreamForks<>(collection.stream());
    }

    public static <T> StreamForks<T> of(T[] array) {
        Vars.requireNotNull(array, "array can not be null");
        return new StreamForks<>(Stream.of(array));
    }

    public StreamForks<T> fork(Object key, Function<Stream<T>, ?> fn) {
        Vars.requireNotNull(fn, "fork function can not be null");
        forks.put(key, fn);
        return this;
    }

    public ForkResult done() {
        ForkingStreamConsumer<T> consumer = build();

        try {
            stream.sequential().forEach(consumer);
        }
        finally {
            consumer.finish();
        }
        return consumer;
    }

    private ForkingStreamConsumer<T> build() {
        List<BlockingQueue<StreamItem<T>>> queues = new ArrayList<>(forks.size());
        Map<Object, Future<?>>             actions = new LinkedHashMap<>(forks.size());

        forks.forEach((key, fn) -> actions.put(key, getForkResult(queues, fn)));

        return new ForkingStreamConsumer<>(queues, actions);
    }

    private Future<?> getForkResult(List<BlockingQueue<StreamItem<T>>> queues, Function<Stream<T>, ?> fn) {
        BlockingQueue<StreamItem<T>> queue = new LinkedBlockingQueue<>();
        queues.add(queue);
        Spliterator<T> spliterator = new BlockingQueueSpliterator<>(queue);
        Stream<T>      source      = StreamSupport.stream(spliterator, false);

        return CompletableFuture.supplyAsync(() -> fn.apply(source));
    }

    /**
     * the result returned after the fork operation
     */
    public interface ForkResult {

        /**
         * obtain the result of the fork operation
         *
         */
        <R> R get(Object key);
    }

    @SuppressWarnings("unchecked")
    public static class ForkingStreamConsumer<T> implements Consumer<T>, ForkResult {

        static final Object END_OF_STREAM = new Object();

        private final List<BlockingQueue<StreamItem<T>>> queues;
        private final Map<Object, Future<?>>             actions;

        public ForkingStreamConsumer(List<BlockingQueue<StreamItem<T>>> queues, Map<Object, Future<?>> actions) {
            this.queues  = queues;
            this.actions = actions;
        }

        void finish() {
            StreamItem<T> end = StreamItem.end();
            queues.forEach(q -> q.add(end));
        }

        @Override
        public <R> R get(Object key) {
            Future<?> action = Vars.requireNotNull(actions.get(key), "no fork registered for key: {}", key);

            try {
                return (R) action.get();
            }
            catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(exception);
            }
            catch (ExecutionException exception) {
                Throwable cause = exception.getCause();
                if (cause instanceof RuntimeException runtimeException) {
                    throw runtimeException;
                }
                if (cause instanceof Error error) {
                    throw error;
                }
                throw new RuntimeException(cause);
            }
        }

        @Override
        public void accept(T t) {
            StreamItem<T> value = StreamItem.value(t);
            queues.forEach(q -> q.add(value));
        }
    }

    public static class BlockingQueueSpliterator<T> implements Spliterator<T> {

        private final BlockingQueue<?> queue;

        public BlockingQueueSpliterator(BlockingQueue<?> queue) {
            this.queue = Vars.requireNotNull(queue, "queue can not be null");
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean tryAdvance(Consumer<? super T> action) {
            Vars.requireNotNull(action, "action can not be null");

            Object next = take();
            if (next instanceof StreamItem<?> item) {
                if (item.end) {
                    return false;
                }

                action.accept((T) item.value);
                return true;
            }

            if (next != ForkingStreamConsumer.END_OF_STREAM) {
                action.accept((T) next);
                return true;
            }

            return false;
        }

        private Object take() {
            try {
                return queue.take();
            }
            catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(exception);
            }
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return ORDERED;
        }
    }

    private static final class StreamItem<T> {

        private static final StreamItem<?> END = new StreamItem<>(null, true);

        private final T       value;
        private final boolean end;

        private StreamItem(T value, boolean end) {
            this.value = value;
            this.end   = end;
        }

        private static <T> StreamItem<T> value(T value) {
            return new StreamItem<>(value, false);
        }

        @SuppressWarnings("unchecked")
        private static <T> StreamItem<T> end() {
            return (StreamItem<T>) END;
        }
    }
}
