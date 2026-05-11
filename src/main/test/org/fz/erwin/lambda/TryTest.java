package org.fz.erwin.lambda;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.*;

class TryTest {

    @Test
    void adaptersDelegateSuccessfulCalls() {
        AtomicInteger counter = new AtomicInteger();
        AtomicReference<String> seen = new AtomicReference<>();

        Runnable runnable = Try.run(counter::incrementAndGet);
        Function<String, String> function = Try.apply(value -> value.toUpperCase());
        BiFunction<String, String, String> biFunction = Try.apply((left, right) -> left + right);
        Consumer<String> consumer = Try.accept(seen::set);
        BiConsumer<String, String> biConsumer = Try.accept((left, right) -> seen.set(left + right));
        Supplier<String> supplier = Try.get(() -> "value");
        Predicate<String> predicate = Try.test(value -> value.startsWith("v"));

        runnable.run();
        consumer.accept("a");
        biConsumer.accept("b", "c");

        assertEquals(1, counter.get());
        assertEquals("VALUE", function.apply("value"));
        assertEquals("lr", biFunction.apply("l", "r"));
        assertEquals("bc", seen.get());
        assertEquals("value", supplier.get());
        assertTrue(predicate.test("value"));
    }

    @Test
    void adaptersWrapCheckedExceptionsInLambdasException() {
        Try.LambdasException runnable = assertThrows(Try.LambdasException.class,
                                                     () -> Try.run(() -> {
                                                         throw new IOException("run");
                                                     }).run());
        Try.LambdasException function = assertThrows(Try.LambdasException.class,
                                                     () -> Try.apply((String value) -> {
                                                         throw new IOException("apply");
                                                     }).apply("x"));
        Try.LambdasException biFunction = assertThrows(Try.LambdasException.class,
                                                       () -> Try.apply((String left, String right) -> {
                                                           throw new IOException("bi");
                                                       }).apply("a", "b"));
        Try.LambdasException consumer = assertThrows(Try.LambdasException.class,
                                                     () -> Try.accept((String value) -> {
                                                         throw new IOException("accept");
                                                     }).accept("x"));
        Try.LambdasException biConsumer = assertThrows(Try.LambdasException.class,
                                                       () -> Try.accept((String left, String right) -> {
                                                           throw new IOException("bi accept");
                                                       }).accept("a", "b"));
        Try.LambdasException supplier = assertThrows(Try.LambdasException.class,
                                                     () -> Try.get(() -> {
                                                         throw new IOException("get");
                                                     }).get());
        Try.LambdasException predicate = assertThrows(Try.LambdasException.class,
                                                      () -> Try.test((String value) -> {
                                                          throw new IOException("test");
                                                      }).test("x"));

        assertInstanceOf(IOException.class, runnable.getCause());
        assertInstanceOf(IOException.class, function.getCause());
        assertInstanceOf(IOException.class, biFunction.getCause());
        assertInstanceOf(IOException.class, consumer.getCause());
        assertInstanceOf(IOException.class, biConsumer.getCause());
        assertInstanceOf(IOException.class, supplier.getCause());
        assertInstanceOf(IOException.class, predicate.getCause());
    }

    @Test
    void adaptersRejectNullUncheckedFunctions() {
        assertThrows(NullPointerException.class, () -> Try.run(null));
        assertThrows(NullPointerException.class, () -> Try.apply((Try.UncheckedFunction<String, String>) null));
        assertThrows(NullPointerException.class,
                     () -> Try.apply((Try.UncheckedBiFunction<String, String, String>) null));
        assertThrows(NullPointerException.class, () -> Try.accept((Try.UncheckedConsumer<String>) null));
        assertThrows(NullPointerException.class,
                     () -> Try.accept((Try.UncheckedBiConsumer<String, String>) null));
        assertThrows(NullPointerException.class, () -> Try.get(null));
        assertThrows(NullPointerException.class, () -> Try.test(null));
    }

    @Test
    void suppressAdaptersIgnoreExceptions() {
        AtomicInteger counter = new AtomicInteger();

        Try.suppress((Try.UncheckedRunnable) () -> {
            throw new IOException("ignored");
        }).run();
        Try.suppress((Try.UncheckedConsumer<String>) value -> {
            throw new IOException("ignored");
        }).accept("x");
        Try.suppress((Try.UncheckedRunnable) counter::incrementAndGet).run();
        Try.suppress((Try.UncheckedConsumer<String>) value -> counter.incrementAndGet()).accept("x");

        assertEquals(2, counter.get());
    }

    @Test
    void lambdasExceptionConstructorsPreserveMessageAndCause() {
        Throwable cause = new IllegalStateException("cause");

        Try.LambdasException messageOnly = new Try.LambdasException("message");
        Try.LambdasException messageAndCause = new Try.LambdasException("message", cause);
        Try.LambdasException causeOnly = new Try.LambdasException(cause);
        Try.LambdasException full = new Try.LambdasException("message", cause, false, false);

        assertEquals("message", messageOnly.getMessage());
        assertEquals("message", messageAndCause.getMessage());
        assertSame(cause, messageAndCause.getCause());
        assertSame(cause, causeOnly.getCause());
        assertEquals("message", full.getMessage());
        assertSame(cause, full.getCause());
    }
}
