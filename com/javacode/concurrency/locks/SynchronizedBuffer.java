package com.javacode.concurrency.locks;

import com.javacode.concurrency.threadLesson.ColorScheme;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.javacode.concurrency.threadLesson.ColorScheme.*;

public class SynchronizedBuffer {
    // мы познакомились с паттерном Producer-Consumer и научились блокировать доступ с помощью
    // ключевого слова synchronized. однако у такого способа есть свои ньюансы которые иногда не
    // позволяют нам им воспользоваться. первое, это ограничение использование блока synchronized.
    // мы не можем использовать его дальше, чем в отдельном методе. то есть у нас в один момент
    // может работать только один synchronized метод, либо один synchronized блок внутри метода.
    // второе, это так называемое "голодание" потоков, мы не можем гарантировать, какой именно
    // поток будет использоваться в следующий момент и при "гонке потоков", вполне может случиться
    // ситауция когда один, или несколько потоков не получают ресурсов и простаивают. в случае
    // если потоков немного, использование synchronized блоков оправдывает себя, но если ресурсов
    // используется много мы можем поступить другим образом. в java имеется интерфейс Lock и его
    // частная реализация ReentrantLock, реализуем Producer-Consumer паттерн используя этот класс

    // для начала нам необходимо определить несколько полей в нашем класса.

    // первое поле - обьект лока, на его основе мы будем создавать обьекты интерфейса Condition
    // у него есть две имплементации, первая без параметров, а вторая с fairness параметром,
    // это булевое значение, если мы передадим true в конструктор true то мы гарантируем
    // очередность работы потоков, однако такая операция ресорсозатратна и не всегда выгодно
    // ее использовать, мы поступим немного по другому, как, узнаем ниже
    private static final Lock monitor = new ReentrantLock();
    // два следующих поля это обьекты Condition, не будем пока вдаваться в подробности что это,
    // просто учтем что это некое подобие wait() и notify(), а если проще то это условия блокировки.
    // они создаются с помощью метода ReentrantLock.newCondition().
    private static final Condition canRead = monitor.newCondition();
    private static final Condition canWrite = monitor.newCondition();

    // теперь определим переменное значение которое будет использоваться двумя потоками
    // мы определим его как Integer
    private static int buffer = 0;
    // следующее поле - булево, оно будет отвечать за то пуст контейнер или нет
    private static boolean isEmpty = true;
    // теперь перейдем к методам blockingWrite() и blockingRead()

    public static void main(String[] args) {
        // здесь нам особо нечего описывать, тут мы просто запускаем потоки.
        // немного поговорим о том что мы тут наделали. как было упомянуто выше у класса
        // ReentrantLock ест ьдве имплементации конструктора, одна принимает в себя true
        // чтобы гарантировать очередность использования лока потоками. таким образом
        // мы можем избавиться от необходимости писать какой то кусок кода, но это требует
        // ресурсов(в часности памяти) и не всегда удобно. универсальным способом здесь
        // все же является задание условия лока монитора, даже если мы получим исключение
        // в одном потоке, остальные будут продолжать работу, это обеспечивает нам
        // высокую отказоустойчивость и избавляет от "голодания" потоков. Есть и другие
        // реализации интерфейса Lock, но они уже специфичны и созданы для определенных
        // целей, их изучение в данной лекции необязательно, однако самому ознакомиться
        // с ними стоит.
        new Thread(SynchronizedBuffer::blockingWrite).start();
        new Thread(SynchronizedBuffer::blockingRead).start();

    }

    // этот метод у нас аналогичен методу produce() из предыдущей лекции, но там мы синхронизирвоали
    // работу блокируя монитор обьекта, здесь мы делаем несколько иначе
    private static void blockingWrite() {
        for (int i = 0; i < 10; i++) {
            // сначала заблокируем обьект ReentrantLock
            monitor.lock();
            // все наши операции мы будем выполнять в блоке try-catch, это  инструкции
            // для потоков, сигнал потокам чтения и приостановка потока записи, а разблокировку
            // обьекта мы вынесем в блок finally, даже если мы получим исключение, обьект
            // всегда должен разблокироваться, иначе программа "зависнет"
            try {
                // чтобы наша программа не упала в бесконечное ожидание ответа от потока, нам надо
                // поставить условие при котором поток будет засыпать, для этого мы и завели
                // булевую переменную, проверяем ее, если она false, значит наш writer
                // уже захватил монитор, выводим сообщение что он снова пытается это сделать
                // и "усыпляем" его, в ридере мы делаем аналогичную проверку, но там
                // проверяем на true
                while (!isEmpty){
                    System.out.println(RED+"Writer is trying to access a resources");
                    System.out.println("Buffer is full");
                    canWrite.await();
                }
                // выполняем наши действия
                buffer++;
                // меняем значение булева и выводим сообщение о работе потока
                isEmpty = false;
                System.out.println(YELLOW + "Producer produced: " + buffer);
                // теперь нам необходимо уведомить потоки, что обьект свободен, используем метод signal()
                // так же есть метод signalAll(), оба метода аналогичны notify() и notifyAll()
                canRead.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // разблокируем обьект для потоков
                monitor.unlock();
            }


        }
    }
    // этот метод идентичен предыдущему за исключением инструкций для потоков и другого порядка
    // использования Condition, здесь мы сигнализируем записывающему потоку и "усыпляем" читающий поток
    // перейдем в main()
    private static void blockingRead() {
        for (int i = 0; i < 10; i++) {
            try {
                monitor.lock();
                while (isEmpty){
                    System.out.println(RED+"Reader is trying to access a resources");
                    System.out.println("Buffer is empty");
                    canRead.await();
                }
                int readValue = buffer;
                isEmpty = true;
                System.out.println(BLUE + "Reader reads from buffer, value: " + readValue);
                canWrite.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                monitor.unlock();
            }

        }

    }
}
