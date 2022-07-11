package com.javacode.streams;

import com.javacode.lambdas.model.Employee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// вернемся к изучению интерфейса Stream. сначала небольшое отступление. сам по себе Stream
// не является какой либо коллекцией, это так называемый wrapper, обертка, для других коллекций,
// получая какую либо коллекцию он преобразовывает ее в поток и обратно. преимуществ у
// стрима много, мы будем их рассматривать ниже.

// итак мы создали какой то класс у которого полями определили две коллекции, это List с
// обьектами класса Employee и Map где ключ число, а значение этот же обьект.
// Employee это тот же класс которым мы пользовались ранее, но мы добавили в него поле
// id и определили методы equals() и hashcode(), чтобы можно было сравнивать обьекты.

public class StreamsOverviewMain {
    private static List<Employee> employeeList = new ArrayList<>();
    private static List<Employee> secondlist = new ArrayList<>();
    private static Map<Integer, Employee> employeeMap = null;

    public static void main(String[] args) throws IOException {
        // заполняем наш список
        employeeList.add(new Employee(1, "Alex", "Black", 50000,"IT"));
        employeeList.add(new Employee(2, "John", "Green", 75000,"IT"));
        employeeList.add(new Employee(6, "Sam", "Brown", 80000,"IT"));
        employeeList.add(new Employee(9, "Tony", "Gray", 90000,"IT"));
        employeeList.add(new Employee(10, "Mike", "Yellow", 60000,"IT"));
        employeeList.add(new Employee(11, "Victoria", "Pink", 75000,"IT"));
        employeeList.add(new Employee(16, "Sean", "Magenta", 80000,"Finance"));
        employeeList.add(new Employee(19, "Kate", "Black", 88000,"Finance"));
        employeeList.add(new Employee(9, "Tony", "Gray", 90000,"Finance"));
        employeeList.add(new Employee(10, "Mike", "Yellow", 60000,"IT"));
        employeeList.add(new Employee(11, "Victoria", "Pink", 75000,"Finance"));
        secondlist.addAll(employeeList);
        // ниже мы создали три служебных метода, тест потока из коллекции, тест потока из файла
        // и поиск обьекта по id, перейдем к первому методу testStreamFromList
        testStreamFromList();
//        testStreamFromFile();
    }

