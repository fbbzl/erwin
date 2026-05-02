package org.fz.erwin.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Exception tool class All static methods are composed of an expression and an exceptionNotice. When the expression
 * is established, an exception will be thrown with the specified exceptionNotice
 *
 * @author fengbinbin
 * @since 2017/4/2/038 11:52
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Throws {

    static final Supplier<String>
            MAP_NULL        = () -> "map is null",
            KEY_NULL        = () -> "key is null",
            VALUE_NULL      = () -> "value is null",
            COLLECTION_NULL = () -> "collection is null",
            ELEMENT_NULL    = () -> "element is null",
            ARRAY_NULL      = () -> "array is null",
            TYPE_NULL       = () -> "type can not be null",
            INSTANCED_NULL  = () -> "instanced object can not be null";

    public static void ifTrue(Object expression, Supplier<String> notice) {
        if (Objects.equals(expression, Boolean.TRUE)) throw new IllegalArgumentException(notice.get());
    }

    public static void ifFalse(Object expression, Supplier<String> notice) {
        if (Objects.equals(expression, Boolean.FALSE)) throw new IllegalArgumentException(notice.get());
    }

    public static void ifNull(Object object, Supplier<String> notice) {
        if (object == null) throw new IllegalArgumentException(notice.get());
    }

    public static void ifNotNull(Object object, Supplier<String> notice) {
        if (object != null) throw new IllegalArgumentException(notice.get());
    }

    public static void ifEmpty(Object[] array, Supplier<String> notice) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(notice.get());
    }

    public static void ifNotEmpty(Object[] array, Supplier<String> notice) {
        if (array != null && array.length > 0) throw new IllegalArgumentException(notice.get());
    }

    public static void ifEmpty(Collection<?> collection, Supplier<String> notice) {
        if (collection == null || collection.isEmpty()) throw new IllegalArgumentException(notice.get());
    }

    public static void ifEmpty(Map<?, ?> map, Supplier<String> notice) {
        if (map == null || map.isEmpty()) throw new IllegalArgumentException(notice.get());
    }

    public static void ifNotEmpty(Collection<?> collection, Supplier<String> notice) {
        if (collection != null && !collection.isEmpty()) throw new IllegalArgumentException(notice.get());
    }

    public static void ifNotEmpty(Map<?, ?> map, Supplier<String> notice) {
        if (map != null && !map.isEmpty()) throw new IllegalArgumentException(notice.get());
    }

    public static void ifEmpty(String text, Supplier<String> notice) {
        if (text == null || text.isEmpty()) throw new IllegalArgumentException(notice.get());
    }

    public static void ifNotEmpty(String text, Supplier<String> notice) {
        if (text != null && !text.isEmpty()) throw new IllegalArgumentException(notice.get());
    }

    public static void ifBlank(String text, Supplier<String> notice) {
        if (text == null || text.trim().isEmpty()) throw new IllegalArgumentException(notice.get());
    }

    public static void ifNotBlank(String text, Supplier<String> notice) {
        if (text != null && !text.trim().isEmpty()) throw new IllegalArgumentException(notice.get());
    }

    public static void ifEquals(Object l, Object r, Supplier<String> notice) {
        if (Objects.equals(l, r)) throw new IllegalArgumentException(notice.get());
    }

    public static void ifNotEquals(Object l, Object r, Supplier<String> notice) {
        if (!Objects.equals(l, r)) throw new IllegalArgumentException(notice.get());
    }


    public static <T> void ifContains(Collection<T> collection, T element, Supplier<String> notice) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        if (collection.contains(element)) throw new IllegalArgumentException(notice.get());
    }

    public static <T> void ifNotContains(Collection<T> collection, T element, Supplier<String> notice) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        if (!collection.contains(element)) throw new IllegalArgumentException(notice.get());
    }

    public static void ifContains(CharSequence origin, CharSequence target, Supplier<String> notice) {
        if (origin == null || target == null || origin.toString().contains(target))
            throw new IllegalArgumentException(notice.get());
    }

    public static void ifNotContains(CharSequence origin, CharSequence target, Supplier<String> notice) {
        if (origin == null || target == null || !origin.toString().contains(target))
            throw new IllegalArgumentException(notice.get());
    }

    public static <K, V> void ifContainsKey(Map<K, V> map, K key, Supplier<String> notice) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        if (map.containsKey(key)) throw new IllegalArgumentException(notice.get());
    }

    public static <K, V> void ifNotContainsKey(Map<K, V> map, K key, Supplier<String> notice) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        if (!map.containsKey(key)) throw new IllegalArgumentException(notice.get());
    }

    public static <K, V> void ifContainsValue(Map<K, V> map, V value, Supplier<String> notice) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        if (map.containsValue(value)) throw new IllegalArgumentException(notice.get());
    }

    public static <K, V> void ifNotContainsValue(Map<K, V> map, V value, Supplier<String> notice) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        if (!map.containsValue(value)) throw new IllegalArgumentException(notice.get());
    }

    public static <T> void ifInstanceOf(Class<?> type, T object, Supplier<String> notice) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        if (type.isInstance(object)) throw new IllegalArgumentException(notice.get());
    }

    public static <T> void ifNotInstanceOf(Class<?> type, T object, Supplier<String> notice) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        if (!type.isInstance(object)) throw new IllegalArgumentException(notice.get());
    }

    public static <T> void ifHasNullElement(Collection<T> collection, Supplier<String> notice) {
        Throws.ifNull(collection, COLLECTION_NULL);

        for (T t : collection) Throws.ifNull(t, notice);
    }

    public static <T> void ifHasNullElement(T[] array, Supplier<String> notice) {
        Throws.ifNull(array, ARRAY_NULL);

        for (T t : array) Throws.ifNull(t, notice);
    }

    public static void ifAssignable(Class<?> superType, Class<?> subType, Supplier<String> notice) {
        if (superType == null || subType == null || superType.isAssignableFrom(subType))
            throw new IllegalArgumentException(notice.get());
    }

    public static void ifNotAssignable(Class<?> superType, Class<?> subType, Supplier<String> notice) {
        if (superType == null || subType == null || !superType.isAssignableFrom(subType))
            throw new IllegalArgumentException(notice.get());
    }

}
