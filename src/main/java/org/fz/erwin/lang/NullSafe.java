package org.fz.erwin.lang;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;
import java.util.function.Function;
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

    //***************************************       nullable supplier start      *************************************//

    public static <T> T nullable(Supplier<T> supplier) {
        return nullThen(supplier, null);
    }

    public static <T> T nullThen(Supplier<T> supplier, Consumer<NullPointerException> handleNull) {
        try {return supplier.get();}
        catch (NullPointerException nullPoint) {
            if (handleNull != null) {handleNull.accept(nullPoint);}
            return null;
        }
    }

    public static <T> T nullDefault(Supplier<T> supplier, T defaultValue) {
        try {return supplier.get();} catch (NullPointerException nullPoint) {return defaultValue;}
    }

    public static <T> T nullDefault(Supplier<T> supplier, Supplier<T> defaultValue) {
        try {return supplier.get();} catch (NullPointerException nullPoint) {return defaultValue.get();}
    }

    public static <T> T nullThrow(Supplier<T> supplier, RuntimeException exception) {
        try {return supplier.get();} catch (NullPointerException e) {throw exception;}
    }

    public static <T> T nullThrow(Supplier<T> supplier, Supplier<RuntimeException> exception) {
        try {return supplier.get();} catch (NullPointerException e) {throw exception.get();}
    }

    //***************************************       nullable runnable start      *************************************//

    public static void nullable(Runnable runnable) {
        nullThen(runnable, null);
    }

    public static void nullThen(Runnable runnable, Consumer<NullPointerException> handleNull) {
        try {runnable.run();}
        catch (NullPointerException nullPoint) {
            if (handleNull != null) {handleNull.accept(nullPoint);}
        }
    }

    public static void nullThrow(Runnable runnable, RuntimeException exception) {
        try {runnable.run();} catch (NullPointerException nullPoint) {throw exception;}
    }

    public static void nullThrow(Runnable runnable, Supplier<RuntimeException> exception) {
        try {runnable.run();} catch (NullPointerException nullPoint) {throw exception.get();}
    }

    //***************************************       nullable consumer start      *************************************//

    public static <T> void nullable(T arg, Consumer<T> consumer) {
        nullThen(arg, consumer, null);
    }

    public static <T> void nullThen(T arg, Consumer<T> consumer,
                                    Consumer<NullPointerException> handleNull) {
        try {consumer.accept(arg);}
        catch (NullPointerException nullPoint) {
            if (handleNull != null) {handleNull.accept(nullPoint);}
        }
    }

    public static <T> void nullThrow(T arg, Consumer<T> consumer, RuntimeException exception) {
        try {consumer.accept(arg);} catch (NullPointerException nullPoint) {throw exception;}
    }

    public static <T> void nullThrow(T arg, Consumer<T> consumer, Supplier<RuntimeException> exception) {
        try {consumer.accept(arg);} catch (NullPointerException nullPoint) {throw exception.get();}
    }

    //***************************************       nullable function start      *************************************//

    public static <T, R> R nullable(T arg, Function<T, R> fn) {
        return nullThen(arg, fn, null);
    }

    public static <T, R> R nullThen(T arg, Function<T, R> fn,
                                    Consumer<NullPointerException> handleNull) {
        try {return fn.apply(arg);}
        catch (NullPointerException nullPoint) {
            if (handleNull != null) {handleNull.accept(nullPoint);}
            return null;
        }
    }

    public static <T, R> R nullDefault(T arg, Function<T, R> fn, R defaultValue) {
        try {return fn.apply(arg);} catch (NullPointerException nullPoint) {return defaultValue;}
    }

    public static <T, R> R nullDefault(T arg, Function<T, R> fn, Supplier<R> defaultValue) {
        try {return fn.apply(arg);} catch (NullPointerException nullPoint) {return defaultValue.get();}
    }

    public static <T, R> R nullThrow(T arg, Function<T, R> fn, RuntimeException exception) {
        try {return fn.apply(arg);} catch (NullPointerException nullPoint) {throw exception;}
    }

    public static <T, R> R nullThrow(T arg, Function<T, R> fn, Supplier<RuntimeException> exception) {
        try {return fn.apply(arg);} catch (NullPointerException nullPoint) {throw exception.get();}
    }


    /**
     *wrap the lambda and handle null pointer exceptions
     */
    public final static class NullSafeLambda {

        public static <T> Supplier<T> nullable(Supplier<T> supplier) {
            return nullThen(supplier, null);
        }

        public static <T> Supplier<T> nullThen(Supplier<T> supplier,
                                               Consumer<NullPointerException> handleNull) {
            return () -> NullSafe.nullThen(supplier, handleNull);
        }

        public static <T> Supplier<T> nullDefault(Supplier<T> supplier, T defaultValue) {
            return () -> NullSafe.nullDefault(supplier, defaultValue);
        }

        public static <T> Supplier<T> nullDefault(Supplier<T> supplier, Supplier<T> defaultValue) {
            return () -> NullSafe.nullDefault(supplier, defaultValue);
        }

        public static <T> Supplier<T> nullThrow(Supplier<T> supplier, Supplier<RuntimeException> exception) {
            return () -> NullSafe.nullThrow(supplier, exception);
        }

        public static <T> Supplier<T> nullThrow(Supplier<T> supplier, RuntimeException exception) {
            return () -> NullSafe.nullThrow(supplier, exception);
        }

        public static Runnable nullable(Runnable runnable) {
            return nullThen(runnable, null);
        }

        public static Runnable nullThen(Runnable runnable, Consumer<NullPointerException> handleNull) {
            return () -> NullSafe.nullThen(runnable, handleNull);
        }

        public Runnable nullThrow(Runnable runnable, Supplier<RuntimeException> exception) {
            return () -> NullSafe.nullThrow(runnable, exception);
        }

        public Runnable nullThrow(Runnable runnable, RuntimeException exception) {
            return () -> NullSafe.nullThrow(runnable, exception);
        }

        public static <T> Consumer<T> nullable(Consumer<T> consumer) {
            return nullThen(consumer, null);
        }

        public static <T> Consumer<T> nullThen(Consumer<T> consumer,
                                               Consumer<NullPointerException> handleNull) {
            return t -> NullSafe.nullThen(t, consumer, handleNull);
        }

        public static <T> Consumer<T> nullThrow(Consumer<T> consumer, RuntimeException exception) {
            return t -> NullSafe.nullThrow(t, consumer, exception);
        }

        public static <T> Consumer<T> nullThrow(Consumer<T> consumer, Supplier<RuntimeException> exception) {
            return t -> NullSafe.nullThrow(t, consumer, exception);
        }

        public static <T, R> Function<T, R> nullable(Function<T, R> fn) {
            return nullThen(fn, null);
        }

        public static <T, R> Function<T, R> nullThen(Function<T, R> fn,
                                                     Consumer<NullPointerException> handleNull) {
            return t -> NullSafe.nullThen(t, fn, handleNull);
        }

        public static <T, R> Function<T, R> nullDefault(Function<T, R> fn, R defaultValue) {
            return t -> NullSafe.nullDefault(t, fn, defaultValue);
        }

        public static <T, R> Function<T, R> nullDefault(Function<T, R> fn, Supplier<R> defaultValue) {
            return t -> NullSafe.nullDefault(t, fn, defaultValue);
        }

        public static <T, R> Function<T, R> nullThrow(Function<T, R> fn, RuntimeException exception) {
            return t -> NullSafe.nullThrow(t, fn, exception);
        }

        public static <T, R> Function<T, R> nullThrow(Function<T, R> fn, Supplier<RuntimeException> exception) {
            return t -> NullSafe.nullThrow(t, fn, exception);
        }
    }

}