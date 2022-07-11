package com.javacode.oop.inheritance;

// Создаем класс электромобиля и наследуем его поля от другого класса

public class ElectricCar extends Auto {

    private int batteryValue;
    private int passengersNumber;

    public ElectricCar(String producer, String model,
                       int batteryValue, int passengersNumber) {
        super(producer, model, new Engine());
        this.batteryValue = batteryValue;
        this.passengersNumber = passengersNumber;
    }



    public void charge() {
        System.out.println("Battery is charging");
    }

    public int getBatteryValue() {
        return batteryValue;
    }

    public void setBatteryValue(int batteryValue) {
        this.batteryValue = batteryValue;
    }

    public int getPassengersNumber() {
        return passengersNumber;
    }

    public void setPassengersNumber(int passengersNumber) {
        this.passengersNumber = passengersNumber;
    }

    // так как у электрического автомобиля зарядка реализована по другому
    // то в реализации мы вызываем другой метод, отличный от fuelUp()
    @Override
    public void energize() {
        charge();
    }
}
