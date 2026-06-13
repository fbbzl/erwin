package lang;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.BiFunction;

import static org.fz.erwin.lambda.LambdaMetas.lambdaConstructor;

/**
 * @author fengbinbin
 * @version 1.0
 * @since 2025/10/28 12:53
 */
public class LambdaMetaTest {

    @Data
    @AllArgsConstructor
    public static class Person {
        private final String name;
        private final Integer    age;


    }


    // ----------------------------
    // main 方法：测试多个构造场景
    // ----------------------------
    public static void main(String[] args) {
        // 测试 1: 3 参数构造 Person
        System.out.println("=== 测试 Person 构造 ===");
        BiFunction<String, Integer, Person> personCtor = lambdaConstructor(Person.class, String.class, Integer.class);
        Person                       p1         = personCtor.apply("Alice", 30 );
        System.out.println(p1);
    }

}