    private static void testStreamFromList() {
        // знакомимся с методами интерфейса Stream
        // создаем поток из списка
        Stream<Employee> stream = employeeList.stream(); // лучше не присваивать стрим переменной,
        // но тут я это сделал для своего удобства
        // первый метод filter(), он принимает в себя Predicate, который возвращает булево,
        // и создает новый поток на основе совпадений.
        // мы можем использовать его несколько раз подряд, однако с помощью операторов
        //  && || мы можем все сделать в одном методе, здесь мы выводим тех
        // у кого зп больше 80к и id меньше 10, а затем преобразовываем поток
        // обратно в коллекцию с помощью метода collect(), в который передается обьект
        // класса Collectors и на нем вызывается метод toList() для преобразования в список,
        // мы также можем преобразовать в Set и Map, мы можем сразу после фильтров вызвать toList(),
        // однако такое работает только для списков, остальные структуры вызываются
        // через обьект Collectors
        stream.filter(e -> e.getSalary() > 80000)
                .filter(e -> e.getId() < 10).collect(Collectors.toList()); // убрали sout чтобы не мешался
//                .forEach(System.out::println);
        // теперь создадим поток из массива
        Integer[] ids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        // для этого пользуемся методом of(), мы можем не передавать весь массив, а только
        // те элементы которые нам нужны по их индексу, а можем напрямую передавать элементы
        // Stream.of(1,2,3,4);
        // здесь передадим массив c id, мы будем искать совпадения id с нашими Employee
        // для этого воспользуемся методом map(), этот метод принимает в себя параметром функцию
        // мы написали служебный метод findById(), чтобы передать его в другой метод воспользуемся
        // метод референсом, пока спустимся и ознакомимся с findById()
        // вернемся к методу map(), этот метод принимает в себя функцию и обрабатывает
        // каждый элемент в потоке, затем преобразовываем обратно в List и выводим на экран получившийся
        // результат, где id совпадает, будет выведен работник, где нет - null, чтобы этого не случилось
        // добавим фильтр убирающий null значения Objects::nonNull
        Stream.of(ids).map(StreamsOverviewMain::findById).filter(Objects::nonNull).toList()
                .forEach(System.out::println);
        // если мы например хотим увидеть только первое совпадение после фильтра то добавляем метод
        // findFirst(), напишем псевдокод чтобы увидеть как это выглядит в переменной
        Optional<Employee> first = Stream.of(ids).map(StreamsOverviewMain::findById)
                .filter(Objects::nonNull).findFirst();
        // как мы видим эта переменная относится к классу Optional, о нем мы поговорим позже
        // теперь перейдем к методу testStreamFromFile()

        // теперь поподробней разберем map() и его производные, создадим стрим
        OptionalDouble average = Stream.of(ids)
                // точно так же получаем всех работников с id и убираем null-значения
                .map(StreamsOverviewMain::findById)
                .filter(Objects::nonNull)
                // этот метод после обработки возвращает не Stream<Integer>, а IntStream
                // здесь мы хотим увидеть зарплаты наших сотрудников
                .mapToInt(Employee::getSalary)
                // а теперь присвоим это все переменной и просуммируем
//                .sum();
                // это просто, заместо этого выведем среднее значение
                // как мы можем видеть тип переменной при этом OptionalDouble, о нем поговорим ниже
                .average();
        // так же после mapToInt() мы можем применить методы min() и max()
        System.out.println("------------------------------------------------------------");
        // теперь познакомимся с flatMap()
        // мы создали список secondList который заполнили значениями из первого employeeList
        // создаем новый список который параметризируем списком параметризированным Employee :)
        // проще говоря заведем двумерный список и добавим в него два наших списка
        List<List<Employee>> departments = new ArrayList<>();
        departments.add(employeeList);
        departments.add(secondlist);
        // теперь используем flatMap(), для чего нужен этот метод? Если мы работаем с многомерными
        // структурами данных, он возвращает несколько потоков, в каждом потоке свой элемент(у нас список)
        // далее мы уже можем их обрабатывать,
        // если мы будем использовать просто map() то при попытке вывести имена работников мы получим
        // строкое представление самих списков, не отдельных элементов,
        // если сказать проще мы разворачиваем Stream<List<Employee>> с двумя списками, в два Stream<Employee>
        departments.stream().flatMap(l -> l.stream().map(Employee::getName))
                .forEach(System.out::println);
        System.out.println("-------------------------------------------------------------------");
        // перейдем к методу forEach()
        // это терминальный метод , то есть после того как мы получили
        // в него элементы и обработали их, стрим закрывается и доступа к ним больше нет, но
        // есть альтернатива позволяющая снова получить элементы - peek(), этот метод возвращает тот же
        // стрим который в него передали, но перед этим проводит какие то операции с его элементами
        // здесь мы взяли массив с числами, отфильтровали как нам удобно, с помощью peek() получили
        // все элементы этого массива, изменили их и записали в новый список, получили обратно тот же стрим
        // который изначально передавали, вывели на экран и вывели на экран список измененных значений
        List<Integer> ids1 = new ArrayList<>();
        Stream.of(ids).filter(e -> e < 5).peek(e -> ids1.add(e *= 2))
                .forEach(e -> System.out.println("Original stream: " + e));
        ids1.forEach(e -> System.out.println("Modified collection from stream: " + e));

        // так же с помощью forEach() мы не можем модифицировать локальные переменные, а использовать
        // их сможем только пометив их как final, еще одно из ограничений касается самого цикла, мы
        // не можем выйти из него в каком то месте, как могли бы в обычном цикле с помощью break или return

        // теперь перейдем к filter()
        // как уже поминалось выше, мы можем задавать одновременно несколько условий в фильтре
        // и вроде бы такое условие будет работать быстрее, чем если мы пропишем сначала один
        // фильтр, а потом второй, но на самом деле java применит все фильтры, сначала к первому элементу
        // потом ко второму и т.д., асиптотика остается прежней.
        Arrays.stream(ids).sorted().filter(e -> e < 6 && e > 3).toList();
        // findFirst()
        // опять же как было сказано выше, этот метод возвращает первое совпадение по фильтру
        Optional<Integer> first1 = Stream.of(ids).filter(Objects::nonNull).findFirst();
        // есть альтернативный метод findAny(), он используется при работе с параллельными стримами
        // и вернет первое совпадение из всех потоков
        // как мы видим здесь тип переменной снова Optional, познакомимся с ним поближе.
        // Optional - это класс обертка, в документации java указано, что он может содержать
        // не только какое то значение, но и null, представим такую ситуацию:
        Stream.of(1, 2, 3).filter(e -> e % 5 == 0).findFirst();
        // мы заведомо поставили условие которое не может вернуть ни одного элемента, точнее вернет null,
        // и как раз тут нам пригодится класс Option, если мы по какой то причине можем получить null,
        // можно использовать этот класс обертку для того чтобы записать null в значение.
        // хорошо, но что если нам нужно получить результат после фильтра и мы не знаем будет ли он null,
        // или нет? если мы попытаемся на нашем примере получить этот элемент ide не скомпилирует код,
        // мы получим исключение NoSuchElementException:
//        Stream.of(1, 2, 3).filter(e -> e % 5 == 0).findFirst().get();
        // для того чтобы обработать null существует метод orElse(), он возвращает
        // Integer значение которое мы укажем, мы можем присвоить его переменной и уже с ней
        // работать, реализация orElse() выглядит так:
        // value != null ? value : other, где other - число которое мы задали параметром
        Integer integer = Stream.of(1, 2, 3).filter(e -> e % 5 == 0).findFirst().orElse(0);
        // есть модификация этого метода orElseGet(), в нее мы аргументом передаем интерфейс Supplier,
        // напоминание, в Supplier мы ничего не передаем, он просто возвращает обьект какого то типа
        // нужен этот метод на случай если мы получаем null, но хотим вернуть, например, не число, а
        // какой то обьект
        Employee employee = employeeList.stream().filter(e -> e.getId() == 0).findFirst()
                .orElseGet(() -> employeeList.get(1));

        // методы интерфейса Stream делятся на три типа
        // 1) Intermediate - промежуточные(map, filter, peek и т.д.), эти методы после своего выполения
        // возвращают нам стрим
        // 2) Terminal - терминальные(forEach, findFirst, findAny и т.д.) эти методы после своего выполнения
        // закрывают стрим и сделать еще что то с ним нельзя
        // 3) Short-circuit - методы сокращающие количество элементов которые будут в дальнейшем
        // обработаны другими методами(limit, skip и т.д.), они могут быть как терминальными, так и
        // промежуточными
        // методы выполняющиеся в интерфейсе Stream, являются ленивыми, вычисление результата будет
        // происходить только в самом конце, сначала все цепочка методов применяется к первому элементу,
        // потом ко второму и т.д.
        System.out.println("--------------------------------------------------------------------------");
        // теперь поговорим о reduction operations, эти операции возвращают после себя одно значение,
        // к ним относятся min, max, average и т.д. познакомимся с ними поподробней
        // если при использовании intStream мы можем пользоваться этими методами без проблем, то в случае
        // когда у нас необходимо сравнить обьекты нам необходимо определить компаратор сравнивающий
        // обьекты, перейдем к методу testSortAndReduce()
        testSortAndReduce();


    }

