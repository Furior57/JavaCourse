package com.javacode.concurrency.volatileLesson;

import com.javacode.concurrency.threadLesson.ColorScheme;

public class VolatileMain {

    private static volatile int counter=0;

    public static void main(String[] args) {
        // поговорим о волатильности.
        // мы значем что потоки обрабатывают информацию в случайном порядке, у кого больше ресурсов,
        // тот и будет первым, однако это может привести к крайне неприятным последствиям.
        // немного углубимся в механику работы потоков, при работе с какими то данными, например с
        // переменными, поток берет значение переменной, сохраняет его у себя в кеше, обрабатывает
        // и возвращает результат, изменяя переменную, проблема в том что у нас может быть запущен
        // параллельный поток который тоже обращается к этой переменной и у него тоже есть свой кеш
        // в результате мы сталкиваемся с ситуацией когда одни потоки работают с актуаной переменной,
        // а другие с устаревшей. решается эта проблема с помощью ключевого слова volatile,
        // если мы помечаем какую либо переменную этим словом, мы запрещаем потокам кешировать ее и
        // ее значение всегда хранится в памяти в актуальном состоянии

        // напишем код который симулирует данную ситуацию, для начала перейдем во внутренний класс
        // SimpleWriter
        // запустим потоки наших классов
        new SimpleReader().start();
        new SimpleWriter().start();
        // теперь перейдем в класс FibonacciConcurrent


    }

    // небольшое напоминание, для работы с потоками лучше имплементировать интерфейс Runnable,
    // но тут мы расширим класс Thread
    private static class SimpleWriter extends Thread {
        // переопределим метод run(), нам понадобится переменная котроую мы будем изменять
        // определим ее в самом классе VolatileMain
        // в этом потоке мы будем добавлять к counter единицу
        @Override
        public void run() {
            int localCounter = counter;
            for (int i = 0; i < 10; i++) {
                System.out.println(ColorScheme.BLUE + "writer increments counter " + (localCounter + 1));
                counter = ++localCounter;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // перейдем в класс SimpleReader
            }
        }
    }

    // в этом классе мы реализуем поток считывающий переменную
    private static class SimpleReader extends Thread {
        // запускаем цикл while до тех пор пока наша локальная переменная не будет равна 10
        // если мы запустим нашу программу сейчас, она упадет в бесконечный цикл, почему?
        // дело в том что потоки завершаются с завершением программы, то есть наш writer
        // взял значение глобальной переменной, сохранил его в кеше и там и держит, глобальная
        // переменная не меняется, для того чтобы все отработало как надо нам нужно добавить
        // к ней ключевое слово volatile, после этого поток перестанет использовать кеш и будет
        // сразу передавать все изменения в переменную, вернемся в main()
        @Override
        public void run() {
            int localCounter = counter;
            while (localCounter <10) {
                if (localCounter != counter) {
                    System.out.println(ColorScheme.GREEN + "Reader reads counter " + counter);
                    localCounter=counter;
                }
            }

        }
    }


}
