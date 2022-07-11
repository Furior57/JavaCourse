package com.javacode.concurrency.executors;


import java.util.Random;

import static com.javacode.concurrency.threadLesson.ColorScheme.*;

// это вспомогательный класс, он нужен нам для того чтобы расчитывать максимальный делитель для двух чисел
// расширяет класс Random и имплементирует интерфейс Runnable
public class GCDRunnable extends Random implements Runnable {

    private boolean isDaemon;

    // в этот конструктор мы будем передавать булево которое отвечает за то, будет ли наш поток демоном
    public GCDRunnable(boolean isDaemon) {
        this.isDaemon = isDaemon;
    }

    // переопределяем метод run()
    @Override
    public void run() {
        // эта переменная сообщает пользовательский поток это, или демон
        String threadType = isDaemon ? " daemon " : " user ";
        // получаем имя текущего потока и записываем в строку
        String threadDescription = Thread.currentThread().getName();
        // выводим имя
        System.out.println(BLUE + "Starting " + threadDescription + threadType);
        // запускаем цикл
        for (int i = 0; i < 10000000; i++) {
            // получаем два псевдослучайных числа и присваиваем им переменные
            int a = nextInt();
            int b = nextInt();
                // чтобы не захламлять экран выводим только кратные 10000 итерации
                if (i % 10000 == 0) {
                    // вернемся в main() 38 строку и почитаем про метод interrupt()
                    // если в этом условии написать Thread.interrupted(), поток завершится как
                    // только отработает метод interrupt(), мы же поставим условие так:
                    // !Thread.interrupted(), теперь мы проверяем, если наш поток не был прерван,
                    //  мы продолжаем работу, если был - выводим сообщение и завершаем работу.
                    //  вернемся в main() 44 строку
                    if (!Thread.interrupted()) {
                    // вычисляем максимальный делитель псевдослучайных чисел
                    // и присваиваем ему переменную
                    int gcd = computeGCD(a, b);
                    // если делитель больше 5 выводим сообщение что в текущем потоке
                    // максимальный делитель от двух чисел равен такому то числу
                    if (gcd > 5) {
                        System.out.println(GREEN + "Running in " + threadDescription +
                                ". The GCD of " + a + " and " + b + " is " + gcd);
                    }
                }else {
                        System.out.println(BLUE+"Thread was interrupted");
                        return;
                    }
            }
        }
        // выводим сообщение о завершении работы потока
        System.out.println(BLUE + "Leaving the thread " + threadDescription);
    }

    // это основной метод нашего класса, он принимает два числа, проверяет не является ли
    // второе число нулем, если является то делить нельзя, возвращает первое число,
    // если не является, то начинает рекурсивно делить первое число на второе, передавая
    // в следующей итерации второе число как первое, а остаток от деления как второе,
    // когда остаток от деления станет 0, возвращает первое число, это и есть максимальный
    // делитель
    private int computeGCD(int number1, int number2) {
        if (number2 == 0) {
            return number1;
        } else {
            return computeGCD(number2, number1 % number2);
        }
    }
}
