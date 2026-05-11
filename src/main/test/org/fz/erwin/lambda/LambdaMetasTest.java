package org.fz.erwin.lambda;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class LambdaMetasTest {

    @Test
    void constructorLambdasCreateInstancesForSupportedArities() {
        Supplier<Person> noArgs = LambdaMetas.lambdaConstructor(Person.class);
        Function<String, Person> oneArg = LambdaMetas.lambdaConstructor(Person.class, String.class);
        BiFunction<String, Integer, Person> twoArgs = LambdaMetas.lambdaConstructor(Person.class, String.class,
                                                                                   Integer.class);

        assertEquals("unknown", noArgs.get().getName());
        assertEquals("alice", oneArg.apply("alice").getName());

        Person bob = twoArgs.apply("bob", 18);
        assertEquals("bob", bob.getName());
        assertEquals(18, bob.getAge());
    }

    @Test
    void getterAndSetterLambdasUseMethodNames() {
        Person person = new Person("alice", 18);
        Function<Person, String> getter = LambdaMetas.lambdaGetter(Person.class, String.class, "getName");
        BiConsumer<Person, String> setter = LambdaMetas.lambdaSetter(Person.class, String.class, "setName");

        assertEquals("alice", getter.apply(person));

        setter.accept(person, "bob");
        assertEquals("bob", getter.apply(person));
    }

    @Test
    void getterAndSetterLambdasCanBeBuiltFromFields() throws NoSuchFieldException {
        Person person = new Person("alice", 18);
        Field name = Person.class.getDeclaredField("name");

        Function<Person, String> getter = LambdaMetas.lambdaGetter(name);
        BiConsumer<Person, String> setter = LambdaMetas.lambdaSetter(name);

        assertEquals("alice", getter.apply(person));

        setter.accept(person, "carol");
        assertEquals("carol", getter.apply(person));
    }

    @Test
    void constructorFailuresAreWrappedInLambdasException() {
        Try.LambdasException exception = assertThrows(Try.LambdasException.class,
                                                      () -> LambdaMetas.lambdaConstructor(NoDefaultConstructor.class));

        assertTrue(exception.getMessage().contains("can not generate lambda constructor"));
        assertInstanceOf(NoSuchMethodException.class, exception.getCause());
    }

    @Test
    void accessorFailuresUseIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                     () -> LambdaMetas.lambdaGetter(Person.class, String.class, "missing"));
        assertThrows(IllegalArgumentException.class,
                     () -> LambdaMetas.lambdaSetter(Person.class, String.class, "missing"));
    }

    static class Person {
        private String  name;
        private Integer age;

        public Person() {
            this("unknown", 0);
        }

        public Person(String name) {
            this(name, 0);
        }

        public Person(String name, Integer age) {
            this.name = name;
            this.age  = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }
    }

    static class NoDefaultConstructor {
        public NoDefaultConstructor(String value) {
        }
    }
}
