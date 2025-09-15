package Restructured.cards.core;
// cards/core/Shoe.java
import java.util.*;

public class Shoe {
    private final List<Card> drawPile = new ArrayList<>();
    private final List<Card> discardPile = new ArrayList<>();
    private final Shuffler shuffler;
    private final int cutIndex; // when reached, shoe is considered “depleted”

    public Shoe(int decks, Shuffler shuffler, double penetration) {
        if (decks <= 0) throw new IllegalArgumentException("decks>0");
        if (penetration <= 0 || penetration >= 1) throw new IllegalArgumentException("0<penetration<1");
        this.shuffler = shuffler;

        for (int i = 0; i < decks; i++) {
            // build one deck without shuffling and append
            for (Suit s : Suit.values())
                for (Rank r : Rank.values())
                    drawPile.add(new Card(r, s));
        }
        shuffler.shuffle(drawPile);

        // cut card: e.g. penetration 0.75 means stop after 75% consumed
        this.cutIndex = (int) Math.round(drawPile.size() * (1.0 - penetration));
        // Optionally move a cut-card marker; here we just track the index.
    }

    public int size() { return drawPile.size(); }
    public boolean isDepleted() { return drawPile.size() <= cutIndex; }

    public Card draw() {
        if (drawPile.isEmpty()) throw new NoSuchElementException("Shoe empty");
        return drawPile.remove(drawPile.size() - 1);
    }

    public void discard(Card c) { discardPile.add(c); }
    public void discardAll(Collection<Card> cards) { discardPile.addAll(cards); }

    public void reshuffle() {
        // Typical casino reshuffle: combine discards back into shoe and shuffle
        drawPile.addAll(discardPile);
        discardPile.clear();
        shuffler.shuffle(drawPile);
    }

    public List<Card> peekAll() { return Collections.unmodifiableList(drawPile); }
}
