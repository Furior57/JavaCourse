package com.javacode.collections.map;

import java.util.Objects;

// имплементируем интерфейс Comparable параметром передаем наш класс
public class WordWrapper implements Comparable<WordWrapper> {
    // поля класса будут final, чтобы нечаяно их не изменить,
    // в этом случае мы потеряем наш элемент
    private final String word;
    private final Integer count;

    public WordWrapper(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "word='" + word + '\'' +
                ", count=" + count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordWrapper that = (WordWrapper) o;
        return Objects.equals(word, that.word) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, count);
    }

    // имплементируем compareTo(), чтобы вернуть значения от большего к меньшему
    // поменяем знак у возврата сравнения, помним если вызывающий элемент
    // меньше вызываемого, то возврат -1, а в нашем случае просто 1
    // но вообще так делать не стоит, в документации java четко прописано что
    // должен возвращать метод compareTo() поэтому если нам нужно вывести элементы
    // в обратном порядке сортировки, лучше воспользоваться методом класса
    // Collections.reverse()
    @Override
    public int compareTo(WordWrapper thatWord) {
        if (count < thatWord.getCount()) {
            return 1;
        }
        if (count > thatWord.getCount()) {
            return -1;
        }
        return word.compareTo(thatWord.getWord());
    }
}
