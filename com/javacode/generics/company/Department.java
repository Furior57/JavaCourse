package com.javacode.generics.company;

import com.javacode.generics.company.employees.Employee;

import java.util.ArrayList;
import java.util.List;

public class Department<T extends Employee> {
    private String name;
    private int employeeNumbers;
    private List<T> employees = new ArrayList<>();

    public Department(String name, int employeeNumbers) {
        this.name = name;
        this.employeeNumbers = employeeNumbers;
    }

    // мы параметризируем наш класс и все связные с этим типом поля и методы
    // вернемся в Main метод на 13 строку
    public boolean addEmployee(T employee) {
        return employees.add(employee);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getEmployees() {
        return employees;
    }

    public int getEmployeeNumbers() {
        return employeeNumbers;
    }

    public void setEmployeeNumbers(int employeeNumbers) {
        this.employeeNumbers = employeeNumbers;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", employeeNumbers=" + employeeNumbers +
//                ", employees=" + employees +
                '}';
    }
}
