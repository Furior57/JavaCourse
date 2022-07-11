//package com.javacode.collections.stackQueue;
//
//import com.javalesson.collections.collectionClass.Card;
//import com.javalesson.collections.collectionClass.CardComparator;
//
//import java.util.*;
//
//public class StackQueueLauncher {
//    public static void main(String[] args) {
//        // класс Stack в Java представляет собой структуру данных стек(внезапно)
//        // то есть используется принцип последний зашел-первый вышел
//        // здесь мы создали псевдокласс Passenger для того чтобы класть
//        // экземпляры этого класса в стек
//        passengerProcessing();
//
//        // интерфейс Queue переводится с английского как очередь, он отличается
//        // от Stack тем что добавляет элементы в конец, а удаляет их из начала
//        // методы: add() унаследован от Collection, element()- позволяет получить
//        // элемент не удаляя его из очереди, отличается от peek() тем что в случае
//        // пустой очереди бросает исключение, а peek вернет null,
//        // remove() и poll(), позволяют удалить элемент из очереди,
//        // первый так же бросает исключение при пустой очереди, второй вернет null
//        // offer() по своей сути тот же push() но бросает исключения если пытаемся
//        // передать элемент который имеет свойство не позволяющее ему быть добавленным
//        // либо null, либо неправильное свойство, метод возвращает булево в зависимости
//        // от того успешно или нет добавлен элемент
//
//        // создаем очередь и заполняем ее простыми числами по приоритету
//        // так как мы заполняем очередь простым типом то сортировку можно
//        // не имплементировать вручную
//        Queue<Integer> queue = new PriorityQueue<>();
//        queue.offer(2);
//        queue.offer(5);
//        queue.offer(1);
//        queue.offer(4);
//
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
//
//
//        // имплементируем PriorityQueue, суть в том что он выстраивает элементы
//        // по приоритету, от меньшего к большему, сортирует проще говоря для
//        // этого нам надо имплементировать метод compareTo(), либо
//        // указать Comparator при создании PriorityQueue,
//        // но так как мы будем сортировать свой класс Card где уже
//        // имплементирован метод compareTo(), нам просто остается
//        // заполнить очередь в цикле
//        // здесь мы вручную указали размер очереди и компаратор по которому
//        // будет идти сортировка, в нашем случае от большего к меньшему
//        Queue<Card> cardQueue = new PriorityQueue<>
//                (52, new CardComparator());
//        // заполняем нашу очередь
//        for (Card.Face face : Card.Face.values()) {
//            for (Card.Suit suit : Card.Suit.values()) {
//                cardQueue.offer(new Card(suit, face));
//            }
//        }
//
//        for (int i = 0; i < 10; i++) {
//            System.out.println(cardQueue.poll());
//        }
//        // после этого вывода в колоде останется 42 карты
//        // однако если мы воспользуемся методом toString()
//        // или пройдемся итератором по коллекции нет гарантии
//        // что вывод будет отсортирован, там карты выйдут в случайном порядке
//
//        // чтобы очистить очередь есть метод clear()
////        cardQueue.clear();
//        // здесь возвратом будет null, так как мы очистили очередь
////        System.out.println(cardQueue.poll());
//
//
//        // Так же имеется интерфейс Deque, double queue, он отличается тем
//        // что элементы можно брать как с начала списка, так и с конца
//        // у этого интерфейса две наиболее популярных имплементации
//        // это ArrayDeque и LinkedList, так же множество популярных
//        // имплементаций находится в пакете Concurrent и используются
//        // для многопоточности.
//        // Для примера используем ArrayDeque
//
//        Deque<Card> cards = new ArrayDeque<>();
//        // добавляем элементы в начало очереди
//        for (int i = 0; i < 20; i++) {
//            cards.offerFirst(cardQueue.poll());
//        }
//        // получаем и удаляем элементы с конца
//        for (int i = 0; i < 20; i++) {
//            System.out.println(cards.pollLast());
//        }
//        // проверяем что наша очередь пуста
//        System.out.println(cards.size());
//
//        // так же мы можем удалить первое или последнее вхождение
//        // элемента с помощью метода removeFirstOccurrence():
//
//        // создадим новую карту
//        Card cardForRemove = new Card(Card.Suit.SPADES, Card.Face.Four);
//        // добавим в Deque
//        cards.offerFirst(cardForRemove);
//        // проверим что она там есть
//        System.out.println(cards.size());
//        // по хорошему надо еще накидать карт, но и так код большой,
//        // просто удалим ее и проверим размер колоды
//        cards.removeFirstOccurrence(cardForRemove);
//        // проверим размер колоды
//        System.out.println(cards.size());
//        // аналогично есть метод removeLastOccurrence()
//
//
//    }
//
//    private static void passengerProcessing() {
//        Stack<Passenger> bus = new Stack<>();
//        Passenger passenger = new Passenger("Ivan","Ivanov");
//        // добавляем элементы в стек
//        bus.push(new Passenger("Alex","Popailov"));
//        bus.push(new Passenger("Andrew","Stivenson"));
//        bus.push(passenger);
//        bus.push(new Passenger("Mihail","Bulgakov"));
//
//
//        // получить элементы из стека можно двумя способами.
//        // первый это peek(), он выведет элемент не удаляя его
//        System.out.println("Last passenger is "+bus.peek());
//        // ищем позицию пасажира, внимание, здесь будет считать позицию с конца,
//        // то есть наш предпоследний пассажир будет иметь номер 2
//        System.out.println("Passenger found at position "+bus.search(passenger));
//        // второй это pop(), он получит значение и удалит его из стека
////        Passenger lastPassenger = bus.pop();
////        System.out.println(lastPassenger+" move alone. " +
////                "Last passenger is "+bus.peek());
//
//        // это условие получает последнего пассажира, удаляет из стека
//        // и уменьшает счетчик пассажиров автобуса
//        while (!bus.empty()) {
//            System.out.println(bus.pop()+" leave the bus");
//            Passenger.number -= 1;
//        }
//
//
//
//    }
//
//    private static class Passenger {
//
//        private static int number = 0;
//        private String name;
//        private String surname;
//
//        public Passenger(String name, String surname) {
//            number++;
//            this.name = name;
//            this.surname = surname;
//        }
//
//
//
//
//        public static int getNumber() {
//            return number;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getSurname() {
//            return surname;
//        }
//
//        @Override
//        public String toString() {
//            return "Passanger{" +
//                    "name='" + name + '\'' +
//                    ", surname='" + surname + '\'' +
//                    '}';
//        }
//    }
//
//
//
//}
