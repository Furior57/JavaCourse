package com.javacode.helloWorld.controlstatement;

public class Controls {
    public static void main(String[] args) {

        int i = 10;

        {
            int k = 15; // эту переменную мы обьявили в отдельном код-блоке
            // и снаружи {} этих скобок она не видна
        }
//      System.out.println(i);
//      System.out.println(k); // этот кусок кода будет ошибочным, переменной
        // k в этой области видимости нет
        // однако переменные которые входят в область видимости верхнего уровня
        // будут видны в малых областях видимости (code-block)

        /*if-else statement*/

        int testScore = 50;
//      if (testScore > 90)
//          System.out.println(testScore);
        // при этом выполнится только первая строка после условия if
        // чтобы выполнить несколько действий их необходимо обьединить в
        // code-block
/*      if (testScore >= 90) {
            System.out.println("Excellent");
            System.out.println("It's work");
        } else {
            System.out.println("You've got a bad mark");
        }*/
        // это была простейшая конструкция, добавим несколько условий
        System.out.println("You've got");
        if (testScore >= 90) {
            System.out.println("an excellent");
        } else if (testScore >= 75) {
            System.out.println("a good");
        } else if (testScore >= 60) {
            System.out.println("a middle");
        } else {
            System.out.println("a bad");
        }
        System.out.println("mark");


    }
}
