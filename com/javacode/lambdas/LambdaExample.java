package com.javacode.lambdas;

import com.javacode.lambdas.model.Circle;

import java.util.ArrayList;
import java.util.List;

// Теперь мы более подробно познакомимся с функциональными интерфейсами
// Функциональный интерфейс - это интерфейс с одной нереализованной(абстракной) функцией
// обозначаются такие интерфейсы аннотацией @FunctionalInterface
// при этом они не обязаны иметь только одну функцию, но абстрактная будет только одна,
// остальные имеют модификатор либо static либо default, для использования
// лямбда выражений такие интерфейсы обязательны
// Прямо здесь напишем подобный интерфейс для работы с нашими тренировочными массивами
@FunctionalInterface
    // эту аннотацию не обязательно ставить, компилятор разберется что мы хотим сделать,
    // однако если мы ошибемся в сигнатуре метода, то компилятор будет считать что мы
    // перегружаем функцию и возможные ошибки вылезут либо на этапе компиляции, либо
    // что еще хуже в runtime, "усложнили пример использовав дженерик наследник Number"
interface ElementProcessor<T extends Number>{
    // этот интефейс будет принимать в функцию int элемент и каким то образом
    // преобразовывать его в double
    // реализация метода сразу после метода main()
    double process(T element);
}

public class LambdaExample {
    public static void main(String[] args) {

        List<Integer> intList = new ArrayList<>();
        for (int i=0; i<5;i++) {
            intList.add(i);
        }
        List<Double> doubleList = new ArrayList<>();
        for (int i=0; i<5;i++) {
            doubleList.add(i+0.03);
        }
        // вызываем функцию и аргументом передаем массив Integer и в месте где мы
        // передали аргументом наш интерфейс реализовываем лямбда-функцию которая
        // будет считать экспоненту для нашего числа, так как i у нас типа Number из за
        // дженерика, получим double значение с помощью метода doubleValue()
        processElements(doubleList, i-> Math.exp(i.doubleValue()));
        // а теперь создадим свои метод который будем использовать как лямбду,
        // назовем его multiply() и он будет умножать числа
        // мы несколько усложнили эту конструкцию добавив второй параметр
//        processElements(intList, 4, (x,y)->multiply(x,y));
        // реализацию мы переписали снова под один аргумент, а сами перейдем в класс
        // CalcTime где реализуем передачу функции аргументом

        // инициализируем две переменные разных типов и создадим отдельный интерфейс
        // Transformable с методом transform(), а так же класс TransformUtils,
        // перейдем в него
        String s = "Hello";
        Double d = 0.123;
        // инициализируем инстанс класса который только что создали
        TransformUtils<Double> doubleUtils = new TransformUtils<>();
        // а теперь напишем лямбда выражение используя метод референс
        // существует несколько типов использования
        // первый, мы обращаемся к статическому методу класса через Math::sin, аргументы писать не нужно
        doubleUtils.transform(d, Math::sin);
        // сделаем тоже самое используя статический метод нашего класса
        TransformUtils<String> stringUtils = new TransformUtils<>();
        stringUtils.transform(s, TransformUtils::exclane);
        // второй вариант это использование инстанса класса и не статического метода
        // создадим суффикс котроый будем присоединять
        String suffix = " Art";
        // здесь все похоже, но аргументом мы передадим суффик, а обращаться будем к
        // строке s и методу concat который склеит строки
        // эта запись будет аналогична s.concat(suffix);
        stringUtils.transform(suffix, s::concat);
        // третий вариант, мы имеем класс и вызываем у него нестатический метод
        // все равно что мы бы написали stringUtils.transform(s, y-> y.toUpperCase())
        stringUtils.transform(s, String::toUpperCase);
        // последний вариант, мы обращаемся к конструктору класса, используется нечасто
        // в данном случае мы вызываем конструктор и передаем в него строку, создавая
        // новый инстанс той же строки(узнать это копирует ссылку на обьект или сам обьект)
        stringUtils.transform(s, String::new);
        // Теперь мы поближе рассмотрим области видисомти для лямба-выражений
        // перейдем в класс ScopeTest
        // создадим инстанс ScopeTest
        ScopeTest scope = new ScopeTest();
        // теперь указываем типом переменной наш внутренний класс
        // обращаясь к нему через внешний, а конструктор вызываем через
        // уже созданный обьект внешнего класса, немного запутанно
        ScopeTest.LambdaScopeInner inner = scope.new LambdaScopeInner();
        // и запускаем наш метод
        inner.testScope(999.000);
        // Вывод:
//        d =999.0
//        Inner d =456.123
//        Outer d =0.123
//        Consumer e =789.0

        // разбираемся как работают дефолт методы и как происходит наследование методов
        Circle circle = new Circle();
        // вывод будет 0.01 что подтверждает преимущество имплементации класса
        // однако если мы будем имплементировать два разных интерфейса с одинаковым
        // методом ide выдаст ошибку и попросит определить какой именно метод мы будем
        // имплементировать или переопределить имплементацию полностью
        System.out.println(circle.calcSomething());
    }
    // после создания функционального интерфейса нам необходимо его применить
    // для этого мы создаем функцию которая будет принимать наш интерфейс как аргумент
    // используем дженерик чтобы принимать любые числовые значения
    private static <T extends Number> void processElements(List<T> intList, ElementProcessor function) {
        // используем эту функцию при добавлении элементов в массив, goTo 35
        List<Double> doubleList = new ArrayList<>();
        for (T i : intList) {
            doubleList.add(function.process(i));
        }
        System.out.println(doubleList);
    }

    private static double multiply(int x, int y) {
        return x*y/10.0;
    }
}
