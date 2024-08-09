package ru.otus.testclasses;

public interface SecureInterface {
    void securePrint(String info);

    void insecurePrint(String info);

    void insecurePrint(String info, int index);
}
