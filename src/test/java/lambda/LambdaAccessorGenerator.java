package lambda;// LambdaAccessorGenerator.java

import java.lang.invoke.*;
import java.lang.reflect.Method;

import static java.lang.invoke.MethodType.methodType;

public class LambdaAccessorGenerator {

    private static final java.util.Map<String, Setter> CACHE = new java.util.concurrent.ConcurrentHashMap<>();

    public static Setter generateSetter(Class<?> targetClass, Method setterMethod) {
        String key = targetClass.getName() + "." + setterMethod.getName();
        return CACHE.computeIfAbsent(key, k -> {
            try {
                return makeSetter(targetClass, setterMethod);
            } catch (Throwable t) {
                throw new RuntimeException("Failed to generate setter for " + key, t);
            }
        });
    }

    private static Setter makeSetter(Class<?> targetClass, Method setterMethod) throws Throwable {
        String name = setterMethod.getName();
        Class<?> paramType = setterMethod.getParameterTypes()[0];

        // ✅ 使用 publicLookup().findVirtual() —— 不需要 --add-opens
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        MethodType mt = methodType(void.class, paramType);
        MethodHandle mh = lookup.findVirtual(targetClass, name, mt);

        MethodType interfaceType = methodType(void.class, Object.class, Object.class);
        MethodType implType = mh.type();

        CallSite site = LambdaMetafactory.metafactory(
            MethodHandles.lookup(),
            "set",
            methodType(Setter.class),
            interfaceType,
            mh,
            implType
        );

        return (Setter) site.getTarget().invokeExact();
    }
}