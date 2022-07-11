package com.javacode.collections.TreeMap;

import java.util.*;

// наша программа будет считать среднюю оценку студентов
public class TreeMapLauncher {
    public static void main(String[] args) {
        NavigableMap<AverageStudentGrade,Set<SubjectGrade>> grades = createGrades();
        printMap(grades, false);
        System.out.println("\n");
        // допустим мы хотим отделить студентов которые будут получать стипендию
        // для этого нам необходимо установить минимальный средний бал
        // при котором она выплачивается
        // воспользуемся методом ceilingKey(), напомню, этот метод возвращает
        // наименьшее значение которое равно, либо больше значения которое мы передали
        // в метод. мы в этот метод передадим обьект класса AverageStudentGrade
        // в котором поле имени оставим пустым, а значение установим 80, это
        // будет наш порог получения стипендии
        AverageStudentGrade border =
                grades.ceilingKey(new AverageStudentGrade("", 80));
        // воспользуемся методом tailMap(), этот метод вернет коллекцию элементов
        // которые меньше либо равны того элемента что мы передали, тот самый список на стипендию
        NavigableMap<AverageStudentGrade, Set<SubjectGrade>> stipendiats =
                (NavigableMap<AverageStudentGrade, Set<SubjectGrade>>) grades.tailMap(border);

        printMap(stipendiats,false);
        System.out.println("--------------------------------------");
        // чтобы получить нашу коллекцию в обратном порядке мы во-первых
        // должны использовать интерфейс NavigableMap, для того чтобы получить
        // иерархию, а во-вторых использовать метод descendingMap()
        printMap(stipendiats.descendingMap(),false);
        System.out.println("--------------------------------------");
        System.out.println("Contender");
        // здесь с помощью метода lowerKey() мы получаем наибольшее
        // значение которое меньше чем наш border, то есть это ближайший
        // претендент на стипендию
        AverageStudentGrade contender = grades.lowerKey(border);
        System.out.println(contender);
        System.out.println("--------------------------------------");
        // а здесь мы выведем студента с самой высокой оценкой
        System.out.println(stipendiats.lastEntry());
        // ну либо сделать descendingMap() и использовать firstEntry() который
        // выведет первый элемент в коллекции

    }
    // вспомогательный метод в котором в Set записаны оценки студентов по разным
    // предметам
    public static NavigableMap<AverageStudentGrade, Set<SubjectGrade>> createGrades() {
        Set<SubjectGrade> alexGrades = new HashSet<>();
        alexGrades.add(new SubjectGrade("physics", 93));
        alexGrades.add(new SubjectGrade("math", 96));
        alexGrades.add(new SubjectGrade("English", 90));
        alexGrades.add(new SubjectGrade("chemistry", 76));
        alexGrades.add(new SubjectGrade("history", 79));

        Set<SubjectGrade> andrewGrades = new HashSet<>();

        andrewGrades.add(new SubjectGrade("physics", 95));
        andrewGrades.add(new SubjectGrade("math", 90));
        andrewGrades.add(new SubjectGrade("English", 85));
        andrewGrades.add(new SubjectGrade("chemistry", 90));
        andrewGrades.add(new SubjectGrade("history", 95));

        Set<SubjectGrade> ivanGrades = new HashSet<>();

        ivanGrades.add(new SubjectGrade("physics", 75));
        ivanGrades.add(new SubjectGrade("math", 86));
        ivanGrades.add(new SubjectGrade("English", 85));
        ivanGrades.add(new SubjectGrade("chemistry", 74));
        ivanGrades.add(new SubjectGrade("history", 68));
        // для упорядочивания значений передадим наши сеты в NavigableMap
        // ключом выбрав элемент класса AverageStudentGrade, а значением
        // множество с оценками студентов
        NavigableMap<AverageStudentGrade,Set<SubjectGrade>> studentsGrade =
                new TreeMap<>();
        // теперь заполняем наше дерево, ключ это элемент класса AverageStudentGrade
        // содержащий в себе имя студента и его среднюю оценку, расчитаную
        // методом calcAverage, а значение все так же множество со всеми его оценками
        studentsGrade.put(new AverageStudentGrade
                ("Alex",calcAverage(alexGrades) ), alexGrades);
        studentsGrade.put(new AverageStudentGrade
                ("Andrew",calcAverage(andrewGrades) ), andrewGrades);
        studentsGrade.put(new AverageStudentGrade
                ("Ivan",calcAverage(ivanGrades) ), ivanGrades);

        return studentsGrade;
    }

    // этот метод вычисляет средний балл студента
    private static float calcAverage(Set<SubjectGrade> grades) {
        float sum=0;
        for (SubjectGrade e : grades) {
            sum += e.getGrade();
        }
        return sum/grades.size();
    }
    // метод принимает Map со средней оценкой и булево отвечающее за то
    // печатать ли все оценки по предметам или нет
    private static void printMap(Map<AverageStudentGrade, Set<SubjectGrade>> grade,
                                 boolean printValue) {
        // получим ключи по которым будем итерироваться
        Set<AverageStudentGrade> averageGrades = grade.keySet();
        for (AverageStudentGrade sg : averageGrades) {
            System.out.println(sg);
            if (printValue) {
                System.out.println(grade.get(sg));
            }
        }

    }

}
