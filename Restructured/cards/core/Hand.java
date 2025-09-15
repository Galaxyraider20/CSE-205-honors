package Restructured.cards;

import java.util.*;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void add(Card card) {cards.add(card);}

    public void addAll(Collection<Card> newCards){cards.addAll(newCards);}

    public List<Card> cards() {return Collections.unmodifiableList(cards);}

    public int size() {return cards.size();}

    public Card get(int index){return cards.get(index);}

    public Card last(){return cards.get(cards.size()-1);}

    public void clear(){cards.clear();}

    public String toString(){return cards.toString();}
}
