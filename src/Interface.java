// Creating a Interface interface
import java.rmi.*;
public interface Interface extends Remote
{
    // Declaring the method prototype
    public String query(String search) throws RemoteException;
    public String sendMessage(String clientMessage) throws RemoteException;
}
