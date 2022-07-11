package com.javacode.lambdas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CalcTime {
    // Создаем функциональный интерфейс с одним методом
    @FunctionalInterface
    interface Timerable{
        void calcTimer();
    }
    public static void main(String[] args) {
        // пример передачи функции аргументом, отметим что аргументов
        // у функции нет, поэтому ставим пустые скобки, теперь этой
        // записью мы можем замерять время выполнения любой функции
        // в нашем коде, что экономит уйму времени, вернемся в LambdaExample 47
        TimeUtil.measure(()->fillList());

    }
    // метод заполняет массив размером 10млн случайными числами
    private static List<Integer> fillList() {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10000000; i++) {
            list.add(random.nextInt(100));
        }
        return list;
    }
    // класс для подсчета времени выполнения операции
    protected static class TimeUtil{
        // с помощью системного времени в милисекундах
        // отмеряем время выполнения операции
        private static void measure(Timerable function) {
            long start = System.currentTimeMillis();
            function.calcTimer();
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        }
    }
}
