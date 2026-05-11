package org.fz.erwin.lang;

import lombok.experimental.UtilityClass;
import org.fz.erwin.exception.Throws;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

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

    public void requireTrue(Object expression, Supplier<String> notice) {
        Throws.ifFalse(expression, notice);
    }

    public void requireTrue(Object expression, String notice, Object... params) {
        Throws.ifFalse(expression, notice, params);
    }

    public void requireTrue(Object expression) {
        requireTrue(expression, REQUIRE_TRUE);
    }

    public void requireFalse(Object expression, Supplier<String> notice) {
        Throws.ifTrue(expression, notice);
    }

    public void requireFalse(Object expression, String notice, Object... params) {
        Throws.ifTrue(expression, notice, params);
    }

    public void requireFalse(Object expression) {
        requireFalse(expression, REQUIRE_FALSE);
    }

    public <T> T requireNotNull(T object, Supplier<String> notice) {
        Throws.ifNull(object, notice);
        return object;
    }

    public <T> T requireNotNull(T object, String notice, Object... params) {
        Throws.ifNull(object, notice, params);
        return object;
    }

    public <T> T requireNotNull(T object) {
        return requireNotNull(object, REQUIRE_NOT_NULL);
    }

    public <T> T requireNull(T object, Supplier<String> notice) {
        Throws.ifNotNull(object, notice);
        return null;
    }

    public <T> T requireNull(T object, String notice, Object... params) {
        Throws.ifNotNull(object, notice, params);
        return null;
    }

    public <T> T requireNull(T object) {
        return requireNull(object, REQUIRE_NULL);
    }

    public <T> T[] requireNotEmpty(T[] array, Supplier<String> notice) {
        Throws.ifEmpty(array, notice);
        return array;
    }

    public <T> T[] requireNotEmpty(T[] array, String notice, Object... params) {
        Throws.ifEmpty(array, notice, params);
        return array;
    }

    public <T> T[] requireNotEmpty(T[] array) {
        return requireNotEmpty(array, REQUIRE_ARRAY_NOT_EMPTY);
    }

    public <T> Collection<T> requireNotEmpty(Collection<T> collection, Supplier<String> notice) {
        Throws.ifEmpty(collection, notice);
        return collection;
    }

    public <T> Collection<T> requireNotEmpty(Collection<T> collection, String notice, Object... params) {
        Throws.ifEmpty(collection, notice, params);
        return collection;
    }

    public <T> Collection<T> requireNotEmpty(Collection<T> collection) {
        return requireNotEmpty(collection, REQUIRE_COLLECTION_NOT_EMPTY);
    }

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map, Supplier<String> notice) {
        Throws.ifEmpty(map, notice);
        return map;
    }

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map, String notice, Object... params) {
        Throws.ifEmpty(map, notice, params);
        return map;
    }

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map) {
        return requireNotEmpty(map, REQUIRE_MAP_NOT_EMPTY);
    }

    public String requireNotBlank(String string, Supplier<String> notice) {
        Throws.ifBlank(string, notice);
        return string;
    }

    public String requireNotBlank(String string, String notice, Object... params) {
        Throws.ifBlank(string, notice, params);
        return string;
    }

    public String requireNotBlank(String string) {
        return requireNotBlank(string, REQUIRE_NOT_BLANK);
    }

    public <T> void requireEquals(T l, T r, Supplier<String> notice) {
        Throws.ifNotEquals(l, r, notice);
    }

    public <T> void requireEquals(T l, T r, String notice, Object... params) {
        Throws.ifNotEquals(l, r, notice, params);
    }

    public <T> void requireEquals(T l, T r) {
        requireEquals(l, r, REQUIRE_EQUALS);
    }

    public <T> void requireNotEquals(T l, T r, Supplier<String> notice) {
        Throws.ifEquals(l, r, notice);
    }

    public <T> void requireNotEquals(T l, T r, String notice, Object... params) {
        Throws.ifEquals(l, r, notice, params);
    }

    public <T> void requireNotEquals(T l, T r) {
        requireNotEquals(l, r, REQUIRE_NOT_EQUALS);
    }

    public <T> void requireContains(Collection<T> collection, T element, Supplier<String> notice) {
        Throws.ifNotContains(collection, element, notice);
    }

    public <T> void requireContains(Collection<T> collection, T element, String notice, Object... params) {
        Throws.ifNotContains(collection, element, notice, params);
    }

    public <T> void requireContains(Collection<T> collection, T element) {
        requireContains(collection, element, REQUIRE_CONTAINS);
    }

    public <T> void requireNotContains(Collection<T> collection, T element, Supplier<String> notice) {
        Throws.ifContains(collection, element, notice);
    }

    public <T> void requireNotContains(Collection<T> collection, T element, String notice, Object... params) {
        Throws.ifContains(collection, element, notice, params);
    }

    public <T> void requireNotContains(Collection<T> collection, T element) {
        requireNotContains(collection, element, REQUIRE_NOT_CONTAINS);
    }

}
