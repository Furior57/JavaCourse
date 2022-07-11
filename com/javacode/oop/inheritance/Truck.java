package com.javacode.oop.inheritance;
// создаем класс грузовика и наследуем поля от другого класса

public class Truck extends FuelAuto {

    private int cargoWeight;

    public Truck(String producer, String model, Engine engine, int availablePetrol,
                 int tankValue, int cargoWeight) {
        super(producer, model, engine, availablePetrol, tankValue);
        this.cargoWeight = cargoWeight;
        System.out.println("Constructing truck");
    }

    @Override
    public void start() {
        super.start();
        System.out.println("Truck is running");
    }

    @Override
    public void stop() {
        super.stop();
        System.out.println("Truck is stopped");
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public void load() {
        System.out.println("Cargo loaded");
    }

    public void unload() {
        System.out.println("Cargo unloaded");
    }

    @Override
    public void energize() {
        fuelUp(50);
    }
}
