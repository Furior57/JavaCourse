package com.javacode.collections.map;

import java.util.*;

public class MapLauncher {
    public static void main(String[] args) {
        // по своей сути имплементация интерфейса Map это ассоциативный массив
        // то есть это массив в котором хранятся пары ключ-значение и поиск значения
        // можно выполнять не только по индексу но и по ключу
        // в java Map по умолчанию реализован на основе хеш таблиц, что дает преимущество
        // при поиске значения, скорость будет O(1), но при этом вставка значения
        // в массив может проходить довольно долго за O(N) времени, так же долго
        // могут проходить операции сравнения значений, такую структуру данных
        // требуется периодически перехешировать
        // но в java так же есть имплементация TreeMap, время поиска элемента O(logN),
        // зато и операции вставки и сравнения проходят за такое же время

        // создадим Map коллекцию, как и Set это параметризированная коллекция
        // параметром передается пара key-value, как и в Set параметр не может
        // быть примитивным типом, но это не значит что мы не можем хранить
        // примитивные типы в Map, в java есть autoboxing который позволяет
        // нам это сделать(напомню это значит что java автоматически упакует
        // примитивное значение в класс обертку, в нашем случае Integer)
        Map<String, Integer> numbers = new HashMap<>();
        // добавляем элемент, важно помнить что ключи всегда должны быть уникальными
        numbers.put("One", 1);
        numbers.put("Two", 2);
        numbers.put("Tree", 3);

        // получаем доступ к элементу по ключу
        numbers.get("Two");


        // немного практики, напишем программу которая будет считывать слова с консоли
        // и подсчитывать сколько раз это слово встречается в HashMap

        Scanner scan = new Scanner(System.in);

        Map<String, Integer> wordMap = new HashMap<>();
        System.out.println("Please enter some text");
        String string = scan.nextLine();
        // разделим введеный текст на отдельные слова используя разделителем пробел
        String[] tokens = string.split(" ");
        for (String token : tokens) {
            // сохраняем отдельные слова приводя их к нижнему регистру
            String word = token.toLowerCase(Locale.ROOT);
            // создадим переменную в которую поместим наше слово как ключ
            Integer count = wordMap.get(word);
            // по этого ключа еще нет, создадим его и зададим значение 1
            if (count == null) {
                wordMap.put(word, 1);
                // если есть увеличим значение на 1
            } else {
                wordMap.put(word, count + 1);
            }
        }
        // что если мы хотим упорядочивать наши слова по значению, допустим от большего
        // к меньшему, для этого нам понадобится создать отдельный класс
        // назовем его WordWrapper

        // для того чтобы воспользоваться нашим методом printMap() нам нужно
        // сначала обернуть наши слова классом WordWrapper и сохранить их в
        // TreeMap, для этого воспользуемся методом entrySet(), он возвращает
        // множество пар ключ-значение которые мы уже запишем в TreeMap

        NavigableSet<WordWrapper> wordWrappers = convertToSet(wordMap);
        printSet(wordWrappers);

        // создадим метод для вывода нашей Map на экран
//        printMap(wordMap);

//        printSet(wordWrappers);


    }
    // метод для вывода на экран нашего сета WordWrapper-ов
    private static void printSet(NavigableSet<WordWrapper> wordWrappers) {
        for (WordWrapper e : wordWrappers) {
            System.out.println(e);
        }
    }

    private static NavigableSet<WordWrapper> convertToSet(Map<String, Integer> wordMap) {
        // этот метод удаляет значение по ключу
        wordMap.remove("one");
        // другая его имплементация, здесь элемент будет удален только в том
        // случае если и ключ и его значение совпадают
        wordMap.remove("two", 4);
        // этот метод заменяет значение ключа, сначала пишем старое значение,
        // потом новое
        wordMap.replace("tree", 2, 4);
        // можно просто поменять значение
        wordMap.replace("tree", 2);


        // создадим TreeSet который будет содержать наши обьекты
        // класса WordWrapper
        NavigableSet<WordWrapper> wordSet = new TreeSet<>();

        // Map.Entry<> это параметризированный класс обьектами которого
        // записи Map, поэтому именно его мы указываем типом элемента
        // а дальше итерируемся по множеству пар ключ-значение полученый
        // с помощью entrySet()
        for (Map.Entry<String, Integer> e : wordMap.entrySet()) {
            // записываем в наше дерево обьекты передавая параметрами
            // ключи и значения
            wordSet.add(new WordWrapper(e.getKey(), e.getValue()));
        }// если мы хотим чтобы наша коллекция была немодифицируемая, то можем
        // возпользоваться этим методом
        Collections.unmodifiableNavigableSet(wordSet);
        // обратная ситуация, нам необходимо чтобы коллекцию можно было модифицировать
        // при возврате обернем ее в TreeSet
        return new TreeSet<>(wordSet) ;
    }

    private static void printMap(Map<String, Integer> wordMap) {
        // с помощью метода keySet() мы создали множество в которое поместили наши ключи
        // помним что в HashMap данные хранятся не в том порядке в котором их добавили
        // чтобы упорядочить ключи передадим наш wordMap в TreeMap
        Map<String, Integer> treeMapWord = new TreeMap<>(wordMap);
        Set<String> keys = treeMapWord.keySet();
        for (String k : keys) {
            System.out.printf("%-10s %-10s\n", k, treeMapWord.get(k));
        }
    }


}
