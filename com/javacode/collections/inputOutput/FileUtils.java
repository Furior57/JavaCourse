package com.javacode.collections.inputOutput;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;

public class FileUtils {

    public void printIOFileDetails(String path) throws IOException {
        File file = new File(path);

        // Методы класса File
        // получение абсолютного пути (C:\ и т.д.)
        System.out.println("Absolute path " + file.getAbsolutePath());
        // получение относительного пути(то есть пути по отношению к текущему
        // рабочему каталогу)
        System.out.println("Relation path " + file.getPath());
        // получение количества свободного места, ответ в байтах, здесь перевел в Mb
        System.out.println("Free spase in MBytes " + file.getFreeSpace()/1000000);
        // получение родительской директории
        System.out.println("Parent directory " + file.getParent());
        // булево, проверят является ли путь абсолютным
        System.out.println("Is absolute path? " + file.isAbsolute());
        // проруряем текущую рабочую директорию
        // для этого используем системный метод getProperty()
        // агрументом передаем ключ, здесь user.dir, возвратом
        // будет текущая директория, ключей не очень много и в целом
        // ими почти не придется пользоваться, там больше техническая
        // информация, версия ос, версия JVM и так далее
        System.out.println("Current directory is: " +
                System.getProperty("user.dir"));

        // этот метод проверяет является ли обьект директорией, булево
        if (file.isDirectory()) {
            System.out.println("It is a directory, printing content: ");
            // этот метод возвращает список файлов в директории
            // если обьект является директорией, если не является вернет null
            String[] list = file.list();
            if (list != null) {
                for (String e : list) {
                    System.out.println(e);
                }
            } else System.out.println("It is a file ");
            // эта функция создает новый файл, если файл создан возвращает
            // true, если файл уже был создан возвращает false
            System.out.println("Creating a new file " + file.createNewFile());
            // функция проверяет имеем ли доступ к чтению файла, булево
            System.out.println("Can read = "+ file.canRead());
            // аналогичная проверка на запись
            System.out.println("Can write = "+ file.canWrite());
            // этот метод определяет можем ли мы исполнить файл, булево
            System.out.println("Can execute = "+file.canExecute());
            // проверяем скрытый ли файл, булево
            System.out.println("File is hidden = " + file.isHidden());
            // возвращает long означающий количество миллисекунд с 1.01.1970
            System.out.println("Last modified = "+ file.lastModified());
            // удаление файла
            System.out.println("deleting a file "+file.delete());
            // конвертация обьекта File в обьект Path, по русски из файла
            // получаем абсолютный путь до него в виде обьекта
            // это может пригодиться при использовании legasy систем со старой версией
            // java
            Path filePath = file.toPath();
            // так же мы можем инициализировать поток FileInputStream
            // передав в него обьект File. FileOutputStream мы можем
            // инициализировать таким же образом
            FileInputStream in = new FileInputStream(file);
            // так же мы можем использовать BufferedInputStream, оборачивая им
            // конструктор FileInputStream, что в целом предпочтительней почти во
            // всех ситуациях, буферизация позволяет сократить количество обращений
            // к файлу и соответственно ускорить его чтение
            BufferedInputStream buf =
                    new BufferedInputStream(new FileInputStream(file));




        }
    }

