package moviebuddy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTests {

    @Test
    void objectCreateAndMethodCall() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Duck duck = new Duck();
        duck.quack();

        Class<?> duckClass = Class.forName("moviebuddy.ReflectionTests$Duck");
        Object duckObject = duckClass.getDeclaredConstructor().newInstance();
        Method quack = duckClass.getDeclaredMethod("quack", new Class<?>[0]);
        quack.invoke(duckObject);
    }

    static class Duck {
        void quack() {
            System.out.println("꽥!");
        }
    }
}
