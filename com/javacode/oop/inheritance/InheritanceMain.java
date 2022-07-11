package com.javacode.oop.inheritance;

import java.util.ArrayList;

public class InheritanceMain {
    public static void main(String[] args) {

        Engine truckEngine = new Engine(6.0, EngineType.DIESEL, 900,
                new ArrayList<>());
        Truck truck = new Truck("Volvo", "VNL-300",
                truckEngine,300, 500,10);
        ElectricCar electricCar = new ElectricCar("Tesla",
                "model s", 40, 5);


        // здесь мы передали два разных обьекта двух разных классов
        // у каждого свой метод energize()
        // вывод будет такой:
        //  Adding fuel  -  это для класса Truck
        //  Battery is charging - это для класса ElectricCar
        // именно это и называется полиморфизмом, одна и та же
        // функция ведет себя по разному в зависимости от того
        // какой обьект в нее передается
        runCar(truck);
        runCar(electricCar);



    }
    // Пример полиморфизма в ООП. Эта функция параметром принимает обьект класса
    // Auto, однако, этот класс у нас абстрактный, а значит нельзя создать
    // обьект этого класса, почему IDE не выдает ошибку?
    // дело в том что классы Bus, Truck и ElectricCar, являются наследниками
    // класса Auto и мы вполне себе моем написать такую конструкцию:
    //  Auto auto = new Bus(); Ошибки при этом не будет, не смотря на то
    // что мы создаем обьект класса Auto, в реализации мы вполне можем создать
    // новый обьект наследного класса, это называется полиморфизм,
    // в зависимости от контекста наш класс может принимать разные формы
    // и по разному реализовывать функции продолжение на 15 строке
    public static void runCar(Auto auto) {
        auto.start();
        auto.stop();
        auto.energize();
    }

}
