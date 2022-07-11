package com.javacode.generics.company;

import com.javacode.generics.company.employees.Accountant;
import com.javacode.generics.company.employees.ITSpecialist;
import com.javacode.generics.company.employees.Manager;

public class CompanyMain {
    public static void main(String[] args) {
        // мы создали несколько тренировочных классов
        // ими мы схематично описали абстрактную компанию в которой есть отделы,
        // в отделах есть сколько то сотрудников разных профессий, goTo 24
        Company company = new Company();
        // теперь мы имеем параметризированный класс и при обьявлении его инстанса
        // мы указывает обьекты какого типа он использует, в нашем случае в разные
        // департаменты идут сотрудники разных профессий goTo Company-9
        Department<Accountant> accounting = new Department<>("Accounting", 5);
        Department<Manager> management = new Department<>("Management", 2);
        Department<ITSpecialist> itGuys = new Department<>("Management", 2);

        Accountant accountant = new Accountant("Alex", 1500.0f);
        ITSpecialist it = new ITSpecialist("Art", 2000.0f);
        Manager manager = new Manager("Galina", 4000.0f);

        // мы хотим добавить нового сотрудника в отдел
        // Alex ушел работать в бухгалтерию
        accounting.addEmployee(accountant);
        // однако мы можем сделать так чтобы
        // и наш it-специалист тоже был добавлен в бухгалтерию
        // такого быть не должно, необходимо ограничить добавление сотрудников
//        accounting.addEmployee(it);
        // перейдем в Department на 18 строку и поменяем сигнатуру нашего метода
        // так, чтобы он работал правильно, и добавим сотрудников в департаменты
        itGuys.addEmployee(it);
        management.addEmployee(manager);
        // добавим в компанию департаменты
        company.addDepartment(accounting);
        company.addDepartment(itGuys);
        company.addDepartment(management);
        // выведем на экран департаменты
        company.print(company.getDepartments());
        // а можем сотрудников
        company.print(company.getEmployees());
        // все потому что мы использовали wildcard без ограничений, однако в реальной
        // практике такое почти не используется, теперь рассмотрим ограничение по
        // нижней границе goTo Company 55




    }
}
