package com.javacode.oop.Exceptions;
// Создание пользовательского исключения

// При создании класса расширяем им класс ошибок которые нас интересуют
// еще раз напомню, что при расширении на класс ниже уровнем
// например ArrayIndexOutOfBoundsException, мы не сможем обрабатывать ошибки
// класса в который он входит, то есть RuntimeException мы уже в качестве
// параметра передать не можем
// При создании такого класса необходимо сделать исчерпывающее
// описание того, для чего этот класс сделан и когда его не стоит использовать
public class InvalidInputParamException extends RuntimeException {
    public InvalidInputParamException() {
    }

    public InvalidInputParamException(String message) {
        super(message);
    }

    public InvalidInputParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputParamException(Throwable cause) {
        super(cause);
    }

    public InvalidInputParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
