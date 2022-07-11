package com.javacode.oop.inheritance;

// однако мы решили не реализовывать данный метод здесь
// для этого мы обьявили класс FuelAuto абстрактным (нам ведь не нужны
// обьекты этого класса, он лишь расширяет функционал других классов)
// и теперь мы должны сделать имплементацию (реализацию) нашего метода
// energize() уже в классах которые наслеются от этого класса
// то есть в Truck и Bus
public abstract class FuelAuto extends Auto {

    private int availablePetrol;
    private int tankValue;

    public FuelAuto(String producer, String model, Engine engine,
                    int availablePetrol, int tankValue) {
        super(producer, model, engine);
        this.availablePetrol = availablePetrol;
        this.tankValue = tankValue;
    }

    public void fuelUp(int petrolVolume) {
        availablePetrol += petrolVolume;
        System.out.println("Adding fuel");
    }



    public int getAvailablePetrol() {
        return availablePetrol;
    }

    public void setAvailablePetrol(int availablePetrol) {
        this.availablePetrol = availablePetrol;
    }

    public int getTankValue() {
        return tankValue;
    }

    public void setTankValue(int tankValue) {
        this.tankValue = tankValue;
    }
}
