package com.javacode.lambdas;

import java.util.Locale;

// так же как и интерфейс параметризируем класс
public class TransformUtils<T> {
    // первая функция будет принимать обьект типа Т и обьект интерфейса Transformable
    // возвращать будет функцию transform() из этого интерфейса
    T transform(T t, Transformable<T> function) {
        return function.transform(t);
    }

    // вторая функция будет статической, принимать и возвращать String, ставить в конце строки
    // восклицательный знак и переводить ее в верхний регистр
    static String exclane(String s) {
        return s.toUpperCase(Locale.ROOT)+"!!!!";
    }
    // все это было подготовкой к ознакомлению с method reference вернемся в LambdaExample 53
}
