package com.javacode.lambdas;

import com.javacode.lambdas.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaFunctionExample {
    public static void main(String[] args) {
        // чтобы пользоваться лямбда-выражениями в java мы должны написать
        // функциональный интерфейс, но на само деле в самом java-core
        // определено уже множество таких интерфейсов, их хватает на
        // большую часть случаев, настало время с ними познакомиться

        // первое что необходимо уяснить, что такое метод и что такое функция.
        // Метод - это набор каких то логических операций который определен
        // в обьекте(классе), то есть у класса есть методы, не функции
        // Функция - это набор логических действий который определен вне
        // какого либо обьекта(класса) и существует сам по себе.
        // если мы написали класс и написали какую то логику и проименовали ее,
        // это метод, однако если мы определили анонимный класс и в нем создали
        // какую то логику, это уже функция.
        // еще одно важное отличие, метод может существовать только в контексте
        // обьекта, его нельзя передать аргументом, однако функции можно присвоить
        // переменную и передать ее в качестве аргумента.
        // java обьектно ориентированный язык
        // в нем все есть обьект, даже методы, функция быть обьектом не может по
        // определению, однако с помощью функциональных интерфейсов это
        // ограничение обошли, именно благодаря этому стали возможны лямбда
        // выражения в java.

        // теперь перейдем к нативными функциональными интерфейсами
        // мы определили несколько классов с которыми будем в дальнейшем работать
        // сначала определим два списка и заполним их
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Art", "Green", 130000,""));
        employees.add(new Employee(2,"Alex", "Black", 240000,""));
        employees.add(new Employee(3,"Ant", "Red", 85000,""));
        employees.add(new Employee(4,"John", "White", 258000,""));

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Art", "Green", 33));
        persons.add(new Person("Alex", "Black", 37));
        persons.add(new Person("Ant", "Red", 28));
        persons.add(new Person("John", "White", 56));

        // Первый интерфейс с которым мы познакомимся это Predicate,
        // этот интерфейс получает какой то тип на вход и возвращает булево
        // например мы хотим отфильтровать коллекцию, если элемент который
        // мы передаем соответствует параметрам поиска, возвращает true
        // мы написали функцию и передали в нее наши списки с разными обьектами
        // обратим внимание что в нашем случае вернется первое совпадение,
        // а не все, но в целом механизм фильтрации понятен
        System.out.println(findMatch(employees, e -> e.getSalary() < 90000));
        System.out.println(findMatch(persons, e -> e.getAge() < 35));

        // следующий интерфейс с которым мы познакомимся это Function
        // этот интерфейс принимает в себя значение какого то типа, производит
        // с ним операции и возвращает другой тип, параметризируется
        // двумя типами данных, первый тип ввод, второй возвращаемое значение
        System.out.println(calcSum(employees, Employee::getSalary));
        System.out.println(calcSum(persons, e -> e.getAge()));
        // следующий интерфейс это BiFunction, его отличие в том что он параметризирован
        // тремя типами <T,U,R> первые два типа это принимаемые аргументы с которыми
        // мы производим операции, третий - возвращаемое значение, у этого интерфейса
        // есть подтип BinaryOperator, он расширяет BiFunction, при этом
        // BiFunction параметризируется тремя одинаковыми типами
        // мы создали метод в котором мы работает с BinaryOperator(),
        // для начала мы создадим
        // обьект BinaryOperator, инициализируем его лямбда-выражением, после этого
        // инициализируем переменную в которую будем записывать сумму
        BinaryOperator<Integer> combiner = (n1, n2) -> n1 + n2;
        Integer zero = 0;
        // передаем список, нулевой элемент, функцию которая получает значения
        // для суммирования и наш BinaryOperator, по сути это то действие которое
        // мы планируем совершить с нашими значениями
        System.out.println("Combine sum: " + combine(employees,
                zero, Employee::getSalary, combiner));
        // мы можем инициализировать новый оператор с другим действием
        // что в дальнейшем может очень сильно сократить количесво кода который мы пишем
        BinaryOperator<Integer> combiner1 = (n1, n2) -> Math.max(n1, n2);
        System.out.println("Combine max value: " + combine(employees,
                zero, Employee::getSalary, combiner1));

        // следующий интерфейс с которым мы поработаем это Consumer, этот интерфейс
        // принимает один тип и возвращает void, он может пригодиться например там
        // где мы хотим пройтись по списку методом forEach, этот метод принимает в себя
        // аргументом интерфейс Consumer расширенный тем типом на котором мы вызвали
        // этот метод, здесь мы повысим зарплату наших сотрудников на 10%
        employees.forEach(e -> e.setSalary(e.getSalary() * 11 / 10));
        // а теперь пройдемся по этому списку и выведем поочередно все зарплаты
        employees.forEach(employee -> System.out.println(employee.getSalary()));
        // или просто всех сотрудников
        employees.forEach(System.out::println);

        // следующий интерфейс Supplier, он обладает одним методом, get()
        // мы создали небольшую структуру классов
        // основной класс(в дальнейшем был изменен на интерфейс)
        // Shape, от которого наследуются(в дальнейшем имплементируют интерфейс) три класса
        // Circle, Square, Rectangle, они умеют только сообщать о том
        // что мы их создали
        // определим массив Supplier в который передадим конструкторы наших фигур
        Supplier[] shapes = {Circle::new, Square::new, Rectangle::new};
        // теперь мы хотим вызвать конструктор случайного обьекта в массиве
        // для этого нам понадобится класс Random
        Random random = new Random();
        // в цикле будем вызывать наши конструкторы по их индексу, для этого
        // поставим границу у метода random.nextInt(3)
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(3);
            // создаем обьект Supplier присваиваем ему какой то обьект из нашего
            // массива
            Supplier supplier = shapes[index];
            // и получаем этот обьект, больше этот интерфейс ничего не умеет
            supplier.get();
        }

        // перейдем в интерфейс Shape

    }

    // итерируемся по коллекции, если совпадение найдено - возвращает элемент
    private static <T> T findMatch(List<T> elements, Predicate<T> predicateFunction) {
        for (T e : elements) {
            if (predicateFunction.test(e)) {
                return e;
            }
        }
        return null;
    }

    // этот метод принимает в себя список и суммирует значение которое мы получим из него
    private static <T> int calcSum(List<T> list, Function<T, Integer> function) {
        int sum = 0;
        for (T e : list) {
            sum = sum + function.apply(e);
        }
        return sum;
    }

    // здесь внимательно, сложная конструкция, мы создаем метод, параметризируем его
    // двумя типами, первый ввод, второй возврат, указываем тип возврата.
    // Аргументами принимаем параметризированный список, какой то тип в который
    // будем суммировать наш результат, обьект интерфейса Function и обьект
    // интерфейса BiFunction
    private static <T, R> R combine(List<T> list, R zero, Function<T, R> function,
                                    BinaryOperator<R> combiner) {
        for (T e : list) {
            // мы помним что у BiFunction третий параметризированный тип это
            // возврат работы этого интерфейса, присваиваем его аргументу zero
            // и вызываем метод apply() в который передаем zero и обьект Function
            // у которого вызываем метод apply и уже туда передаем элемент коллекции

            zero = combiner.apply(zero, function.apply(e));
        }
        return zero;
    }
}
