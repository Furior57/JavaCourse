//package com.javacode.collections.inputOutput;
//
//import com.javalesson.collections.TreeMap.AverageStudentGrade;
//import com.javalesson.collections.TreeMap.SubjectGrade;
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
//import java.nio.file.StandardOpenOption;
//import java.util.*;
//
//import static java.nio.file.StandardOpenOption.*;
//
//public class Writer {
//
//    public void writeWithFormatter() throws FileNotFoundException {
//        Formatter fotmatter = new Formatter("BankAccounts");
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("==================================\n");
//        int counter = 0;
//        while (counter < 3) {
//            try {
//                fotmatter.format("%d, %s, %s, %.2f, %n", scanner.nextInt(), scanner.next(),
//                        scanner.next(), scanner.nextFloat());
//                counter++;
//            } catch (InputMismatchException e) {
//                System.out.println("Please, enter correct data");
//                scanner.nextLine();
//            }
//        }
//        fotmatter.close();
//    }
//
//    public void bufferedWrite(String fileName, SortedMap<AverageStudentGrade, Set<SubjectGrade>> grades) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            for (AverageStudentGrade gradeKey : grades.keySet()) {
//                writer.write("===================================\n");
//                writer.write("Student: " + gradeKey.getName() +
//                        " Average grades: " + gradeKey.getAverageGrade() + "\n");
//                for (SubjectGrade grade : grades.get(gradeKey)) {
//                    writer.write("Subject: " + grade.getSubject() +
//                            " Grades: " + grade.getGrade() + "\n");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    // вспомогательный метод для сериализации обьектов
//
//    // для того чтобы иметь возможность отправить обьект по сети
//    // необходимо его сериализировать
//    // сериализация это механизм преобразования обьектов java в
//    // массив байтов которые мы потом либо сохраняем на диск, либо
//    // передаем по сети, а затем с помощью десериализации мы восстанавливаем
//    // обьект, разьяснения в классе Student
//    // итак мы пометили наш обьект и его поля как сериализуемый
//    // теперь нам необходимо конвертировать его в байты и записать в файл
//    public void writeObject(List<Student> students, String filename) throws IOException {
//        // создаем обьект класса ObjectOutputStream и в его конструкторе
//        // параметром передаем еще один поток, делается это потому
//        // что ObjectOutputStream умеет сериализовать обьекты, но не знает
//        // что в дальнейшем с сериализованным обьектом делать, записывать ли его в файл
//        // либо отправить по сети, в данный момент мы будем записывать все в файл
//        // для того чтобы передать файл мы воспользуемся Files.newOutputStream()
//        // который открывает(или создает) файл возвращая поток вывода который
//        // уже используется для записи байтов в этот файл
//        try (ObjectOutputStream out =
//                     new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))) {
//            // а здесь мы уже с помощью метода writeObject() делаем сериализацию
//            // и записываем данные
//            for (Student student : students) {
//                out.writeObject(student);
//            }
//            // для чего сделана эта запись обьяняется в классе Reader в методе
//            // readObject()
//            out.writeObject(new Student("", -1, null));
//        } catch (IOException e) {
//            System.out.println("File cannot be opened, program terminates");
//            e.printStackTrace();
//        }
//
//
//    }
//
//    // определим метод для записи с помощью буфера
//    public void nioWriteWithBuffer(String filename) throws IOException {
//        Path path = Paths.get(filename);
//        Charset charset = Charset.forName("UTF-8");
//        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
//            // для записи используем метод write, передадим в него имя файла, которое и запишется,
//            // отступ с которого нужно начать записывать(в символах) и размер
//            // буфера, мы его укажем как размер обьекта filename
//            writer.write(filename, 0, filename.length());
//        }
//    }
//
//    // по аналогии с классом Reader напишем функцию которая будет принимать
//    // байтовый поток и преобразовывать в символьный, а затем записывать в файл
//    public void nioWriteWithStream(String filename) throws IOException {
//        Path path = Paths.get(filename);
//        String string = "Это тестовая строка для записи, Хеллоу ворлд!";
//        // преобразовываем нашу строку в байты для последующей обработки OutputStream
//        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
//        // OutputStream так же абстрактный класс и для создания его экземпляра
//        // пользуемся методом Files.newOutputStream(), так же мы передадим туда
//        // параметром дополнительные опции, для этого воспользуемся перечислением
//        // StandardOpenOption, в нашем случае мы указали что создаем новый файл
//        // если он не существует и что мы добавляем данные в конец файла
//        // на тот случай если файл все таки существует, по умолчанию
//        // если не указать эти опции будет создаваться новый файл и данные
//        // всегда записываются с начала файла. И еще один момент, можно заметить
//        // что APPEND написан просто так, без указания названия перечисления
//        // это сделано с помощью Static Import, эта переменная была импортирована
//        // перед созданием класса и теперь мы можем просто писать ее название
//        try (OutputStream stream = Files.newOutputStream(path, StandardOpenOption.CREATE,
//                APPEND)) {
//            // используем метод для записи куда передаем нашу байтовую строку,
//            // стартовую позицию записи и конечную позицию, в нашем случае размер строки
//            stream.write(bytes, 0, bytes.length);
//        }
//    }
//
//    // записываем данные в файл с помощью канала
//    public void nioWriteWithChannel(String filename) throws IOException {
//        // эту строку мы будем записывать в файл
//        String str = "О сколько нам открытий чудных готовит просвещенья путь!";
//        // напоминание: создаваемый канал будет работать в том режиме, в котором
//        // работает поток который в него передают FileOutputStream будет работать
//        // только в режиме записи, делать чтение из этого канала будет невозможно
//        // поэтому так же создадим обьект RandomAccessFile который работает
//        // в обоих режимах и чтения и записи
//        RandomAccessFile file = new RandomAccessFile(filename, "rw");
//        FileChannel channel = file.getChannel();
//        // создаем буфер, мы не будем сами указывать размер буфера, а сделаем
//        // его равным размеру байтового массива котроый мы получили из строки методом
//        // getBytes()
////        ByteBuffer buffer = ByteBuffer.allocate(str.getBytes().length);
//        // но это не самый оптимальный способ, оптимальным будет обернуть массив
//        // в метод wrap(), этот метод создаст на основе байтового массива новый буфер
//        // и при изменении этого массива буфер тоже изменится, это справедливо и в
//        // обратную сторону, при изменении буфера изменится массив котроый мы передали
//        // так же метод wrap() сам поставит курсор на 0 и задаст capacity как размер массива
//        // поэтому метод flip() нам здесь не нужен, при использовании этого метода
//        // limit устанавливается на текущую позицию курсора, а сам курсор
//        // сбрасывается на 0, если при чтении наша каретка дошла до конца и flip()
//        // отработал правильно, курсор изначально стоял на 0 и flip() просто туда же
//        // установит limit и записи не произойдет вообще, однако если мы уже записали
//        // данные и снова хотим воспользоваться буфером, тогда после записи нужно использовать
//        // flip()
//        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
//        // записываем данные из буфера и закрываем канал
//        channel.write(buffer);
//        channel.close();
//
//        // дополнительные методы буфера
//
//        // этот метод используется если мы уже прочитали часть данных с буфера
//        // он удаляет те данные которые уже были прочитанны
////        buffer.compact();
//        // устанавливаем маркер на позицию
////        buffer.mark();
//        // сбрасываем курсор на позицию помеченую ранее
////        buffer.reset();
//
//    }
//
//    // RandomAccessFile
//
//    // работать с каналами и потоками мы можем только в одном режиме, либо
//    // чтение, либо запись. так же данные которые мы получили в канал будут считываться
//    // или записываться от начала и до конца, работать с определенным сегментом данных мы
//    // не можем, исправить эту неудобность призван класс RandomAccessFile
//
//    // RandomAccessFile как следует из названия позволяет нам получать произвольный доступ
//    // к файлу(в нашем случае к каналу), перемещаться по нему к определенной позиции и
//    // сегментированно модифицировать данные в канале, то есть изменяя, удаляя
//    // или заменяя какой то определенный сегмент в файле(канале).
//    // Так же с помощью RandomAccessFile мы можем работать с каналом сразу и в режиме чтения
//    // и в режиме записи данных
//    // создадим метод
//
//    public void writeWithRandomAccess(String fileName) throws IOException {
//        // вообще мы можем работать и на чтение и на запись, но метод создадим в
//        // классе для записи, чтобы не множить сущности
//        // создадим два байтовых буфера. в одном мы будем содержать строку которую будем
//        // модифицировать, а во второй мы положим часть строки из первого буфера, которую будем
//        // модифицировать и возвращать в первый буфер.
//
//        // создадим буффер со строкой который в дальнейшем будем записывать в файл
//        // вспоминаем что это абстрактный класс и для создания экземпляра неоходим метод
//        // здесь мы используем wrap() (помним что размер буфера необходимо задавать,
//        // а мы передаем сразу строку и не хотим инициализировать лишнюю переменную)
//        // в который передадим байтовый массив созданный из String методом getBytes()
//        ByteBuffer mark = ByteBuffer.wrap(" MARKED AREA ".getBytes());
//        // теперь создаем второй буффер, здесь нам wrap() уже особо не нужен, так что
//        // создадим его с помощью метода allocate() и зададим сами его размер
//        ByteBuffer buffer = ByteBuffer.allocate(10);
//        // создадим обьект Path, здесь он нам нужен для создания FileChannel из файла
//        Path path = Paths.get(fileName);
//        // а теперь создадим сам канал
//        // для этого воспользуемся методом open() класса FileChannel, аргументами
//        // он принимает обьект Path и опции открытия, мы воспользуемся перечислением
//        // StandardOpenOption, которое статически импортируем в наш класс, для
//        // лаконичности написания кода и используем READ и WRITE чтомы иметь возможность
//        // читать файл и записывать в него, так же канал создаем с помощью try-with-resources
//        // ибо помним что канал после использования необходимо закрыть
//        try (FileChannel openFile = FileChannel.open(path, READ, WRITE)) {
//            // считаем первые 10 байт из буфера и поместим их в buffer для обработки
//            // читаем данные либо до тех пор пока не заполнился буфер, либо пока не кончились
//            // данные в буфере из которого читаем
//            // создаем переменную в которую передаем считанный байт и инициализируем ее
//            int byteNum = 0;
//            // пока буфер не пустой, или byteNum не равно -1, что означает конец данных
//            while (buffer.hasRemaining() && byteNum != -1) {
//               byteNum = openFile.read(buffer);
//            }
//            // теперь модифицируем наш канал буфером mark который создали ранее
//            // помним что каретка в канале сейчас находится в конце файла, если мы ходим
//            // добавить информацию в конец файла то ничег оне делаем ,но мы хотим записать
//            // буфер в начало канала, так что переместим каретку методом position()
//            openFile.position(0);
//            openFile.write(mark);
//            // у буфера как и у канала есть своя каретка и она так же сейчас находится
//            // в конце буфера, установим ее в начало методом rewind()
//            mark.rewind();
//            openFile.position(openFile.size() / 2);
//            openFile.write(mark);
//            mark.rewind();
//            openFile.position(openFile.size() - 1);
//            openFile.write(mark);
//            mark.rewind();
//            buffer.rewind();
//            openFile.write(buffer);
//
//            // в конце отметим что данные у нас не "вклиниваются" в текст буффера
//            // а затирают его собой на том месте куда мы их записываем
//        }
//
//    }
//
//
//}
