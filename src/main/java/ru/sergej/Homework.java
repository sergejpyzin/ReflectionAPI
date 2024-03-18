package ru.sergej;

import ru.sergej.annotations.*;

import java.util.List;

public class Homework {

  /*
   * Доделать запускатель тестов:
   * 1. Создать аннотации BeforeEach, BeforeAll, AfterEach, AfterAll
   * 2. Доработать класс TestRunner так, что
   * 2.1 Перед всеми тестами запускаеются методы, над которыми стоит BeforeAll
   * 2.2 Перед каждым тестом запускаются методы, над которыми стоит BeforeEach
   * 2.3 Запускаются все тест-методы (это уже реализовано)
   * 2.4 После каждого теста запускаются методы, над которыми стоит AfterEach
   * 2.5 После всех тестов запускаются методы, над которыми стоит AfterAll
   * Другими словами, BeforeAll -> BeforeEach -> Test1 -> AfterEach -> BeforeEach -> Test2 -> AfterEach -> AfterAll

   * 3.* Доработать аннотацию Test: добавить параметр int order,
   * по котрому нужно отсортировать тест-методы (от меньшего к большему) и запустить в нужном порядке.
   * Значение order по умолчанию - 0
   * 4.** Создать класс Asserter для проверки результатов внутри теста с методами:
   * 4.1 assertEquals(int expected, int actual)
   * Идеи реализации: внутри Asserter'а кидать исключения, которые перехвываются в тесте.
   * Из TestRunner можно возвращать какой-то объект, описывающий результат тестирования.
   */

    public static void main(String[] args) {

        List<TestResult> results = TestRunner.run(Homework.class);
        for (TestResult result : results) {
            System.out.println("Тест '" + result.getTestName() + "': " + (result.isPassed() ? "пройден" : "не пройден"));
        }
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before All");
    }

    @BeforeEach
    static void beforeEach() {
        System.out.println("before Each");
    }

    @Test(order = 2)
    void test1() {
        System.out.println("test1");
        Asserter.assertEquals(2 + 2, 4);
    }

    @Test(order = 3)
    void test2() {
        System.out.println("test2");
        Asserter.assertEquals(3 * 3, 9);
    }

    @Test(order = 1)
    void test3() {
        System.out.println("test3");
        Asserter.assertEquals(5 - 2, 3);
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
