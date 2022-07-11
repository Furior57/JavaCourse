package com.javacode.collections.inputOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


public class FileTreeVisitor {
    public static void main(String[] args) throws IOException {
        // FileVisitor это абстрактный интерфейс котроый предоставляет нам функционал
        // необходимый для рекурсивного обхода директорий и каких то действий. если проще,
        // FileVisitor это поиск файла или директории при обходе графа
        // и выполнение какой то логики. выполнение логики происходит с помощью 4 методов:
        // preVisitDirectory() - выполнение логики перед посещением файла
        // visitFile() - выполнение логики при нахождении файла
        // visitFileFailed() - выполнении логики при получении исключения(доспут к файлу закрыт,
        // или он не существует)
        // postVisitDirectory() - выполнение логики после посещения файла
        // помним что интерфейс абстрактный и мы должны имплементировать все четыре метода
        // продолжение во вложенном классе Visitor

        // инициализируем обьект Path с которым будем работать
        Path path = Paths.get("temp");
        // этим методом мы инициализируем обход директории и согласно нашему классу Visitor
        // при нахождении файлов мы выводим их имя на экран
        // однако в этом случае мы вывели просто файлы, все вложенные директории были пропущены
        // все потому что мы имплементировали только один метод, который коворит что делать
        // если был найден файл, имплементируем второй метод preVisitDirectory() - 47 строка
//        Files.walkFileTree(path, new Visitor());
        // и снова мы получаем исключение при попытке скопировать вложеную
        // директорию... поехал в следующую лекцию
        Files.walkFileTree(path, new CopyFileVisitor(path, Paths.get(
                "temp2copy"+ File.separator+"copy")));
    }
    // если нам не нужны все четыре метода, для сокращения кода мы можем воспользоваться
    // интерфейсом SimpleFileVisitor, в нем эти методы уже имплементированы
    // создадим класс который расширяется этим интерфейсом

    // Параметризируем SimpleFileVisitor, для этого между знаками <> указываем класс или
    // интерфейс, или тип данных которым мы будем параметризировать, generic-и мы еще не
    // проходили, пока просто сделаем это
    public static class Visitor extends SimpleFileVisitor<Path> {
        // имплементируем метод visitFile(), в него мы должны передать обьект Path
        // и набор атрибутов, учитываем что возврат у нас FileVisitResult, это перечисление
        // которое говорит что делать после того как файл найден, мы здесь выводим на экран
        // имя файла и продолжаем дальше, продолжение на 21 строке
        // в классе Files нет возможности переименовывать файлы, как выкрутиться?
        // есть метод Files.move() который перемещает файлы и переименовывает их в процессе
        // заменим System.out.println(file) который печатает список файлов на их переименование

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            // прежде чем начинать переименовывание нам необходимо разделить наш path на субдиректории,
            // чтобы понимать где имя файла, а где директории, для этого существует метод
            // Path.subPath(), в него аргументами передается начало позиции с которой мы будем разделять
            // путь и конечную позицию, конечную позицию мы будем вычислять
            // с помощью другого метода Path.getNameCount() этот метод вернет количество имен в пути
            // начальная позицию представляет из себя имя директории находящейся ближе всего к корню
            // а конечная - наиболее удаленную от корня директорию
            // складываем start и end и получаем относительную ссылку на файл

            // теперь сделаем собственно переименовывание с помощью Files.move()
            // аргументами передаем откуда берем файл и куда кладем с указанием
            // имени файла. в данном случае заработает только если мы перемещаем файлы
            // из директории temp и возможны баги с именованием, но это учебный пример
            // File.separator подставляет разделитель для нашей ОС, можем и вручную его стрингом
            // передать, LocalDate.now().format(DateTimeFormatter.ISO_DATE) подставляет время ОС
            // now() думаю не нужно отдельно обьяснять format() форматирует дату в определенном
            // формате, помним, что в разных странах дата записываетс япо разному, здесь мы
            // используем представление времени. Теперь переименуем директории 72 строка
            // (весь код переименования был вынесен в отдельный метод renameFile())
            renameFile(file);
            return FileVisitResult.CONTINUE;
        }

        // здесь имплементируем метод который отрабатывает перед посещением файла, как видно
        // в Path здесь автоматичести передается dir - директория, выводим ее на экран,
        // сначала у нас выведется список с директориями, включая вложенные,
        // потом список файлов в директориях, потом список файлов корневой папки
        // далее 42 строка
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println(dir);
            return FileVisitResult.CONTINUE;
        }

        // переименование директории происходит точно так же, но в методе postVisitDirectory()
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//            renameFile(dir);
            return FileVisitResult.CONTINUE;
        }

        // в этот метод мы вынесли весь процесс переименования файлов и директорий
        private void renameFile(Path file) throws IOException {
            // при попытке переместить папку if падает в цикл, я так и не понял почему.
            // поражение, я так и не разобрался. если вернусь к лекции позже надо еще раз
            // посмотреть
            int nameCount = file.getNameCount();
            if (nameCount > 1) {
                Path start = file.subpath(0, nameCount - 1);
                Path end = file.subpath(nameCount - 1, nameCount);
                Files.move(file, Paths.get(start + "\\" + "renamed_" + end));
            }
        }
    }

    // теперь создадим класс для рекурсивного копирования файлов, то есть пройдемся
    // по всем директориям в пути и скопируем их с файлами в новую директорию
    public static class CopyFileVisitor extends SimpleFileVisitor<Path> {
        // создадим два поля, исходная папка и путь назначения
        Path source;
        Path destination;

        // и сгенерируем конструктор
        public CopyFileVisitor(Path source, Path destination) {
            this.source = source;
            this.destination = destination;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) throws IOException {
            // пробуем копировать директорию
            try {
                // этот метод занимается копированием файлов и директорий
                copy(path);
            } catch (IOException e) {
                System.out.println("Copy directory error: "+ path);
                e.printStackTrace();
                // если мы получили исключение то пропускаем поддерево
                return FileVisitResult.SKIP_SUBTREE;
            }
            return FileVisitResult.CONTINUE;
        }

        private void copy(Path path) throws IOException {
            // прежде чем начинать копировать директории или файлы нам необходимо
            // получить относительный путь делаем это методом Path.relativize();
            Path relative = source.relativize(path);
            System.out.println("Relative path: " + relative);
            // строим новый абсолютный путь передавая в него директории или файлы которые копируем
            // то есть мы создаем новый Path включающий в себя папки(файлы) которые копируем
            Path destinationPath = destination.resolve(relative);
            System.out.println("Destination path: "+destinationPath);
            // а теперь мы копируем директории передавая аргументами relative и destinationPath
            Files.copy(relative, destinationPath);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            // аналогично копируем файлы
            try {
                // этот метод занимается копированием файлов и директорий
                copy(file);
            } catch (IOException e) {
                System.out.println("Copy file error: "+ file);
                e.printStackTrace();
            }
            return FileVisitResult.CONTINUE;

        }

        // если файл отсутствует, либо мы не имеем к нему доступа, либо мы не можем по
        // какой то причине скопировать файл, выводим ошибку
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            System.out.println("Copy failed, file: " + file + "\n Reason: " + exc);
            return FileVisitResult.CONTINUE;
        }
    }
}
