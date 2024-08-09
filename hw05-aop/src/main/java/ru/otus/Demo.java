package ru.otus;

import ru.otus.proxy.AopLogger;
import ru.otus.testclasses.SecureInterface;
import ru.otus.testclasses.SecureInterfaceImpl;
import ru.otus.testclasses.TestLogging;
import ru.otus.testclasses.TestLoggingInterface;

public class Demo {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {

        TestLoggingInterface testLog =
                (TestLoggingInterface) AopLogger.addLogging(new TestLogging(), TestLoggingInterface.class);
        testLog.calculate("Calculus test!");
        testLog.calculate(42, "Question");
        testLog.calculate(1, 2, "Three");

        System.out.println();

        SecureInterface securePrint =
                (SecureInterface) AopLogger.addLogging(new SecureInterfaceImpl(), SecureInterface.class);
        securePrint.securePrint("Secure info");
        securePrint.insecurePrint("Hello, world!");
        securePrint.insecurePrint("Good-by, world!", 2);
    }
}
