package lambda;

/**
 * @author fengbinbin
 * @version 1.0
 * @since 2025/4/27 16:37
 */
@FunctionalInterface
public interface Setter<T, V> {
    void set(T instance, V value);
}

