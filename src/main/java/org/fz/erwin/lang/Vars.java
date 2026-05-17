package org.fz.erwin.lang;

import lombok.experimental.UtilityClass;
import org.fz.erwin.exception.Throws;
import org.fz.erwin.exception.Throws.ExceptionSupplier;
import org.fz.erwin.exception.Throws.MessageSupplier;

import java.util.Collection;
import java.util.Map;

/**
 * Variable operation related verification method
 *
 * @author fbb
 * @version 1.0
 * @since 2020/1/2/002 11:52
 */
@UtilityClass
public class Vars {
    private static final String
            REQUIRE_TRUE                 = "require true but still false",
            REQUIRE_FALSE                = "require false but still true",
            REQUIRE_NOT_NULL             = "require not null but still null",
            REQUIRE_NULL                 = "require null but still not null",
            REQUIRE_COLLECTION_NOT_EMPTY = "require collection not empty but still empty",
            REQUIRE_COLLECTION_EMPTY     = "require collection empty but still not empty",
            REQUIRE_NOT_BLANK            = "require not blank but still blank",
            REQUIRE_MAP_NOT_EMPTY        = "require map not empty but still empty",
            REQUIRE_MAP_EMPTY            = "require map empty but still not empty",
            REQUIRE_ARRAY_EMPTY          = "require array empty but still not empty",
            REQUIRE_ARRAY_NOT_EMPTY      = "require array not empty but still empty",
            REQUIRE_EQUALS               = "require equals but still not equals",
            REQUIRE_NOT_EQUALS           = "require not equals but still equals",
            REQUIRE_NOT_CONTAINS         = "require not contains but still contains",
            REQUIRE_CONTAINS             = "require contains but still not contains";

    public void requireTrue(Object expression, MessageSupplier message) {
        Throws.ifFalse(expression, message);
    }

    public void requireTrue(Object expression, ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifFalse(expression, exception);
    }

    public void requireTrue(Object expression, String message, Object... params) {
        Throws.ifFalse(expression, message, params);
    }

    public void requireTrue(Object expression) {
        requireTrue(expression, REQUIRE_TRUE);
    }

    public void requireFalse(Object expression, MessageSupplier message) {
        Throws.ifTrue(expression, message);
    }

    public void requireFalse(Object expression, ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifTrue(expression, exception);
    }

    public void requireFalse(Object expression, String message, Object... params) {
        Throws.ifTrue(expression, message, params);
    }

    public void requireFalse(Object expression) {
        requireFalse(expression, REQUIRE_FALSE);
    }

    public <T> T requireNotNull(T object, MessageSupplier message) {
        Throws.ifNull(object, message);
        return object;
    }

    public <T> T requireNotNull(T object, ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifNull(object, exception);
        return object;
    }

    public <T> T requireNotNull(T object, String message, Object... params) {
        Throws.ifNull(object, message, params);
        return object;
    }

    public <T> T requireNotNull(T object) {
        return requireNotNull(object, REQUIRE_NOT_NULL);
    }

    public <T> T requireNull(T object, MessageSupplier message) {
        Throws.ifNotNull(object, message);
        return null;
    }

    public <T> T requireNull(T object, ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifNotNull(object, exception);
        return null;
    }

    public <T> T requireNull(T object, String message, Object... params) {
        Throws.ifNotNull(object, message, params);
        return null;
    }

    public <T> T requireNull(T object) {
        return requireNull(object, REQUIRE_NULL);
    }

    public <T> T[] requireNotEmpty(T[] array, MessageSupplier message) {
        Throws.ifEmpty(array, message);
        return array;
    }

    public <T> T[] requireNotEmpty(T[] array, ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifEmpty(array, exception);
        return array;
    }

    public <T> T[] requireNotEmpty(T[] array, String message, Object... params) {
        Throws.ifEmpty(array, message, params);
        return array;
    }

    public <T> T[] requireNotEmpty(T[] array) {
        return requireNotEmpty(array, REQUIRE_ARRAY_NOT_EMPTY);
    }

    public <T> Collection<T> requireNotEmpty(Collection<T> collection, MessageSupplier message) {
        Throws.ifEmpty(collection, message);
        return collection;
    }

    public <T> Collection<T> requireNotEmpty(Collection<T> collection,
                                             ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifEmpty(collection, exception);
        return collection;
    }

    public <T> Collection<T> requireNotEmpty(Collection<T> collection, String message, Object... params) {
        Throws.ifEmpty(collection, message, params);
        return collection;
    }

    public <T> Collection<T> requireNotEmpty(Collection<T> collection) {
        return requireNotEmpty(collection, REQUIRE_COLLECTION_NOT_EMPTY);
    }

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map, MessageSupplier message) {
        Throws.ifEmpty(map, message);
        return map;
    }

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map,
                                            ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifEmpty(map, exception);
        return map;
    }

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map, String message, Object... params) {
        Throws.ifEmpty(map, message, params);
        return map;
    }

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map) {
        return requireNotEmpty(map, REQUIRE_MAP_NOT_EMPTY);
    }

    public String requireNotBlank(String string, MessageSupplier message) {
        Throws.ifBlank(string, message);
        return string;
    }

    public String requireNotBlank(String string, ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifBlank(string, exception);
        return string;
    }

    public String requireNotBlank(String string, String message, Object... params) {
        Throws.ifBlank(string, message, params);
        return string;
    }

    public String requireNotBlank(String string) {
        return requireNotBlank(string, REQUIRE_NOT_BLANK);
    }

    public <T> void requireEquals(T l, T r, MessageSupplier message) {
        Throws.ifNotEquals(l, r, message);
    }

    public <T> void requireEquals(T l, T r, ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifNotEquals(l, r, exception);
    }

    public <T> void requireEquals(T l, T r, String message, Object... params) {
        Throws.ifNotEquals(l, r, message, params);
    }

    public <T> void requireEquals(T l, T r) {
        requireEquals(l, r, REQUIRE_EQUALS);
    }

    public <T> void requireNotEquals(T l, T r, MessageSupplier message) {
        Throws.ifEquals(l, r, message);
    }

    public <T> void requireNotEquals(T l, T r, ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifEquals(l, r, exception);
    }

    public <T> void requireNotEquals(T l, T r, String message, Object... params) {
        Throws.ifEquals(l, r, message, params);
    }

    public <T> void requireNotEquals(T l, T r) {
        requireNotEquals(l, r, REQUIRE_NOT_EQUALS);
    }

    public <T> void requireContains(Collection<T> collection, T element, MessageSupplier message) {
        Throws.ifNotContains(collection, element, message);
    }

    public <T> void requireContains(Collection<T> collection, T element,
                                    ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifNotContains(collection, element, exception);
    }

    public <T> void requireContains(Collection<T> collection, T element, String message, Object... params) {
        Throws.ifNotContains(collection, element, message, params);
    }

    public <T> void requireContains(Collection<T> collection, T element) {
        requireContains(collection, element, REQUIRE_CONTAINS);
    }

    public <T> void requireNotContains(Collection<T> collection, T element, MessageSupplier message) {
        Throws.ifContains(collection, element, message);
    }

    public <T> void requireNotContains(Collection<T> collection, T element,
                                       ExceptionSupplier<? extends RuntimeException> exception) {
        Throws.ifContains(collection, element, exception);
    }

    public <T> void requireNotContains(Collection<T> collection, T element, String message, Object... params) {
        Throws.ifContains(collection, element, message, params);
    }

    public <T> void requireNotContains(Collection<T> collection, T element) {
        requireNotContains(collection, element, REQUIRE_NOT_CONTAINS);
    }

}
