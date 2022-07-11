package com.javacode.oop.interfaces;
// так как наш класс абстрактный, имплементирование
// интерфейса не вызовет в нем ошибок, однако все
// наследные классы должны переопределить методы
// интерфейса
// Преимущество интерфейсов в том что мы в один класс имплементировать
// несколько интерфейсов, общаем внимание что метод второго интерфейса
// был определен в нашем абстрактном классе, поскольку для всех
// наследных классов он одинаков, а значит нет
// необходимости реализовывать его в каждом классе наследнике

public abstract class Electronics implements Pricable {
    private String make;
    private String model;
    private int quantity;
    private int price;

    public Electronics(String make, String model, int quantity, int price) {
        this.make = make;
        this.model = model;
        this.quantity = quantity;
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int calcOrderPrice() {
        return getPrice()*getQuantity();
    }
}
