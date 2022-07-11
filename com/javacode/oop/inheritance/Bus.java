package com.javacode.oop.inheritance;

// создаем класс автобуса и наследуем поля от другого класса

public class Bus extends FuelAuto {

    private int passengerNumber;

    public Bus(String producer, String model, Engine engine, int availablePetrol,
               int tankValue, int passengerNumber) {
        super(producer, model, engine, availablePetrol, tankValue);
        this.passengerNumber = passengerNumber;
        System.out.println("Constructing bus");
    }

    // method overloading
    public void fuelUp() {
        int volume = getTankValue() - getAvailablePetrol();
        fuelUp(volume);
    }



    // method overriding
    // быстрая команда переопределения метода ctrl+O
    @Override   // это аннотация метода, она предупредит что мы допустили
    // ошибку при переопределении родительского метода, например
    // добавили лишний параметр
    public void fuelUp(int petrolVolume) {
        int volume = getAvailablePetrol() + petrolVolume;
        if (volume > getTankValue()) {
            setAvailablePetrol(getTankValue());
        }
        // Если мы переопределяем метод, у нас должно совпадать имя метода
        // и его параметры, а так же модификатор доступа не должен сужать
        // доступ, однако расширить доступ можно, то есть если
        // основной метод protected, то мы можем
        // переопределить метод и сделать его public
        // нужно помнить что если метод который мы хотим переопределить
        // имеет модификатор private, мы не можем получить к нему доступ
        // так же тип возврата должен совпадать, если основной метод
        // void, то и наш метод должен вернуть void, но! мы можем возвращать
        // подкласс возврата основного класса, то есть, если основной
        // метод возвращает тип Object, то его подклассом является String
        // который мы можем вернуть в переопределенном методе

    }

    // реализация абстрактного метода
    @Override
    public void energize() {
        fuelUp(50);
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    public void pickingUpPassenger(int passengerNum) {
        this.passengerNumber += passengerNum;
        System.out.println("Picking up passenger");
    }

    @Override
    public void start() {
        super.start();
        System.out.println("Bus is running");
    }

    @Override
    public void stop() {
        super.stop();
        System.out.println("Bus is stopped");
    }

    public void releasePassengers() {
        if (isRunning) {
            stop();
        }
        passengerNumber = 0;
        System.out.println("Passengers release");
    }
}
