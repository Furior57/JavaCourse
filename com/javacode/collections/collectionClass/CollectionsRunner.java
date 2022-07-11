package com.javacode.collections.collectionClass;

import java.util.*;

public class CollectionsRunner {
    public static void main(String[] args) {
        // помимо интерфейса Collection есть класс Collections
        // у которого есть свои методы
        // создадим ArrayList и заполним его
        List<String> colors = new ArrayList<>();
        colors.add("yellow");
        colors.add("blue");
        colors.add("green");
        colors.add("black");
        colors.add("red");
        // а теперь отсортируем элементы
        // обращаемся к классу Collections и в качестве параметра в метод sort
        // передаем список который необходимо отсортировать
        // здесь неявным образом для сортировки будет использован метод
        // compareTo()
        Collections.sort(colors);
        // создаем колоду карт используя два цикла foreach
        List<Card> deckOfCards = new ArrayList<>();
        for (Card.Face face : Card.Face.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                deckOfCards.add(new Card(suit, face));
            }
        }
        // выводим 4 колонки со списком карт
//        System.out.println("Original deck of cards");
//        for (int i = 0; i < deckOfCards.size(); i++) {
//            printOutput(deckOfCards, i);
//        }

        // если мы имеем отсортированный массив то для поиска данных в нем
        // удобно пользоваться бинарным поиском. принцип бинарного поиска
        // состоит в том что воображаемая каретка встает на индекс
        // посередине массива и сравнивает значение которое ищем с
        // элементом на который она указывает, если искомое значение больше
        // отбрасывается половина массива находящаяся слева от каретки
        // и повторяется алгоритм, аналогично если искомое значение
        // меньше, отбрасывается правая половина массива.
        // так как в данном случае у нас не массив а список возпользуемся
        // классом Collection
        // создадим карту для ее поиска
        Card card = new Card(Card.Suit.SPADES, Card.Face.Ace);
        // отсортируем список, иначе поиск не сработает
        Collections.sort(deckOfCards);
        // пользуемся бинарным поиском чтобы получить индекс искомой
        // карты. Если карта не была найдена вернется отрицательное
        // значение
        int index = Collections.binarySearch(deckOfCards, card);
        if (index >= 0) {
            System.out.println("Card was found ad position "+ index);
        }else System.out.println("Card was not found");

        // разворачиваем колоду в обратном порядке
        Collections.reverse(deckOfCards);
        // перемешаем карты
        Collections.shuffle(deckOfCards);

//        System.out.println("Shuffled deck of cards");
//        for (int i = 0; i < deckOfCards.size(); i++) {
//            printOutput(deckOfCards, i);
//        }

        // сортируем карты в обратном порядке
//        Collections.sort(deckOfCards, Collections.reverseOrder());

//        System.out.println("Sorted deck of cards");
//        for (int i = 0; i < deckOfCards.size(); i++) {
//            printOutput(deckOfCards, i);
//        }

        // сортируем карты со своей имплементацией
//        Collections.sort(deckOfCards, new CardComparator());

//        System.out.println("Handle compare deck of cards");
//        for (int i = 0; i < deckOfCards.size(); i++) {
//            printOutput(deckOfCards, i);
//        }
        // создадим новую колоду карт фиксированного размера
        // однако несмотря на то что в памяти есть место под 52 элемента
        // сам массив пуст, у него нет размера, поэтому
        // метод fill() не отработает, необходимо сначала заполнить
        // массив какими то значениями чтобы у него появился
        // фактический размер, а уже потом заполнять с помощью fill()
        // аналогичная ситуация будет если мы воспользуемся
        // методом copy(), нам так же необходимо сначала инициализировать
        // размер массива
        List<Card> cardList = new ArrayList<>(52);
        // передадим в новый массив уже созданную колоду карт, чтобы
        // у него появился размер
        List<Card> cardList1 = new ArrayList<>(deckOfCards);
        // заполняем колоду одним элементом
        Collections.fill(cardList1,card);
        // считаем количество вхождений нашей карты в списке(сколько раз
        // она там повторяется), этот метод использует для сравнения
        // карт метод equals(), так что во избежание ошибок
        // необходимо имплементировать его, это сделано генератором
        // в классе Card
        int frequency = Collections.frequency(cardList1, card);
        // ищем минимальное значение в списке
        Card min = Collections.min(cardList1);
        // ищем максимальное значение
        Card max = Collections.max(cardList1);


    }

    // этот метод был создан автоматически с помощью функции Extract method
    // чтобы им воспользоваться мы нажали комбинацию клавиш ctrl+shift+A
    // и в поиске ввели extract method он автоматически создам метод из
    // выделеного блока кода и заменил весь повторяющийся код
    // вызовом этого метода, очень удобно.
    private static void printOutput(List<Card> deckOfCards, int i) {
        System.out.printf("%-20s %s", deckOfCards.get(i),
                (i + 1) % 4 == 0 ? "\n" : " ");
    }

}
