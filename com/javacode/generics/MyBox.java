package com.javacode.generics;

public class MyBox<X>{
    // сейчас внимательно
    // мы создадим конструктор нашего класса, при обьявлении класса мы указали
    // что параметризируем его типом X, соответственно мы должны создать конструктор
    // который будет принимать этот тип, так? а вот и нет, мы можем параметризировать
    // конструктор любым другим дженерик типом, главное при первом его обьявлении
    // обернуть его в diamond-оператор(<T>), более того, мы не обязаны передавать в него
    // только один параметр, определим поле того типа которым параметризировали класс
    // и передаим его в конструктор
    X someField;
    public <T> MyBox(T param, X param2) {
        // здесь выведется короткое имя класса который мы передали параметром
        System.out.println(param.getClass().getSimpleName());
        this.someField = param2;
    }
    // перейдем в класс GenericInheritance на 34 строку

    // создадим метод, параметризируем его типом U и вернем тот же параметр что и передали
    // вернемся в класс GenericInheritance на 40 строку
    public static <U> U returnValue(U param) {
        return param;
    }
}
