package com.javacode.oop.constuctors;

// импортируем класс из другого пакета для работы с ним
import com.javacode.oop.domainmodel.Employee;
// а так мы импортируем ВСЕ классы из пакета
// однако при таком импортировании можно столкнуться с проблемой,
// в разных пакетах классы могут одинаково называться
// поэтому лучше импортировать необходимый класс по имени

// а так мы импортируем все статические методы в наш класс
// теперь при их использовании мы не указываем из какой библиотеки они
// вместо Math.sqrt(4) мы пишем просто sqrt(4)
import static java.lang.Math.*;


public class ConstructorMain {
    public static void main(String[] args) {

        double sqrt = sqrt(4);

        Employee employee = new Employee("John", "Manager",
                4000);

        // мы можем не выполнять импорт пакета, обьявление нового экземпляра
        // будет выглядеть так
//        com.javalesson.domainmodel.Employee employee1 = new
//                com.javalesson.domainmodel.Employee();
        // то есть мы полностью указываем адрес по которому лежит наш класс
        // это удобно в случае если мы обращаемся к библиотеке один раз
        // и нам нет необходимости импортировать ее полностью ради этого

        Employee employee1 = new Employee("David", "Driver");

        // здесь еще один интересный момент, мы переопределили представление
        // класса с помощью метода toString(), теперь при выводе в sout
        // мы можем указать название экземпляра класса и получим выводом не его
        // адрес в памяти, а строку с описанием параметров,
        // согласно нашего метода toString()
        System.out.println(employee);

        Employee employee2 = new Employee("Art", "Manager",
                5000);

        System.out.println(employee1);
    }
}
