package org.fz.erwin.lang;

import lombok.experimental.UtilityClass;
import org.fz.erwin.exception.Throws;
import org.fz.erwin.exception.Throws.ExceptionSupplier;
import org.fz.erwin.exception.Throws.NoticeSupplier;

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
public class Variables {
    private static final NoticeSupplier
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


    public void requireTrue(Object expression, ExceptionSupplier exception)                              {Throws.ifFalse(expression, exception);}

    public void requireTrue(Object expression, NoticeSupplier notice)                                    {Throws.ifFalse(expression, notice);}

    public void requireTrue(Object expression)                                                           {Throws.ifFalse(expression, REQUIRE_TRUE);}

    public void requireFalse(Object expression, ExceptionSupplier exception)                             {Throws.ifTrue(expression, exception);}

    public void requireFalse(Object expression, NoticeSupplier notice)                                   {Throws.ifTrue(expression, notice);}

    public void requireFalse(Object expression)                                                          {Throws.ifTrue(expression, REQUIRE_FALSE);}

    public <T> T requireNotNull(T object, ExceptionSupplier exception)                                   {Throws.ifNull(object, exception); return object;}

    public <T> T requireNotNull(T object, NoticeSupplier notice)                                         {Throws.ifNull(object, notice); return object;}

    public <T> T requireNotNull(T object)                                                                {Throws.ifNull(object, REQUIRE_NOT_NULL); return object;}

    public <T> T requireNull(T object, ExceptionSupplier exception)                                      {Throws.ifNotNull(object, exception); return null;}

    public <T> T requireNull(T object, NoticeSupplier notice)                                            {Throws.ifNotNull(object, notice); return null;}

    public <T> T requireNull(T object)                                                                   {Throws.ifNotNull(object, REQUIRE_NULL); return null;}

    public <T> T[] requireNotEmpty(T[] array, ExceptionSupplier exception)                               {Throws.ifEmpty(array, exception); return array;}

    public <T> T[] requireNotEmpty(T[] array, NoticeSupplier notice)                                     {Throws.ifEmpty(array, notice); return array;}

    public <T> T[] requireNotEmpty(T[] array)                                                            {Throws.ifEmpty(array, REQUIRE_COLLECTION_NOT_EMPTY); return array;}

    public <T> Collection<T> requireNotEmpty(Collection<T> collection, ExceptionSupplier exception)      {Throws.ifEmpty(collection, exception); return collection;}

    public <T> Collection<T> requireNotEmpty(Collection<T> collection, NoticeSupplier notice)            {Throws.ifEmpty(collection, notice); return collection;}

    public <T> Collection<T> requireNotEmpty(Collection<T> collection)                                   {Throws.ifEmpty(collection, REQUIRE_ARRAY_NOT_EMPTY); return collection;}

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map, ExceptionSupplier exception)                  {Throws.ifEmpty(map, exception); return map;}

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map, NoticeSupplier notice)                        {Throws.ifEmpty(map, notice); return map;}

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map)                                               {Throws.ifEmpty(map, REQUIRE_MAP_NOT_EMPTY); return map;}

    public String requireNotBlank(String string, ExceptionSupplier exception)                            {Throws.ifBlank(string, exception); return string;}

    public String requireNotBlank(String string, NoticeSupplier notice)                                  {Throws.ifBlank(string, notice); return string;}

    public String requireNotBlank(String string)                                                         {Throws.ifBlank(string, REQUIRE_NOT_BLANK); return string;}

    public <T> void requireEquals(T l, T r, ExceptionSupplier exception)                                 {Throws.ifNotEquals(l, r, exception);}

    public <T> void requireEquals(T l, T r, NoticeSupplier notice)                                       {Throws.ifNotEquals(l, r, notice);}

    public <T> void requireEquals(T l, T r)                                                              {Throws.ifNotEquals(l, r, REQUIRE_EQUALS);}

    public <T> void requireNotEquals(T l, T r, ExceptionSupplier exception)                              {Throws.ifEquals(l, r, exception);}

    public <T> void requireNotEquals(T l, T r, NoticeSupplier notice)                                    {Throws.ifEquals(l, r, notice);}

    public <T> void requireNotEquals(T l, T r)                                                           {Throws.ifEquals(l, r, REQUIRE_NOT_EQUALS);}

    public <T> void requireContains(Collection<T> collection, T element, ExceptionSupplier exception)    {Throws.ifContains(collection, element, exception);}

    public <T> void requireContains(Collection<T> collection, T element, NoticeSupplier notice)          {Throws.ifContains(collection, element, notice);}

    public <T> void requireContains(Collection<T> collection, T element)                                 {Throws.ifContains(collection, element, REQUIRE_CONTAINS);}

    public <T> void requireNotContains(Collection<T> collection, T element, ExceptionSupplier exception) {Throws.ifContains(collection, element, exception);}

    public <T> void requireNotContains(Collection<T> collection, T element, NoticeSupplier notice)       {Throws.ifContains(collection, element, notice);}

    public <T> void requireNotContains(Collection<T> collection, T element)                              {Throws.ifContains(collection, element, REQUIRE_NOT_CONTAINS);}

}
