package org.fz.erwin.stream;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
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
    private final Map<Object, Function<Stream<T>, ?>> forks = new HashMap<>(16);

    public StreamForks(Stream<T> stream) {
        this.stream = stream;
    }

    public static <T> StreamForks<T> of(Collection<T> collection) {
        return new StreamForks<>(collection.stream());
    }

    public static <T> StreamForks<T> of(T[] array) {
        return new StreamForks<>(Stream.of(array));
    }

    public StreamForks<T> fork(Object key, Function<Stream<T>, ?> fn) {
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
        List<BlockingQueue<T>> queues = new ArrayList<>(10);

        Map<Object, Future<?>> actions = forks.entrySet().stream().reduce(new HashMap<>(16), (map, e) -> {
            map.put(e.getKey(), getForkResult(queues, e.getValue()));
            return map;
        }, (m1, m2) -> {
            m1.putAll(m2);
            return m1;
        });

        return new ForkingStreamConsumer<>(queues, actions);
    }

    private Future<?> getForkResult(List<BlockingQueue<T>> queues, Function<Stream<T>, ?> fn) {
        BlockingQueue<T> queue = new LinkedBlockingQueue<>();
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

        private final List<BlockingQueue<T>> queues;
        private final Map<Object, Future<?>> actions;

        public ForkingStreamConsumer(List<BlockingQueue<T>> queues, Map<Object, Future<?>> actions) {
            this.queues  = queues;
            this.actions = actions;
        }

        void finish() {
            accept((T) END_OF_STREAM);
        }

        @Override
        public <R> R get(Object key) {
            try {
                return ((Future<R>) actions.get(key)).get();
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        @Override
        public void accept(T t) {
            queues.forEach(q -> q.add(t));
        }
    }

    public static class BlockingQueueSpliterator<T> implements Spliterator<T> {

        private final BlockingQueue<T> queue;

        public BlockingQueueSpliterator(BlockingQueue<T> queue) {
            this.queue = queue;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            T t;

            while (true) {
                try {
                    t = queue.take();
                    break;
                }
                catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }

            if (t != ForkingStreamConsumer.END_OF_STREAM) {
                action.accept(t);
                return true;
            }

            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return 0;
        }
    }
}
