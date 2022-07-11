package com.javacode.collections.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListRealisation {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        // добавляем задачу в начало списка, в нулевой индекс
        linkedList.addFirst("It's first data");
        linkedList.addFirst("It's second data");
        linkedList.addFirst("It's third data");
        // добавляем задачу в конец списка
        linkedList.addLast("It's addLast()");
        // есть методы getFirst() и getLast() они не удаляют полученые элементы,
        // просто выводят их

        // здесь мы пользуемся итератором. Collection расширяет интерфейс Iterable
        // и позволяет поочередно проходить по всем элементам списка
        // (либо другого итерируемого обьекта) и получать элементы

        // вызываем метод iterator() на обьекте класса LinkedList
        // и присваиваем полученый обьект переменной
        // напоминаю быстро присвоить переменную можно комбинацией
        // ctrl+alt+V
        Iterator<String> iterator = linkedList.iterator();

        // hasNext() возвращает true если следующий элемент коллекции существует
        // компилятор подсказывает нам что лучше воспользоваться циклом
        // foreach, но для учебного примера возьмем while
        while (iterator.hasNext()) {
            // next() выводит значение следующего элемента
            System.out.println("Example element of list: "+iterator.next());
        }
        // для работы с двусвязными списками есть метод listIterator
        // метод с его использованием реализован в классе toDoList

    }
    // сначала создаем LinkedList в этой области видимости
    LinkedList<String> linkedList1 = new LinkedList<>();




}
