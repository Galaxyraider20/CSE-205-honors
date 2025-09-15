package Restructured.cards;

import java.util.*;

public class StandardDeck implements Deck
{
    private final List<Card> cards = new ArrayList<>(); 

    public StandardDeck()
    {
        for(Suit suit: Suit.values())
        {
            for(Rank rank : Rank.values())
            {
                cards.add(new Card(rank,suit));
            }
        }
    }

    public int size() {return cards.size();}

    public boolean isEmpty() {return cards.isEmpty();}

    public Card draw()
    {
        if(cards.isEmpty()) 
            throw new NoSuchElementException("Deck is empty");
        return cards.remove(cards.size()-1);
    }

    public void shuffle() {Collections.shuffle(cards, new Random());}

    public List<Card> cards(){return Collections.unmodifiableList(cards);}

}