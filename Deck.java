import java.util.Random;

public class Deck {
    public Card[] Deck;
    public int top;
    public final int length = 52;

    public Deck(boolean shuffle)
    {
        this();
        if(shuffle)
            shuffle();
    }

    public Deck()
    {
        Deck = new Card[52];
        top = 51;
        fillDeck();
    }

    public Card cardAt(int i)
    {
        if(i>top)
            return null;
        else
            return Deck[i];
    }
    public void fillDeck()
    {
        int i ,val;
        for(i=0;i<52;i++)
        {
            val = (i+1)%13;
            if(val==0)
                val = 13;
            if(i < 13)
            {
                Deck[i] = new Card(val,Suit.CLUBS,false);
            }
            else if(i < 26)
            {
                Deck[i] = new Card(val,Suit.DIAMONDS,false);
            }
            else if(i < 39)
            {
                Deck[i] = new Card(val,Suit.HEARTS,false);
            }
            else if(i <52)
            {
                Deck[i] = new Card(val,Suit.SPADES,false);
            }
        }
    }

    public void shuffle()
    {
        System.out.println("shuffling...");
        //deciding how mant times the deck will be shuffled (between 100 and 1000)
        Random rand = new Random();
        int no = 50;
        while(no<100)
        {
            no = rand.nextInt(1000);
            System.out.println("no "+ no);
        }


        //switch positions of cards randomly.
        int cnt = 0;
        int pos1, pos2;
        Card temp;
        System.out.println("Number of Shuffles -> "+no);
        while(cnt<no)
        {
            pos1 = rand.nextInt(52);
            pos2 = rand.nextInt(52);

            //switch the positions of these two randoms

            temp = Deck[pos1];
            Deck[pos1] = Deck[pos2];
            Deck[pos2] = temp;

            cnt++;
        }
    }
    public void displayDeck()
    {
        int i;
        for (i = 0;i< length;i++)
        {
            if(cardAt(i)!= null)
                System.out.println(cardAt(i).toString());
            //else
               //System.out.println("null");
        }
    }
    
}
