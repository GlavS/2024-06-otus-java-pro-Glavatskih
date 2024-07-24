package ru.otus.testrunner;

import static ru.otus.testrunner.TestHelper.*;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestRunner {
    private TestRunner() {}

    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    public static void runTests(Class<?> testClass) {
        int successCounter = 0;
        Method[] testMethods = getTestMethods(testClass, Test.class);
        if (testMethods.length == 0) {
            logger.warn("No test methods found");
            return;
        }
        Method[] beforeMethods = getTestMethods(testClass, Before.class);
        Method[] afterMethods = getTestMethods(testClass, After.class);

        for (Method testMethod : testMethods) {
            Object testClassInstance = instantiateTestClass(testClass);
            for (Method beforeMethod : beforeMethods) {
                invokeConfigMethod(beforeMethod, testClassInstance);
            }
            successCounter += invokeTestMethod(testMethod, testClassInstance);
            for (Method afterMethod : afterMethods) {
                invokeConfigMethod(afterMethod, testClassInstance);
            }
        }
        logger.info("Tests complete: {}", testMethods.length);
        logger.info("Tests successful: {}", successCounter);
        if (successCounter < testMethods.length) {
            logger.error("Tests failed: {}", testMethods.length - successCounter);
        }
    }
}