    public void printNIOFileDetails(String filename) throws IOException {
        // java NIO это усовершенствованный java IO основное различие
        // заключается в том что NIO ориентирован на использование буферов
        // а IO на использование потоков, так или иначе основные обьекты
        // с которыми работает NIO принадлежат двум классам это Files и Paths
        // и одним интерфейсом - Path
        // самый простой класс это Paths у него есть только один метод get()
        // он из переданной строки или URI получает обьект типа Path
        Path path = Paths.get(filename);
        // Path это переработанный аналог класса File, с измененным API
        // Альтернативный способ создать обьект типа Path
        Path path1 = FileSystems.getDefault().getPath(filename);
        // Третий способ
        Path path2 = Paths.get(System.getProperty("user.dir"), filename);

        // c помощью обьекта Path мы можем создать обьект файловой системы
        // и работать с ним, однако возможности довольно ограниченные, изучим потом
        FileSystem fileSystem = path.getFileSystem();

        // Методы для работы с обьектами Path

        // получаем имя файла с которым работаем
        System.out.println("Path name= "+path.getFileName());
        // преобразование обьекта в абсолютный путь
        Path absolutePath = path.toAbsolutePath();
        System.out.println("Convert to absolute path: "+ absolutePath);
        // получаем родительскую директорию
        System.out.println("Parent directory= "+absolutePath.getParent());
        // Получаем корневую директорию, чтобы ее получить надо получить абсолютный путь
        // если этого не сделать вернет null
        System.out.println("Root directory= "+absolutePath.getRoot());
        // показывает количество имен в пути, если в обьект передать просто имя файла
        // то вернет 1, если передать абсолютный путь, то вернет количество всех имен в нем
        System.out.println("Name count "+absolutePath.getNameCount());
        // как это можно использовать? мы можем воспользваоться таким методом
        System.out.println("Sub path= "+absolutePath.subpath(0,3));
        // практическое применение пока не представляю какое, наверно руками путь не писать
        // так же имеется метод toRealPath() который тоже дает нам абсолютный путь до файла
        // но есть различия с toAbsolutePath(), например он умеет генерировтаь исключения
        // а так же он может вренуть реальное имя файла с учетом регистра, даже если мы
        // при запросе его не учитывали
        Path path3 = Paths.get("../../");
        System.out.println("Real path=" + path3.toRealPath());

        // Поработаем с файлами и классом Files

        // проверяем существует ли файл
        System.out.println("File exists: "+ Files.exists(path));
        // обратная операция, однако она не эквивалентна !Files.exists(path)
        // первый метод проверяет существует ли файл и если например
        // доступ к нему запрещен мы получим false, однако и второй метод
        // вернет нам false потому что файл существует, это важно и нужно помнить
        System.out.println("File does not exists: "+ Files.notExists(path));
        // проверим права доступа к файлу
        System.out.println("Is readable: " + Files.isReadable(path));
        System.out.println("Is writable: " + Files.isWritable(path));
        // проверяем можем ли исполнить файл
        System.out.println("Is executable: " + Files.isExecutable(path));
        // проверяем являются ли два обьекта Path эквивалентными друг другу
        System.out.println("Is same file: "+Files.isSameFile(path, path2));

        // создадим директорию
        // для начала возьмем абсолютный путь и из него получим родительскую папку
        Path parentPath = absolutePath.getParent();
        // с помощью этого метода мы строим новый абсолютный путь из абсолютного
        // пути и относительного, сама папка files в этот момент не обязана существовать
        // мы всего лишь сохраняем сам путь, по сути String обернутый в класс Path
        Path filesPath = parentPath.resolve("files");
        // а вот теперь мы проверяем существует ли директория и создаем ее
        // если директория уже существует, а мы попытаемся ее создать вернется исключение
        if (Files.notExists(filesPath)) {
            Files.createDirectory(filesPath);
        }
        // теперь скопируем в эту директорию файл, есть несколько имплиментаций этого метода
        // мы воспользуемся той где мы указываем обьект с абсолютным путем до файла и
        // вторым аргументом передаем обьект с путем куда копируем файл, в нашем случае
        // мы опять используем resolve() куда передаем обьект с названием файла
        // и последним аргументом мы передаем параметры копирования в нашем случае это
        // замена файла если он уже существует, если мы хотим переместить файл
        // то воспользуемся методом Files.move(), суть в нем абсолютно такая же
        Files.copy(absolutePath, filesPath.resolve(path),StandardCopyOption.REPLACE_EXISTING);
        // удалим файл, однако сейчас мы удаляем не рекурсивно и если в директории лежит файл
        // мы получим исключение DirectoryNotEmptyException, сейчас мы удалим сам файл
        Files.delete(filesPath.resolve(path));
        // а сейчас директорию
        Files.delete(filesPath);


    }

    // работаем с директориями

    public void processDir() throws IOException {
        // инициализируем обьект Path с помощью которого будем работать с директорией
        // temp
        Path dir = Paths.get("temp");
        // создаем директорию
        // если директория уже существует мы получим исключение
        // FileAlreadyExistsException, поэтому сделаем проверку
        if (Files.notExists(dir)) {
            Files.createDirectory(dir);
        }
        // создадим временную директорию
        // первым аргументом указываем родительскую директорию, вторым
        // префикс названия папки, остальное название генерируется автоматически
        // если мы несколько раз запустим этот метод, то создадим несколько
        // временных папок, так что надо позаботиться об их удалении после работы
        Files.createTempDirectory(dir, "tmp");


        // создаем иерархию директорий
        // c помощью этого метода мы можем создат сразу несколько вложенных папок
        // однако есть и другие имплементации, не обязательно делать Paths.get()
        Files.createDirectories(Paths.get("temp/a/b/c"));

        // создаем обьект Iterable в котором лежат обьекты Path, это
        // все корневые директории Windows в нашем случае, в линуксе все несколько иначе
        Iterable<Path> rootDir = FileSystems.getDefault().getRootDirectories();
        for (Path p : rootDir) {
            System.out.println(p);
        }
        // такой записью мы задаем фильтр по которому будем делать выборку
        // ниже мы используем glob для вывода определенных типов файлов,
        // а здесь мы создали обьект фильтра и переопределили абстрактный метод accept()
        // для того чтобы выводить только сами директории, без их содержимого
        // мы можем заменить glob на этот фильтр и получим ожидаемый результат
        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return Files.isDirectory(entry);
            }
        };

        // выведем все содержимое директории, это тоже ресурс и его необходимо закрыть
        // далее аргументом в newDirectoryStream мы передаем обьект Path и так называемый
        // glob, glob это нечто типа фильтра по которому мы можем отсортировать файлы
        // здесь мы выведем только файлы с расширением txt и bin, * говорит что имя файла не важно
        // *.txt так это выглядит, если нужно вывести несколько типов то мы заключаем
        // расширение в фигурные скобки и через запятую указываем все типы расширений
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir, "*.{txt,bin}")) {
            // это итерируемый обьект и мы можем пройтись по нему и вывести содержимое
            for (Path p : paths) {
                System.out.println(p);
                // так же выведу абсолютный путь до обьекта
                System.out.println(p.toAbsolutePath());
            }

        }


    }

}
