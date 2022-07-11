package com.javacode.helloWorld.methods;

import java.util.Locale;

public class Methods {
    public static void main(String[] args) {
        printMessage("Art");
        System.out.println(calcRectanglesSquare(5, 10));
        System.out.println(calcSquare(5));
        int square = calcSquare(10);// чтобы автоматически создать переменную
        // для этого метода надо нажать кнтрл+альт+V, не работает для void
        // чтобы продублировать строку надо нажать кнтрл+D
        // чтобы посмотреть сам метод необходимо зажав кнтрл
        // нажать по нему левой кнопкой мыши, это работает и с переменными
        // и с обьектами

        /*String*/

        String str1 = "I like coffee";
        //приводим строку к верхнему регистру
//        System.out.println(str1.toUpperCase());
//        // приводим строку к нижнему регистру
//        System.out.println(str1.toLowerCase());
        String str3 = "I like coffee!!!";
        // с помощью этого метода класса мы можем взять часть строки
        //указывая индекс начального символа(включается) и индекс конечного
        // (не включается)
        String s = str3.substring(0, 13);
        // здесь мы создаем новый обьект класса String в новой области памяти
        String str4 = new String("I like black coffee");

        String str2 = str1;
        boolean b = str2 == str3; // мы так же можем сравнивать строки
        // при этом сравниваются ссылки на область памяти
        // если кратко то пожоже на питон, в памяти хранятся не две одинаковых
        // строки а одна на которую ссылются раззные переменные
        System.out.println(b);
        // вывод false несмотря на то что мы имеем один и тот же текст
        // все потому что это теперь два разных обьекта с разными областями
        // памяти. Чтобы сравнивать не обьекты а значения воспользуемся
        // методом equals()
        boolean b1 = str2.equals(str4);
//        System.out.println(b1);

        // с помощью этого метода мы можем проверить есть ли вхождения
        // префикса в строку (от начала), второй аргумент - количество символов
        // которые мы пропускаем прежде чем начинаем сравнивать
        boolean iLike = str1.startsWith(" like", 1);
        System.out.println(iLike);
        // есть такой же метод который проверяет вхождения постфикса
        boolean fee = str1.endsWith("fee");
        System.out.println(fee);

        // с помощью этого метода мы заменяем какой то текст в строке
        System.out.println(str4.replace("black", "white"));

        // этот метод возвращает индекс первого символа в искомой строке
        // если есть вхождения, если их нет вернет -1
        System.out.println(str3.indexOf("!!!"));

    }

    static void printMessage(String name) {
        System.out.println("Hello " + name);
    } // создавая метод мы должны указать что он возвращает (void), а

    // так же если мы будем вызывать этот метод из статического метода,
    // то мы так же должны сделать этот метод статическим
    // при указании параметра мы должны указать его тип
    static int calcRectanglesSquare(int x, int y) { // после static
        // мы указываем тип возвращаемого значения
        int square = x * y;
        return square;
    }

    static int calcSquare(int x) {
        return x * x;
    }


}
