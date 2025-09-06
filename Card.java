public class Card {
    private String value;
    private int intvalue;
    private Suit suit;
    public boolean isFaceUp;

    public Card(int intval,Suit su,boolean faceup)
    {
        this(intval,su);
        isFaceUp = faceup;
    }

    public Card(String val,Suit su,boolean faceup)
    {
        this(val,su);
        isFaceUp = faceup;
    }

    public Card(int intval,Suit su)
    {
        intvalue = intval;
        suit = su;
        intToString();
        value = value + " of " +suit;
        isFaceUp = false;
    }

    public Card(String val,Suit su)
    {
        value = val;
        suit = su;
        isFaceUp = false;
    }


    public String value()
    {
        return value;
    }

    public int intvalue()
    {
        return intvalue;
    }

    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    private void intToString()
    {
        if(intvalue>10||intvalue==1)
        {
            switch (intvalue){

                case 1:
                    value = "A";
                    intvalue = -99;
                    break;
                case 11:
                    value = "J";
                    intvalue = 10;
                    break;
                case 12:
                    value = "Q";
                    intvalue = 10;
                    break;
                case 13:
                    value = "K";
                    intvalue = 10;
                    break;

            } 
        }
        else
        {
            value = Integer.toString(intvalue);
        }
    }

    public String toString()
    {
        return value;
    }
}
