package org.fz.erwin.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.hutool.core.text.CharSequenceUtil.format;

/**
 * Exception tool class All static methods are composed of an expression and an exceptionMessage. When the expression
 * is established, an exception will be thrown with the specified exceptionMessage
 *
 * @author fengbinbin
 * @since 2017/4/2/038 11:52
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Throws {

    static final String
            MAP_NULL        = "map is null",
            KEY_NULL        = "key is null",
            VALUE_NULL      = "value is null",
            COLLECTION_NULL = "collection is null",
            ELEMENT_NULL    = "element is null",
            ARRAY_NULL      = "array is null",
            TYPE_NULL       = "type can not be null",
            INSTANCED_NULL  = "instanced object can not be null";

    @FunctionalInterface
    public interface MessageSupplier extends Supplier<String> {
    }

    @FunctionalInterface
    public interface ExceptionSupplier<E extends RuntimeException> {

        E get();
    }

    private static void throwIf(boolean expression, MessageSupplier message) {
        if (expression) throw new IllegalArgumentException(message.get());
    }

    private static void throwIf(boolean expression, ExceptionSupplier<? extends RuntimeException> exception) {
        if (expression) throw Objects.requireNonNull(exception.get(), "exception can not be null");
    }

    public static void ifTrue(Object expression, MessageSupplier message) {
        throwIf(Objects.equals(expression, Boolean.TRUE), message);
    }

    public static void ifTrue(Object expression, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(Objects.equals(expression, Boolean.TRUE), exception);
    }

    public static void ifTrue(Object expression, String message, Object... params) {
        if (Objects.equals(expression, Boolean.TRUE)) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifFalse(Object expression, MessageSupplier message) {
        throwIf(Objects.equals(expression, Boolean.FALSE), message);
    }

    public static void ifFalse(Object expression, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(Objects.equals(expression, Boolean.FALSE), exception);
    }

    public static void ifFalse(Object expression, String message, Object... params) {
        if (Objects.equals(expression, Boolean.FALSE)) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNull(Object object, MessageSupplier message) {
        throwIf(object == null, message);
    }

    public static void ifNull(Object object, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(object == null, exception);
    }

    public static void ifNull(Object object, String message, Object... params) {
        if (object == null) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNotNull(Object object, MessageSupplier message) {
        throwIf(object != null, message);
    }

    public static void ifNotNull(Object object, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(object != null, exception);
    }

    public static void ifNotNull(Object object, String message, Object... params) {
        if (object != null) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifEmpty(Object[] array, MessageSupplier message) {
        throwIf(array == null || array.length == 0, message);
    }

    public static void ifEmpty(Object[] array, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(array == null || array.length == 0, exception);
    }

    public static void ifEmpty(Object[] array, String message, Object... params) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNotEmpty(Object[] array, MessageSupplier message) {
        throwIf(array != null && array.length > 0, message);
    }

    public static void ifNotEmpty(Object[] array, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(array != null && array.length > 0, exception);
    }

    public static void ifNotEmpty(Object[] array, String message, Object... params) {
        if (array != null && array.length > 0) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifEmpty(Collection<?> collection, MessageSupplier message) {
        throwIf(collection == null || collection.isEmpty(), message);
    }

    public static void ifEmpty(Collection<?> collection, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(collection == null || collection.isEmpty(), exception);
    }

    public static void ifEmpty(Collection<?> collection, String message, Object... params) {
        if (collection == null || collection.isEmpty()) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifEmpty(Map<?, ?> map, MessageSupplier message) {
        throwIf(map == null || map.isEmpty(), message);
    }

    public static void ifEmpty(Map<?, ?> map, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(map == null || map.isEmpty(), exception);
    }

    public static void ifEmpty(Map<?, ?> map, String message, Object... params) {
        if (map == null || map.isEmpty()) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNotEmpty(Collection<?> collection, MessageSupplier message) {
        throwIf(collection != null && !collection.isEmpty(), message);
    }

    public static void ifNotEmpty(Collection<?> collection, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(collection != null && !collection.isEmpty(), exception);
    }

    public static void ifNotEmpty(Collection<?> collection, String message, Object... params) {
        if (collection != null && !collection.isEmpty()) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNotEmpty(Map<?, ?> map, MessageSupplier message) {
        throwIf(map != null && !map.isEmpty(), message);
    }

    public static void ifNotEmpty(Map<?, ?> map, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(map != null && !map.isEmpty(), exception);
    }

    public static void ifNotEmpty(Map<?, ?> map, String message, Object... params) {
        if (map != null && !map.isEmpty()) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifEmpty(String text, MessageSupplier message) {
        throwIf(text == null || text.isEmpty(), message);
    }

    public static void ifEmpty(String text, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(text == null || text.isEmpty(), exception);
    }

    public static void ifEmpty(String text, String message, Object... params) {
        if (text == null || text.isEmpty()) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNotEmpty(String text, MessageSupplier message) {
        throwIf(text != null && !text.isEmpty(), message);
    }

    public static void ifNotEmpty(String text, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(text != null && !text.isEmpty(), exception);
    }

    public static void ifNotEmpty(String text, String message, Object... params) {
        if (text != null && !text.isEmpty()) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifBlank(String text, MessageSupplier message) {
        throwIf(text == null || text.trim().isEmpty(), message);
    }

    public static void ifBlank(String text, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(text == null || text.trim().isEmpty(), exception);
    }

    public static void ifBlank(String text, String message, Object... params) {
        if (text == null || text.trim().isEmpty()) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNotBlank(String text, MessageSupplier message) {
        throwIf(text != null && !text.trim().isEmpty(), message);
    }

    public static void ifNotBlank(String text, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(text != null && !text.trim().isEmpty(), exception);
    }

    public static void ifNotBlank(String text, String message, Object... params) {
        if (text != null && !text.trim().isEmpty()) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifEquals(Object l, Object r, MessageSupplier message) {
        throwIf(Objects.equals(l, r), message);
    }

    public static void ifEquals(Object l, Object r, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(Objects.equals(l, r), exception);
    }

    public static void ifEquals(Object l, Object r, String message, Object... params) {
        if (Objects.equals(l, r)) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNotEquals(Object l, Object r, MessageSupplier message) {
        throwIf(!Objects.equals(l, r), message);
    }

    public static void ifNotEquals(Object l, Object r, ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(!Objects.equals(l, r), exception);
    }

    public static void ifNotEquals(Object l, Object r, String message, Object... params) {
        if (!Objects.equals(l, r)) throw new IllegalArgumentException(format(message, params));
    }


    public static <T> void ifContains(Collection<T> collection, T element, MessageSupplier message) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        throwIf(collection.contains(element), message);
    }

    public static <T> void ifContains(Collection<T> collection, T element,
                                      ExceptionSupplier<? extends RuntimeException> exception) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        throwIf(collection.contains(element), exception);
    }

    public static <T> void ifContains(Collection<T> collection, T element, String message, Object... params) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        if (collection.contains(element)) throw new IllegalArgumentException(format(message, params));
    }

    public static <T> void ifNotContains(Collection<T> collection, T element, MessageSupplier message) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        throwIf(!collection.contains(element), message);
    }

    public static <T> void ifNotContains(Collection<T> collection, T element,
                                         ExceptionSupplier<? extends RuntimeException> exception) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        throwIf(!collection.contains(element), exception);
    }

    public static <T> void ifNotContains(Collection<T> collection, T element, String message, Object... params) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        if (!collection.contains(element)) throw new IllegalArgumentException(format(message, params));
    }

    public static void ifContains(CharSequence origin, CharSequence target, MessageSupplier message) {
        throwIf(origin == null || target == null || origin.toString().contains(target), message);
    }

    public static void ifContains(CharSequence origin, CharSequence target,
                                  ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(origin == null || target == null || origin.toString().contains(target), exception);
    }

    public static void ifContains(CharSequence origin, CharSequence target, String message, Object... params) {
        if (origin == null || target == null || origin.toString().contains(target))
            throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNotContains(CharSequence origin, CharSequence target, MessageSupplier message) {
        throwIf(origin == null || target == null || !origin.toString().contains(target), message);
    }

    public static void ifNotContains(CharSequence origin, CharSequence target,
                                     ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(origin == null || target == null || !origin.toString().contains(target), exception);
    }

    public static void ifNotContains(CharSequence origin, CharSequence target, String message, Object... params) {
        if (origin == null || target == null || !origin.toString().contains(target))
            throw new IllegalArgumentException(format(message, params));
    }

    public static <K, V> void ifContainsKey(Map<K, V> map, K key, MessageSupplier message) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        throwIf(map.containsKey(key), message);
    }

    public static <K, V> void ifContainsKey(Map<K, V> map, K key,
                                            ExceptionSupplier<? extends RuntimeException> exception) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        throwIf(map.containsKey(key), exception);
    }

    public static <K, V> void ifContainsKey(Map<K, V> map, K key, String message, Object... params) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        if (map.containsKey(key)) throw new IllegalArgumentException(format(message, params));
    }

    public static <K, V> void ifNotContainsKey(Map<K, V> map, K key, MessageSupplier message) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        throwIf(!map.containsKey(key), message);
    }

    public static <K, V> void ifNotContainsKey(Map<K, V> map, K key,
                                               ExceptionSupplier<? extends RuntimeException> exception) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        throwIf(!map.containsKey(key), exception);
    }

    public static <K, V> void ifNotContainsKey(Map<K, V> map, K key, String message, Object... params) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        if (!map.containsKey(key)) throw new IllegalArgumentException(format(message, params));
    }

    public static <K, V> void ifContainsValue(Map<K, V> map, V value, MessageSupplier message) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        throwIf(map.containsValue(value), message);
    }

    public static <K, V> void ifContainsValue(Map<K, V> map, V value,
                                              ExceptionSupplier<? extends RuntimeException> exception) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        throwIf(map.containsValue(value), exception);
    }

    public static <K, V> void ifContainsValue(Map<K, V> map, V value, String message, Object... params) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        if (map.containsValue(value)) throw new IllegalArgumentException(format(message, params));
    }

    public static <K, V> void ifNotContainsValue(Map<K, V> map, V value, MessageSupplier message) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        throwIf(!map.containsValue(value), message);
    }

    public static <K, V> void ifNotContainsValue(Map<K, V> map, V value,
                                                 ExceptionSupplier<? extends RuntimeException> exception) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        throwIf(!map.containsValue(value), exception);
    }

    public static <K, V> void ifNotContainsValue(Map<K, V> map, V value, String message, Object... params) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        if (!map.containsValue(value)) throw new IllegalArgumentException(format(message, params));
    }

    public static <T> void ifInstanceOf(Class<?> type, T object, MessageSupplier message) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        throwIf(type.isInstance(object), message);
    }

    public static <T> void ifInstanceOf(Class<?> type, T object,
                                        ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        throwIf(type.isInstance(object), exception);
    }

    public static <T> void ifInstanceOf(Class<?> type, T object, String message, Object... params) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        if (type.isInstance(object)) throw new IllegalArgumentException(format(message, params));
    }

    public static <T> void ifNotInstanceOf(Class<?> type, T object, MessageSupplier message) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        throwIf(!type.isInstance(object), message);
    }

    public static <T> void ifNotInstanceOf(Class<?> type, T object,
                                           ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        throwIf(!type.isInstance(object), exception);
    }

    public static <T> void ifNotInstanceOf(Class<?> type, T object, String message, Object... params) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        if (!type.isInstance(object)) throw new IllegalArgumentException(format(message, params));
    }

    public static <T> void ifHasNullElement(Collection<T> collection, MessageSupplier message) {
        Throws.ifNull(collection, COLLECTION_NULL);

        for (T t : collection) Throws.ifNull(t, message);
    }

    public static <T> void ifHasNullElement(Collection<T> collection,
                                            ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifNull(collection, COLLECTION_NULL);

        for (T t : collection) Throws.ifNull(t, exception);
    }

    public static <T> void ifHasNullElement(Collection<T> collection, String message, Object... params) {
        Throws.ifNull(collection, COLLECTION_NULL);

        for (T t : collection) Throws.ifNull(t, message, params);
    }

    public static <T> void ifHasNullElement(T[] array, MessageSupplier message) {
        Throws.ifNull(array, ARRAY_NULL);

        for (T t : array) Throws.ifNull(t, message);
    }

    public static <T> void ifHasNullElement(T[] array, ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifNull(array, ARRAY_NULL);

        for (T t : array) Throws.ifNull(t, exception);
    }

    public static <T> void ifHasNullElement(T[] array, String message, Object... params) {
        Throws.ifNull(array, ARRAY_NULL);

        for (T t : array) Throws.ifNull(t, message, params);
    }

    public static void ifAssignable(Class<?> superType, Class<?> subType, MessageSupplier message) {
        throwIf(superType == null || subType == null || superType.isAssignableFrom(subType), message);
    }

    public static void ifAssignable(Class<?> superType, Class<?> subType,
                                    ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(superType == null || subType == null || superType.isAssignableFrom(subType), exception);
    }

    public static void ifAssignable(Class<?> superType, Class<?> subType, String message, Object... params) {
        if (superType == null || subType == null || superType.isAssignableFrom(subType))
            throw new IllegalArgumentException(format(message, params));
    }

    public static void ifNotAssignable(Class<?> superType, Class<?> subType, MessageSupplier message) {
        throwIf(superType == null || subType == null || !superType.isAssignableFrom(subType), message);
    }

    public static void ifNotAssignable(Class<?> superType, Class<?> subType,
                                       ExceptionSupplier<? extends RuntimeException> exception) {
        throwIf(superType == null || subType == null || !superType.isAssignableFrom(subType), exception);
    }

    public static void ifNotAssignable(Class<?> superType, Class<?> subType, String message, Object... params) {
        if (superType == null || subType == null || !superType.isAssignableFrom(subType))
            throw new IllegalArgumentException(format(message, params));
    }

}
