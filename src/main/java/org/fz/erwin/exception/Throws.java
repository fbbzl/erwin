package org.fz.erwin.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Exception tool class All static methods are composed of an expression and an exceptionNotice. When the expression
 * is established, an exception will be
 * thrown with the specified exceptionNotice
 *
 * @author fengbinbin
 * @since 2017/4/2/038 11:52
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Throws {

    static final NoticeSupplier
            MAP_NULL        = () -> "map is null",
            KEY_NULL        = () -> "key is null",
            VALUE_NULL      = () -> "value is null",
            COLLECTION_NULL = () -> "collection is null",
            ELEMENT_NULL    = () -> "element is null",
            ARRAY_NULL      = () -> "array is null",
            TYPE_NULL       = () -> "type can not be null",
            INSTANCED_NULL  = () -> "instanced object can not be null";

    public static void ifTrue(Object expression, Supplier<? extends RuntimeException> exception) {
        if (Objects.equals(expression, Boolean.TRUE)) throw exception.get();
    }

    public static void ifTrue(Object expression, NoticeSupplier notice) {
        ifTrue(expression, () -> new RuntimeException(notice.get()));
    }

    public static void ifFalse(Object expression, Supplier<? extends RuntimeException> exception) {
        if (Objects.equals(expression, Boolean.FALSE)) throw exception.get();
    }

    public static void ifFalse(Object expression, NoticeSupplier notice) {
        ifFalse(expression, () -> new RuntimeException(notice.get()));
    }

    public static void ifNull(Object object, Supplier<? extends RuntimeException> exception) {
        if (object == null) throw exception.get();
    }

    public static void ifNull(Object object, NoticeSupplier notice) {
        ifNull(object, () -> new RuntimeException(notice.get()));
    }

    public static void ifNotNull(Object object, Supplier<? extends RuntimeException> exception) {
        if (object != null) throw exception.get();
    }

    public static void ifNotNull(Object object, NoticeSupplier notice) {
        ifNotNull(object, () -> new RuntimeException(notice.get()));
    }

    public static void ifEmpty(Object[] array, Supplier<? extends RuntimeException> exception) {
        if (array == null || array.length == 0) throw exception.get();
    }

    public static void ifEmpty(Object[] array, NoticeSupplier notice) {
        ifEmpty(array, () -> new RuntimeException(notice.get()));
    }

    public static void ifNotEmpty(Object[] array, Supplier<? extends RuntimeException> exception) {
        if (array != null && array.length > 0) throw exception.get();
    }

    public static void ifNotEmpty(Object[] array, NoticeSupplier notice) {
        ifNotEmpty(array, () -> new RuntimeException(notice.get()));
    }

    public static void ifEmpty(Collection<?> collection, Supplier<? extends RuntimeException> exception) {
        if (collection == null || collection.isEmpty()) throw exception.get();
    }

    public static void ifEmpty(Collection<?> collection, NoticeSupplier notice) {
        ifEmpty(collection, () -> new RuntimeException(notice.get()));
    }

    public static void ifEmpty(Map<?, ?> map, Supplier<? extends RuntimeException> exception) {
        if (map == null || map.isEmpty()) throw exception.get();
    }

    public static void ifEmpty(Map<?, ?> map, NoticeSupplier notice) {
        ifEmpty(map, () -> new RuntimeException(notice.get()));
    }

    public static void ifNotEmpty(Collection<?> collection, Supplier<? extends RuntimeException> exception) {
        if (collection != null && !collection.isEmpty()) throw exception.get();
    }

    public static void ifNotEmpty(Collection<?> collection, NoticeSupplier notice) {
        ifNotEmpty(collection, () -> new RuntimeException(notice.get()));
    }

    public static void ifNotEmpty(Map<?, ?> map, Supplier<? extends RuntimeException> exception) {
        if (map != null && !map.isEmpty()) throw exception.get();
    }

    public static void ifNotEmpty(Map<?, ?> map, NoticeSupplier notice) {
        ifNotEmpty(map, () -> new RuntimeException(notice.get()));
    }

    public static void ifEmpty(String text, Supplier<? extends RuntimeException> exception) {
        if (text == null || text.isEmpty()) throw exception.get();
    }

    public static void ifEmpty(String text, NoticeSupplier notice) {
        ifEmpty(text, () -> new RuntimeException(notice.get()));
    }

    public static void ifNotEmpty(String text, Supplier<? extends RuntimeException> exception) {
        if (text != null && !text.isEmpty()) throw exception.get();
    }

    public static void ifNotEmpty(String text, NoticeSupplier notice) {
        ifNotEmpty(text, () -> new RuntimeException(notice.get()));
    }

    public static void ifBlank(String text, Supplier<? extends RuntimeException> exception) {
        if (text == null || text.trim().isEmpty()) throw exception.get();
    }

    public static void ifBlank(String text, NoticeSupplier notice) {
        ifBlank(text, () -> new RuntimeException(notice.get()));
    }

    public static void ifNotBlank(String text, Supplier<? extends RuntimeException> exception) {
        if (text != null && !text.trim().isEmpty()) throw exception.get();
    }

    public static void ifNotBlank(String text, NoticeSupplier notice) {
        ifNotBlank(text, () -> new RuntimeException(notice.get()));
    }

    public static void ifEquals(Object l, Object r, Supplier<? extends RuntimeException> exception) {
        if (Objects.equals(l, r)) throw exception.get();
    }

    public static void ifEquals(Object l, Object r, NoticeSupplier notice) {
        ifEquals(l, r, () -> new RuntimeException(notice.get()));
    }

    public static void ifNotEquals(Object l, Object r, Supplier<? extends RuntimeException> exception) {
        if (!Objects.equals(l, r)) throw exception.get();
    }

    public static void ifNotEquals(Object l, Object r, NoticeSupplier notice) {
        ifNotEquals(l, r, () -> new RuntimeException(notice.get()));
    }

    public static <T> void ifContains(Collection<T> collection, T element, Supplier<? extends RuntimeException> exception) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        if (collection.contains(element)) throw exception.get();
    }

    public static <T> void ifContains(Collection<T> collection, T element, NoticeSupplier notice) {
        ifContains(collection, element, () -> new RuntimeException(notice.get()));
    }

    public static <T> void ifNotContains(Collection<T> collection, T element, Supplier<? extends RuntimeException> exception) {
        ifNull(collection, COLLECTION_NULL);
        ifNull(element, ELEMENT_NULL);

        if (!collection.contains(element)) throw exception.get();
    }

    public static <T> void ifNotContains(Collection<T> collection, T element, NoticeSupplier notice) {
        ifNotContains(collection, element, () -> new RuntimeException(notice.get()));
    }

    public static void ifContains(CharSequence origin, CharSequence target, NoticeSupplier notice) {
        ifContains(origin, target, () -> new RuntimeException(notice.get()));
    }

    public static void ifContains(CharSequence origin, CharSequence target, Supplier<? extends RuntimeException> exception) {
        if (origin == null || target == null || origin.toString().contains(target)) throw exception.get();
    }

    public static void ifNotContains(CharSequence origin, CharSequence target, NoticeSupplier notice) {
        ifNotContains(origin, target, () -> new RuntimeException(notice.get()));
    }

    public static void ifNotContains(CharSequence origin, CharSequence target, Supplier<? extends RuntimeException> exception) {
        if (origin == null || target == null || !origin.toString().contains(target)) throw exception.get();
    }

    public static <K, V> void ifContainsKey(Map<K, V> map, K key, Supplier<? extends RuntimeException> exception) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        if (map.containsKey(key)) throw exception.get();
    }

    public static <K, V> void ifContainsKey(Map<K, V> map, K key, NoticeSupplier notice) {
        ifContainsKey(map, key, () -> new RuntimeException(notice.get()));
    }

    public static <K, V> void ifNotContainsKey(Map<K, V> map, K key, Supplier<? extends RuntimeException> exception) {
        ifNull(map, MAP_NULL);
        ifNull(key, KEY_NULL);

        if (!map.containsKey(key)) throw exception.get();
    }

    public static <K, V> void ifNotContainsKey(Map<K, V> map, K key, NoticeSupplier notice) {
        ifNotContainsKey(map, key, () -> new RuntimeException(notice.get()));
    }

    public static <K, V> void ifContainsValue(Map<K, V> map, V value, Supplier<? extends RuntimeException> exception) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        if (map.containsValue(value)) throw exception.get();
    }

    public static <K, V> void ifContainsValue(Map<K, V> map, V value, NoticeSupplier notice) {
        ifContainsValue(map, value, () -> new RuntimeException(notice.get()));
    }

    public static <K, V> void ifNotContainsValue(Map<K, V> map, V value, Supplier<? extends RuntimeException> exception) {
        ifNull(map, MAP_NULL);
        ifNull(value, VALUE_NULL);

        if (!map.containsValue(value)) throw exception.get();
    }

    public static <K, V> void ifNotContainsValue(Map<K, V> map, V value, NoticeSupplier notice) {
        ifNotContainsValue(map, value, () -> new RuntimeException(notice.get()));
    }

    public static <T> void ifInstanceOf(Class<?> type, T object, Supplier<? extends RuntimeException> exception) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        if (type.isInstance(object)) throw exception.get();
    }

    public static <T> void ifInstanceOf(Class<?> type, T object, NoticeSupplier notice) {
        ifInstanceOf(type, object, () -> new RuntimeException(notice.get()));
    }

    public static <T> void ifNotInstanceOf(Class<?> type, T object, Supplier<? extends RuntimeException> exception) {
        Throws.ifNull(type, TYPE_NULL);
        Throws.ifNull(object, INSTANCED_NULL);

        if (!type.isInstance(object)) throw exception.get();
    }

    public static <T> void ifNotInstanceOf(Class<?> type, T object, NoticeSupplier notice) {
        ifNotInstanceOf(type, object, () -> new RuntimeException(notice.get()));
    }

    public static <T> void ifHasNullElement(Collection<T> collection, Supplier<? extends RuntimeException> exception) {
        Throws.ifNull(collection, COLLECTION_NULL);

        for (T t : collection) Throws.ifNull(t, exception);
    }

    public static <T> void ifHasNullElement(Collection<T> collection, NoticeSupplier notice) {
        ifHasNullElement(collection, () -> new RuntimeException(notice.get()));
    }

    public static <T> void ifHasNullElement(T[] array, Supplier<? extends RuntimeException> exception) {
        Throws.ifNull(array, ARRAY_NULL);

        for (T t : array) Throws.ifNull(t, exception);
    }

    public static <T> void ifHasNullElement(T[] array, NoticeSupplier notice) {
        ifHasNullElement(array, () -> new RuntimeException(notice.get()));
    }

    public static void ifAssignable(Class<?> superType, Class<?> subType, NoticeSupplier notice) {
        ifAssignable(superType, subType, () -> new RuntimeException(notice.get()));
    }

    public static void ifAssignable(Class<?> superType, Class<?> subType, Supplier<? extends RuntimeException> exception) {
        if (superType == null || subType == null || superType.isAssignableFrom(subType)) throw exception.get();
    }

    public static void ifNotAssignable(Class<?> superType, Class<?> subType, NoticeSupplier notice) {
        ifNotAssignable(superType, subType, () -> new RuntimeException(notice.get()));
    }

    public static void ifNotAssignable(Class<?> superType, Class<?> subType, Supplier<? extends RuntimeException> exception) {
        if (superType == null || subType == null || !superType.isAssignableFrom(subType)) throw exception.get();
    }

    /**
     * @author fengbinbin
     * @since 2017/4/2/038 11:52
     */
    @FunctionalInterface
    public interface NoticeSupplier {
        /**
         * get one exception notice
         */
        String get();
    }
}
