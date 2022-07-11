package com.javacode.oop.inheritance;

public class Piston {

    private double volume;
    private int pistonNumber;

    public Piston(double volume, int pistonNumber) {
        this.volume = volume;
        this.pistonNumber = pistonNumber;
    }

    public void movePiston() {
        System.out.println("Piston #"+pistonNumber+" is move");
    }

    public int getPistonNumber() {
        return pistonNumber;
    }
}
