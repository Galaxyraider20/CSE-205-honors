package Restructured.cards;

import java.util.*;
public interface Deck {
    int size();
    boolean isEmpty();
    Card draw();
    void shuffle();
    List<Card> cards();
}
