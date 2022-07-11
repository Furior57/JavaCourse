package com.javacode.collections.collectionClass;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// создаем вложенный класс имплементирующий интерфейс Comparable
// напомню, если мы имплементируем интерфейс, то мы обязаны
// переопределить его абстрактные методы, здесь compareTo()
// Comparable должен быть параметризирован тем типом котроый сравниваем
// ну и вдогонку Comparator это обьект который сравнивает
// а Comparable это сравниваемый обьект
public class Card implements Comparable<Card> {

    // создаем два перечисления для описания мастей и достоиств карт
    public enum Suit {SPADES, HEARTS, CLUBS, DIAMONDS}

    public enum Face {
        Ace, Deuse, Three, Four, Five, Six, Seven, Eight, Nine,
        Ten, Jack, Queen, King
    }

    // создаем два обьекта для описания карты и инициализируем их
    // в конструкторе
    private final Suit suit;
    private final Face face;

    public Card(Suit suit, Face face) {
        this.suit = suit;
        this.face = face;
    }

    public Suit getSuit() {
        return suit;
    }

    public Face getFace() {
        return face;
    }

    @Override
    public int compareTo(Card card) {
        // получаем массив из перечисления с помощью метода values()
        Face[] values = Face.values();
        // в целом ненужная операция, преобразовывем массив в список
        List<Face> faces = Arrays.asList(values);
        // если значение
        if (faces.indexOf(this.face) < faces.indexOf(card.getFace())) {
            return -1;
        } else if (faces.indexOf(this.face) > faces.indexOf(card.getFace())) {
            return 1;
        } else if (faces.indexOf(this.face) == faces.indexOf(card.getFace())) {
            return String.valueOf(suit).compareTo(
                    String.valueOf(card.getSuit()));
        } else return 0;

    }

    @Override
    public String toString() {
        return face + " of " + suit;
    }

    // имплементация метода equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit && face == card.face;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, face);
    }
}
