package ru.sergej;

public class TestRunnerDemo {

    // private никому не видно
    // default (package-private) внутри пакета
    // protected внутри пакета + наследники
    // public всем

    public static void main(String[] args) {
        TestRunner.run(TestRunnerDemo.class);
    }


    @BeforeAll
    static void beforeAll() {
        System.out.println("before All");
    }

    @BeforeEach
    static void beforeEach() {
        System.out.println("before Each");
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

    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }

    @AfterAll
    void afterAll() {
        System.out.println("After All");
    }



}
