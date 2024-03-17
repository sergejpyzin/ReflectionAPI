package ru.sergej;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class  TestRunner {

  public static void run(Class<?> testClass) {
    final Object testObj = initTestObj(testClass);

    List<Method> beforeAllMethods = new ArrayList<>();
    List<Method> beforeEachMethods = new ArrayList<>();
    List<Method> testMethods = new ArrayList<>();
    List<Method> afterEachMethods = new ArrayList<>();
    List<Method> afterAllMethods = new ArrayList<>();

    // Получаем все методы класса
    for (Method method : testClass.getDeclaredMethods()) {
      // Сортируем методы по аннотациям
      if (method.isAnnotationPresent(BeforeAll.class)) {
        beforeAllMethods.add(method);
      } else if (method.isAnnotationPresent(BeforeEach.class)) {
        beforeEachMethods.add(method);
      } else if (method.isAnnotationPresent(Test.class)) {
        testMethods.add(method);
      } else if (method.isAnnotationPresent(AfterEach.class)) {
        afterEachMethods.add(method);
      } else if (method.isAnnotationPresent(AfterAll.class)) {
        afterAllMethods.add(method);
      }
    }

    try {
      // Запускаем методы перед всеми тестами
      for (Method beforeAllMethod : beforeAllMethods) {
        beforeAllMethod.invoke(testObj);
      }

      // Запускаем каждый тест в отдельности
      for (Method testMethod : testMethods) {
        // Запускаем методы перед каждым тестом
        for (Method beforeEachMethod : beforeEachMethods) {
          beforeEachMethod.invoke(testObj);
        }

        // Запускаем сам тест
        testMethod.invoke(testObj);

        // Запускаем методы после каждого теста
        for (Method afterEachMethod : afterEachMethods) {
          afterEachMethod.invoke(testObj);
        }
      }

      // Запускаем методы после всех тестов
      for (Method afterAllMethod : afterAllMethods) {
        afterAllMethod.invoke(testObj);
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
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
