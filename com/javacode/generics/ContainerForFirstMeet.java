package com.javacode.generics;
// это технический класс для знакомства с дженериками
public class ContainerForFirstMeet<T> {

    private T object;

    public ContainerForFirstMeet(T object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
