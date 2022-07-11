//package com.javacode.collections.inputOutput;
//
//import com.javalesson.collections.TreeMap.AverageStudentGrade;
//import com.javalesson.collections.TreeMap.SubjectGrade;
//import com.javalesson.collections.TreeMap.TreeMapLauncher;
//
//import java.io.*;
//import java.util.*;
//
//public class IOMain {
//    // для удобства и чистоты кода все вынесено в отдельные
//    // классы, описание в классе OldIOMain, реализация в Writer и Reader
//    // описание начинать читать с этого же класса
//    private static final String FILE_NAME = "Grade book.txt";
//    private static final SortedMap<AverageStudentGrade, Set<SubjectGrade>> GRADES = TreeMapLauncher.createGrades();
//    private static final String BINARY_FILE = "Students.bin";
//    private static final String BUFFERED_FILE = "Buffer.txt";
//
//    public static void main(String[] args) throws IOException {
//        Reader reader = new Reader();
//        // считываем поток с файла
////        reader.readFile(FILE_NAME);
//        Writer writer = new Writer();
//        // записываем поток в файл
//        writer.bufferedWrite(FILE_NAME,GRADES);
//        // вводим с клавиатуры поток и форматируем его для записи в файл
////        writer.writeWithFormatter();
//
//        // вся информация которую мы записываем является string-ом, то есть если
//        // записываем какое то число в файл, то оно становится строкой,
//        // то есть если мы попробуем записать экземпляр класса
//        // то мы потеряем всю мета-информацию о нем, где строка, где int,
//        // а где ссылка на другой обьект.
//        // но в java есть возможность записывать непосредственно обьекты,
//        // со всеми их полями, для этого используется ObjectOutputStream
//        // разьяснения в методе processGrades()
//
//        // передаем в наш метод Map с оценками для создания обьектов Student,
//        // writer, которым будем все записывать и название файла
////        processGrades(GRADES, writer, BINARY_FILE);
//
//        // итак мы имеем файл с какой то информацией о состоянии наших обьектов, в данный
//        // момент просто открыть этот файл и прочитать его у нас не выйдет
//        // нам необходимо сначала выполнить десереализацию, сделаем это в классе Reader
////         outputObjects(BINARY_FILE, reader);
//        FileUtils fileUtils = new FileUtils();
////        fileUtils.printIOFileDetails("C:\\test.txt");
////        fileUtils.printNIOFileDetails(FILE_NAME);
//
//        // считываем содержимое файла с помощью NIO
////        Reader reader1 = new Reader();
////        reader1.readFileInFull(FILE_NAME);
//
//        // метод для буферного считывания файла
////        reader.nioReadFileWithBuffered(FILE_NAME);
//
//        // метод для буферной записи nio
////        writer.nioWriteWithBuffer(BUFFERED_FILE);
//
//        // метод для преобразования байтового потока в символьный и последующего чтения
////        reader.nioReadWithStream(FILE_NAME);
//
//        // метод NIO для байтовой записи данных
////        writer.nioWriteWithStream("Test file for write");
//        // читаем данные с помощью канала
////        reader.nioReadWithChannel(FILE_NAME);
//        // записываем данные с помощью канала
////        writer.nioWriteWithChannel("Buffer.bin");
////        writer.writeWithRandomAccess(FILE_NAME);
//        // создаем в корневой папке директорию temp
//        fileUtils.processDir();
//
//    }
//
//    // мы создали вспомогательный класс Student экземпляры которого мы и будем
//    // записывать в поток, для этого создадим метод
//    // агрументами принимаем Map в которой будет средняя оценка - ключ и
//    // множество с оценками по предметам - значение, так же передадим
//    // туда writer с помощью которого будем записывать и имя файла
//    private static void processGrades(SortedMap<AverageStudentGrade, Set<SubjectGrade>> grades
//            , Writer writer, String filename) throws IOException {
//        // создадим список со студентами и в цикле заполним его
//        List<Student> students = new ArrayList<>();
//        for (AverageStudentGrade gradeKey : grades.keySet()) {
//            students.add(new Student(gradeKey.getName(), gradeKey.getAverageGrade(),
//                    grades.get(gradeKey)));
//        }
//        // используем метод написаный нами для сериализации, обьяснения в классе Writer
//        writer.writeObject(students, filename);
//    }
//
//    private static void outputObjects(String filename, Reader reader) {
//        List<Student> students = reader.readObject(filename);
//        for (Student s : students) {
//            System.out.printf("%s, %.2f %n", s.getName(), s.getAverageGrade());
//            System.out.println(s.getGradeSet());
//        }
//    }
//
//
//
//}
