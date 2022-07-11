package com.javacode.collections.inputOutput;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.Set;

public class FileAttributor {
    // работаем с атрибутам файлов
    public static void main(String[] args) throws IOException {
        // как всегда сначала создаем обьект Path с которым будем работать
        Path path = Paths.get("temp");
        // Поработаем с атрибутами с помощью класса Files
        // посмотрим размер папки
        System.out.println("Size " + Files.size(path));
        // посмотрим последнюю дату изменения файла(папки)
        System.out.println("Date modified " + Files.getLastModifiedTime(path));
        // проверяем можно ли делать запись
        System.out.println("Is writable? " + Files.isWritable(path));

        // немного теории, если разные наборы атрибутов с которыми мы можем работать
        // BasicFileAttributeView - базовый набор который поддерживается всеми ОС
        // DosFileAttributeView - поддерживается Windows
        // PosixFileAttributeView - поддерживается для Unix-систем
        // AclFileAttributeView - Access control list позволяет управлять правами
        // доступа файлов и папок в Windows
        // UserDefineFileAttributeView - настраиваемые атрибуты, имеет структуру
        // ключ-значение, ключ имя атрибута, значение хранится в виде двоичных
        // данных, может быть любого типа
        // FileOwnerAttributeView - атрибуты владельца файла, имеет две функции
        // узнать владельца, изменить владельца

        // для работы с этими наборами создадим метод, но сначала создадим
        // обьект абстрактного класса FileStore, этот класс являет собой
        // репрезентацию файлового хранилища, логического диска, например,
        // обьявляем с помощью метода класса Files
        FileStore fs = Files.getFileStore(path);
        // получаем тип файловой системы
        System.out.println("File system type " + fs.type());
        // получаем размер хранилища
        System.out.println(" Total space " + fs.getTotalSpace());
        // интересный шорткат, если мы зажмем альт и поведем курсор
        // вниз захватывая несколько строк, то все что мы будем писать после этого
        // применится сразу к нескольким строкам
        // здесь мы создали метод который проверяет поддерживает ли файловая система
        // заданый набор артибутов, возврат булево
        // .class требует отдельного внимания, это расширение файла в котором
        // содержится вся информация о классе, здесь мы получаем обьект с этой
        // информацией и передаем в наш метод который уже его обработает
        validateView(fs, BasicFileAttributeView.class);
        validateView(fs, DosFileAttributeView.class);
        validateView(fs, PosixFileAttributeView.class);
        validateView(fs, AclFileAttributeView.class);
        validateView(fs, UserDefinedFileAttributeView.class);
        validateView(fs, FileOwnerAttributeView.class);
        // здесь мы получаем атрибуты из набора DOS и передаем их в переменную
        // мы можем вывести их сразу, но сейчас нам интересна дата создания файла
        // ее мы получаем методом creationTime() котроый вызывает набор атрибутов
        DosFileAttributes dosFileAttributes = Files.readAttributes(path,
                DosFileAttributes.class);
        System.out.println("Creation time "+ dosFileAttributes.creationTime());
        // здесь описан пример задания прав доступа, в нашей ОС это недоступно
        // используем метод PosixFilePermissions.fromString(), для задания
        // прав доступа из строки, здесь указано что владелец файла может
        // читать его, записывать и исполнять, а 6 дефисов пропускает остальных
        // пользователей, дальше методом PosixFilePermissions.asFileAttribute()
        // мы создаем обьект с заданными правами и передаем его при создании
        // файла ограничивая права, здесь не работает, но разобраться нужно
//        Set<PosixFilePermission> posixFilePermissions =
//                PosixFilePermissions.fromString("rwx------");
//        FileAttribute<Set<PosixFilePermission>> setFileAttribute =
//                PosixFilePermissions.asFileAttribute(posixFilePermissions);
//        Files.createFile(Paths.get("file1.txt"), setFileAttribute);
        // отдельной лекции стоит ACL-access control list, это права доступа
        // мы можем задавать группу прав для определенного пользователя и


    }
    // с дженериками мы пока не знакомы, просто повторим все что было написано
    // в лекции, передаем в метод FileStore и обьект с информацией о классе
    // набора атрибутов который будем проверять, поддерживается ли он нашей файловой
    // системой или нет
    private static void validateView(FileStore fs, Class<? extends FileAttributeView>
            viewClass) {
        boolean supports = fs.supportsFileAttributeView(viewClass);
        // выводим информацию поддерживается ли набор файловой системой
        // .getSimpleName() выводит простое имя класса без него вывод будет:
        // java.nio.file.attribute.BasicFileAttributeView и по аналогии для всех наборов
        System.out.println("Supports "+viewClass.getSimpleName()+" - "+ supports);
    }
}
