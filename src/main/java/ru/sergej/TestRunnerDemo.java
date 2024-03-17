package ru.sergej;

public class TestRunnerDemo {

    // private никому не видно
    // default (package-private) внутри пакета
    // protected внутри пакета + наследники
    // public всем

    public static void main(String[] args) {
        TestRunner.run(TestRunnerDemo.class);
    }

    @Test
    void test1() {
        System.out.println("test1");
    }

    @Test
    void test2() {
        System.out.println("test2");
    }

    @Test
    void test3() {
        System.out.println("test3");
    }

}