    private static void testStreamFromFile() throws IOException {
        // создаем поток из файла, далее разбор по методам:
        // Files.lines() - возвращает поток строк, в него мы передаем обьект Path с помощью метода
        // Paths.get(), где указываем путь к файлу, у нас файл в корне проекта поэтому просто указываем
        // его название, filter() уже разобрали, выводим все слова меньше 4 букв, с map() приводим
        // все слова к верхнему регистру, distinct() разобрали, далее сортируем и выводим на экран,
        // в Java 7 и более ранних версиях мы бы написали несколько циклов и отдельный метод для фильтра,
        // здесь все можно уместить в две строки,
        // более того, у данного способа использования потока из файла очень хорошая асимптотика и нам
        // не нужно беспокоиться о времени выполнения этих операций
        Files.lines(Paths.get("text")).filter(e -> e.length() < 4)
                .map(String::toUpperCase)
                .distinct().sorted().forEach(System.out::println);

    }

    private static Employee findById(int id) {
        // как найти работника по id?
        // сначала проверяем не пустой ли наш employeeMap, если пустой выполняем следующие
        // действия, если заполнен переходим сразу к return
        if (employeeMap == null) {
            // теперь по каждому методу отдельно:
            // создадим поток из нашего списка(stream()), оставим только уникальные элементы
            // (distinct()), преобразуем в Map(collect(Collectors.toMap()), метод toMap()
            // принимает в себя две функции, из первой получаем ключ, из второй значение
            // здесь ключ получаем методом getId, а значением с помощью лямбды передаем
            // сам обьект (обратим внимание на один момент, чтобы метод distinct() отработал как нужно
            // и оставил только уникальные обьекты, в нашем классе должны быть переопределены методы
            // equals() и hashCode() иначе метод просто не поймет что это разные обьекты)
            employeeMap = employeeList.stream().distinct()
                    .collect(Collectors.toMap(Employee::getId, e -> e));
        }
        // возвращаем обьект по ключу, вернемся на 71 строку
        return employeeMap.get(id);
    }

