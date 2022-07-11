package com.javacode.collections.arrays;


import java.util.Arrays;
import java.util.Scanner;

public class ArrayLauncher {
    // обьявляем поле класса в которое будет приходить ввод с клавиатуры
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        // инициализация массива, обьявляем переменную того типа
        // который мы будем помещать в массив, у нас int,
        // после этого ставим [] и присваиваем значение с помощью
        // new где снова указываем тип массива, а в квадратных
        // скобках указываем на сколько ячеек этот массив, для того
        // чтобы java выделила под него память
        int[] myArray = new int[10];
        // заполняем данные в массиве вручную при инициализации
        int[] newArray = {1, 2, 3, 4};
        // заполняем массив данными в цикле
        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = i;
        }

        // заполняем массив вручную с консоли

        int[] consoleArray = new int[8];


        for (int i = 0; i < consoleArray.length; i++) {
            System.out.println("Enter values for index #" + i);
            consoleArray[i] = scan.nextInt();
        }

        // выводим на экран заполненый массив
        System.out.println("Print array");
        System.out.println(Arrays.toString(consoleArray));
        // выводим отсортированный массив
        System.out.println(Arrays.toString(sort(consoleArray)));


    }

    // сортируем данные в массиве
    private static int[] sort(int[] array) {
        // алгоритм сортировки, работает за O(N^2)
        // что крайне неэффективно, но для учебного примера используем его
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        // копируем элементы массива, можно циклом
        // первым аргументом указываем массив который копируем
        // вторым сколько элементов копируем, здесь копируем весь массив
        int[] myNewArray = Arrays.copyOf(array, array.length);

        return myNewArray;

    }


}
