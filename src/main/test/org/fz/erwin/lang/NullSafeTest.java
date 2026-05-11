package org.fz.erwin.lang;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.fz.erwin.TestAssertions.assertThrowsWithMessage;
import static org.junit.jupiter.api.Assertions.*;

class NullSafeTest {

    @Test
    void supplierOperationsReturnValueOrNullSafely() {
        assertEquals("value", NullSafe.nullable(() -> "value"));
        assertNull(NullSafe.nullable(() -> {
            String value = null;
            return value.trim();
        }));

        AtomicReference<NullPointerException> handled = new AtomicReference<>();
        assertNull(NullSafe.nullThen(() -> {
            String value = null;
            return value.trim();
        }, handled::set));
        assertTrue(handled.get() instanceof NullPointerException);
    }

    @Test
    void supplierOperationsUseDefaultsAndThrowConfiguredExceptions() {
        AtomicInteger defaultSupplierCalls = new AtomicInteger();

        assertEquals("fallback", NullSafe.nullDefault(() -> {
            String value = null;
            return value.trim();
        }, "fallback"));
        assertEquals("fallback", NullSafe.nullDefault(() -> {
            String value = null;
            return value.trim();
        }, () -> {
            defaultSupplierCalls.incrementAndGet();
            return "fallback";
        }));
        assertEquals(1, defaultSupplierCalls.get());

        IllegalStateException configured = new IllegalStateException("configured");
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> NullSafe.nullThrow(() -> {
            String value = null;
            return value.trim();
        }, configured));
        assertSame(configured, thrown);

        assertThrowsWithMessage(NullPointerException.class, "missing name",
                                () -> NullSafe.nullThrow(() -> {
                                    String value = null;
                                    return value.trim();
                                }, "missing {}", "name"));
    }

    @Test
    void runnableOperationsSuppressOrTranslateNullPointerExceptions() {
        AtomicInteger ran = new AtomicInteger();
        AtomicInteger handled = new AtomicInteger();

        NullSafe.nullable((Runnable) ran::incrementAndGet);
        NullSafe.nullable(() -> {
            throw new NullPointerException("ignored");
        });
        NullSafe.nullThen((Runnable) () -> {
            throw new NullPointerException("handled");
        }, exception -> handled.incrementAndGet());

        assertEquals(1, ran.get());
        assertEquals(1, handled.get());

        IllegalArgumentException configured = new IllegalArgumentException("configured");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> NullSafe.nullThrow(
                (Runnable) () -> {
                    throw new NullPointerException("wrapped");
                },
                configured));
        assertSame(configured, thrown);

        assertThrowsWithMessage(NullPointerException.class, "run failed 7",
                                () -> NullSafe.nullThrow((Runnable) () -> {
                                    throw new NullPointerException("wrapped");
                                }, "run failed {}", 7));
    }

    @Test
    void consumerOperationsHandleNullPointersFromConsumerBody() {
        AtomicReference<String> seen = new AtomicReference<>();
        AtomicInteger handled = new AtomicInteger();
        Consumer<String> trimConsumer = value -> value.trim();
        Consumer<String> recordTrimmed = value -> seen.set(value.trim());

        NullSafe.nullable(" x ", recordTrimmed);
        NullSafe.nullable((String) null, trimConsumer);
        NullSafe.nullThen((String) null, trimConsumer, exception -> handled.incrementAndGet());

        assertEquals("x", seen.get());
        assertEquals(1, handled.get());

        IllegalStateException configured = new IllegalStateException("consumer failed");
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> NullSafe.nullThrow(
                (String) null,
                trimConsumer,
                configured));
        assertSame(configured, thrown);

        assertThrowsWithMessage(NullPointerException.class, "consumer name",
                                () -> NullSafe.nullThrow((String) null, trimConsumer,
                                                         "consumer {}", "name"));
    }

    @Test
    void functionOperationsReturnNullDefaultsOrConfiguredExceptions() {
        AtomicInteger handled = new AtomicInteger();
        Function<String, Integer> length = value -> value.length();

        assertEquals(Integer.valueOf(3), NullSafe.nullable("abc", length));
        assertNull(NullSafe.nullable((String) null, length));
        assertNull(NullSafe.nullThen((String) null, length, exception -> handled.incrementAndGet()));
        assertEquals(1, handled.get());

        assertEquals(Integer.valueOf(9), NullSafe.nullDefault((String) null, length, Integer.valueOf(9)));
        assertEquals(Integer.valueOf(10), NullSafe.nullDefault((String) null, length, () -> 10));

        IllegalStateException configured = new IllegalStateException("function failed");
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> NullSafe.nullThrow(
                (String) null,
                length,
                configured));
        assertSame(configured, thrown);

        assertThrowsWithMessage(NullPointerException.class, "function name",
                                () -> NullSafe.nullThrow((String) null, length,
                                                         "function {}", "name"));
    }

    @Test
    void lambdaWrappersDeferNullSafeBehaviorUntilInvocation() {
        Supplier<String> supplier = NullSafe.NullSafeLambda.nullDefault(() -> {
            String value = null;
            return value.trim();
        }, "fallback");
        Runnable runnable = NullSafe.NullSafeLambda.nullable((Runnable) () -> {
            throw new NullPointerException("ignored");
        });
        Consumer<String> consumer = NullSafe.NullSafeLambda.nullable((Consumer<String>) value -> value.trim());
        Function<String, Integer> function =
                NullSafe.NullSafeLambda.nullDefault((Function<String, Integer>) value -> value.length(), () -> 5);

        assertEquals("fallback", supplier.get());
        assertDoesNotThrow(runnable::run);
        assertDoesNotThrow(() -> consumer.accept(null));
        assertEquals(5, function.apply(null));
    }

    @Test
    void lambdaWrapperRunnableNullThrowMethodsCanUseInstanceOverloads() {
        NullSafe.NullSafeLambda wrappers = new NullSafe.NullSafeLambda();
        IllegalArgumentException configured = new IllegalArgumentException("configured");

        Runnable withException = wrappers.nullThrow((Runnable) () -> {
            throw new NullPointerException("wrapped");
        }, configured);
        Runnable withMessage = wrappers.nullThrow((Runnable) () -> {
            throw new NullPointerException("wrapped");
        }, "message {}", 1);

        assertSame(configured, assertThrows(IllegalArgumentException.class, withException::run));
        assertThrowsWithMessage(NullPointerException.class, "message 1", withMessage::run);
    }
}
