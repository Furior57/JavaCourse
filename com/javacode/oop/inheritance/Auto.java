package com.javacode.oop.inheritance;

// Это родительский класс, от него наследуются классы отдельных типов машин

// мы обьявили этот класс абстрактным, мы больше не можем создать
// обьект этого класса, однако мы можем его наследовать другими классами
public abstract class Auto {

    private String producer;
    private String model;
    private Engine engine;
    private int currentSpeed;
    protected boolean isRunning;


    // определяем конструктор класса, ВНИМАНИЕ, если мы переопределяем
    // конструктор класса по своему, то в каждом классе который
    // наследуется от нашего класса мы тоже должны переопределить
    // конструктор, по умолчанию они используют стандартный пустой
    // при создании конструкторов в наследных классах
    // мы параметрами указываем поля которые определили в родительском классе
    // а в теле конструктора с помощью ключевого слова super
    // указываем какие именно поля родительского класса мы определяем
    // в целом java умеет генерировать автоматически такой конструктор,
    // но знать об этом нужно


    public Auto(String producer, String model, Engine engine,
                int currentSpeed, boolean isRunning) {
        this.producer = producer;
        this.model = model;
        this.engine = engine;
        this.currentSpeed = currentSpeed;
        this.isRunning = isRunning;
    }

    public Auto(String producer, String model, Engine engine) {
        this.producer = producer;
        this.model = model;
        this.engine = engine;

    }

    // здесь мы обьявили астрактный метод, у него нет тела, только обьявление
    // теперь мы должны имплементировать (реализовать) этот метод в классе наследнике
    // FuelAuto, продолжение там
    // а здесь напомню что от класса Auto так же наследуется клас ElectricCar
    // значит мы должны и там имплементировать(реализовать) этот метод
    public abstract void energize();

    public Engine getEngine() {
        return engine;
    }

    public void start() {
        isRunning = true;
        currentSpeed = 10;

    }

    public void stop() {
        isRunning = false;
        currentSpeed = 0;

    }

    public void accelerate(int kmPerHour) {
        currentSpeed += kmPerHour;
        System.out.println("Accelerating. Current speed is"+currentSpeed+
                " km per hour");
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Engine getEngineType() {
        return engine;
    }

    public void setEngineType(Engine engineType) {
        this.engine = engine;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
