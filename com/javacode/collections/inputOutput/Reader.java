//package com.javacode.collections.inputOutput;
//
//import java.io.*;
//import java.nio.Buffer;
//import java.nio.ByteBuffer;
//import java.nio.channels.FileChannel;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Reader {
//    // Этот метод реализует буферное чтение из файла
//    public void readFile(String fileName) throws IOException {
//        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            int c;
//            while ((c = reader.read()) != -1) {
//                System.out.println(c);
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Error, check available file");
//        }
//    }
//    // по аналогии с сериализацией создаем метод для десериализации
//    // принимать он будет имя файла, а возвращать список с обьектами Student
//    public List<Student> readObject(String filename) {
//        // инициализируем список
//        List<Student> student = new ArrayList<>();
//        // создаем обьект класса ObjectInputStream и в параметры так же передаем
//        // поток указывая в нем аргументом имя файла через метод Paths.get()
//        try (ObjectInputStream input = new
//                ObjectInputStream(Files.newInputStream(Paths.get(filename)))) {
//            // теперь мы имеем какой то поток в котором лежат наши обьекты, но как
//            // обьяснить ide что это именно обьекты, а не символьный поток
//            // для этого воспользуемся методом readObject() и с помощью приведения
//            // типов передадим его обьекту Student, обработая при этом исключение
//            // ClassNotFoundException, не забываем что сереализация передает граф обьектов
//            // то есть у нас есть поля которые сами являются обьектами, просто напоминание
//
//            // итак у нас есть байтовый поток из которого мы хотим восстановить обьекты
//            // мы воспользовались методом readObject() и начали в цикле восстанавливать обьекты
//            // вроде бы все хорошо, но это не совсем так. этот метод не знает в какой момент
//            // закончится информация в файле и будет работать до тех пор пока не выбросит
//            // исключение EOFException - End Of File Exception, что означает конец файла
//            // у нас есть два пути, либо мы ловим это исключение блоком catch и обрабатываем,
//            // завершая исполнение десериализации, либо каким то образом вручную ловим конец
//            // файла, в целом можно пойти первым путем, но зачем приводить к лишним исключениям?
//            // мы вручную пометим конец файла, для этого в последнюю позицию мы запишем обьект
//            // Student с пустым именем, средней оценкой -1 и null заместо списка с оценками
//            // а здесь будем перехватывать его и завершать работу цикла(на самом деле то еще
//            // дело, гораздо проще присвоить обьектам id и при записи обьекта с определенным
//            // id завершать цикл, заодно так можно проверять не потерялись ли какие то обьекты
//            // при транспортировке, но буду делать как сказал преподаватель)
//            boolean keepReading = true;
//            while (keepReading) {
//                Student s = (Student) input.readObject();
//                // здесь мы сравниваем не имя студента с пустой строкой
//                // а пустую строку с именем студента, чтобы не получить NullPointerException
//                if (!"".equals(s.getName())) {
//                    student.add(s);
//                } else {
//                    keepReading = false;
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Cannot open file, program terminate");
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            System.out.println("Data is corrupted");
//            e.printStackTrace();
//        }
//        return student;
//    }
//
//    // пользуемся java NIO для чтения файла
//    // таким способом можно пользоваться для чтения маленьких файлов
//    public void readFileInFull(String fileName) throws IOException {
//        // получим обьект класса Path
//        Path path = Paths.get(fileName);
//        // считываем все строки в файле с помощью метода Files.readAllLines()
//        // так же мы можем считать все байты
//        List<String> lines = Files.readAllLines(path);
//
//        for (String l : lines) {
//            System.out.println(l);
//        }
//    }
//
//    // если нам необходимо считывать большие обьемы информации, то мы уже должны
//    // пользоваться буфферизацией, она позволяет сократить количество обращений к файлу
//    // и примерно в полтора раза быстрее чем посимвольное считывание, напишем метод
//    public void nioReadFileWithBuffered(String fileName) throws IOException {
//        // так же сначала создаем обьект Path
//        Path path = Paths.get(fileName);
//        // теперь с помощью try-with-resources создаем обьект BufferedReader и работаем с ним
//        // так же мы можем указать с какой кодировкой работать, по умолчанию обычно
//        // используется UTF-8, но нет гарантии что какой нибудь легаси код не работает на
//        // другой кодировке, для указания кодировки нам необходимо создать обьект
//        // типа Charset, с указанием имени кодировки и передать его аргументом в метод
//        // newBufferedReader(file, charset)
//        // создаем обьект Charset, обращаем внимание что это абстрактный класс, так что
//        // мы не можем создать его instance с помощью оператора new, для этого внутри класса
//        // определен метод forName() куда строкой передаем название кодировки
//        Charset charset = Charset.forName("UTF-8");
//        // так же если кодировка является стандартной мы можем воспользоваться классом
//        // StandardCharset и обратиться к одному из его полей поторые реализовывают
//        // несколько стандартных кодировок и при их вывове создают инстанс класса
//        // Charset необходимой кодировки
//        Charset charset1 = StandardCharsets.UTF_8;
//        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
//            String s;
//            // пока строка не пустая выводим ее на экран
//            while ((s = reader.readLine()) != null) {
//                System.out.println(s);
//            }
//        }
//    }
//
//    // NIO это новая обработка ввода-вывода информации, изначально в java
//    // для этой цели использовался IO, их различие не только в том что
//    // NIO ориентирован на буферную обработку, но и в том что NIO при
//    // обработке канала с данными не блокирует его, вторая расшифровка
//    // NIO - non-blocking IO, однако в ходе работы скорее всего придется столкнуться с
//    // легаси кодом в котором используется IO и для обратной совместимости
//    // при чтении существует класс InputStream, это абстрактный класс и мы не можем
//    // создать его инстанс с помощью оператора new, а пользуемся методом класса Files
//    public void nioReadWithStream(String filename) throws IOException {
//        Path path = Paths.get(filename);
//        // пользуемся try-with-resources
//        try (InputStream in = Files.newInputStream(path)) {
//            // оборачиваем InputStream буфером, для этого при создании инстанса
//            // BufferedReader передаем в качестве аргумента инстанс класса
//            // InputStreamReader в который аргументом передаем наш InputStream
//            // InputStreamReader декодирует байтовый поток и преобразует
//            // в символьный для дальнейшей работы с NIO
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            String s;
//            while ((s = reader.readLine()) != null) {
//                System.out.println(s);
//            }
//        }
//
//    }
//
//    // Теперь немного о каналах. канал - это некий шлюз который позволяет нам
//    // получить доступ к устройству ввода-вывода. при чтении данных мы передаем
//    // данные в канал из устройства вывода(файл, сокет и т.д.), а из канала
//    // передаем их в буфер для дальнейшей обработки. при записи данных
//    // мы помещаем данные в буфер, откуда передаем их в канал, а из канала записываем
//    // на устройство вывода. по своей сути канал и поток очень похожи, но канал
//    // позволяет асинхронно работать с информацией в нем, одновременно и передавая ее
//    // и записывая, так же используя поток мы не можем перемещаться по нему
//    // а в канале есть понятия указателя и маркера, что позволяет получить доступ к
//    // определенному блоку данных. еще есть понятие селекторов, его рассмотрим позже.
//
//    // Для начала поработаем с FileChannel, то есть с файлами
//    public void nioReadWithChannel(String filename) throws IOException {
//        // есть два способа работы с каналом. первый это работа с потоками
//        // создаем поток из файла
////        FileInputStream file = new FileInputStream(filename);
//        // получаем обьект канала из этого потока с которым планируем в дальнейшем работать
////        FileChannel channel = file.getChannel();
//        // этот метод подходит для работы с легаси кодом, однако при этом мы теряем
//        // одно из преимуществ канала - асинхронность, здесь наш канал работает только
//        // на чтение, чтобы работать на вывод нам нужно создать новый поток вывода
//        // и на его основе новый канал
//        // для создания двустороннего канала воспользуемся классом RandomAccessFile
//        // при создании инстанса мы передаем обьект File или String с именем файла,
//        // String с описанием прав доступа к файлу, в нашем случае чтение и запись
//        // а так же можем передать булево OpenAndDelete, как можно догадаться
//        // это маркер того что файл нужно удалить после использования
//        RandomAccessFile file = new RandomAccessFile(filename,"rw");
//        // получаем канал, уже двусторонний
//        FileChannel channel = file.getChannel();
//        // теперь нам нужно создать сам буффер куда мы поместим данные
//        // это абстрактный класс, поэтому для его создания нужно воспользоваться
//        // методом allocate(), куда мы передадим размер буффера, это количество байт,
//        // под каждый примитивный тип данных существует свой класс буфера
//        ByteBuffer buffer = ByteBuffer.allocate(100);
//        // у нас есть аллоцированный буффер теперь нам нужно его обработать
//        // в нашем случае наполнить данными и вывести их на экран, для этого
//        // воспользуемся двумя циклами
//        // сначала заполним буфер данными и присвоим отдельной переменной
//        int bytesNumber = channel.read(buffer);
//        // теперь запускаем первый цикл, пока переменная больше нуля
//        while (bytesNumber > 0) {
//            // перемещаем каретку на начало, здесь дело в том, что когда мы получаем
//            // данные из буфера, мы переводим каретку(position) вперед на количество
//            // байт которые мы записали, при чтении данных каретка автоматически не
//            // переходит стартовую позицию буфера а продолжает считывать до limit поэтому
//            // мы переведем каретку руками методом flip() в начале каждой итерации
//            buffer.flip();
//            // запускаем второй цикл, метод который мы в него передаем возвращает true
//            // пока в буффере есть хотя бы один символ
//            while (buffer.hasRemaining()) {
//                // выводим информацию из буфера на экран, не забыв сделать каст приведя
//                // ее к символьному типу иначе получим длинную строку цифр
//                // помним что println() переводит строку, нам в данный момент это
//                // не нужно, поэтому просто используем print() символ переноса строки
//                // сам считается из буфера, информация считывается посимвольно,
//                // каждый байт содержит только один символ(по умолчанию, кодировки разные есть)
//                System.out.print((char) buffer.get());
//            }
//            // по окончанию вывода данных из буфера очищаем его
//            buffer.clear();
//            // и получаем новую порцию данных переходя к следующей итерации
//            bytesNumber = channel.read(buffer);
//        }
//        // канал как и файл необходимо закрывать после работы с ним высвобождая ресурсы
//        channel.close();
//    }
//
//
//
//
//
//}
