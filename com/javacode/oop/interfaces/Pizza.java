package com.javacode.oop.interfaces;
// таким способом мы используем интерфейс, с помощью ключевого слова
// implements, при этом мы сразу должны переопределить какой то из
// методов этого интерфейса, иначе ide будет показывать ошибку
// однако если мы имплементируем интерфейс в абстрактный класс
// то такой ошибки не будет, так как там мы можем использовать абстрактные
// методы без их определения
public class Pizza implements Pricable {
    public String name;
    private int quantity;
    private double price;
    private Size size;

    public Pizza(String name, int quantity, double price, Size size) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public int calcDeliveryPrice() {
        if (size == Size.XL || quantity > 1) {
            return 0;
        } else {
            return 7;
        }
    }


    @Override
    public int calcOrderPrice() {
        return (int) price*quantity;
    }
}
