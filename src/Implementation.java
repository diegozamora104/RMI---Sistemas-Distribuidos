// Java program to implement the EchoInterface interface
import java.rmi.*;
import java.rmi.server.*;

public class Implementation extends UnicastRemoteObject implements EchoInterface {

    // Default constructor to throw RemoteException
    // from its parent constructor
    Implementation() throws RemoteException
    {
        super();
    }

    // Implementation of the query interface
    public String query(String search) throws RemoteException {
        String result;
        if (search.equals("Reflection in Java"))
            result = "Found";
        else
            result = "Not Found";

        return result;
    }

    public String sendMessage(String clientMessage) throws RemoteException {
        return "Client Message".equals(clientMessage) ? "Server Message" : null;
    }

    public String unexposedMethod() { return null; }
}
