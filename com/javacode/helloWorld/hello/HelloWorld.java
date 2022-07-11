package com.javacode.helloWorld.hello;

public class HelloWorld {
    // Привет мир!
    public static void main(String[] args) {
        System.out.println("Hello world!");

        /* Типы данных */

        byte b = 10;
        System.out.println("byte = " + b);// в java с помощью символа + мы
        // можем делать конкатенацию строк, тип переменной при этом приведется
        // к типу string
        short sh = 23;
        int i = b + sh;
        long l = 24L;   // хорошим тоном считается ставьть L в конце числа
        // к тому же по умолчанию компилятор считает этот тип как int
        // чтобы обойти это надо записать число в формате
        // 10_123_123_123 то есть по разрядам
        float myFloatValue = 2.24F; // согласно конвенции первое слово в
        // названии переменной начинается с маленькой буквы, остальные
        // с большой
        double db = 2.24;

        double sum = b + sh + i + l + myFloatValue;
        System.out.println("SUM = " + sum);

        char ch = 'H';  // для обьявления типа char в значении используются
        // одинарные кавычки и один символ
        // символы можно записывать непосредственно в кодировке UTF-8
        char ch2 = '\u00A9';
        System.out.println("Char = " + ch + " " + ch2);
        // если мы в выводе не укажем двойные кавычки, а просто
        // напишем названия переменных типа char, то он вывод
        // будет в виде чисел которые соответствуют этим символам
        // в таблице символов
        System.out.println(ch + ch2);   // вывод: 241, сумма чисел символов

        boolean bl1 = true;
        boolean bl2 = false;

        String s = "It's string"; // это непримитивный тип данных
        // он реализован как отдельный класс
        System.out.println(s);


        /* Приведение типов данных */


        short someShort = b;
        System.out.println("someSHort = " + someShort);
        // приведение типа byte к типу short
        // важно помнить что каждый тип вмещает в себя определенное количество
        // байт, поэтому не получится привести short к byte, первый
        // больше размером. Из-за этого же может потеряться точность
        // например если мы приведем тип long к типу double,
        // размер у них вроде бы один и тот же, может быть погрешность
        // однако есть возможность принудительно привести тип
        // заведем переменную int
        int someInt = someShort;
        someShort = (short) someInt;
//         здесь мы явно указываем тип к которому приводится переменная
//         однако этой возможностью нужно пользоваться с осторожностью
//         мы можем потерять часть значения которое приводим к чему то

        double someDouble = myFloatValue;
        System.out.println(someDouble); // изначальное значение было 2.24
        // приведение float к double не может быть сделано без потери
        // точности значения, а int, например, можно. тип long
        // тоже потеряет точность значения





    }
}
