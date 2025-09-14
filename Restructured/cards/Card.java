package Restructured.cards;

public class Card 
{
    private final Rank rank;
    private final Suit suit;
    private boolean faceUp;


    public Card(Rank rank, Suit suit, boolean faceUp) 
    {
        this.rank = rank;
        this.suit = suit;
        this.faceUp = faceUp;
    }

    //if faceUp values is not specified, it defaults to true.
    public Card(Rank rank, Suit suit) 
    {
        this(rank, suit, true);
    }

    //returns rank
    public Rank rank() { return rank; }
    //returns suit
    public Suit suit() { return suit; }
    //returns the faceUp state of the card
    public boolean isFaceUp() { return faceUp; }
    //changes the faceUp value of the card
    public void flip() { faceUp = !faceUp; }
}