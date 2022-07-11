package com.javacode.oop.innerclasses;

import java.lang.module.ModuleDescriptor;

public class CellPhone {
    private String make;
    private String model;
    private Display display;
    private RadioModule gsm;
    // данное поле создано с типом интерфейса для анонимного класса
    private AbstractPhoneButton button;

    // мы можем обьявить внутри класса интерфейсы
    // в данном случае это нужно для демонстрации анонимного класса
    // а именно, мы не можем создать экземпляр интерфейса, что
    // прекрасно подходит для демонстрации анонимного класса
    public interface AbstractPhoneButton {
        void click();
    }



    public CellPhone(String make, String model) {
        this.make = make;
        this.model = model;
    }

    public void turnOn() {
        initDisplay();
        gsm = new RadioModule();
        initButton();
    }
    // здесь мы определяем анонимный класс, то есть класс без имени
    // при инициализации нашего анонимного класса, мы расширяем его
    // тем классом или интерфейсом к которому обращаемся,
    // для этого мы создали поле button, с типом AbstractPhoneButton
    // при этом мы обязаны реализовать абстрактные методы
    //, так как это выражение, после фигурных скобок мы должны поставить
    // точку с запятой.
    // Вот и все. Мы можем создавать сколько угодно переменных и в каждой
    // реализовывать свою логику, это нужно делать для создания
    // одноразовых классов, когда не имеет смысла ради одной или нескольких
    // строчек кода отдельно инициализировать класс
    public void initButton() {
        button = new AbstractPhoneButton() {
            @Override
            public void click() {
                System.out.println("Click button");
            }
        };
    }

    public void call(String number) {
        gsm.call(number);
        button.click();
    }

    private void initDisplay() {
        display = new Display();
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Display getDisplay() {
        return display;
    }


}
