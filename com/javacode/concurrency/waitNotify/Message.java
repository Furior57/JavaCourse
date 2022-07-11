package com.javacode.concurrency.waitNotify;
// это служебный класс, его задача содержать в себе какой то String и служить локом для потоков
public class Message {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
