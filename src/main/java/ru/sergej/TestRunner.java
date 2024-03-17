package ru.sergej;


import ru.sergej.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class  TestRunner {

  public static void run(Class<?> testClass) {
    final Object testObj = initTestObj(testClass);

    // Выполнение методов BeforeAll перед всеми тестами
    invokeAnnotatedMethods(testClass, BeforeAll.class);

    // Получение всех тест-методов
    List<Method> testMethods = new ArrayList<>();
    for (Method method : testClass.getDeclaredMethods()) {
      if (method.isAnnotationPresent(Test.class)) {
        testMethods.add(method);
      }
    }

    // Сортировка тест-методов по порядку (если добавить пункт 3)
    testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).order()));

    // Выполнение всех тест-методов
    for (Method testMethod : testMethods) {
      // Выполнение методов BeforeEach перед каждым тестом
      invokeAnnotatedMethods(testClass, BeforeEach.class);
      try {
        testMethod.invoke(testObj);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
      // Выполнение методов AfterEach после каждого теста
      invokeAnnotatedMethods(testClass, AfterEach.class);
    }

    // Выполнение методов AfterAll после всех тестов
    invokeAnnotatedMethods(testClass, AfterAll.class);
  }

  private static void invokeAnnotatedMethods(Class<?> testClass, Class<? extends Annotation> annotation) {
    final Object testObj = initTestObj(testClass);
    for (Method method : testClass.getDeclaredMethods()) {
      if (method.isAnnotationPresent(annotation)) {
        try {
          method.invoke(testObj);
        } catch (IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private static Object initTestObj(Class<?> testClass) {
    try {
      Constructor<?> noArgsConstructor = testClass.getDeclaredConstructor();
      return noArgsConstructor.newInstance();
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException("Не удалось создать объект тест класса");
    }

  }
}
