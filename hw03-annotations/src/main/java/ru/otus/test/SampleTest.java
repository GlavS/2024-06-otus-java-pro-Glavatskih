package ru.otus.test;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

@SuppressWarnings("java:S106")
public class SampleTest {

    @Before
    void setup() {
        System.out.println("Setup......");
    }

    @Before
    void before() {
        System.out.println("Second setup.....");
    }

    @Test
    void testPrintOne() {
        System.out.println("Hello World first!");
    }

    @Test
    void testPrintTwo() {
        System.out.println("Hello World second!");
    }

    @Test
    void testPrintThree() {
        throw new AssertionError("Assertion error!");
    }

    @Test
    void testPrintFour() {
        System.out.println("Hello World fourth!");
    }

    @After
    void tearDown() {
        System.out.println("Tear down......");
    }
}
