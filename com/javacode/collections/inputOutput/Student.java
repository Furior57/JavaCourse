//package com.javacode.collections.inputOutput;
//
//import com.javalesson.collections.TreeMap.SubjectGrade;
//
//import java.io.Serializable;
//import java.util.Set;
//// для того чтобы отправить по сети обьект нашего класса(либо сохранить на диск) нам необходимо
//// имплементировать интерфейс Serializable
//// этот интерфейс позволяет сериализировать данный обьект,
//// то есть записать его в байтовом значении, с последующей десереализацией
//// которая восстановит состояние обьекта
//// так же важно помнить что если обьект содержит ссылки на какие то
//// другие обьекты то их тоже надо сериализовывать, для этого
//// имплементируем Serializable во всех классах обьекты которых
//// лежат в полях нашего класса, однако иногда нам не нужны все поля класса
//// в таком случае мы помечаем поле которое не требуется сериализовывать
//// ключевым словом transient, это поле будет игнорироваться при передаче обьекта
//// и при десереализации его значением будет null (именно обьекты, примитивные типы
//// помечать не надо)
//public class Student implements Serializable {
//    private String name;
//    private float  averageGrade;
//    private Set<SubjectGrade> gradeSet;
//
//    public Student(String name, float averageGrade, Set<SubjectGrade> gradeSet) {
//        this.name = name;
//        this.averageGrade = averageGrade;
//        this.gradeSet = gradeSet;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public float getAverageGrade() {
//        return averageGrade;
//    }
//
//    public Set<SubjectGrade> getGradeSet() {
//        return gradeSet;
//    }
//}
