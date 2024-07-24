package ru.otus;

import ru.otus.test.SampleTest;
import ru.otus.testrunner.TestRunner;

public class Main {
    public static void main(String[] args) {
        TestRunner.runTests(SampleTest.class);
    }
}
