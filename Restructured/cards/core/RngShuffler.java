package Restructured.cards.core;
import java.util.*;
public class RngShuffler implements Shuffler{
    private final Random random;

    RngShuffler(Random random){this.random = random;}

    RngShuffler(){this(new Random());}

    RngShuffler(long seed){this(new Random(seed));}

    @Override
    public void shuffle(List<Card> cards){Collections.shuffle(cards,random);}
}
