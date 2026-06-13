package lambda;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PerformanceReport {

    private static final int WARMUP_COUNT = 10_000;
    private static final int TEST_COUNT   = 1_000_000_000;
    private static final int RUN_TIMES    = 1_000_000_000; // 运行5次取平均

    public static void main(String[] args) throws Exception {
        new PerformanceReport().run();
    }

    public void run() throws Exception {
        Person person = new Person();
        Method setNameMethod = Person.class.getMethod("setName", String.class);

        Setter reflectionSetter = (target, value) -> {
            try {
                setNameMethod.invoke(target, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Setter asmSetter    = AccessorGenerator.generateSetter(Person.class, setNameMethod);
        Setter lambdaSetter = LambdaAccessorGenerator.generateSetter(Person.class, setNameMethod);

        // 预热
        System.out.println("Warming up...");
        for (int i = 0; i < WARMUP_COUNT; i++) {
            person.setName("Warm");
            reflectionSetter.set(person, "Warm");
            asmSetter.set(person, "Warm");
            lambdaSetter.set(person, "Warm");
        }
        System.out.println("Warmup finished.\n");

        // 收集每次运行的数据
        List<RunResult> results = new ArrayList<>();
        for (int run = 1; run <= RUN_TIMES; run++) {
            System.out.printf("Running test %d/%d...\n", run, RUN_TIMES);
            RunResult result = executeTest(person, reflectionSetter, asmSetter, lambdaSetter);
            results.add(result);
        }

        // 生成统计报告
        generateReport(results);
    }

    private RunResult executeTest(Person person, Setter reflection, Setter asm, Setter lambda) {
        Map<String, Double> times = new LinkedHashMap<>();

        // Direct
        long start = System.nanoTime();
        for (int i = 0; i < TEST_COUNT; i++) {
            person.setName("Alice");
        }
        double direct = (double)(System.nanoTime() - start) / TEST_COUNT;
        times.put("Direct", direct);

        // Reflection
        start = System.nanoTime();
        for (int i = 0; i < TEST_COUNT; i++) {
            reflection.set(person, "Alice");
        }
        double reflect = (double)(System.nanoTime() - start) / TEST_COUNT;
        times.put("Reflection", reflect);


        // LambdaMetafactory
        start = System.nanoTime();
        for (int i = 0; i < TEST_COUNT; i++) {
            lambda.set(person, "Alice");
        }
        double lambdaTime = (double)(System.nanoTime() - start) / TEST_COUNT;
        times.put("LambdaMetafactory", lambdaTime);

        // ASM
        start = System.nanoTime();
        for (int i = 0; i < TEST_COUNT; i++) {
            asm.set(person, "Alice");
        }
        double asmTime = (double)(System.nanoTime() - start) / TEST_COUNT;
        times.put("ASM", asmTime);


        return new RunResult(times);
    }

    private void generateReport(List<RunResult> results) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              PERFORMANCE BENCHMARK REPORT");
        System.out.println("=".repeat(60));

        // 汇总数据
        Map<String, List<Double>> data = new LinkedHashMap<>();
        for (var entry : results.get(0).times.entrySet()) {
            data.put(entry.getKey(), new ArrayList<>());
        }

        for (RunResult r : results) {
            for (var entry : r.times.entrySet()) {
                data.get(entry.getKey()).add(entry.getValue());
            }
        }

        // 计算平均值和标准差
        Map<String, Stat> stats = new LinkedHashMap<>();
        for (var entry : data.entrySet()) {
            String method = entry.getKey();
            List<Double> vals = entry.getValue();
            double avg = vals.stream().mapToDouble(v -> v).average().orElse(0);
            double variance = vals.stream().mapToDouble(v -> (v - avg) * (v - avg)).average().orElse(0);
            double stddev = Math.sqrt(variance);
            stats.put(method, new Stat(avg, stddev));
        }

        // 排序：按平均耗时升序（最快在前）
        List<Map.Entry<String, Stat>> sorted = new ArrayList<>(stats.entrySet());
        sorted.sort(Map.Entry.comparingByValue());

        // 输出表头
        System.out.printf("%-20s %-15s %-15s %-10s%n", "Method", "Avg Time (ns)", "StdDev (ns)", "Rank");
        System.out.println("-".repeat(60));

        int rank = 1;
        for (var entry : sorted) {
            String method = entry.getKey();
            Stat stat = entry.getValue();
            System.out.printf("%-20s %-,15.2f %-,15.2f %-10d%n",
                method, stat.avg, stat.stddev, rank++);
        }

        // 性能倍数对比（相对于 Direct）
        System.out.println("\nPerformance Multiplier vs Direct:");
        Stat directStat = stats.get("Direct");
        for (var entry : sorted) {
            String method = entry.getKey();
            Stat stat = entry.getValue();
            double ratio = stat.avg / directStat.avg;
            System.out.printf("  %-20s: %.2fx%n", method, ratio);
        }

        // 最佳/最差波动
        System.out.println("\nStability Analysis:");
        sorted.forEach(e -> {
            double cv = e.getValue().stddev / e.getValue().avg; // 变异系数
            System.out.printf("  %-20s: CV = %.4f%n", e.getKey(), cv);
        });
    }

    // 内部类：单次运行结果
    private static class RunResult {
        final Map<String, Double> times;
        RunResult(Map<String, Double> times) { this.times = times; }
    }

    // 内部类：统计信息
    private static class Stat implements Comparable<Stat> {
        final double avg, stddev;
        Stat(double avg, double stddev) { this.avg = avg; this.stddev = stddev; }
        public int compareTo(Stat other) { return Double.compare(this.avg, other.avg); }
    }
}