package com.javacode.oop.interfaces;
// Создаем свой интерфейс, интерфейсы принято называть глаголами,
// в конце часто можно увидеть "*able"
// Интерфейсы позволяют связывать обьекты которые не могут быть связаны
// в нашем случае пиццу и телефон, они никак не связаны, но могут быть
// доставлены
@FunctionalInterface // эта аннотация говорит что данный интерфейс
// функциональный, то есть в нем есть только одна абстрактная функция
// при попытке создать еще одну функцию мы получим ошибку компиляции
public interface Deliverable {
    // по умолчанию все методы в интерфейсе public abstract
    // поэтому реализовываются они так:
    int calcDeliveryPrice();
    // все переменные которые мы обьявляем в интерфейсе должны
    // быть статическими и константами, а так же сразу иметь значение
    // по умолчанию они уже static final поэтому реализация такая:
//    int QUANTITY = 5;

}