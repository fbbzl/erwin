// 文件路径：com/yourcompany/reflect/AccessorGenerator.java
// 请根据你的实际包名修改
package lambda;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

/**
 * 使用 ASM 和 MethodHandles.Lookup.defineHiddenClass() 动态生成字段访问器
 * 适用于 JDK 15+
 */
public class AccessorGenerator {

    // 缓存已生成的 FieldSetter 实例
    private static final java.util.Map<String, Setter> CACHE =
        new java.util.concurrent.ConcurrentHashMap<>();

    // 预计算 AccessorGenerator 所在的包路径（内部形式）
    private static final String PACKAGE_INTERNAL = AccessorGenerator.class.getPackageName().replace('.', '/');

    /**
     * 为指定类的 setter 方法生成一个 FieldSetter
     * @param targetClass 目标类
     * @param setterMethod setter 方法
     * @return FieldSetter 实例
     */
    public static Setter generateSetter(Class<?> targetClass, Method setterMethod) {
        String cacheKey = targetClass.getName() + "." + setterMethod.getName();
        return CACHE.computeIfAbsent(cacheKey, k -> {
            try {
                return createFieldSetterInstance(targetClass, setterMethod);
            } catch (Throwable e) {
                throw new RuntimeException("Failed to generate accessor for " + setterMethod, e);
            }
        });
    }

    /**
     * 使用 ASM 生成字节码，并通过 defineHiddenClass 创建隐藏类实例
     */
    private static Setter createFieldSetterInstance(Class<?> targetClass, Method setterMethod)
            throws Throwable {

        String targetClassName = targetClass.getName().replace('.', '/');
        String methodName = setterMethod.getName();
        Class<?> paramType = setterMethod.getParameterTypes()[0];
        String paramDescriptor = Type.getDescriptor(paramType);

        // ✅ 确保生成的类在 AccessorGenerator 的包下
        String generatedClassInternalName = PACKAGE_INTERNAL + "/" + 
            targetClass.getSimpleName() + "_" + methodName + "_Accessor";

        // --- 使用 ASM 生成字节码 ---
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        MethodVisitor mv;

        // visit: 定义类
        cw.visit(V17,
                ACC_FINAL | ACC_SUPER,
                generatedClassInternalName,
                null,
                "java/lang/Object",
                new String[]{Setter.class.getName().replace('.', '/')});

        // 构造函数 <init>()
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // 实现 set(Object, Object) 方法
        mv = cw.visitMethod(ACC_PUBLIC, "set", "(Ljava/lang/Object;Ljava/lang/Object;)V", null,
                new String[]{"java/lang/Throwable"});
        mv.visitCode();

        // 将 target 对象 (arg1) 转换为目标类型
        mv.visitVarInsn(ALOAD, 1);
        mv.visitTypeInsn(CHECKCAST, targetClassName);
        mv.visitVarInsn(ASTORE, 3); // 存入局部变量 3

        // 将 value 对象 (arg2) 转换为参数类型
        mv.visitVarInsn(ALOAD, 2);
        mv.visitTypeInsn(CHECKCAST, Type.getInternalName(paramType));
        mv.visitVarInsn(ASTORE, 4); // 存入局部变量 4

        // 调用 setter 方法
        mv.visitVarInsn(ALOAD, 3);           // 加载 target 对象
        mv.visitVarInsn(ALOAD, 4);           // 加载 value 值
        mv.visitMethodInsn(INVOKEVIRTUAL,   // 调用虚拟方法
                targetClassName,
                methodName,
                "(" + paramDescriptor + ")V",
                false);

        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 5); // 根据实际使用调整
        mv.visitEnd();

        cw.visitEnd();
        byte[] bytecode = cw.toByteArray();

        // --- 使用 MethodHandles.Lookup.defineHiddenClass() ---
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // 定义隐藏类
        MethodHandles.Lookup hiddenLookup = lookup.defineHiddenClass(
            bytecode,
            true, // 是否允许执行 <clinit>
            MethodHandles.Lookup.ClassOption.NESTMATE
        );

        // 获取隐藏类的 Class 对象
        Class<?> generatedClass = hiddenLookup.lookupClass();

        // 使用 MethodHandle 调用无参构造函数创建实例
        MethodHandle constructor = hiddenLookup.findConstructor(
            generatedClass,
            MethodType.methodType(void.class)
        );

        // 创建并返回实例
        return (Setter) constructor.invoke();
    }
}