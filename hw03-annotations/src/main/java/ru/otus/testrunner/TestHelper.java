package ru.otus.testrunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("java:S3011")
public class TestHelper {
    private static final Logger logger = LoggerFactory.getLogger(TestHelper.class);

    private TestHelper() {
        throw new UnsupportedOperationException("This is utility class, no instantiation");
    }

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
            throw new TestClassInstantiationException(e);
        }
        return instance;
    }

    public static TestExitStatus invokeTestMethod(Method method, Object testClass) {
        method.setAccessible(true);
        try {
            method.invoke(testClass);
        } catch (InvocationTargetException e) {
            logger.error(e.getCause().getMessage(), e.getCause());
            return TestExitStatus.FAIL;
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            return TestExitStatus.FAIL;
        }
        return TestExitStatus.SUCCESS;
    }

    public static void displayStats(int totalTests, int successTests) {
        logger.info("Tests complete: {}", totalTests);
        logger.info("Tests successful: {}", successTests);
        if (successTests < totalTests) {
            logger.error("Tests failed: {}", totalTests - successTests);
        }
    }

    public static void runAfterMethods(Method[] afterMethods, Object testClass) {
        for (Method afterMethod : afterMethods) {
            invokeTestMethod(afterMethod, testClass);
        }
    }

    public static TestExitStatus runBeforeMethods(Method[] beforeMethods, Object testClass) {
        for (Method beforeMethod : beforeMethods) {
            if (invokeTestMethod(beforeMethod, testClass) == TestExitStatus.FAIL) {
                return TestExitStatus.FAIL;
            }
        }
        return TestExitStatus.SUCCESS;
    }
}
