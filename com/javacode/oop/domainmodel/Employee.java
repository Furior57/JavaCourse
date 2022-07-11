package com.javacode.oop.domainmodel;

public class Employee {

    private static int id = 1000;
    private int employeeId;
    private String name;
    private String position;
    private int salary;
    private String department;

    // это не статичный блок инициализации, с помощью таких скобок мы можем присвоить
    // значение полю где то после того как применили логику, сбрасывая инзменения,
    // это как пример
    {
        department = "IT";
    }

    // то же самое, но блок статический
    static {
        id = 1001;
    }


    // инициализируем конструктор класса, указываем что при создании
    // экземпляра класса мы должны передать три параметра
    public Employee(String name, String position, int salary) {
        // но внутри мы не именуем параметры, а передаем их
        // другому конструктору, который принимает 4 параметра
        // четвертым идет отдел, а уже он создает экземпляр класса,
        // присваивает id и создает имена для параметров
        // в данном случае это сделано для того чтобы отдел
        // автоматически ставился как IT
        this(name, position, salary, "IT");
        System.out.println("Constructor with 3 params called");
    }
    // а вот здесь интересная фича, во первых, этот конструктор был сгенерирован
    // с помощью alt+insert, во вторых, не смотря на то что мы уже создали
    // один конструктор класса, мы пожем перегрузить его и создать новый,
    // но параметры должны отличаться, в этом конструкторе
    // мы можем реализовать  какую то другую логику при создании экземпляра
    // класса, чтобы его запустить мы должны передать только два параметра,
    // допустим здесь будет указано что это временный сотрудник

    private Employee(String name, String position,
                     int salary, String department) {
        id = id + 1;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.department = department;
        System.out.println("Constructor with 4 params called");
    }


    public Employee() {
    }

    public Employee(String name, String position) {
        employeeId = id + 1;
        this.name = name;
        this.position = position;
        System.out.println(name + " is temporary employer");
    }


    public static int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() {
        return salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    // с помощью alt+insert генерируем метод toString() выводящий данные о нашем
    // сотруднике

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                '}';
    }
}
