package com.javacode.oop.innerclasses;
// данный класс создан для примера работы локальных классов
// Локальный класс - это класс созданный внутри блока кода другого класса
// его область видимости, как и у локальных переменных только внутри
// этого блока модификатором доступа такого класса может быть только final или
// static final, если локальный класс обьявлен в статичном блоке кода, то
// обращаться он может только к статичным полям внешнего класса
// либо по умолчанию, локальный класс в контексте экземпляра
// видит все переменные окружающего его класса и может к ним обращаться
// время жизни экземпляра локального класса ограничивается исполнением блока кода
// в котором он написан. Обычно локальные классы используют если его реализация
// проста и не нужно захламлять пространство имен
public class RadioModule {
    public RadioModule() {
        System.out.println("RadioModule initialized");
    }
    // создаем метод принимающий в себя строку с номером телефона
    public void call(String number) {
        // данная переменная будет дальше использоваться для проверки
        // длины номера
        int length = 10;
        // Внутри метода создаем класс
        class GsmModule {
            // Два поля, одно для номера телефона, второе для проверки
            // валидности номера
            private String phoneNumber;
            private int validNumber;
            // конструктор класса
            public GsmModule(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }
//            реализовываем метод проверки номера на валидность
            public boolean isValid() {
                // а вот здесь уже интересно, мы сравниваем длину строки
                // с номером с переменной инициализированной извне этого класса
                // в том методе который создал этот класс? если не совпало вернем
                // false
                if (phoneNumber.length() != length) {
                    return false;
                // иначе выполняем блок try-catch
                } else {
                    // пробуем конвертировать строку в число, если будут
                    // символы или буквы вернется false иначе true
                    try {
                        validNumber = Integer.parseInt(phoneNumber);
                        return true;
                        // перехватываем ошибку и возвращаем false
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }

            }

            public void dialIn() {
                if (isValid()) {
                    System.out.println("Call in phone number " + validNumber);
                } else {
                    System.out.println("Phone number is invalid");
                }
            }
        }
        // внутри нашего метода создаем экземпляр локального класса
        // и обращаемся к его методу
        GsmModule module = new GsmModule(number);
        module.dialIn();
    }
}
