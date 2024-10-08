package ru.otus;

import ru.otus.classes.SecureInterface;
import ru.otus.classes.SecureInterfaceImpl;
import ru.otus.classes.TestLogging;
import ru.otus.classes.TestLoggingInterface;
import ru.otus.proxy.AopLogger;

public class Demo {

    public static void main(String[] args) {

        TestLoggingInterface testLog =
                (TestLoggingInterface) AopLogger.addLogging(new TestLogging(), TestLoggingInterface.class);
        testLog.calculate("Calculus test!");
        testLog.calculate(42, "Question");
        testLog.calculate(1, 2, "Three");

        SecureInterface securePrint =
                (SecureInterface) AopLogger.addLogging(new SecureInterfaceImpl(), SecureInterface.class);
        securePrint.securePrint("Secure info");
        securePrint.insecurePrint("Hello, world!");
        securePrint.insecurePrint("Goodbye, world!", 2);
    }
}
