package com.javacode.collections.arrays;

import java.util.Arrays;

public class GradeBook {
    public static void main(String[] args) {
        int[][] gradeArray = {{87, 96, 70},
                {68, 87, 90}, {94, 100, 90},
                {100, 81, 82}, {83, 65, 86},
                {78, 87, 65}, {85, 75, 83},
                {91, 94, 100}, {76, 72, 84},
                {87, 93, 73}};

        processArrays();
    }

    // с помощью цикла foreach проходимся по двумерному массиву
    //    и записываем минимальное значение в переменную min
    private static int calcMin(int[][] grades) {
        int min = 100;
        for (int[] row : grades) {
            for (int i : row) {
                if (min > i) {
                    min = i;
                }
            }
        }
        return min;
    }

    private static void varArgs() {
        double a = 0.56;
        double b = 1.92;
        double c = 3.45;
        double d = 5.01;

        System.out.println("Avarage og 2 elements is " + calcAverage(a, b));
        System.out.println("Avarage og 3 elements is " + calcAverage(a, b, c));
        System.out.println("Avarage og 4 elements is " + calcAverage(a, b, c, d));
    }

    // таким способом мы обьявляем метод который принимает
    // неопределенное количесво параметров
    // такая запись называется varargs или аргументы переменной длины
    private static double calcAverage(double... args) {
        double sum = 0;
        // с помощью цикла foreach проходимся по всем аргументам
        // и суммируем их а в конце выводим среднее значение
        for (double i : args) {
            sum += i;
        }
        return sum / args.length;
    }

    private static void processArrays() {
        double[] doubleArray = {8.9, 5.65, 8.12, 45.0, 77.1};
        // используем сортировку с помощью метода класса Arrays
        Arrays.sort(doubleArray);
        // выводим значения массива с помощью приведения к строке
        System.out.println(Arrays.toString(doubleArray));

        int[] filledArray = new int[7];
        // заполняем массив одним значением с помощью метода fill
        Arrays.fill(filledArray, 7);
        System.out.println(Arrays.toString(filledArray));

        int[] intArray = {1,2,3,4,5,6,7};
        int[] arrayCopy = new int[10];
        // копируем один массив в другой с помощью arraycopy
        // аргументы - копируемый массив, позиция с которой копируем
        // массив куда копируем, позиция с которой начинаем копировать,
        // сколько элементов копируем
        System.arraycopy(intArray, 0, arrayCopy,
                0, intArray.length);

        System.out.println(Arrays.toString(arrayCopy));

    }
}
