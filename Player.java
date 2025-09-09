public class Player {
    private String name;
    public Hand hand;
    private int balance;
    private boolean isDealer;
    private int atStake;

    public Player(String nam, boolean dealer)
    {
        name = nam;
        hand = new Hand();
        isDealer = dealer;

    }

    public Player(String nam)
    {
        name = nam;
        hand = new Hand();
    }

    public String name()
    {
        return name;
    }

    public void changeAtStake(int money,boolean reset)
    {
        if(reset)
            atStake = money;
        else    
            atStake += money; 
    }
    public int getBalance()
    {
        return balance;
    }
    public void deal(int no, Deck deck)
    {
        while(no > 0)
        {
            hand.add(deck.Deck[deck.top]);
            deck.Deck[deck.top] = null;
            deck.top--;
            no--;
        }
    }

    public void deal(int no,Deck deck,int facedownno)
    {
        while(no > 0)
        {
            hand.add(deck.Deck[deck.top]);

            if(facedownno>0)
            {
                deck.Deck[deck.top] = null;
                deck.top--;
                no--;
                facedownno--;
            }
            else
            {
                deck.Deck[deck.top] = null;
                deck.top--;
                no--;
                hand.hand.get(no).isFaceUp = true;
            }
        }
    }

    public void changeMoney(int money)
    {
        balance += money; 
    }

    /*
    public void deal(int no,Deck deck,int facedownno, boolean shuffle )
    {

    }

    public void deal(int no,Deck deck, boolean shuffle )
    {
        if(shuffle)
        {
            deck.shuffle();
        }
        deal(no,deck);
    }
    */








}
