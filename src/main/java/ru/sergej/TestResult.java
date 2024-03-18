package ru.sergej;

public class TestResult {

    private final boolean passed;
    private final String testName;

    public TestResult(String testName, boolean passed) {
        this.testName = testName;
        this.passed = passed;
    }

    public String getTestName() {
        return testName;
    }

    public boolean isPassed() {
        return passed;
    }

    @Override
    public String toString() {
        return "Тест '" + testName + "': " + (passed ? "пройден" : "не пройден");
    }
}