    private static void testSortAndReduce() {
        // метод stream.map умеет принимать в себя обьект Comparator, в нашем случае мы можем просто вычесть
        // id первого элемента из id второго, но мы воспользуемся метод референсом и методом
        // интерфейса Comparator.comparingInt(), в который передаем id наших Employee
        // небольшое напоминание, метод equals() проверяет обьекты на равенство между собой, а
        // интерфейсы Comparable и Comparator позволяют устанавливать порядок сортировки,
        // и еще одно напоминание, в нашем классе мы можем имплементировать Comparable только по
        // одной логике, однако компараторов можем создать сколько угодно
        Employee employee = employeeList.stream().max(Comparator.comparingInt(Employee::getId)).get();
        // теперь подобным образом определим минимальную зп среди сотрудников
        Employee employee1 = employeeList.stream().min(Comparator.comparingInt(Employee::getSalary)).get();
        // так же мы можем отсортировать наших сотрудников по какому то признаку
//        employeeList.stream().sorted(Comparator.comparingInt(Employee::getSalary)).toList()
//                        .forEach(System.out::println);
//        System.out.println("Youngest employee "+employee);
//        System.out.println("Minimum wage at "+employee1);
        // все reductions operation можно свести к одному методу в интерфейсе Stream, это reduce()
        // у него есть несколько реализаций, первая принимает в себя BinaryOperator и возращает
        // тип Optional, вторая принимает в себя экземпляр того типа который обрабатывает и
        // BinaryOperator, если последний возвращает null, то возвращает переданный экземпляр,
        // есть и третья реализация, но ее пока касаться не будем, поработаем со второй.

        // для большинства случаев хватает стандартных функций, sum, min, max, average, но мы
        // воспроизведем такой случай, допустим мы хотим получить не только сумму зарплат сотрудников, но
        // и сумму id
        // первое, нам нужен дефолтный Employee, в reduce он называется identity
        // далее мы определяем лямбда функцию, в ней будут два аргумента и в теле функции
        // мы на каждой итерации задаем id и salary как сумму этих параметров двух аргументов
        // немного запутано, обьсним на пальцах, мы перем первый элемент коллекции и второй элемент
        // к нужному значению у первого элемента прибавляем нужное значение второго и так до конца коллекции
        // где e1 это предпоследний элемент а e2 последний, после последнего суммирования возвращаем
        // e1 где уже просуммированы все значения
        Employee identity = new Employee(0,"","",0,"");
        Employee reducedEmployee = employeeList.stream().reduce(identity, (e1, e2) -> {
            e1.setId(e1.getId() + e2.getId());
            e1.setSalary(e1.getSalary() + e2.getSalary());
            return e1;
        });
        System.out.println(reducedEmployee);


    }

    // здесь подробнее говорим о преобразовании стрима в коллекцию
    private static void collectorsViewer() {
        // итак мы имеем какой то стрим, как мы можем преодбразовать его обратно в коллекцию
        // с первым случаем мы уже работали, это преобразование в список, здесь мы вывели в отдельную
        // переменную, чтобы убедиться что из списка Employee мы получили список String, далее
        // будем просто преобразовывать
        List<String> collect = employeeList.stream()
                .map(e -> e.getName().toUpperCase()).sorted().collect(Collectors.toList());
        // по аналогии можно преобразовывать в любую коллекцию, не будем перебирать их все,
        // рассмотрим метод Collectors.toCollection(), он принимает в себя интерфейс Supplier
        // еще раз вспоминаем, что этот интерфейс возвращает какой то обьект, мы можем указать
        // здесь конструктор той коллекции, которая нас интересует, но метод для нее не представлен
        // в классе Collectors, здесь мы инициализируем ArrayList
        employeeList.stream()
                .map(e-> e.getName().toUpperCase())
                .sorted().collect(Collectors.toCollection(ArrayList::new));
        // теперь создадим два метода, partitionBy() и groupBy()

    }
    // мы добавили в наш класс Employee дополнительное поле с указанием отдела в котором работает человек
    // оно понадобится для метода groupBy(), пока определим метод partitionBy()
    private static void partitionBy() {
        // метод Collectors.partitioningBy() принимает в себя Predicate, мы указываем что нас интересуют
        // все работники с зп выше 70к, выведем все в отдельную переменную, как мы видим это Map,
        // где ключом служит булево, а значением список, если зп 70к и выше, то эти работники записываются
        // в список с ключом true, если меньше в список с ключом false, то есть мы разделили одну коллекцию
        // на две
        Map<Boolean, List<Employee>> collect =
                employeeList.stream().collect(Collectors.partitioningBy(e -> e.getSalary() > 70000));
    }

