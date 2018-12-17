//program for client application
import java.rmi.*;
public class Client
{
    public static void main(String args[])
    {
        String answer,value="Reflection in Java";
        try
        {
            // lookup method to find reference of remote object
            EchoInterface access = (EchoInterface) Naming.lookup("rmi://localhost:1900" + "/geeksforgeeks");
            answer = access.query(value);

            System.out.println("Article on " + value + " " + answer+" at GeeksforGeeks");
            System.out.println(access.sendMessage("Client Message"));
        }
        catch(Exception ae)
        {
            System.out.println(ae);
        }
    }
}

