package com.javacode.callableAndFuture;


import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

// служебный класс позволяющий замерять время, поля определены классом Instant, который создает
// "закладку" на временной шкале, напоминание, временная шкала в java начинается с 00 00 1 января 1970 года
// и считается миллисекундами, однако внутри этого класса время замеряется двумя полями, первое хранит
// секунды, второе хранит наносекунды в секунде, что позволяет добиться точности замеров временных
// отрезков
class Timer {
    public Instant start;
    public Instant end;

    public double timeInSecond() {
        // считаем сколько прошло времени между отрезками и переводим в секунды
        return Duration.between(start, end).toMillis() / 1000.0;
    }
}

// до сих пор мы использовали только интерфейс Runnable для работы с многопоточностью, однако
// этот интерфейс просто запускает какие то действия, сам по себе он ничего не возвращает,
// здесь нам на помощь приходит другой интерфейс Callable, у него один метод call(), параметризируется
// дженерик типом, возвращает его же и может выкинуть исколючение
public class CallableFutureBase {

    public static void main(String[] args) {
        // здесь мы будем хранить результаты вычислений наших потоков
        List<Future<Double>> resultMeasuring = new ArrayList<>();
        // создадим потоки с помощью ExecutorService с неопределенным числом потоков
        ExecutorService threads = Executors.newCachedThreadPool();
        // метод submit() имеет три реализации, нас интересует та, которая принимает Callable,
        // реализуем лямбда выражение возвращающая замер времени, как мы видим переменная
        // присвоенная этому выражению имееет тип Future<Double>, большинство методов
        // ExecutorService возвращает этот тип. Future - это абстракция асинхронного вычисления,
        // нужна она чтобы получить результат вычисления в какой либо момент, либо сам результат,
        // либо исключение, имеет метод cancel() для отменены задачи, два булевых метода на проверку
        // была ли отменена задача, или была выполнена, а так же два метода для получения результата
        // вычисления, один просто ожидает результат, во второй мы передаем таймаут ожидания
        // здесь мы в цикле замеряем время и добавляем результат в список
        for (int i = 0; i < 30; i++) {
            resultMeasuring.add(threads.submit(() -> {
                Timer timer = new Timer();
                Random random = new Random();
                // этим методом мы присваиваем нашей переменной start текущее время
                timer.start = Instant.now();
                Thread.sleep(random.nextInt(1000));
                timer.end = Instant.now();
                return timer.timeInSecond();
            }));

        }
        // не забываем закрыть поток, иначе программа зависнет
        threads.shutdown();
        // а теперь с помощью лямбды проходим по списку и выводим результаты на экран, так как
        // в списке лежать обьекты Future, мы получаем их значение методом get(), при этом
        // мы должны обернуть их в блок try-catch и так как наш get() должен что то вернуть, то
        // в случае исключения возвращаем 0. ах да, не забываем про то что наша программа многопоточна,
        // поэтому создаем не просто stream, а parallelStream, в ином случае когда наш поток вызывает
        // метод get() он блокирует main() поток пока не завершит работу.
        resultMeasuring.parallelStream().map(f-> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        }).forEach(System.out::println);

    }

}
