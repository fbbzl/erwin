package lambda;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// PerformanceTest.java
public class PerformanceTest {

    private static final int WARMUP_COUNT = 10000;
    private static final int TEST_COUNT   = 1000000;

    public static void main(String[] args) throws Exception {
        new PerformanceTest().run();
    }

    public void run() throws Exception {
        Person person        = new Person();
        Method setNameMethod = Person.class.getMethod("setName", String.class);

        // 使用 Setter 而不是 FieldSetter
        Setter reflectionSetter = (target, value) -> {
            try {
                setNameMethod.invoke(target, value);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
        Setter asmSetter        = AccessorGenerator.generateSetter(Person.class, setNameMethod);
        Setter lambdaSetter     = LambdaAccessorGenerator.generateSetter(Person.class, setNameMethod);

        // ======== 预热 ========
        System.out.println("Warming up...");
        for (int i = 0; i < WARMUP_COUNT; i++) {
            person.setName("Warm");
            reflectionSetter.set(person, "Warm");
            asmSetter.set(person, "Warm");
            lambdaSetter.set(person, "Warm");
        }
        System.out.println("Warmup finished.\n");

        // ======== 正式测试 ========
        System.out.println("Starting performance test...");
        System.out.printf("%-20s %-15s%n", "Method", "Avg Time (ns)");
        System.out.println("-".repeat(35));

        // Direct
        long start = System.nanoTime();
        for (int i = 0; i < TEST_COUNT; i++) {
            person.setName("Alice");
        }
        double directTime = (double)(System.nanoTime() - start) / TEST_COUNT;
        System.out.printf("%-20s %-,15.2f%n", "Direct", directTime);

        // Reflection
        start = System.nanoTime();
        for (int i = 0; i < TEST_COUNT; i++) {
            reflectionSetter.set(person, "Alice");
        }
        double reflectTime = (double)(System.nanoTime() - start) / TEST_COUNT;
        System.out.printf("%-20s %-,15.2f%n", "Reflection", reflectTime);

        // ASM
        start = System.nanoTime();
        for (int i = 0; i < TEST_COUNT; i++) {
            asmSetter.set(person, "Alice");
        }
        double asmTime = (double)(System.nanoTime() - start) / TEST_COUNT;
        System.out.printf("%-20s %-,15.2f%n", "ASM", asmTime);

        // LambdaMetafactory
        start = System.nanoTime();
        for (int i = 0; i < TEST_COUNT; i++) {
            lambdaSetter.set(person, "Alice");
        }
        double lambdaTime = (double)(System.nanoTime() - start) / TEST_COUNT;
        System.out.printf("%-20s %-,15.2f%n", "LambdaMetafactory", lambdaTime);

        // 性能对比
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Performance Ratio (vs Direct):");
        System.out.printf("%-20s %.2fx%n", "Reflection", reflectTime / directTime);
        System.out.printf("%-20s %.2fx%n", "ASM", asmTime / directTime);
        System.out.printf("%-20s %.2fx%n", "LambdaMetafactory", lambdaTime / directTime);
    }
}