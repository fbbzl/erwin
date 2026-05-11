package org.fz.erwin.stream;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.fz.erwin.TestAssertions.assertThrowsWithMessage;
import static org.junit.jupiter.api.Assertions.*;

class StreamForksTest {

    @Test
    void forksACollectionStreamIntoIndependentResults() {
        StreamForks.ForkResult result = StreamForks.of(List.of(1, 2, 3, 4))
                                                   .fork("sum", stream -> stream.mapToInt(Integer::intValue).sum())
                                                   .fork("even", stream -> stream.filter(value -> value % 2 == 0)
                                                                                 .toList())
                                                   .fork("count", stream -> stream.count())
                                                   .done();

        assertEquals(10, result.<Integer>get("sum"));
        assertIterableEquals(List.of(2, 4), result.<List<Integer>>get("even"));
        assertEquals(4L, result.<Long>get("count"));
    }

    @Test
    void forksArrayStreamsAndPreservesNullValues() {
        String[] values = {"a", null, "b"};

        StreamForks.ForkResult result = StreamForks.of(values)
                                                   .fork("list", stream -> stream.toList())
                                                   .fork("nulls", stream -> stream.filter(Objects::isNull).count())
                                                   .done();

        assertIterableEquals(Arrays.asList("a", null, "b"), result.<List<String>>get("list"));
        assertEquals(1L, result.<Long>get("nulls"));
    }

    @Test
    void validatesPublicInputs() {
        assertThrowsWithMessage(IllegalArgumentException.class, "stream can not be null",
                                () -> new StreamForks<>((java.util.stream.Stream<String>) null));
        assertThrowsWithMessage(IllegalArgumentException.class, "collection can not be null",
                                () -> StreamForks.of((List<String>) null));
        assertThrowsWithMessage(IllegalArgumentException.class, "array can not be null",
                                () -> StreamForks.of((String[]) null));
        assertThrowsWithMessage(IllegalArgumentException.class, "fork function can not be null",
                                () -> StreamForks.of(List.of("a")).fork("x", null));
    }

    @Test
    void resultGetRejectsMissingKeysAndPropagatesRuntimeExceptions() {
        StreamForks.ForkResult result = StreamForks.of(List.of("a"))
                                                   .fork("value", stream -> stream.findFirst().orElseThrow())
                                                   .done();
        assertThrowsWithMessage(IllegalArgumentException.class, "no fork registered for key: missing",
                                () -> result.get("missing"));

        IllegalStateException configured = new IllegalStateException("fork failed");
        StreamForks.ForkResult failed = StreamForks.of(List.of("a"))
                                                   .fork("boom", stream -> {
                                                       throw configured;
                                                   })
                                                   .done();

        assertSame(configured, assertThrows(IllegalStateException.class, () -> failed.get("boom")));
    }

    @Test
    void blockingQueueSpliteratorReadsValuesUntilLegacyEndMarker() {
        BlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add(StreamForks.ForkingStreamConsumer.END_OF_STREAM);
        StreamForks.BlockingQueueSpliterator<String> spliterator =
                new StreamForks.BlockingQueueSpliterator<>(queue);
        List<String> values = new ArrayList<>();

        assertTrue(spliterator.tryAdvance(values::add));
        assertFalse(spliterator.tryAdvance(values::add));
        assertIterableEquals(List.of("a"), values);
        assertNull(spliterator.trySplit());
        assertEquals(Long.MAX_VALUE, spliterator.estimateSize());
        assertEquals(Spliterator.ORDERED, spliterator.characteristics());
    }

    @Test
    void blockingQueueSpliteratorValidatesInputs() {
        assertThrowsWithMessage(IllegalArgumentException.class, "queue can not be null",
                                () -> new StreamForks.BlockingQueueSpliterator<>(null));

        BlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        StreamForks.BlockingQueueSpliterator<String> spliterator =
                new StreamForks.BlockingQueueSpliterator<>(queue);

        assertThrowsWithMessage(IllegalArgumentException.class, "action can not be null",
                                () -> spliterator.tryAdvance(null));
        assertDoesNotThrow(() -> assertTrue(spliterator.tryAdvance(value -> assertEquals("a", value))));
    }
}
