package lambda;

/**
 * @author fengbinbin
 * @version 1.0
 * @since 2025/4/27 16:37
 */
@FunctionalInterface
public interface Getter<T, V> {
    V get(T instance);
}