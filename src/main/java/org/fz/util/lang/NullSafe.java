package org.fz.util.lang;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

/**
 * All methods in this class will  suppress null pointer exception,
 * for further operations * This class cannot completely replace Optional {@link java.util.Optional} * However,
 * some scenarios can get better code readability and conciseness than Optional
 * <p>
 * Example: NullSafe.nullDefault(() -> user.getName().getLength(), "xx")
 * <p>
 * In this case, the user object is null, and the whole method will return the specified default value "xx"
 *
 * @author fengbinbin
 * @version 1.0
 * @apiNote All Lambdas in this class are forbidden to use method references instead!!!!!
 * @since 2024/5/30 11:04
 */
@UtilityClass
public class NullSafe {

    public static <T> T nullable(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static <T> T nullDefault(Supplier<T> supplier, T nullDefaultValue) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return nullDefaultValue;
        }
    }

    public static <T> T nullDefault(Supplier<T> supplier, Supplier<T> nullDefaultValue) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return nullDefaultValue.get();
        }
    }

    public static <T> T nullThrow(Supplier<T> supplier, RuntimeException exception) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            throw exception;
        }
    }

    public static <T> T nullThrow(Supplier<T> supplier, Supplier<? extends RuntimeException> exception) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            throw exception.get();
        }
    }

    public void nullThrow(Runnable runnable, RuntimeException exception) {
        try {
            runnable.run();
        } catch (NullPointerException e) {
            throw exception;
        }
    }

    public void nullThrow(Runnable runnable, Supplier<? extends RuntimeException> exception) {
        try {
            runnable.run();
        } catch (NullPointerException e) {
            throw exception.get();
        }
    }

}
