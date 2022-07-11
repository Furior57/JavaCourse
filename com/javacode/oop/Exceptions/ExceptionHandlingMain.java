package com.javacode.oop.Exceptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


// перехват исключений и их обработка
public class ExceptionHandlingMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // эта переменная отвечает за то продолжать ли выполнение программы
        boolean continueLoop = true;
        PrintWriter writer = null;
        // чтобы подавить поглощение исключения про которое написано ниже
        // обернем наш метод в блок try-catch и будем перехватывать
        //NullPointerException(код был переписан под перехват ошибки массива)
        // ниже старый код, закомментирован
//        try {
//            doEverithing(scan, continueLoop, writer);
//        } catch (InvalidInputParamException e) {
//            System.out.println("NPE");
//            e.printStackTrace();
//            System.out.println("-----------------------------");
//            // данный метод возвращает массив с подавлеными исключениями
//            // теперь мы сможем увидеть все исключения которые программа
//            // выдала в процессе работы
//            Throwable[] suppressed = e.getSuppressed();
//            System.out.println(suppressed[0]);
        try {
            doEverything(scan, continueLoop);
        } catch (InvalidInputParamException e) {
            System.out.println("InvalidInputParamException");
            e.printStackTrace();
        }
    }
    // с помощью комбинации клавиш ctrl+alt+M мы можем вынести
    // блок кода в отдельный метод
    private static void doEverything(Scanner scan, boolean continueLoop) {
        // запускаем программу в цикле do-while, она отработает как минимум
        // один цикл
        do {
            // ключевое слово try перехватывает ошибку
            // весь наш учебный пример построен на двух ошибках, одна из которых
            // связана с закрытием открытого файла в блоке finally
            // если там возникает ошибка, то она сьедает возможные выбросы
            // исключений из основного кода в блоке try-catch
            // решение довольно громоздкое ,но есть горазодо более простой способ
            // следить закрытием ресурсов, try-with-resources, выглядит это так
            // после ключевого слова try мы в скобках указываем какой обьект
            // мы создаем и используем, после выполнения всех операций
            // этот обьект будет закрыт, а если в процессе выполнения
            // мы получили какие то исключения, то они не будут
            // поглощенны, а тоже будут указанны.
            // ниже блок для проверки закрытия был закомментирован
            try(PrintWriter writer = new PrintWriter(new
                    FileWriter("out.txt"))) {
                System.out.println("Please enter numerator");
                int numerator = scan.nextInt();
                System.out.println("Please enter denominator");
                int denominator = scan.nextInt();
                // создаем массив состоящий из одного элемента
                // это делается для демонстрации поглощения исключений
                // все что описано ниже про массив мы потом исправим
                // о чем будет отдельный блок коментариев
                int[] intArray = new int[1];
                // здесь мы пытаемся обратиться ко второму элементу массива
                // в результате чего получим unchecked exception
                // ArrayIndexOutOfBoundsException, такое исключение скомпилируется
                // но при выполнении программы должно выдать ошибку, о чем
                // компилятор нас честно предупреждает
                int i = intArray[1];
//                writer = new PrintWriter(new FileWriter("out.txt"));
                writer.println("Result " + divide(numerator, denominator));
                // если программа отработала штатно меняем значение
                // булевой переменной на ложь, далее програма не будет выполняться
                continueLoop = false;
                // ключевое слово catch обрабатывает ошибку, переменная "e"
                // содержит обьект ошибки, а перед ней тип обьекта,
                // за один раз обрабатывается только один тип ошибки, но
                // блоков catch может быть сколько угодно, однако можно в одном
                // блоке обрабатывать несколько ошибок, блок с обработкой одной
                // ошибки закомментирован
//            } catch (ArithmeticException e) {
//                System.out.println("Exception: " + e);
//                scan.nextLine();
//                System.out.println("Only none-zero parameters allowed");
//

                // здесь с помощью ключевого слова throw мы в ручную
                // генерируем исключение
                if (continueLoop) {
                    throw new RuntimeException("Runtime exception");
                }
                // здесь пример обработки нескольких ошибок
            } catch (InputMismatchException | ArithmeticException e) {
                System.out.println("Exception " + e);
                scan.nextLine();
                System.out.println("Only integer non-zero parameters allowed");
            } catch (IOException e) {
                System.out.println("Unable to open file");
                // выводим описание ошибки
                e.printStackTrace();
                // здесь мы пользуемся пользовательским классом исключения
                // при возникновении исключения(в данном случае любого)
                // мы возбуждаем исключение которое описали сами
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidInputParamException("Index out of bound "+ e);
            }
            // этот блок выполняется всегда, даже если возникла ошибка
            // обычно в нем завершают какие либо процессы которые потребляют память
            // например закрываем открытый текстовый файл
            finally {
                System.out.println("Finally-block called");
                // если мы уберем здесь проверку на null то ошибки с массивом
                // мы не увидим, потому что блок finally выдаст ошибку
                // и завершит работу программы. для теста writer.close()
                // вынесен за условие
//                if (writer != null) {
//                    writer.close();
//                }
                System.out.println("try-catch block finished");
            }
        } while (continueLoop);
    }

    // ключевое слово throws предупреждает что данный метод
    // может выдать исключения которые мы указываем после throws
    // это нужно для разработчиков, данное слово говорит что этот метод
    // нужно обернуть в блок try-catch и обработать ошибки которые тут указаны
    // однако исключения делятся на checked и unchecked, вторые обрабатывать
    // не обязательно, здесь оба исключения unchecked и можно их проигнорировать
    private static int divide(int num, int denum) throws ArithmeticException,
            NullPointerException {
        return num / denum;
    }
    // этот метод создает текстовый файл и записывает в него то что передали
    // аргументом
    private static void saveToFile(int res) throws IOException {


    }


}
