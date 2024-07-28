package ru.otus.testrunner;

import static ru.otus.testrunner.util.TestHelper.*;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.testrunner.util.TestExitStatus;

public class TestRunner {
    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    private TestRunner() {}

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
            if (runBeforeMethods(beforeMethods, testClassInstance) == TestExitStatus.FAIL) {
                runAfterMethods(afterMethods, testClassInstance);
            } else {
                if (invokeTestMethod(testMethod, testClassInstance) == TestExitStatus.SUCCESS) successCounter++;
                runAfterMethods(afterMethods, testClassInstance);
            }
        }
        displayStats(testMethods.length, successCounter);
    }
}
