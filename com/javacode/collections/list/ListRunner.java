package com.javacode.collections.list;

import java.util.Scanner;

public class ListRunner {
    // создаем сканнер для ввода параметров
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        // создаем инстанс класса для дальнейшего использования
        ToDoList list = new ToDoList();

        // при создании массива мы указываем его размер,
        // но что делать если нам в дальнейшем необходимо увеличить размер массива?
        // если мы будем работать только с массивом, то выглядеть это будет так:
        // создали первый массив и заполнили его значениями
        int[] array = new int[5];
        for (int i = 0; i < array.length; i++) {
            array[i] = i * 2;
        }
        // потребовалось расширить массив, для этого мы создали новый,
        // большего размера и скопировали в него старый массив,
        // ссылочку на массив потом желательно удалить чтобы не занимал
        // место в памяти, либо присвоить ей новый массив
        int[] nArray = new int[10];
        System.arraycopy(array, 0, nArray, 0, array.length);
        nArray[6] = 100;
//        System.out.println(Arrays.toString(nArray));

        // Преимущество массива состоит в скорости получения
        // данных. Какого бы размера не был массив скорость всегда O(1)
        // но мы ограничены размером массива, это константная величина
        // указывающаяся при создании массива, так что расширить его
        // не получится. Для этого создан List, это динамический
        // массив. Более подробно в классе ToDoList

        // здесь мы будем реализовывать управление классом ToDoList
        // информационное сообщение с описанием параметров
        printOut();
        // переменная принимающая значение с клавиатуры
        int param = scanner.nextInt();
        // здесь подойдет while, если ввод будет 0, программа завершится
        while (param != 0) {
            // реализация параметров
            switch (param) {
                case 1:
                    System.out.println("please enter a task to add");
                    // здесь пользователь вводит задачу
                    // есть интересная особенность, необходимо перед считыванием
                    // самой задачи из консоли прописать nextLine(),
                    // если этого не сделать, то мы перейдем сразу к строке с
                    // просьбой ввести номер параметра, это произошло потому
                    // что перед этим мы попросили ввести номер параметра
                    // с помощью метода nextInt(), мы считали только число
                    // игнорируя конец строки, который как раз таки считывает
                    // nextLine(), и поэтому получив конец строки он сразу
                    // завершается, для правильной работы
                    // мы сначала вызываем nextLine() чтобы считать конец строки,
                    // а потом его же еще раз чтобы получить задачу
                    scanner.nextLine();
                    String task1 = scanner.nextLine();
                    list.addToList(task1);
                    // предлагаем пользователю снова выбрать что сделать
                    System.out.println("Please choose an action by typing (0-6)");
                    param = scanner.nextInt();
                    break;
                case 2:
                    System.out.println("printing out TO DO LIST");
                    scanner.nextLine();
                    list.printToDoList();
                    System.out.println("Please choose an action by typing (0-6)");
                    param = scanner.nextInt();
                    break;
                case 3:
                    System.out.println("Please enter an index to update");
                    scanner.nextLine();
                    int i = scanner.nextInt();
                    System.out.println("Please enter a new task");
                    scanner.nextLine();
                    String task3 = scanner.nextLine();
                    list.insertTask(i, task3);
                    System.out.println("Please choose an action by typing (0-6)");
                    param = scanner.nextInt();
                    break;
                case 4:
                    System.out.println("Please enter a task to remove");
                    scanner.nextLine();
                    String task4 = scanner.nextLine();
                    list.removeTask(task4);
                    System.out.println("Please choose an action by typing (0-6)");
                    param = scanner.nextInt();
                    break;
                case 5:
                    System.out.println("Please enter the task to get priority");
                    scanner.nextLine();
                    String task5 = scanner.nextLine();
                    System.out.println("Priority of the task is "+
                            list.getTaskPriority(task5));
                    System.out.println("Please choose an action by typing (0-6)");
                    param = scanner.nextInt();
                    break;
                case 6:
                    System.out.println("Please enter a number of position for the task");
                    scanner.nextLine();
                    int index = scanner.nextInt();
                    System.out.println("Please enter a new task");
                    scanner.nextLine();
                    String task6 = scanner.nextLine();
                    list.insertTask(index, task6);
                    System.out.println("Please choose an action by typing (0-6)");
                    param = scanner.nextInt();
                    break;
                default:
                    param = 0;
            }
        }


    }

    // создаем управление нашим листом, реализация методов в классе ToDoList
    private static void printOut() {
        System.out.println("Please choose an action. Press: \n"
                + "1) to add a new item into ToDoList\n"
                + "2) to print out the list\n"
                + "3) to update an existing item\n"
                + "4) to remove an item from the list\n"
                + "5) to get task priority or number in the list\n"
                + "6) to add a new item at specific position\n"
                + "press 0 for exit\n\n"
                + "AFTER CHOOSING AN OPTION PLEASE PRESS ENTER");
    }
}
