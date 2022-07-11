package com.javacode.oop.inheritance;


import java.util.List;
import java.util.ArrayList;

public class Engine {

    private double volume;
    private EngineType engineType;
    private  int power;
    private List<Piston> pistons = new ArrayList<>();
    // Минусом java является невозможность выбрать родителями несколько классов, либо
    // иногда возникает ситуация когда наследование использовать неудобно, или
    // нерационально, например нам необходимо взять только часть функционала
    // какого то класса, но нет смысла тянуть все его свойства и методы
    // это усложняет и написание и понимание кода. в таких ситуациях
    // удобнее пользоваться композицией.
    // Композиция - это использование функционала какого то обьекта
    // через ссылку на экземпляр этого обьекта
    // в данном случае мы полем указали пустой список в который передадим
    // экземпляры класса Piston, ниже с помощью цикла мы генерируем экземпляры
    // и заполняем ими список, получая доступ к функционалу класса, но
    // не пользуясь наследованием
    public Engine(double volume, EngineType engineType, int power,
                  List<Piston> pistons) {
        this.volume = volume;
        this.engineType = engineType;
        this.power = power;
        for (int i = 1; i <= 5; i++) {
            this.pistons.add(new Piston(0.3, i));
        }

    }

    public Engine() {

    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public List<Piston> getPistons() {
        return pistons;
    }

    public void setPistons(List<Piston> pistons) {
        this.pistons = pistons;
    }
}
