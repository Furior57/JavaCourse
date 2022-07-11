package com.javacode.oop.interfaces;

public class InterfaceRunner {
    public static void main(String[] args) {
        // как и в случае с абстрактным классом нельзя создать
        // обьект интерфейса ,но мы можем создать массив с таким
        // типом, реализация такая, просто для того чтобы знать:
        Deliverable[] deliverables = new Deliverable[3];

        // Теперь при создании обьектов класса мы можем указать тип
        // интерфейса

        Pricable pizza = new Pizza("Neapolitano", 1,
                20, Size.L);
        Pricable cellphone = new Cellphone("Motorola",
                "Razer", 1, 250);
        Pricable fridge = new Fridge("LG", "XNT-250",
                1, 300);

        // Теперь мы можем создать отдельный метод который будет
        // реализовывать какую то логику используя метод интерфейса
        printDeliveryPrice(cellphone);
        printDeliveryPrice(pizza);
        printDeliveryPrice(fridge);


    }
    // названием параметра для использования интерфейса рекомендуется
    // использовать название интерфейса
    private static void printDeliveryPrice(Pricable del) {
        System.out.println("Delivery price "+del.calcDeliveryPrice());
        System.out.println("Order price "+del.calcOrderPrice());
    }
}
