package com.javacode.collections.arrays;

import java.security.SecureRandom;
import java.util.Arrays;

public class Dies {
    public static void main(String[] args) {
        // класс выдающий случайные значения
        SecureRandom random = new SecureRandom();
        // создаем массив в котором будет учитываться сколько раз
        // выпала цифра, номер цифры это номер индекса массива
        int[] frequency = new int[7];
        for (int i = 0; i < 1000; i++) {
            // увеличиваем на единицу случайный индекс массива
            // ограниченый от 1 до 6
            ++frequency[1 + random.nextInt(6)];
        }
        // выводим значения индексов
        for (int i = 1; i < frequency.length; i++) {
            System.out.println("Side " + i + " " + frequency[i]);
        }

        multidimArrayLauncher();

    }

    // пример многомерного массива
    private static void multidimArrayLauncher() {
        // обьявляем двумерный массив и заполняем его
        // обращение к элементу будет выглядеть так a[0][1]
        // вывод при этом будет 2
        int[][] a = {{1, 2}, {2, 3}};

        // строгое указание размера массива
        int[][] b = new int[3][3];
        // указываем что в массиве будет 3 элемента, а их размер не ограничен
        int[][] c = new int[3][];
        // а так мы ограничиваем размер элемента
        c[0] = new int[2];
        // выводим в цикле содержание массива
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.println(Arrays.toString(a[j]));
                System.out.println("----------------");
            }
        }
        // другой способ итерироваться по массиву с помощью цикла foreach
        // таким способом мы можем проходить по массиву по каждому элементу
        // итератор обязательно должен быть того же типа что и элемент массива
        // в данном случае элементом массива тоже является массив
        // и i становится значением элемента, то есть мы получаем готовый
        // элемент, в данном случае массив
        for (int i[] : a) {
            System.out.println(Arrays.toString(i));
        }
    }

}
