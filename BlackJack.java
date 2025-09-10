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
        System.out.println("No of Cards remaining in deck ->"+(deck.top+1));


        //deck.displayDeck();
        //Players have been dealt cards and setup is Complete!


        playRound(Players,Status,deck,reader);

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
                Players.get(i).hand.displayHand(false);
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
                    
                    Players.add(new Player(name));
                    Players.get(i).deal(2,deck);
                    System.out.println( name +"'s Cards");
                    Players.get(i).hand.displayHand(false);


                }
                else    
                {
                    System.out.println("Player " + (i+1) +" please enter your name");
                    name = reader.readLine();
                    if(name.isEmpty())
                        name = "Player " + (i+1);
                
                    Players.add(new Player(name));
                    Players.get(i+1).deal(2,deck);
                    System.out.println( name +"'s Cards");
                    Players.get(i+1).hand.displayHand(false);
                }

            }
        }  
    }



    public boolean check(ArrayList<Integer> Status)
    {
        int i;
        for(i=0;i<Status.size();i++)
        {
            if(Status.get(i)==1)
                return true;
        }
        return false;
    }

    public int calculateHandTotal(Hand hand)
    {
        int i;
        int sum = 0;
        for(i=0;i<hand.hand.size();i++)
        {
            sum += hand.hand.get(i).intvalue();
        }
        return sum;
    }

    public ArrayList<Integer> getWagers(ArrayList<Player> Players)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Integer> wagers = new ArrayList<Integer>();
        for(Player player: Players)
        {
            System.out.println(player.name()+", How much do you want to bet?");
            int bet = Integer.parseInt(reader.readLine());
            if(player.getBalance()>=bet)
            {
                player.changeAtStake(bet, false);
                player.changeMoney(-1*bet,false);
            }
            else
            {
                System.out.println("You're broke lol");
                bet = 0;
            }
            wagers.add(bet);
        }
        for(int i=0;i<Players.size();i++)
        {
            System.out.println(Players.get(i).name()+"' s Balance : "+Players.get(i).getBalance());
        }
        
        return wagers;
    }

    public boolean stillIn(Player player)throws IOException
    {
        boolean stillIn = true;
        boolean cont = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(cont)
        {
            System.out.println(player.name()+", Stand or Hit? (you hand has a value of "+ calculateHandTotal(player.hand)+" )");
            String answer = reader.readLine();
            if(answer.equals("yes")||answer.equals("ye")||answer.equals("y")||answer.equals("hit")||answer.equals("Hit"))
                {
                    stillIn = true;
                    cont = false;
                }
                else if(answer.equals("no")||answer.equals("nah")||answer.equals("n")||answer.equals("stand")||answer.equals("Stand"))
                {
                    stillIn = false;
                    cont = false;
                }
                else
                {
                    System.out.println("Please reply with yes or no");
                }
        }
        return stillIn;
    }
   
    public void playRound(ArrayList<Player> Players, ArrayList<Integer> Status,Deck deck, BufferedReader reader) throws IOException
    {

        //for testing purposes everyone gets 1000$
        for(Player player:Players)
        {
            player.changeMoney(1000,true);
        }

        int[] Val = new int[Players.size()];//Hand Value of all the players
        //asking players who are still playing if they want to stand or hit
        boolean decision = true;
        //setting everyone's initial status to in or 1
        int i;
        for(i=0;i<Players.size();i++)
        {
            Status.add(1);
        }
        //calculating the initial hand totals and storing in Val, reseting atStake values
        for(i=0;i<Players.size();i++)
        {
            Players.get(i).changeAtStake(0, true);
            Val[i] = calculateHandTotal(Players.get(i).hand);
        }
        System.out.println(Status);
        //Placing Bets
        ArrayList<Integer> Wagers = getWagers(Players); // bet placed 
        int pool=0; //total money bet on this round
        for(i=0;i<Wagers.size();i++)
        {
            pool += Wagers.get(i);
        }
        //are all players out? 
        //no-> enter loop
        //yes-> skip loop
        while(check(Status))
        {
            //Run the loop for each player
            for(i=0;i<Status.size();i++)
            {
                //check if the player is in
                if(Status.get(i)==1)
                {
                    decision = true;
                    //runs as long as the player is in
                    while(decision)
                    {
                        decision = stillIn(Players.get(i));
                        if(decision)
                        {
                            Players.get(i).deal(1,deck);
                            System.out.println("You got "+Players.get(i).hand.hand.get(Players.get(i).hand.hand.size()-1).toString());
                            Val[i] += Players.get(i).hand.hand.get(Players.get(i).hand.hand.size()-1).intvalue();
                            if(calculateHandTotal(Players.get(i).hand) > 21)
                            {
                                System.out.println("Sorry " + Players.get(i).name() + ", it's a bust - " + calculateHandTotal(Players.get(i).hand));
                                Status.set(i, 0);
                                Val[i] = 0;
                                break;
                            }
                        }
                        else{
                            Status.set(i, 0);
                        }
                    }
                }
            }
        }
        //checking who has the highest value and awarding money respectively
        System.out.println("Game ended, adjusting money...");
        int max = Val[0];
        for(i=0;i<Val.length;i++)
        {
            if(Val[i]>max)
                max = Val[i];
        }
        //getting the Winners
        ArrayList<Player> Winners = new ArrayList<Player>();
        for(i=0;i<Val.length;i++)
        {
            if(calculateHandTotal(Players.get(i).hand)==max)
            {
                Winners.add(Players.get(i));
            }
        }
        //Distributing the Money

        for(i=0;i<Winners.size();i++)
        {
            Winners.get(i).changeMoney((pool)/Winners.size(),false);
        }

        //Displaying Final Balances
        for(i=0;i<Players.size();i++)
        {
            System.out.println(Players.get(i).name()+"' s Balance : "+Players.get(i).getBalance());
        }

    }

}

