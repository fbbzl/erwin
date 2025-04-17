package org.fz.util.lang;

import java.util.Collection;
import java.util.Map;

import lombok.experimental.UtilityClass;
import org.fz.util.exception.Throws;


/**
 * Variable operation related verification method
 *
 * @author fbb
 * @version 1.0
 * @since 2020/1/2/002 11:52
 */
@UtilityClass
public class Variables {

    public void requireTrue(Object expression, RuntimeException exception)                              {Throws.ifFalse(expression, exception);}

    public void requireTrue(Object expression, String exceptionMessage)                                 {Throws.ifFalse(expression, exceptionMessage);}

    public void requireTrue(Object expression)                                                          {Throws.ifFalse(expression, "require true but still false");}

    public void requireFalse(Object expression, RuntimeException exception)                             {Throws.ifTrue(expression, exception);}

    public void requireFalse(Object expression, String exceptionMessage)                                {Throws.ifTrue(expression, exceptionMessage);}

    public void requireFalse(Object expression)                                                         {Throws.ifTrue(expression, "require false but still true");}

    public <T> T requireNotNull(T object, RuntimeException exception)                                   {Throws.ifNull(object, exception); return object;}

    public <T> T requireNotNull(T object, String exceptionMessage)                                      {Throws.ifNull(object, exceptionMessage); return object;}

    public <T> T requireNotNull(T object)                                                               {Throws.ifNull(object, "require not null but still null"); return object;}

    public <T> T requireNull(T object, RuntimeException exception)                                      {Throws.ifNotNull(object, exception); return null;}

    public <T> T requireNull(T object, String exceptionMessage)                                         {Throws.ifNotNull(object, exceptionMessage); return null;}

    public <T> T requireNull(T object)                                                                  {Throws.ifNotNull(object, "require null but still not null"); return null;}

    public <T> T[] requireNotEmpty(T[] array, RuntimeException exception)                               {Throws.ifEmpty(array, exception); return array;}

    public <T> T[] requireNotEmpty(T[] array, String exceptionMessage)                                  {Throws.ifEmpty(array, exceptionMessage); return array;}

    public <T> T[] requireNotEmpty(T[] array)                                                           {Throws.ifEmpty(array, "require the collection not empty but still empty"); return array;}

    public <T> Collection<T> requireNotEmpty(Collection<T> collection, RuntimeException exception)      {Throws.ifEmpty(collection, exception); return collection;}

    public <T> Collection<T> requireNotEmpty(Collection<T> collection, String exceptionMessage)         {Throws.ifEmpty(collection, exceptionMessage); return collection;}

    public <T> Collection<T> requireNotEmpty(Collection<T> collection)                                  {Throws.ifEmpty(collection, "require the array not empty but still empty"); return collection;}

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map, RuntimeException exception)                  {Throws.ifEmpty(map, exception); return map;}

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map, String exceptionMessage)                     {Throws.ifEmpty(map, exceptionMessage); return map;}

    public <K, V> Map<K, V> requireNotEmpty(Map<K, V> map)                                              {Throws.ifEmpty(map, "require the map not empty but still empty"); return map;}

    public String requireNotBlank(String string, RuntimeException exception)                            {Throws.ifBlank(string, exception); return string;}

    public String requireNotBlank(String string, String exceptionMessage)                               {Throws.ifBlank(string, exceptionMessage); return string;}

    public String requireNotBlank(String string)                                                        {Throws.ifBlank(string, "require not blank but still blank"); return string;}

    public <T> void requireEquals(T l, T r, RuntimeException exception)                                 {Throws.ifNotEquals(l, r, exception);}

    public <T> void requireEquals(T l, T r, String exceptionMessage)                                    {Throws.ifNotEquals(l, r, exceptionMessage);}

    public <T> void requireEquals(T l, T r)                                                             {Throws.ifNotEquals(l, r, "require equals but still not equals");}

    public <T> void requireNotEquals(T l, T r, RuntimeException exception)                              {Throws.ifEquals(l, r, exception);}

    public <T> void requireNotEquals(T l, T r, String exceptionMessage)                                 {Throws.ifEquals(l, r, exceptionMessage);}

    public <T> void requireNotEquals(T l, T r)                                                          {Throws.ifEquals(l, r, "require not equals but still equals");}

    public <T> void requireNotContains(Collection<T> collection, T element, RuntimeException exception) {Throws.ifContains(collection, element, exception);}

    public <T> void requireNotContains(Collection<T> collection, T element, String exceptionMessage)    {Throws.ifContains(collection, element, exceptionMessage);}

    public <T> void requireNotContains(Collection<T> collection, T element)                             {Throws.ifContains(collection, element, "require not contains but still contains");}

}
