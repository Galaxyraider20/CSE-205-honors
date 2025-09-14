import java.io.*;

public class ConsoleUI implements UI{
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public int askInt(String prompt)
    {
        while(true)
       {
            try
            {
                System.out.println(prompt);
                return Integer.parseInt(reader.readLine());
            }
            catch(Exception e)
            {
                System.out.println("Please enter a number");
            }
       }
    }

    public String askString(String prompt)
    {
        while(true)
        {
            try
            {
                System.out.println(prompt);
                String s = reader.readLine();
                if (s != null && s!="") return s;
            }
            catch(Exception e)
            {
                System.out.println("Please enter something");
            }
        }
    }

    public boolean askYesNo(String prompt)
    {
        while(true)
        {
            String s = askString(prompt + " (y/n)");
            s = s.toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("Please answer yes or no.");
        }
    }

    public void println(String msg)
    {
        System.out.println(msg);
    }
}
