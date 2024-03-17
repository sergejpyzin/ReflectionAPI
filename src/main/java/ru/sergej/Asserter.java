package ru.sergej;

public class Asserter {

    public static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Ожидаемое значение: " + expected + ", Фактическое значение: " + actual);
        }
    }

}