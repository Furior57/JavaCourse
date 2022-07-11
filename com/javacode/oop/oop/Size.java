package com.javacode.oop.oop;
// создаем класс перечисления, его задача предоставить нам список каких то
// переменных, имеющих свои значения,
public enum Size {
    VERY_SMALL("XS"), SMALL("S"),
    AVERAGE("M"), BIG("L"), VERY_BIG("XL"), UNDEFINED("");

    Size(String abbreviation){  // реализовываем конструктор класса
        // перечисления, тип возврата у конструкторов не указывается
        this.abbreviation = abbreviation;
    }
    private String abbreviation;

    public String getAbbreviation() {
        return abbreviation;
    }
}
