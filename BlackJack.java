import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BlackJack {

    public void start() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<Player> Players = new ArrayList<Player>();
        ArrayList<Integer> Status = new ArrayList<Integer>();

        Deck deck = new Deck();

        deck.displayDeck();
        deck.shuffle();
        takeNamesAndDistribute(deck,Players,Status);
        System.out.println("Remaining deck - ");
        deck.displayDeck();


        //deck.displayDeck();
        //Players have been dealt cards and setup is Complete!

        boolean bust = false;
        while(!bust)
        {
            bust = playRound(Players,Status);
        }
    }   

    public void takeNamesAndDistribute(Deck deck, ArrayList<Player> Players, ArrayList<Integer> Status) throws IOException
    {
        /**
         * Setting up the Game->
         * choosing the dealer and enetering player names
         * distribting cards to everyone
         */

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //Getting Player Count
        System.out.println("How many people are playing?");
        int playerAmt = 0;
        boolean cont = true;
        while(cont)
        {
            try
            {
                playerAmt = Integer.parseInt(reader.readLine());
                cont = false;
            }
            catch(NumberFormatException e)
            {
                System.out.println("Please enter a numeric value!");
            }
        }
        //checking if user wants a AI Dealer
        System.out.println("Do you want an Robot Dealer?");
        String AIDealer;
        boolean humanDealer = false;
        cont = true;
        while(cont)
        {
            AIDealer = reader.readLine();
            AIDealer = AIDealer.toLowerCase();
            if(AIDealer.equals("yes")||AIDealer.equals("ye")||AIDealer.equals("y"))
            {
                humanDealer = false;
                cont = false;
            }
            else if(AIDealer.equals("no")||AIDealer.equals("nah")||AIDealer.equals("n"))
            {
                humanDealer = true;
                cont = false;
            }
            else
            {
                System.out.println("Please reply with yes or no");
            }
        }

        
        int i;
        String name;
        boolean first = true;
        for(i = 0;i<playerAmt;i++)
        {
            if(i==0&& humanDealer) // Initializing the Dealer First
            {
                System.out.println("Dealer, please enter your name");
                name = reader.readLine();
                if(name.isEmpty())
                    name = "Dealer";
                Players.add(new Player(name,true));
                Players.get(i).deal(2,deck,1);
                System.out.println(name+"'s Cards");
                Players.get(i).hand.displayHand(true);
                System.out.println();

            }
            //all other players
            else
            {
                if(humanDealer)
                {
                    System.out.println("Player " + i +" please enter your name");
                    name = reader.readLine();
                    if(name.isEmpty())
                        name = "Player " + i;
                    if(first)
                    {
                        i--;
                        first = false;
                    }
                }
                else    
                {
                    System.out.println("Player " + (i+1) +" please enter your name");
                    name = reader.readLine();
                    if(name.isEmpty())
                        name = "Player " + (i+1);
                }

                Players.add(new Player(name));
                Players.get(i).deal(2,deck);
                System.out.println( name +"'s Cards");
                Players.get(i).hand.displayHand(false);

            }
        }  
    }



    public boolean check(ArrayList<Integer> Status)
    {
        int i;
        for(i=0;i<Status.size();i++)
        {
            
        }
        return false;
    }


    public boolean playRound(ArrayList<Player> Players, ArrayList<Integer> Status)
    {
        //asking players who are still playing if they want to stand or hit

        //setting everyone's initial status to in or 1
        int i;
        for(i=0;i<Status.size();i++)
        {
            Status.set(i,1);
        }

        while(check(Status))
        {
            
        }
        return true;

    }

}

