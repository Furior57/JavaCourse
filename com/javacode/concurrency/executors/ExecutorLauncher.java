package com.javacode.concurrency.executors;

import java.util.concurrent.*;

import static com.javacode.concurrency.threadLesson.ColorScheme.RED;

public class ExecutorLauncher {
    private static final int POOL_SIZE = 2;
    // код в этом классе частично скопирован с класса Launcher, но здесь мы поговорим об ExecutorService
    // создадим новый метод runWithExecutors(), перейдем к нему
    public static void main(String[] args) throws InterruptedException {
        boolean isDaemon = false;
        System.out.println(RED + "Starting main thread");
        GCDRunnable r = new GCDRunnable(isDaemon);
        runWithExecutors(r,isDaemon);
        System.out.println(RED + "Leaving the main thread");
    }
    // создание потоков в ручном режиме может стать довольно трудоемким и неблагодарным занятием,
    // если нам потребуется 8 потоков, то нам необходимо не только вручную их прописывать, но
    // и следить за тем чтобы они эффективно работали, чтобы не было такой ситуации, когда один поток
    // работает, а остальные его ждут. здесь нам поможет ExecutorService.
    // в java есть такое понятие как пул потоков, в данный момент мы будем работать с частной реализацией,
    // тем самым ExecutorService
    private static void runWithExecutors(GCDRunnable r, boolean isDaemon) throws InterruptedException {
        // создаем инстанс ExecutorService и инициализируем его с помощью метода класса Executors.
        // newFixedThreadPool(), в который аргументом передается предельное количество потоков,
        // и обьект ThreadFactory, о нем поговорим ниже
        // проведем небольшой экскурс в методы класса Executors
        // newSingleThreadExecutor() - создает один поток для работы
        // newCashedThreadPool() - не ограничен количеством потоков, чем больше нагрузка на программу,
        // тем больше потоков, если они не используются в течении какого то времени то они могут
        // быть удалены, но при этом если поток завершил свою работу он сначала возвращается в пул
        // где может получить другое "задание"
        // newScheduledThreadPool() - позволяет работать с потоками по определенному графику
        // пока не будем далее углубляться, вернемся к нашему примеру

        // нам необходимо создать наши потоки как демоны, но как это сделать с пулом?
        // нам поможет интерфейс ThreadFactory
        ThreadFactory tf = new ThreadFactory(){
            // в нем есть всего один метод newThread, в котором мы инициализируем поток и зададим
            // условия для его преобразования в демона, в конце блока кода ставим ;
            @Override
            public Thread newThread(Runnable r) {
                Thread th = new Thread(r);
                if (isDaemon) {
                    th.setDaemon(true);
                }
                return th;
            }
        };


        // этой записью мы создали обьект пула и указали сколько потоков одновременно может работать
        // так же мы передали аргументом ThreadFactory на основе которого будут создаваться потоки,
        // в нашем случае это нужно для того, чтобы указывать демона мы запускаем, или нет
        ExecutorService execServ = Executors.newFixedThreadPool(POOL_SIZE, tf);
        // вызываем метод execute(), он запускает работу пула
        for (int i = 0; i < 5; i++) {
            execServ.execute(r);
        }
        // после выполнения необходимых операций, потоки и пул сами по себе не закроются, потоки
        // будут вечно висеть в статусе WAIT, чтобы завершить работу пула необходим метод shutdown(),
        // этот метод не останавливает мгновенно потоки, он указывает, что они должны остановиться
        // после выполнения своей работы
        execServ.shutdown();
        // однако может случиться такое, что поток завис, либо ожидает лока который не может получить,
        // в этом случае shutdown() не поможет, для этого есть отдельный метод awaitTermination(),
        // он ждет то количество времени которое мы ему передадим и принудительно завершает работу потока,
        // вторым параметром мы передаем единицы измерения времени, у нас секунды
        execServ.awaitTermination(30, TimeUnit.SECONDS);
    }

}


