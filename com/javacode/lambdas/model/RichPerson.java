package com.javacode.lambdas.model;

public class RichPerson {
    String firstName;
    String lastName;
    int salary;
    int age;

    public RichPerson(String firstName, String lastName, int salary, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "RichPerson{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }
}