    // а теперь определим метод groupBy(), как можно понять из названия, он группирует обьекты по
    // какому то признаку, метод похож на partitioningBy(), за исключением того, что ключом в Map
    // будет тот признак который мы передадим.
    // groupingBy() принимает в себя Function, напоминаю, этот интерфейс принимает в себя один тип, а
    // возвращает другой, мы параметризируем наш Function типом Employee на входе и дженерик типом
    // возврата в итоге переменная с результатом работы метода groupingBy() будет иметь тип
    // Map<R, List<Employee>>, а при вызове метода groupBy, передадим в него лямбду которая вернет
    // название департамента e -> e.getDepartment, либо метод референсом Employee::getDepartment
    private static <R> void groupBy(Function<Employee, R> function) {
        Map<R, List<Employee>> collect = employeeList
                .stream().collect(Collectors.groupingBy(function));

    }
    // теперь поговорим об unbounded стримах
    private static void testStreamGenerator(int border) {
        // у интерфейса Stream есть метод generate(), создается стрим не ограниченый размером буфера,
        // принимает он в себя Supplier, то есть какой то
        // обьект, мы передадим метод random(), помним, этот метод возвращает случайные числа, так как
        // мы не поставили границу для него, то начнется бесконечный цикл, чтобы этого избежать
        // мы передали в наш метод аргументом какое то int число которое и будет нашей границей
        // и вызываем метод limit() ограничивающий количество элементов полученных из стрима
        Stream.generate(Math::random).limit(border);

    }
    // еще один пример unbounded стрима, метод iterate()
    private static void testStreamIterator(int border) {
        // этот метод принимает в себя начальное условие и функцию которая генерирует значение
        // исходя из начального условия, потом генерует значение из получившегося и т.д.
        // мы так же должны ограничить его во избежание бесконечного цикла
        Stream.iterate(1, e -> e * 2).limit(border);
    }

    // теперь поговорим о паралельных стримах
    private static void testParallelStream() throws IOException {
        // как можно понять из названия, параллельные стримы - это стримы которые работают параллельно
        // первый способ создания это метод parallelStream()
        employeeList.parallelStream().map(Employee::getName).sorted().toList()
                .forEach(System.out::println);
        // второй способ создания, например когда у нас уже есть другой стрим, в нашем случае это стрим
        // из файла с помощью метода Files.lines()
        Files.lines(Paths.get("text")).parallel().sorted().collect(Collectors.toList());
        // какими методами мы можем пользоваться с параллельными стримами?
        // если у нас числовой стрим Integer, Long мы можем пользоваться sum(), max(), min(), average()
        // при использовании с Double точный результат не гарантирован
        // map(), filter() тоже должны работать нормально, но есть несколько условий для использования
        // во-первых, наша коллекция должня быть большой, при использовании маленьких коллекций
        // весь смысл параллельныз стримов теряется
        // во-вторых, очередность получения элементов из параллельных стримов не гарантируется,
        // то есть мы можем получить последний элемент перед первым
        // allMatch(), anyMatch(), nonMatch(), count() тоже работают с параллельными стримами,
        // однако помним что порядок получения элементов не гарантирован, поэтому элемент
        // возвращаемый anyMatch() не обязательно первый по совпадению, в каком стриме он попадется
        // первым, из того и придет возврат
        // важно понимать что не везде удобно использовать параллельные стримы, дело не только в размере
        // коллекции, если мы имеем какую то коллекцию значений с которыми надо рпоизвести точные вычисления
        // например вычислить числа Фибоначчи, мы не можем гарантировать что ответ будет правильным, потому
        // что вычисления идут параллельно на разных значениях

    }


}
