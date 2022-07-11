//package com.javacode.collections.inputOutput;
//
//import com.javalesson.collections.TreeMap.AverageStudentGrade;
//import com.javalesson.collections.TreeMap.SubjectGrade;
//import com.javalesson.collections.TreeMap.TreeMapLauncher;
//
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.*;
//
//public class OldIOMain {
//
//    // Весь этот класс это старый код на котроом показывалось что такое чтение и запись потока
//    // в данный момент вся реализация чтения и записи выведена в отделньые классы
//
//    private static final String FILE_NAME = "Grade book.txt";
//
//
//    public static void main(String[] args) throws IOException {
////        // для работы с файлами воспользуемся уже готовой функцией которую мы
////        // реализовывали в пакете TreeMap
//        NavigableMap<AverageStudentGrade, Set<SubjectGrade>> grades =
//                TreeMapLauncher.createGrades();
////        // теперь запишем эту информацию в файл воспользовавшись классом FileWriter
////        // так же создаем новый экземпляр класса указывая аргументом название файла
////        // по умолчанию файл создастся в корне проекта
////        // если мы оставим все в таком виде ide будет подсвечивать
////        // new FileWriter как ошибку, это связано с тем что мы никак не обрабатываем
////        // IOException, очень важно помнить, что каждый раз когда мы работаем с файлами
////        // мы должны ловить и обрабатывать исключения, они могут быть разными, от отсутствующего
////        // файла до запрета доступа к нему, здесь мы не будем обрабатывать все возможные ошибки
////        // просто воспользуемся IOException и выведем printStackTrace()
//        FileWriter writer = new FileWriter(FILE_NAME);
//        try {
//            for (AverageStudentGrade gradeKeys : grades.keySet()) {
//                writer.write("---------------------------\n");
//                writer.write("Student " + gradeKeys.getName() + " Average grade " +
//                        gradeKeys.getAverageGrade() + "\n");
//                for (SubjectGrade grade : grades.get(gradeKeys)) {
//                    writer.write("Subject: " + grade.getSubject() + " Grade: " + grade.getGrade() + "\n");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
////            // так же важно помнить что открывая для обработки какой либо файл
////            // мы не закроем его автоматически, поэтому обработаем закрытие файла
////            // в блоке finally, вне зависимости от того была ошибка или нет,
////            // этот блок выполняется всегда, у нас он закроет файл
////            // однако в современной java мы можем напрямую указать в блоке try тот файл
////            // с которым мы работаем, для этого после try в скобках инициализируем конструктор
////            // FileWriter и создадим файл, после окончания работы он автоматически закроется
////            // выглядит это так:
//            // try (FileWriter writer = new FileWriter("Grade book")){someCode},
////            // но мы здесь оставим блок finally, для понимания, что в нем можно не только
////            // файлы закрывать, а реализовать любую логику, которая выполнится в любом
////            // случае, есть у нас исключение, или нет
//        } finally {
////            // еще один важный момент, эту команду тоже в идеале надо заключить в блок
////            // try-catch, в процессе работы файл может быть поврежден или удален
////            // что выдаст исключение, но здесь этого делать мы не будем, повторим
////            // наш код в отдельном методе clearCode() где будем уже правильно
////            // обрабатывать ошибки
//            writer.close();
//            System.out.println("finally block is worked");
//        }
////
////        // Теперь прочитаем наш файл с помощью FileReader
//        FileReader reader = new FileReader(FILE_NAME);
////        // используем небольшую конструкцию, у FileReader есть метод read()
////        // этот метод возвращает какое либо целочисленное значение
////        // которое он считывает из потока, помним что в Unicode
////        // каждый символ имеет целочисленное значение
////        // если метод read() не может считать значение, он
////        // возвращает -1
////
////        // здесь мы сохраняем результат работы read() в переменную
////        // и проверяем не равна ли она -1, если нет - продолжаем работу
//        int ch;
//        while ((ch = reader.read()) != -1) {
//            System.out.println(ch);
//        }
////        // однако посимвольное считывание не является оптимизированым действием
////        // да и на экран мы выведем не символы а числа.
////        // воспользуемся другим классом
////        BufferedReader reader1 = new BufferedReader(new FileReader(FILE_NAME));
////        // этот класс считывает текст из потока и буферизирует данные, что позволяет
////        // эффективно считывать символы, массивы, строки.
////        // так же передадим считанные данные в отдельную переменную, но теперь
////        // она будет стринг
//        String string;
////        // так же создадим условие, но проверять мы будем не является ли следующая строка null
////        while ((string = reader1.readLine()) != null) {
//////            System.out.println(string);
////        }
////        // аналогично можно при записи в файл буферизировать данные, реализация в методе
////        // bufferedWriter()
////
////        // теперь опустимся на уровень битового потока. суть та же, это поток информации,
////        // просто в битовом представлении
////        // воспользуемся try-with-resources где инициализируем не только считывание информации, но
////        // и сразу ее запись в новый файл
////        try (FileInputStream reader3 = new FileInputStream(FILE_NAME);
////             FileOutputStream writerByte = new FileOutputStream("GradeBookByte.txt")) {
////            int c;
////            // напоминание, функция read() при считывании возвращает какое то число, если
////            // считывать нечего возвращает -1, снова воспользуемся этим
////            while ((c = reader3.read()) != -1) {
////                writerByte.write(c);
////            }
////        }
//////        // такое использование чтения-записи не является оптимизированным подходом, ридер поочередно
//////        // считывает каждый символ, обращаясь к физическому диску, а врайтер так же поочередно их
//////        // записывает, лучше всего пользоваться при чтении-записи буферизацией
//////
//////        // теперь посмотрим на класс Formatter, он позволяет форматировать информацию которую мы записываем
//////
////        Formatter formatter = new Formatter("BankAccounts");
//////        // для того чтобы передать в наш форматтер какой то поток создадим сканнер
//////        Scanner scanner = new Scanner(System.in);
//////        // за раз будем вводить например 3 аккаунта, для этого создадим счетчик
////        System.out.println("==================================\n");
////        int counter = 0;
////        while (counter < 3) {
////            try {
//////                // отформатируем наш поток %d - передаем число, %s - передаем строку,
//////                // %.2f - передаем float с точностью до двух знаков, %n - перенос строки
//////                // а дальше передаем сканером то что указали
//////
////                fotmatter.format("%d, %s, %s, %.2f, %n", scanner.nextInt(), scanner.next(),
////                        scanner.next(), scanner.nextFloat());
////                counter++;
////            } catch (InputMismatchException e) {
//////                // ловим исключение при неправильном вводе
////                System.out.println("Please, enter correct data");
////                scanner.nextLine();
////            }
////        }formatter.close();
////
////        // таким образом мы можем записывать информацию в файл, но есть один момент
//
////
////
////
//    }
//
//
//}