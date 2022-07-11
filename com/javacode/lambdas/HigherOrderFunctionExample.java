package com.javacode.lambdas;

import com.javacode.lambdas.model.RichPerson;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class HigherOrderFunctionExample {
    // Функции высшего порядка. Это методы которые принимают в себя аргументом функциональный интерфейс
    // и возвращают тот же тип данных, то есть обьект этого интерфейса
    public static void main(String[] args) {
        // инициализируем и заполним список обьектов RichPerson
        List<RichPerson> persons = new ArrayList<>();
        persons.add(new RichPerson("Art", "Green", 80000, 33));
        persons.add(new RichPerson("Alex", "Black", 135000, 37));
        persons.add(new RichPerson("Ant", "Red", 145000, 28));
        persons.add(new RichPerson("John", "White", 68000, 56));
        // для начала изучим повнимательней интерфейс Predicate, у него есть четыре метода
        // test() - проверяет элемент на соответствие условию
        // and() - проверяет элемент на соответствие условию и этот же элемент
        // на соответствие другому условию, то есть другому Predicate
        // or() - проверяет элемент на соответствие одному из условий, если хоть одно
        // верно возвращает true
        // negate() - обращает возврат, то есть если лямбда-выражение вернуло true, он
        // вернет false
        // у нас есть два метода testPredicate и findAll первый принимает в себя лист элементов
        // второй помимо этого принимает в себя предикат, перейдем в testPredicate
        testPredicate(persons);
        // с методами or() и negate() все понятно реализовывать их не будем
        // с самими методами никаких сложностей нет, но важно понимать как они работают
        // для своей реализации функций высшего порядка

        // теперь поговорим о дефолтных методах интерфейса Function, мы помним что Function
        // принимает в себя один тип данных, преобразует его и возвращает преобразованный тип
        // какие вообще есть в нем методы:
        // apply() - делает то что описано выше
        // compose() - принимает аргументом функцию, проводит с ней операции, а потом
        // работает с результатом работы той функции и преобразует ее в нечто еще
//      выглядит так:  f1.compose(f2)==>f1(f2(x));
        // andThen() - обратная функция, она принимает другую функцию аргументом, сначала
        // работает сама, потом результат передает во вторую функцию
//      выглядит так:  f1.compose(f2)==>f2(f1(x));
        // Function.identify() - возвращает функцию которая не изменяет свой входной параметр
        // ниже разберемся для чего это нужно
        // итак мы определили еще два метода для работы с Function, testFunction и transform
        // второй метод принимает в себя список какого то типа, обьект Function, трансформирует
        // список в другой тип и возвращает его, в первом мы будем определять логику
        // трансформации, перейдем в testFunction()
        // используем
        testFunction(persons);



    }
    // здесь мы определим два разных предиката с разными условиями
    // и передадим их в метод Predicate.and(), чтобы определить всех у кого зп больше 100к
    // и кто младше 30 лет
    private static void testPredicate(List<RichPerson> persons) {
        System.out.println("Testing predicate...");
        Predicate<RichPerson> isRich = x-> x.getSalary()>=100000;
        Predicate<RichPerson> isYoung = y-> y.getAge() <30;
        // вызываем метод findAll, передаем в него предикат у которого вызываем метод and()
        // и передаем в него второй предикат
        System.out.println(findAll(persons, isRich.and(isYoung)));
    }
    // этот метод проверяет условие и если оно верно добавляет элемент в список который в конце
    // возвращает
    private static <T> List<T> findAll(List<T> elements, Predicate<T>predicate) {
        List<T> filteredList = new ArrayList<>();
        for (T element : elements) {
            if (predicate.test(element)) {
                filteredList.add(element);
            }
        }
        return filteredList;
    }

    private static void testFunction(List<RichPerson> persons) {
        System.out.println("Testing functions...");
        // определим первую функцию с которой будем работать, она через пробел выведет
        // имя и фамилию
        Function<RichPerson, String> name = x-> x.getFirstName()+" "+ x.getLastName();
        // и определим вторую функцию которая будет добавлять перед ними слово Hello
        Function<String, String> prefix = y-> "Hello, i'm "+ y;
        // используем метод compose() чтобы создать функцию и присвоить ее переменной
        // вспоминаем что результат обработки функции name будет
        // передан в функцию prefix и обработан
        Function<RichPerson, String> composeFunction = prefix.compose(name);
        // теперь передаем в метод transform наш список и и функцию composeFunction
        List<String> transformedList = transform(persons, composeFunction);
        // таким образом мы можем комбинировать какое угодно количество функций

        // чуть ниже мы определили метод compose, перейдем туда
        // теперь создадим дфе функции параметриизрованные только типом String
        // чтобы использовать их в методе compose
        Function<String, String> stringFunction1 = x -> x + "!!!";
        Function<String, String> stringFunction2 = String::toUpperCase;
        // и передадим эти функции аргументом в compose
        transform(transformedList, compose(stringFunction1, stringFunction2))
                .forEach(System.out::println);

        // теперь посмотрим на метод andThen в интерфейсе Consumer, его реализация выглядит так
        // f1.andThen(f2(x)) ==> f1(x) andThen f2(x), то есть он передаваемое значение
        // применяет сначала к первой функции, а потом ко второй, возврата у этого
        // интерфейса нет, он просто изменяет переданное в него значение
        // ниже мы определили два метода с реализацией testConsumer и processList
        // перейдем туда
        testConsumer(persons);
    }
    // мы создали метод, параметризировали его типом T, аргументом мы передаем
    // неопределенный массив Function<T,T>, возвращаем этот же тип,
    // что значит неопределенный массив? это значит что мы можем передать любое количество
    // аргументов
    private static <T> Function<T, T> compose(Function<T, T>... functions) {
        // для начала нам нужно создать какую то нулевую функцию,
        // когда наш метод получит массив аргументов, он будет итерироваться по нему
        // и присоединять функции к нашей
        // инициализируем нулевую функцию, напоминаю метод identify() возвращает
        // интерфейс Function который всегда возвращает входной параметр, то есть
        // лямбда для этого метода выглядит так t->t, для чего нам это нужно?
        // это нужно чтобы функция result возвращала сама себя и мы видоизменяли ее по очереди
        // каждой переданной функцией в аргументы
        Function<T,T> result = Function.identity();
        for (Function<T, T> f : functions) {
            result = result.compose(f);
        }
        return result;
    }

    private static <T, R> List<R> transform(List<T> elements, Function<T, R> function) {
        List<R> list = new ArrayList<>();
        for (T e : elements) {
            list.add(function.apply(e));
        }
        return list;
    }
    // передаем в этот метод список, определяем функции которыми будем пользоваться,
    // здесь вторую функцию мы не определяем, просто с помощью метод референса
    // выведем значения на экран
    private static void testConsumer(List<RichPerson> persons) {
        System.out.println("Testing consumer...");
        Consumer<RichPerson> consumer = x -> x.setSalary(x.getSalary() * 11 / 10);
        processList(persons, consumer.andThen(System.out::println));
    }
    // этот метод просто применяет accept() ко всем элементам коллекции
    private static <T> void processList(List<T> elements, Consumer<T> consumer) {
        for (T e : elements) {
            consumer.accept(e);
        }
    }
}

