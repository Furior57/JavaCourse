package com.javacode.helloWorld.controlstatement;

public class WhileDo {
    public static void main(String[] args) {
        int finalBalance = 100000;
        double currentBalance = 0;
        int payment = 10000;
        int years = 0;
        double interesedRate = 0.1;
        // работа цикла while
//        while (currentBalance < finalBalance) {
//            currentBalance += payment;
//            currentBalance = currentBalance + currentBalance*interesedRate;
//            years++;
//            System.out.println("Year "+years+" - "+ currentBalance);
        // если условие в цикле изначально ложно, то такой цикл не отработает
           // ни одной итерации, в таком случае используется цикл
           // do while

           do {
                currentBalance += payment;
                currentBalance = currentBalance + currentBalance*interesedRate;
                years++;
                System.out.println("Year "+years+" - "+ currentBalance);
               }while (currentBalance > finalBalance);
           // теперь несмотря на ложное условие цикл пройдет одну итерацию
           // также в условие можно вставлять логические операторы && ||


    }
}
