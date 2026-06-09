package org.fz.erwin.lang;

import lombok.experimental.UtilityClass;
import org.fz.erwin.exception.Throws;

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
    private static final Throws.MessageSupplier
            REQUIRE_TRUE                 = () -> "require true but still false",
            REQUIRE_FALSE                = () -> "require false but still true",
            REQUIRE_NOT_NULL             = () -> "require not null but still null",
            REQUIRE_NULL                 = () -> "require null but still not null",
            REQUIRE_COLLECTION_NOT_EMPTY = () -> "require collection not empty but still empty",
            REQUIRE_COLLECTION_EMPTY     = () -> "require collection empty but still not empty",
            REQUIRE_NOT_BLANK            = () -> "require not blank but still blank",
            REQUIRE_MAP_NOT_EMPTY        = () -> "require map not empty but still empty",
            REQUIRE_MAP_EMPTY            = () -> "require map empty but still not empty",
            REQUIRE_ARRAY_EMPTY          = () -> "require array empty but still not empty",
            REQUIRE_ARRAY_NOT_EMPTY      = () -> "require array not empty but still empty",
            REQUIRE_EQUALS               = () -> "require equals but still not equals",
            REQUIRE_NOT_EQUALS           = () -> "require not equals but still equals",
            REQUIRE_NOT_CONTAINS         = () -> "require not contains but still contains",
            REQUIRE_CONTAINS             = () -> "require contains but still not contains";

    public void requireTrue(Object expression, Throws.MessageSupplier notice)                              { Throws.ifFalse(expression, notice); }
    public void requireTrue(Object expression)                                                       { Throws.ifFalse(expression, REQUIRE_TRUE); }
    public void requireFalse(Object expression, Throws.MessageSupplier notice)                             { Throws.ifTrue(expression, notice); }
    public void requireFalse(Object expression)                                                      { Throws.ifTrue(expression, REQUIRE_FALSE); }
    public <T> T requireNotNull(T object, Throws.MessageSupplier notice)                                   { Throws.ifNull(object, notice); return object; }
    public <T> T requireNotNull(T object)                                                            { Throws.ifNull(object, REQUIRE_NOT_NULL); return object; }
    public <T> T requireNull(T object, Throws.MessageSupplier notice)                                      { Throws.ifNotNull(object, notice); return null; }
    public <T> T requireNull(T object)                                                               { Throws.ifNotNull(object, REQUIRE_NULL); return null; }
    public <T> T[] requireNotEmpty(T[] array, Throws.MessageSupplier notice)                               { Throws.ifEmpty(array, notice); return array; }
    public <T> T[] requireNotEmpty(T[] array)                                                        { Throws.ifEmpty(array, REQUIRE_ARRAY_NOT_EMPTY); return array; }
    public <T> Collection<T> requireNotEmpty(Collection<T> collection, Throws.MessageSupplier notice)      { Throws.ifEmpty(collection, notice); return collection; }
    public <T> Collection<T> requireNotEmpty(Collection<T> collection)                               { Throws.ifEmpty(collection, REQUIRE_COLLECTION_NOT_EMPTY); return collection; }
    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map, Throws.MessageSupplier notice)                  { Throws.ifEmpty(map, notice); return map; }
    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map)                                           { Throws.ifEmpty(map, REQUIRE_MAP_NOT_EMPTY); return map; }
    public String requireNotBlank(String string, Throws.MessageSupplier notice)                            { Throws.ifBlank(string, notice); return string; }
    public String requireNotBlank(String string)                                                     { Throws.ifBlank(string, REQUIRE_NOT_BLANK); return string; }
    public <T> void requireEquals(T l, T r, Throws.MessageSupplier notice)                                 { Throws.ifNotEquals(l, r, notice); }
    public <T> void requireEquals(T l, T r)                                                          { Throws.ifNotEquals(l, r, REQUIRE_EQUALS); }
    public <T> void requireNotEquals(T l, T r, Throws.MessageSupplier notice)                              { Throws.ifEquals(l, r, notice); }
    public <T> void requireNotEquals(T l, T r)                                                       { Throws.ifEquals(l, r, REQUIRE_NOT_EQUALS); }
    public <T> void requireContains(Collection<T> collection, T element, Throws.MessageSupplier notice)    { Throws.ifNotContains(collection, element, notice); }
    public <T> void requireContains(Collection<T> collection, T element)                             { Throws.ifNotContains(collection, element, REQUIRE_CONTAINS); }
    public <T> void requireNotContains(Collection<T> collection, T element, Throws.MessageSupplier notice) { Throws.ifContains(collection, element, notice); }
    public <T> void requireNotContains(Collection<T> collection, T element)                          { Throws.ifContains(collection, element, REQUIRE_NOT_CONTAINS); }

}
