package com.javacode.concurrency.executors;

import static com.javacode.concurrency.threadLesson.ColorScheme.RED;
// до сих пор мы создавали потоки с помощью класса Thread, каждый вручную, однако это не является
// самым оптимальным способом их создания, минусов много, создание потока потребляет много ресурсов,
// причем не только jvm но и системных, фрагментация памяти, то есть когда мы запускаем много потоков,
// после их завершения у нас остаются блоки памяти разных размеров и туда нельзя записать какой то
// большой обьект и множество других особенностей. с точки зрения написания кода это неэффективно.

// в этой лекции мы рассмотрим как правильно завершать потоки, познакомимся с видами потоков и узнаем
// что такое экзекьюторы и в чем их преимущество. для начала перейдем в класс GCDRunnable

// для начала мы создадим метод котроый будет работать с одним потоком - runOneThread()
public class Launcher {
    public static void main(String[] args) throws InterruptedException {
        boolean isDaemon = false;
        System.out.println(RED + "Starting main thread");
        GCDRunnable r = new GCDRunnable(isDaemon);
        runOneThread(r, isDaemon);
        System.out.println(RED+"Leaving the main thread");
    }

    private static void runOneThread(GCDRunnable r, boolean isDaemon) throws InterruptedException {
        // создаем поток и передаем в него обьект GCDRunnable
        Thread th = new Thread(r);
        // существует два типа потоков, пользовательские и демоны, пользовательские потоки это те
        // потоки которые завершаются только выполнив свои инструкции, либо по прерыванию.
        // если программа завершает свою работу, она дожидается конца работы таких потоков и только
        // потом завершается сама
        // демоны же в свою очередь, это, обычно, некие служебные потоки, работа которых прерывается
        // в момент завершения программы, GarbageCollector(сборщик мусора) относится к демонам.
        // инициализировать демона можно методом Thread.setDaemon(true), мы сделаем это условием
        if (isDaemon) {
            th.setDaemon(true);
        }
        th.start();
        // по каким либо причинам мы можем захотеть прервать работу нашего потока, для этого
        // мы можем воспользоваться методом interrupt(), однако если мы просто напишем его вызов,
        // это не прервет работу потока, этот метод просто пометит поток как тот который можно завершить
        // а завершить поток можно методом interrupted(), если он видит что поток можно завершить,
        // он его завершает, в GCDRunnable мы добавим условие которое будет это проверять, перейдем туда
        // на 36 строку.
        // что у нас тут происходит? мы запустили поток main() в нем создали обьект класса GCDRunnable,
        // запустили метод runOneThread(), в котроый передали этот обьект и указали что этот поток - демон,
        // после этого приостановили работу потока main(), пока он приостановлен
        // наш поток работает и выводит делитель, как только задержка кончается метод main() доходит до
        // interrupt() и прерывает поток, который сообщает что его прервали, если поток не будет демоном
        // он отработает до конца
        Thread.sleep(80);
        th.interrupt();
    }
}