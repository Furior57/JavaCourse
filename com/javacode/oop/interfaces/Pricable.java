package com.javacode.oop.interfaces;
// Этот интерфейс создан для того чтобы расширить его
// другими нашими интерфейсами, иначе при создании
// обьекта Deliverable мы не сможем пользоваться
// методами интерфейса Orderable, теперь мы создаем
// обьекты как Pricable и пользуемся всеми методами наших интерфейсов

public interface Pricable extends Deliverable,Orderable{
    // мы можем добавлять в интерфейсы методы которые по умолчанию
    // реализуют какую то логику, при этом имплементировать
    // их в классах не обязательно, так как эти методы не абстрактные

    default int calcPrice() {
        return calcOrderPrice()+calcDeliveryPrice();
    }
    // так же в интерфейсе мы можем обьявлять статические методы
    // их так же можно вызывать без создания обьекта класса
    // применять в случае если все экземпляры класса могут
    // пользоваться этим методом
    static void dontUse() {
    }

}
