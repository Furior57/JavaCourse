package com.javacode.oop.oop;

public class Dog {

    // данное поле является статическим, оно является аттрибутом поля класса,
    // а не какого то обьекта, ниже реализован метод использующий его
    private static int dogsCount;


    // это поля класса, или его свойства, они пишутся вне методов класса

    public Size size = Size.UNDEFINED;  // здесь мы указывааем типом тот класс перечисления
    // который создали отдельно и устанавливаем значение по умолчанию, на тот случай,
    // если пользователь забудет указать размер собаки, иначе мы получим ошибку
    // nullPointerException

    private static final int PAWS = 4; // данное поле мы сделали
    // константным с помощью ключевого слова final, это значение
    // ни при каких обстоятельствах не будет изменено
    public static final int TAIL = 1; // то же самое с хвостом
    // согласно конвенции имена констант пишутся большими буквами

    public String name;     // модификатор public означает что мы можем
    private String breed;    // получить доступ к полям извне класса

    // если мы хотим чтобы изменять их извне было нельзя
    // необходимо выставить модификатор private

    // так же мы можем вообще не писать приватный метод или свойство или публичный
    // при этом ему по умолчанию будет дан тип package private
    // это означает что доступ к нему будет только из пакета где он находится
    // в нашем случае com.javalesson.oop

    // так же есть модификатор доступа protected
    // он используется наследными классами для доступа к полю или методу
    // при этом там так же указывается модификатор protected

    // инициализируем конструктор класса
    public Dog() {
        dogsCount++;
    }

    // Однако иногда нам все равно необходимо узнавать какие значения
    // у полей и при желании менять, для этого есть методы get() и set()
    // get получает значение поля set меняет его на желаемое
    // с помощью alt+insert мы можем быстро создавать их для выбранных полей

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    protected void setPaws(int paws) {
//        this.paws = paws;
//    }

//    public void setTail(int tail) {
//        this.tail = tail;
//    }

    public int getTail() {
        return TAIL;
    }


    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void bark() {    // создаем метод используя перечисления
        switch (size) {
            case BIG: case VERY_BIG:
                System.out.println("WOF-WOF");
                break;
            case SMALL:
            case VERY_SMALL:
                System.out.println("Taif-Tiaf");
                break;
            case AVERAGE:
                System.out.println("Raf_raf");
                break;
            default:    // значение по умолчанию, если ни один кейс не подходит
                System.out.println("Size undefined");
        }
    }

    public void bite() {
        if (dogsCount >= 3) {
            System.out.println("Dogs are biting you");
        } else {
            bark();
        }
    }


    // по аналогии с полем этот метод является методом класса а не какого то
    // конкретного обьекта, то есть обращаемся мы к нему Dog.getDogsCount
    public static int getDogsCount() {
        return dogsCount;
    }

}
