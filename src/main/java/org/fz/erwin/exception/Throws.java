package org.fz.erwin.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.hutool.core.text.CharSequenceUtil.format;

/**
 * Exception tool class All static methods are composed of an expression and an exceptionNotice. When the expression
 * is established, an exception will be thrown with the specified exceptionNotice
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

    private static void throwIf(boolean expression, Supplier<String> notice) {
        if (expression) throw new IllegalArgumentException(notice.get());
    }

    public static void ifTrue(Object expression, Supplier<String> notice) {
        throwIf(Objects.equals(expression, Boolean.TRUE), notice);
    }

    public static void ifTrue(Object expression, String notice, Object... params) {
        if (Objects.equals(expression, Boolean.TRUE)) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifFalse(Object expression, Supplier<String> notice) {
        throwIf(Objects.equals(expression, Boolean.FALSE), notice);
    }

    public static void ifFalse(Object expression, String notice, Object... params) {
        if (Objects.equals(expression, Boolean.FALSE)) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNull(Object object, Supplier<String> notice) {
        throwIf(object == null, notice);
    }

    public static void ifNull(Object object, String notice, Object... params) {
        if (object == null) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNotNull(Object object, Supplier<String> notice) {
        throwIf(object != null, notice);
    }

    public static void ifNotNull(Object object, String notice, Object... params) {
        if (object != null) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifEmpty(Object[] array, Supplier<String> notice) {
        throwIf(array == null || array.length == 0, notice);
    }

    public static void ifEmpty(Object[] array, String notice, Object... params) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNotEmpty(Object[] array, Supplier<String> notice) {
        throwIf(array != null && array.length > 0, notice);
    }

    public static void ifNotEmpty(Object[] array, String notice, Object... params) {
        if (array != null && array.length > 0) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifEmpty(Collection<?> collection, Supplier<String> notice) {
        throwIf(collection == null || collection.isEmpty(), notice);
    }

    public static void ifEmpty(Collection<?> collection, String notice, Object... params) {
        if (collection == null || collection.isEmpty()) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifEmpty(Map<?, ?> map, Supplier<String> notice) {
        throwIf(map == null || map.isEmpty(), notice);
    }

    public static void ifEmpty(Map<?, ?> map, String notice, Object... params) {
        if (map == null || map.isEmpty()) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNotEmpty(Collection<?> collection, Supplier<String> notice) {
        throwIf(collection != null && !collection.isEmpty(), notice);
    }

    public static void ifNotEmpty(Collection<?> collection, String notice, Object... params) {
        if (collection != null && !collection.isEmpty()) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNotEmpty(Map<?, ?> map, Supplier<String> notice) {
        throwIf(map != null && !map.isEmpty(), notice);
    }

    public static void ifNotEmpty(Map<?, ?> map, String notice, Object... params) {
        if (map != null && !map.isEmpty()) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifEmpty(String text, Supplier<String> notice) {
        throwIf(text == null || text.isEmpty(), notice);
    }

    public static void ifEmpty(String text, String notice, Object... params) {
        if (text == null || text.isEmpty()) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNotEmpty(String text, Supplier<String> notice) {
        throwIf(text != null && !text.isEmpty(), notice);
    }

    public static void ifNotEmpty(String text, String notice, Object... params) {
        if (text != null && !text.isEmpty()) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifBlank(String text, Supplier<String> notice) {
        throwIf(text == null || text.trim().isEmpty(), notice);
    }

    public static void ifBlank(String text, String notice, Object... params) {
        if (text == null || text.trim().isEmpty()) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNotBlank(String text, Supplier<String> notice) {
        throwIf(text != null && !text.trim().isEmpty(), notice);
    }

    public static void ifNotBlank(String text, String notice, Object... params) {
        if (text != null && !text.trim().isEmpty()) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifEquals(Object l, Object r, Supplier<String> notice) {
        throwIf(Objects.equals(l, r), notice);
    }

    public static void ifEquals(Object l, Object r, String notice, Object... params) {
        if (Objects.equals(l, r)) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNotEquals(Object l, Object r, Supplier<String> notice) {
        throwIf(!Objects.equals(l, r), notice);
    }

    public static void ifNotEquals(Object l, Object r, String notice, Object... params) {
        if (!Objects.equals(l, r)) throw new IllegalArgumentException(format(notice, params));
    }


    public static <T> void ifContains(Collection<T> collection, T element, Supplier<String> notice) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        throwIf(collection.contains(element), notice);
    }

    public static <T> void ifContains(Collection<T> collection, T element, String notice, Object... params) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        if (collection.contains(element)) throw new IllegalArgumentException(format(notice, params));
    }

    public static <T> void ifNotContains(Collection<T> collection, T element, Supplier<String> notice) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        throwIf(!collection.contains(element), notice);
    }

    public static <T> void ifNotContains(Collection<T> collection, T element, String notice, Object... params) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        if (!collection.contains(element)) throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifContains(CharSequence origin, CharSequence target, Supplier<String> notice) {
        throwIf(origin == null || target == null || origin.toString().contains(target), notice);
    }

    public static void ifContains(CharSequence origin, CharSequence target, String notice, Object... params) {
        if (origin == null || target == null || origin.toString().contains(target))
            throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNotContains(CharSequence origin, CharSequence target, Supplier<String> notice) {
        throwIf(origin == null || target == null || !origin.toString().contains(target), notice);
    }

    public static void ifNotContains(CharSequence origin, CharSequence target, String notice, Object... params) {
        if (origin == null || target == null || !origin.toString().contains(target))
            throw new IllegalArgumentException(format(notice, params));
    }

    public static <K, V> void ifContainsKey(Map<K, V> map, K key, Supplier<String> notice) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        throwIf(map.containsKey(key), notice);
    }

    public static <K, V> void ifContainsKey(Map<K, V> map, K key, String notice, Object... params) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        if (map.containsKey(key)) throw new IllegalArgumentException(format(notice, params));
    }

    public static <K, V> void ifNotContainsKey(Map<K, V> map, K key, Supplier<String> notice) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        throwIf(!map.containsKey(key), notice);
    }

    public static <K, V> void ifNotContainsKey(Map<K, V> map, K key, String notice, Object... params) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        if (!map.containsKey(key)) throw new IllegalArgumentException(format(notice, params));
    }

    public static <K, V> void ifContainsValue(Map<K, V> map, V value, Supplier<String> notice) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        throwIf(map.containsValue(value), notice);
    }

    public static <K, V> void ifContainsValue(Map<K, V> map, V value, String notice, Object... params) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        if (map.containsValue(value)) throw new IllegalArgumentException(format(notice, params));
    }

    public static <K, V> void ifNotContainsValue(Map<K, V> map, V value, Supplier<String> notice) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        throwIf(!map.containsValue(value), notice);
    }

    public static <K, V> void ifNotContainsValue(Map<K, V> map, V value, String notice, Object... params) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        if (!map.containsValue(value)) throw new IllegalArgumentException(format(notice, params));
    }

    public static <T> void ifInstanceOf(Class<?> type, T object, Supplier<String> notice) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        throwIf(type.isInstance(object), notice);
    }

    public static <T> void ifInstanceOf(Class<?> type, T object, String notice, Object... params) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        if (type.isInstance(object)) throw new IllegalArgumentException(format(notice, params));
    }

    public static <T> void ifNotInstanceOf(Class<?> type, T object, Supplier<String> notice) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        throwIf(!type.isInstance(object), notice);
    }

    public static <T> void ifNotInstanceOf(Class<?> type, T object, String notice, Object... params) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        if (!type.isInstance(object)) throw new IllegalArgumentException(format(notice, params));
    }

    public static <T> void ifHasNullElement(Collection<T> collection, Supplier<String> notice) {
        Throws.ifNull(collection, COLLECTION_NULL);

        for (T t : collection) Throws.ifNull(t, notice);
    }

    public static <T> void ifHasNullElement(Collection<T> collection, String notice, Object... params) {
        Throws.ifNull(collection, COLLECTION_NULL);

        for (T t : collection) Throws.ifNull(t, notice, params);
    }

    public static <T> void ifHasNullElement(T[] array, Supplier<String> notice) {
        Throws.ifNull(array, ARRAY_NULL);

        for (T t : array) Throws.ifNull(t, notice);
    }

    public static <T> void ifHasNullElement(T[] array, String notice, Object... params) {
        Throws.ifNull(array, ARRAY_NULL);

        for (T t : array) Throws.ifNull(t, notice, params);
    }

    public static void ifAssignable(Class<?> superType, Class<?> subType, Supplier<String> notice) {
        throwIf(superType == null || subType == null || superType.isAssignableFrom(subType), notice);
    }

    public static void ifAssignable(Class<?> superType, Class<?> subType, String notice, Object... params) {
        if (superType == null || subType == null || superType.isAssignableFrom(subType))
            throw new IllegalArgumentException(format(notice, params));
    }

    public static void ifNotAssignable(Class<?> superType, Class<?> subType, Supplier<String> notice) {
        throwIf(superType == null || subType == null || !superType.isAssignableFrom(subType), notice);
    }

    public static void ifNotAssignable(Class<?> superType, Class<?> subType, String notice, Object... params) {
        if (superType == null || subType == null || !superType.isAssignableFrom(subType))
            throw new IllegalArgumentException(format(notice, params));
    }

}
