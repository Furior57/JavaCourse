package com.javacode.concurrency.locks;

import com.javacode.concurrency.threadLesson.ColorScheme;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.javacode.concurrency.threadLesson.ColorScheme.GREEN;
import static com.javacode.concurrency.threadLesson.ColorScheme.RED;

public class WaitingQueue {

    // мы узнали несколько разных реализаций паттерна Producer-Consumer, теперь поговорим о семафорах
    // в предыдущих лекциях мы строили наш код так, чтобы один поток блокировал доступ к локу, когда он его
    // использует, плюс мы уже упонимали что mutex обьекта это бинарный семафор. теперь мы изучим
    // сами семафоры. их суть заключается в том, что они могут предоставлять доступ к локу сразу нескольким
    // потокам, остальные при этом находятся в режиме ожидания, количество потоков при этом регулируется
    // самим семафором, как только какой то поток завершил свою работу, следующий поток в режиме
    // ожидания получает возможность захватить лок. если привести аналогию, то это очень похоже
    // на call-центр. реализация нашего семафора находится во внутреннем классе SemaphoreServiceDesk
    // перейдем туда.

    public static void main(String[] args) throws InterruptedException {

        // определяем две переменные отвечающие за количество потоков которые могут
        // одновременно работать и за общее количество потоков
        int operators = 5;
        int customers = 21;
        // создадим инстанс нашего внутреннего класса
        SemaphoreServiceDesk serviceDesk = new SemaphoreServiceDesk(operators, customers);
        // теперь нам необходимо создать 20 потоков, сделаем это с помощью ExecutorService
        // вспоминаем, для инициализации нам нужен класс Executors но в этот раз мы будем
        // создавать не лимитированный пул потоков, а кешированный, который будет сам создавать
        // потоки по необходимости
        ExecutorService executorService = Executors.newCachedThreadPool();
        // создадим IntStream с помощью метода range(), который аргументами принимает
        // границы не включая их, у нас от 0 до 21, то есть поток состоит из 20 элементов, делаем
        // это для того чтобы воспользоваться лямбда-выражением, далее сложная запись, разберем по кускам
        // IntStream.range() - создали поток, forEach - в цикле проходим по всем элементам потока IntStream
        // которые обозвали client(вспоминаем что forEach - терминальная операция), вызываем метод
        // executorService.submit(), чтобы описать какую задачу мы выполняем(имплементируем
        // Runnable.run()), serviceDesk.connect() - выполняем логику описаную в нашем классе семафоре и
        // выводим количество подключенных потоков и количество потоков в очереди, так же
        // добавим Thread.sleep() чтобы немного замедлить вывод на экран
        IntStream.range(0, customers).forEach(client->executorService.submit(()->{
            serviceDesk.connect();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(GREEN+" Number of connected customers "+ serviceDesk.getConnectedCustomers());
            System.out.println(RED+" Number of customers in a queue "+ serviceDesk.getCustomersQueued());
        }));
        // завершаем работу потоков, на всякий случай укажем что нужно завершить потоки после 30 секунд
        // ожидания
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);
    }

    private static class SemaphoreServiceDesk {
        // первое что нам необходимо, это завести две переменные, одна отвечает за текущее количество
        // подключенных потоков, вторая за количество потоков в очереди
        // однако мы не можем использовать здесь
        // Integer значение, так как инкрементация Integer не является атомарной операцией, для
        // этого нам пригодится класс AtomicInteger, мы создаем переменную этого типа и инициализируем
        // ее конструктором класса AtomicInteger, аргументом в конструктор передаем значение
        // переменной, если мы ничего не передаем, то значение равно 0, у переменной отвечающей
        // за количество потоков в очереди инициализацию вызовем позже в конструкторе семафора
        private AtomicInteger connectedCustomers = new AtomicInteger();
        private AtomicInteger customersQueued;
        // теперь создадим сам семафор
        private Semaphore semaphore;
        // затем создадим конструктор, у конструктора два параметра, количество операторов(потоков
        // которые могут одновременно работать) и количество пользователей(потоков всего)
        public SemaphoreServiceDesk(int operatorsNum, int customersNumber) {
            // для начала создадим инстанс семафора, аргументом в его конструктор мы передаем
            // максимальное количество потоков, у конструктора семафора есть вторая реализация
            // она принимает в себя boolean значение, по аналогии с ReentrantLock, если мы передадим
            // true, то мы гарантируем, что у нас не будет "голодания" потоков, со всеми вытекающими
            // последствиями по ресурсозатратности(мне тут подсказали что потоки начинают получать
            // доступ к локу по принципу FIFO - first in, first out)
            semaphore = new Semaphore(operatorsNum);
            // теперь инициализируем поле которое мы создали выше и передаем в него
            // общее количество потоков
            customersQueued = new AtomicInteger(customersNumber);
        }

        // нам понадобится метод обеспечивающий доступ к нашему условному call-центру
        public void connect() {
            // воспользуемся классом Random который будет имитировать загруженность работы
            // наших потоков
            Random random = new Random();
            try {
                // метод acquire() используется для того чтобы поток мог получить свой лок
                semaphore.acquire();
                // с помощью метода incrementAndGet() увеличиваем количество подключенных потоков
                connectedCustomers.incrementAndGet();
                // обратный метод который уменьшит количество потоков в очереди
                customersQueued.decrementAndGet();
                // имитируем работу потока
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // в блоке finally освобождаем место под следующий поток и уменьшаем
                // число подключенных потоков, мы вынесли это в отдельный метод
                disconnected();
            }
        }

        private void disconnected() {
            semaphore.release();
            connectedCustomers.decrementAndGet();
        }

        // так же определим два метода геттера которыми мы получаем количество подключенных
        // пользователей и количество пользователей в очереди, но возвращаем мы не типы
        // этих значений, посколько они типа AtomicInteger, а результат работы метода get()
        // который возвращает обычный int, работу с этим классом мы на этом закончим, перейдем в main()
        public int getConnectedCustomers() {
            return connectedCustomers.get();
        }

        public int getCustomersQueued() {
            return customersQueued.get();
        }
    }

}
