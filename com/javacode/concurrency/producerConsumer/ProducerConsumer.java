package com.javacode.concurrency.producerConsumer;

import com.javacode.concurrency.threadLesson.ColorScheme;
import netscape.javascript.JSObject;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.javacode.concurrency.threadLesson.ColorScheme.GREEN;
import static com.javacode.concurrency.threadLesson.ColorScheme.RED;

public class ProducerConsumer {

    // одним из основных паттернов работы с многопоточностью является Producer - Consumer
    // его суть заключается в том, что один поток(Producer) берет задания и кладет их в очередь(queue),
    // откуда потоки-Consumer-ы их забирают и обрабатывают, не будем писать ручную реализацию, но если кратко
    // мы заводим некий массив, это будет буфер заданий, заводим отдельную final переменную где указываем
    // максимальный размер буфера, до тех пор пока длина массива не равна этой переменной работает Producer,
    // складывая в массив обьекты-задания, когда длина сравнивается он переходит в WAIT,
    // Consumer-ы напротив работают только пока в буфере есть хоть один обьект, забирая их оттуда,
    // если обьектов нет переходят в WAIT, достигается это двумя методами wait() и notify().

    // однако в java есть уже готовая имплементация данного паттерна, она называется BlockingQueue
    // создадим обьект этого интерфейса и параметризируем его String, при инициализации
    // вызываем конструктор коллекции ArrayBlockingQueue, есть несколько коллекций которые мы
    // можем использовать, сейчас нам подходит эта, она реализует кольцевой буфер, то есть
    // после последнего элемента снова идет первый, используя кольцевой буфер мы должны задать его размер
    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) throws IOException {
        // теперь нам потребуется две имплементации интерфейса Runnable, вынесем их в отдельные
        // классы, Producer и Consumer, перейдем к ним
        // запускаем потоки
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();

    }

    private static class Producer implements Runnable {
        // здесь нам потребуется какая то коллекция, которую мы будем передавать в буфер
        // мы используем массив String в который положили стих Маяковского
        String[] message = {"Десять прошло.", "Понимаете?", "Десять!", "Как же ж", "поэтам не стараться?",
                "Как", "на театре", "актерам не чудесить?", "Как", "не литься", "лавой демонстраций?",
                "Десять лет —", "сразу не минуют.", "Десять лет —", "ужасно много!", "А мы", "вспоминаем",
                "любую из минут.", "С каждой", "минутой", "шагали в ногу.", "", "Кто не помнит только",
                "переулок", "Орликов?!", "В семнадцатом", "из Орликова", "выпускали голенькова.",
                "А теперь", "задираю голову мою", "на Запад", "и на Восток,", "на Север", "и на Юг.",
                "Солнцами", "окон", "сияет Госторг,", "Ваня", "и Вася —", "иди,", "одевайся!", "",
                "Полдома", "на Тверской", "(Газетного угол).", "Всю ночь", "и день-деньской —",
                "сквозь окошки", "вьюга.", "Этот дом", "пустой", "орал", "на всех:", "— Гражданин,", "стой!", "Руки вверх! —",
                "Не послушал окрика,—", "от тебя —", "мокренько.", "Дом —", "теперь:", "огня игра.",
                "Подходи хоть ночью ты!", "Тут", "тебе", "телеграф —", "сбоку почты.", "Влю-", "блен",
                "весь-", "ма —", "вмес-", "то", "пись-", "ма", "к милке", "прямо", "шли телеграммы.", "На Кузнецком",
                "на мосту,", "где дома", "сейчас", "растут,—", "помню,", "было:", "пала", "кобыла,", "а толпа", "над дохлой",
                "голодная", "охала.", "А теперь", "магазин", "горит", "для разинь.", "Ваня", "наряден.",
                "Идет,", "и губа его", "вся", "в шоколаде", "с фабрики Бабаева.", "", "Вечером", "и поутру,",
                "с трубами", "и без труб —", "подымал", "невозможный труд", "улиц", "разрушенных",
                "труп.", "Под скромностью", "ложной", "радость не тая,", "ору", "с победителями",
                "голода и тьмы:", "— Это —", "я!", "Это —", "мы!", "DONE"};

        // переопределяем run()
        @Override
        public void run() {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // основной метод, инициализируем обьект Random, в цикле с помощью метода put() добавляем
        // в буфер строки, put() используем потому что он выбрасывает исключения, далее выводим
        // информационное сообщение и на случайный промежуток(до одной секунды) приостанавливаем поток,
        // иммитируя загружку программы
        private void produce() throws InterruptedException {
            Random random = new Random();
            for (int i = 0; i < message.length; i++) {
                queue.put(message[i]);
                System.out.println(GREEN + "Producing " + message[i] + ". Queue size is " + queue.size());
                Thread.sleep(random.nextInt(100));
            }
        }
    }

    // аналогично Producer создаем метод забирающий задания
    private static class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // здесь довольно интересная конструкция, запускаем цикл while, но условием ничего не ставим,
        // точнее ставим true, условие выхода мы сделаем позже
        private void consume() throws InterruptedException {
            Random random = new Random();
            while (true) {
                // для того чтобы взять задание пользуемся методом take(), он возвращает первый
                // элемент в очереди и удаляет его
                String take = queue.take();
                System.out.println(RED + "Consuming " + take + " Queue size is " + queue.size());
                // в конце нашего массива мы добавили строку как флаг конца массива, пользуясь
                // методом String.equals() проверяем, если элемент не равен этой строке, на случайное
                // время приостанавливаем поток, если равен завершаем работу метода, вернемся в main()
                // и проверим как все это работает
                if (!"DONE".equals(take)) {
                    Thread.sleep(random.nextInt(1000));
                } else return;

            }
        }

    }
}
