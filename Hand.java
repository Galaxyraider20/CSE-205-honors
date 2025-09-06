import java.util.*;

public class Hand {
    public ArrayList<Card> hand;

    public Hand()
    {
        hand = new ArrayList<Card>();
    }

    public void add(Card card)
    {
        hand.add(card);
    }

    public ArrayList<Card> returnCards()
    {
        return hand;
    }

    public void displayHand(boolean fromPlayer)
    {
        int i;
        for(i=0;i<hand.size();i++)
        {
            Card card = hand.get(i);
            if(!card.isFaceUp() && fromPlayer)
            {
                System.out.println("Card is face down");
                
            }
            else
            {
                System.out.println(card.toString());
            }
        }
    }

    public int size()
    {
        return hand.size();
    }

}
