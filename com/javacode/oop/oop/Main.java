package com.javacode.oop.oop;

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();   // создаем обьект класса Dog
        //firstDog.paws = 4;   // таким образом получаем доступ к полям класса

//         если мы хотим исправить название переменной везде в коде
//         нам нужно нажать Shift+F6
        // мы можем поменять имя экземпляра класса сразу во всем
        // коде где оно используется, для этого выделяем имя
        // и нажимаем Shift+F6, это касается любых имен, методов, классов и т.д.
        dog.setName("Guffy");
        dog.setBreed("Labrador");
        /*System.out.println(dog.getName());
        System.out.println(dog.getBreed());*/

        Dog secondDog = new Dog();

        secondDog.setName("Spike"); // задаем имя
        String n1 = secondDog.getName(); // получаем имя
        /*System.out.println(n1);*/

        secondDog.setSize(Size.VERY_BIG);
        secondDog.bark();

        Dog firstDog = new Dog();
        Dog threeDog = new Dog();
        threeDog.bite();

        Size s = Size.SMALL;  // инициализируем новое значение размера
        // мы не можем воспользоваться здесь оператором new
        s.toString(); // преобразовываем значение в строку
        
        Size s1 = Size.valueOf("BIG");  // вручную указываем размер
        // передавая его строкой

        Size[] values = Size.values();// получаем массив со всеми значениями
        //  класса перечисления
        for (int i = 0; i < values.length; i++) {
            System.out.println(values[i]);
            }



      }


}

