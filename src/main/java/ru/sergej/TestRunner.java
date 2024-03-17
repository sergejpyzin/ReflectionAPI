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

  public static List<TestResult> run(Class<?> testClass) {
    List<TestResult> results = new ArrayList<>();
    final Object testObj = initTestObj(testClass);

    invokeAnnotatedMethods(testClass, BeforeAll.class);

    List<Method> testMethods = new ArrayList<>();
    for (Method method : testClass.getDeclaredMethods()) {
      if (method.isAnnotationPresent(Test.class)) {
        testMethods.add(method);
      }
    }

    testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).order()));

    for (Method testMethod : testMethods) {
      invokeAnnotatedMethods(testClass, BeforeEach.class);
      try {
        testMethod.invoke(testObj);
        results.add(new TestResult(testMethod.getName(), true));
      } catch (IllegalAccessException | InvocationTargetException e) {
        System.out.println(e.getMessage());
        results.add(new TestResult(testMethod.getName(), false));
      }
      invokeAnnotatedMethods(testClass, AfterEach.class);
    }

    invokeAnnotatedMethods(testClass, AfterAll.class);

    return results;
  }

  private static void invokeAnnotatedMethods(Class<?> testClass, Class<? extends Annotation> annotation) {
    final Object testObj = initTestObj(testClass);
    for (Method method : testClass.getDeclaredMethods()) {
      if (method.isAnnotationPresent(annotation)) {
        try {
          method.invoke(testObj);
        } catch (IllegalAccessException | InvocationTargetException e) {
          System.out.println(e.getMessage());
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
