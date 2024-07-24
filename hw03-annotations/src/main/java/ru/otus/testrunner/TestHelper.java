package ru.otus.testrunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"java:S106", "java:S3011"})
public class TestHelper {
    private static final Logger logger = LoggerFactory.getLogger(TestHelper.class);

    private TestHelper() {}

    public static Method[] getTestMethods(Class<?> testClass, Class<? extends Annotation> annotation) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .toArray(Method[]::new);
    }

    public static Object instantiateTestClass(Class<?> testClass) {
        Object instance;
        try {
            Constructor<?> constructor = testClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (Exception e) {
            logger.error("Cannot instantiate test class");
            throw new TestClassInstantiationException(e.getMessage());
        }
        return instance;
    }

    public static int invokeTestMethod(Method method, Object testClass) {
        method.setAccessible(true);
        try {
            method.invoke(testClass);
        } catch (InvocationTargetException e) {
            logger.error(
                    "Error in test method {}: {}",
                    method.getName(),
                    e.getCause().getMessage());
            e.getCause().printStackTrace(System.err);
            return 0;
        } catch (IllegalAccessException e) {
            e.printStackTrace(System.err);
            return 0;
        }
        return 1;
    }

    public static void invokeConfigMethod(Method method, Object testClass) {
        method.setAccessible(true);
        try {
            method.invoke(testClass);
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace(System.err);
        } catch (IllegalAccessException e) {
            e.printStackTrace(System.err);
        }
    }
}
