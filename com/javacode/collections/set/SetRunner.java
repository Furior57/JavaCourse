package com.javacode.collections.set;

import java.util.*;

public class SetRunner {
    public static void main(String[] args) {
        String[] cars = {"Mini", "Mersedes", "Audi", "VW", "Smart",
                "Toyota", "Porshe"};
        String[] otherCars = {"Audi", "Ford", "GMC", "Toyota", "Chevrolet"};
        // быстро посмотреть методы какого либо класса или интерфейса можно
        // комбинацией ctrl+N, мы перейдем в нужный класс-интерфейс, а там
        // нажам ctrl+F12 мы увидим все его методы
        // Set представляет собой множество, то есть коллекцию в которой
        // элементы не могут повторяться. Это неупорядоченная коллекция
        // она не гарантирует что вы получите элементы в том же порядке в
        // котором добавляли их

        // создаем множество имплементируя его классом HashSet
        // передавая в него массив с помощью класса Arrays
        Set<String> carsSet = new HashSet<>(Arrays.asList(cars));
        // создаем еще один Set
        Set<String> otherCarSet = new HashSet<>(Arrays.asList(otherCars));
        // создаем третий сет для демонстрации уникальности элементов
        Set<String> uniCars = new HashSet<>(Arrays.asList(cars));
        uniCars.addAll(otherCarSet);
        // вывод будет такой:
        //[VW, Mini, Toyota, Audi, Chevrolet, Porshe, Mersedes,
        // Ford, GMC, Smart]
        // как видно машины расположены в случайном порядке, связано это с тем
        // что здесь используется хеш-таблица для хранения данных
        // скорость доступа к элементам O(1)
//        print(uniCars);

        // пример использования LinkedHashSet
        // его отличие в том что здесь элементы расположены по
        // порядку добавления, скорость так же O(1)
        Set<String> otherCarSetLink = new LinkedHashSet<>(Arrays.asList
                (otherCars));
        Set<String> uniCarsLink = new LinkedHashSet<>(Arrays.asList(cars));
        uniCarsLink.addAll(otherCarSetLink);
//        print(uniCarsLink);

        // пример использования TreeSet. Эта структура отличается тем,
        // что мы можем отсортировать элементы по какому то признаку
        // по умолчанию используется своя имплементация метода compareTo()
        // скорость доступа O(logN)
        Set<String> otherCarSetTree = new TreeSet<>(Arrays.asList
                (otherCars));
        Set<String> uniCarsTree = new TreeSet<>(Arrays.asList(cars));
        uniCarsTree.addAll(otherCarSetTree);
        // здесь вывод будет в алфавитном порядке:
//        [Audi, Chevrolet, Ford, GMC, Mersedes, Mini, Porshe,
//        Smart, Toyota, VW]
//        print(uniCarsTree);


        // поработаем со своими обьектами и классом HashSet
        // по легенде у нас есть два диллера сдающие автомобили в аренду
        // в конструктор передаем модель, марку, стоимость аренды за день(евро)
        Set<Car> europeCars = new HashSet<>();
        // заполняем наш Set инициализируя конструктор Car сразу при добавлении
        europeCars.add(new Car("Mersedes", "S-201", 15));
        europeCars.add(new Car("Reno", "Logan", 4));
        europeCars.add(new Car("Pigeot", "203", 12));
        europeCars.add(new Car("BMW", "m3", 20));
        europeCars.add(new Car("Nissan", "Primera", 11));
        europeCars.add(new Car("Lexus", "RX-300", 16));
        europeCars.add(new Car("Cherry", "noname", 1));


        Set<Car> asianCars = new HashSet<>();
        asianCars.add(new Car("Reno", "Logan", 4));
        asianCars.add(new Car("Toyota", "Camry", 13));
        asianCars.add(new Car("Nissan", "Primera", 11));
        asianCars.add(new Car("Lexus", "RX-300", 16));
        asianCars.add(new Car("Cherry", "noname", 1));
        asianCars.add(new Car("Mitsubishi", "Lancer", 10));


        Set<Car> uniDealerCars = new HashSet<>();
//        uniDealerCars.addAll(europeCars);
        // итак мы создали множество, наполнили его своими авто и вывели
        // в консоль список авто, несмотря на то что у нас есть один
        // автомобиль который полностью повторяет сам себя, бренд,
        // модель и цена за день, он все равно добавился во множество
        // (в дальнейшем это было исправлено, так что сейчас код работает как надо)
        // дело в том что уникальность обьекта надо проверять, делается
        // это двумя методами hashCode() и equals() их имплементация и комментарии
        // в классе Car
//        printCar(uniDealerCars);
//        System.out.println("\n");

        // как и в математике в java есть операции над множествами

        // обьединение множеств, оно же сложение
        // удаляются повторяющиеся элементы, остальные возвращаются
        // новым множеством
//        uniDealerCars.addAll(europeCars);
//        printCar(uniDealerCars);
//        System.out.println("Выше обьединение множеств\n");
        // разность множеств, оно же вычитание
        // из вызывающего множества удаляются все элементы
        // присутствующие в вызываемом множестве
//        uniDealerCars.removeAll(asianCars);
//        printCar(uniDealerCars);
        System.out.println("\n");

        // пересечение множеств, остаются только те элементы
        // которые присутствуют в обоих множествах
//        europeCars.retainAll(asianCars);
        printCar(europeCars);
        System.out.println("\n");

        // симметрическая разность, остаются только те элементы
        // которые не совпадают в множествах, то есть те которые
        // совпадают будут удалены из обоих множеств
        // имплементации такого метода в java нет поэтому мы из
        // обьединенных множеств вычтем пересечение множеств

        // создадим временный сет на основе первого множества
        Set<Car> tmp = new HashSet<>(europeCars);
        // обьединим его со вторым множеством
        tmp.addAll(asianCars);
        // вычислим пересечение между сетами и запишем его во второй сет
        // (ну или в отдельный временный сет для пересечения)
        europeCars.retainAll(asianCars);
        // а теперь используя наш временный обьединенный сет вычитаем
        // из него пересечение
        tmp.removeAll(europeCars);
        printCar(tmp);

        // так же имеется возможность хранить данные в том порядке
        // в котором мы добаляем их эту возможность реализует
        // LinkedHashSet у него никаких новых методов нет
        // скорость обработки фактически такая же как и у HashSet

        // если нам необходимо упорядочивать данные в множестве, то
        // в java есть структура TreeSet

        // скопируем сблок кода и создадим его как дерево
        // но если мы оставим все как есть ide выдаст исключение
        // связанное с тем, что нам необходимо добавить признак
        // по которому будут упорядочиваться элементы
        // для этого в классе Car имплементируем интерфейс Comparable
        // и метод compareTo() а так же интерфейсом выберем не Set
        // а NavigableSet, чтобы у нас были имплементированы методы
        // позволяющие передвигаться по дереву
        NavigableSet<Car> treeCars = new TreeSet<>();
        treeCars.add(new Car("Mersedes", "S-201", 15));
        treeCars.add(new Car("Reno", "Logan", 4));
        treeCars.add(new Car("Pigeot", "203", 12));
        treeCars.add(new Car("BMW", "m3", 20));
        treeCars.add(new Car("Nissan", "Primera", 11));
        treeCars.add(new Car("Lexus", "RX-300", 16));
        treeCars.add(new Car("Cherry", "noname", 1));

        Car car = new Car("Nissan",
                "Primera", 11);
        Car car1 = new Car
                ("Cherry", "noname", 1);

        printCar(treeCars);
        // этот метод возвращает нам все элементы от rootNode до того элемента
        // который мы передали в метод, переданный элемент в вывод не
        // включается
        SortedSet<Car> cars1 = treeCars.headSet(car);
        printCar(cars1);

        // этим методом мы получим все элементы после того который передали в
        // метод, переданный элемент включается в вывод
        SortedSet<Car> cars2 = treeCars.tailSet(car);
        printCar(cars2);
        // этот метод возвращает часть коллекции от какого то элемента
        // до какого то элемента, есть опциональный параметр fromInclusive
        // указывающий включать ли элемент в вывод или нет, пишется
        // после какого либо из элементов, либо после обоих
        SortedSet<Car> cars3 = treeCars.subSet(car1, true,
                car, true);
        printCar(cars3);
        System.out.println("\n");
        // этот метод возвращает наименьший элемент, который больше
        // переданного элемента
        System.out.println(treeCars.higher(car));
        // аналогично, возвращает наибольший обьект который меньше переданного
        System.out.println(treeCars.lower(car));

        // этот метод используется для получения наименьшего элемента
        // который больше или равен переданному элементу, аналогично
        // метод floor() ищет наибольший элемент который меньше переданного
        // либо равен ему, не путать с higher() и lower() они возвращают
        // элементы которые никогда не будут равны переданному
        System.out.println(treeCars.ceiling(car));


    }

    // функция для вывода элементов в консоль
    private static void print(Set<String> cars) {
        System.out.println(cars);
    }

    private static void printCar(Set<Car> cars) {
        System.out.printf("%-20s %-20s %-20s \n", "Brand", "Model",
                "Price per day");
        for (Car e : cars) {
            System.out.printf("%-20s %-20s %-20s \n",e.getCarBrand(),
                    e.getModel(), e.getPricePerDay());
            ;
        }
    }



}
