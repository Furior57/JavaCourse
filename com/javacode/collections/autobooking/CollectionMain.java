package com.javacode.collections.autobooking;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CollectionMain {
    public static void main(String[] args) {
        // Любой тип наследующий Object может быть сохранен в коллекцию
        // так же любой тип наследующий Object может быть параметром коллекции
        // пример:
        List<String> list = new LinkedList<>();
        // здесь String как параметр так и в дальнейшем элемент коллекции
        // но это не касается примитивных типов, например int, они не
        // наследуют класс Object и такая запись будет ошибочна:
        // List<int> list = new LinkedList<>();
        // мы не можем указать параметром int, но как же тогда создать
        // список из int?
        // Для того чтобы сохранять в коллекции примитивные типы и
        // использовать их как параметры были созданы type wrapper
        // они являются оберткой для примитивных типов
        // у каждого примитивного типа есть соответствующий type wrapper
        // например у int это Integer
        List<Integer> listInt = new LinkedList<>();
        // теперь мы можем записывать в коллекцию int
        // для остальных примитивных типов названия не отличаются
        // но пишутся с большой буквы Float, Double, Boolean, Byte, Long
        // исключением тут являются int и char, они пишутся как
        // Integer и Character

        // как конвертировать Array в List


        // создаем массив который хотим преобразовать в LinkedList
        String[] colors = {"yellow","green","blue"};
        // пользуемся методом Arrays.asList(), им мы преобразовали массив в
        // список и записали в LinkedList
        LinkedList<String> colorsList = new LinkedList<>(Arrays.asList(colors));
        // важный момент, если мы создаем переменную типа List
        // и передаем туда наш массив подобным образом:
        List<String> colorsArray = Arrays.asList(colors);
        // то мы не можем менять размер такого списка, он будет фиксированным,
        // однако если мы создадим новый обьект типа List в который передадим
        // наш массив, мы уже можем спокойно пользоваться полным функционалом
        // интерфейса List:
        colorsList.addLast("black");
        // а если попытаемся добавить элемент в colorsArray:
        // colorsArray.add("black");
        // то получим исключение UnsupportedOperationException
        // причем компилятор нас не предупредит об этом, так что важно
        // это помнить

        // обратно преобразовываем LinkedList в массив
        // при обратном преобразовании мы параметром для метода toArray()
        // указываем конструктор массива того типа который у нас лежит в List
        // и в конструкторе указываем размер массива, в нашем случае такой же
        // как и у List-а
        colors = colorsList.toArray(new String[colorsList.size()]);


    }
}
