package com.javacode.oop.interfaces;

public class Cellphone extends Electronics {

    public Cellphone(String make, String model, int quantity, int price) {
        super(make, model, quantity, price);
    }

    @Override
    public int calcDeliveryPrice() {
        if (getPrice() >= 150) {
            return 0;
        } else {
            return 10;
        }
    }


}
