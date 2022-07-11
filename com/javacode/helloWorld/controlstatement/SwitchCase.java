package com.javacode.helloWorld.controlstatement;

import java.util.Locale;

public class SwitchCase {
    public static void main(String[] args) {
        // Оператор switch используется для задания большого количесва условий

        int choice = 1; // эта переменная отвечает за номер case
        // который будет выполняться
        switch (choice) {
            case 1:
                System.out.println("Case 1");
                break;  // используется для выхода из условия,
            // иначе выполнятся все case
            case 2:
                System.out.println("Case 2");
                break;
            case 3:
                System.out.println("Case 3");
                break;
            default:    // этот блок срабатывает если не найден номер case
                System.out.println("Case not found");
                // в качастве параметра можно передать строку,
                // в таком случае после ключевого слова case должна идти строка,
                // регистр важен, поэтому можно принудительно привести
                // передающийся параметр к нижнему регистру
        }
        String wordForCase = args[0];   // мотаем выше где обьявлен
        // метод main, там в качастве параметров передан массив args
        // здесь мы указываем что первым элеметом этого массива
        // является данная переменная, то есть она аргумент который мы можем
        // передать извне, например с помощью консоли
        switch (wordForCase.toLowerCase()) {    // используем метод класса String
            case "hello":
                System.out.println("Case Hello");
                break;
            default:
                System.out.println("Case not Hello");
        }
        // мы так же можем совмещать несколько кейсов
        // с выполнением одного блока кода
        switch (wordForCase) {
            case "hello":
            case "TestHello":
                System.out.println("Hi, man");
        }


    }

}
