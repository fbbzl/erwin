package org.fz.util.lambda;

import lombok.experimental.UtilityClass;
import org.fz.util.lambda.Try.LambdasException;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.hutool.core.text.CharSequenceUtil.upperFirstAndAddPre;
import static java.lang.invoke.MethodType.methodType;

/**
 * @author fengbinbin
 * @version 1.0
 * @since 2025/4/27 16:37
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class LambdaMetas {
    public static <T> Supplier<T> lambdaConstructor(Type clazz) {
        try {
            Lookup       lookup            = MethodHandles.lookup();
            MethodHandle constructorHandle = lookup.findConstructor((Class<T>) clazz, methodType(void.class));

            CallSite site = LambdaMetafactory.metafactory(
                    lookup,
                    "get",
                    MethodType.methodType(Supplier.class),
                    constructorHandle.type().generic(),
                    constructorHandle,
                    constructorHandle.type());

            return (Supplier<T>) site.getTarget().invokeExact();
        }
        catch (Throwable throwable) {
            throw new LambdasException("can not generate lambda constructor for class [" + clazz + "]");
        }
    }

    public static <P, T> Function<P, T> lambdaConstructor(Type clazz, Class<P> paramType) {
        try {
            Lookup lookup = MethodHandles.lookup();
            MethodHandle constructorHandle = lookup.findConstructor((Class<T>) clazz, methodType(void.class,
                                                                                                 paramType));

            CallSite site = LambdaMetafactory.metafactory(
                    lookup,
                    "apply",
                    MethodType.methodType(Function.class),
                    constructorHandle.type().generic(),
                    constructorHandle,
                    constructorHandle.type());

            return (Function<P, T>) site.getTarget().invokeExact();
        }
        catch (Throwable throwable) {
            throw new LambdasException("can not generate lambda constructor for class [" + clazz + "], param type: [" + paramType + "]", throwable);
        }
    }

    public static <T, R> Function<T, R> lambdaGetter(Type clazz, Class<R> returnType, String methodName) {
        try {
            Lookup       lookup       = MethodHandles.lookup();
            MethodHandle getterHandle = lookup.findVirtual((Class<T>) clazz, methodName, methodType(returnType));

            CallSite site = LambdaMetafactory.metafactory(
                    lookup,
                    "apply",
                    methodType(Function.class),
                    methodType(Object.class, Object.class),
                    getterHandle,
                    getterHandle.type());

            return (Function<T, R>) site.getTarget().invokeExact();
        }
        catch (Throwable throwable) {
            throw new IllegalArgumentException("can not generate lambda getter, class [" + clazz + "], method: [" + methodName + "], return type: [" + returnType + "]", throwable);
        }
    }

    public static <A, P> BiConsumer<A, P> lambdaSetter(Class<A> clazz, Class<P> paramType, String methodName) {
        try {
            Lookup       lookup       = MethodHandles.lookup();
            MethodHandle setterHandle = lookup.findVirtual(clazz, methodName, methodType(void.class, paramType));

            CallSite site = LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    methodType(BiConsumer.class),
                    methodType(void.class, Object.class, Object.class),
                    setterHandle,
                    setterHandle.type());

            return (BiConsumer<A, P>) site.getTarget().invokeExact();
        }
        catch (Throwable throwable) {
            throw new IllegalArgumentException("can not generate lambda setter, class [" + clazz + "], method: [" + methodName + "], param type: [" + paramType + "]", throwable);
        }
    }

    public static <A, R> Function<A, R> lambdaGetter(Field field) {
        return (Function<A, R>) lambdaGetter(field.getDeclaringClass(), field.getType(),
                                             upperFirstAndAddPre(field.getName(), "get"));
    }

    public static <A, P> BiConsumer<A, P> lambdaSetter(Field field) {
        return (BiConsumer<A, P>) lambdaSetter(field.getDeclaringClass(), field.getType(),
                                               upperFirstAndAddPre(field.getName(), "set"));
    }
}
