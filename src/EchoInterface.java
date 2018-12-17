// Creating a EchoInterface interface
import java.rmi.*;
public interface EchoInterface extends Remote
{
    // Declaring the method prototype
    public String sayHello() throws RemoteException;
    public String query(String search) throws RemoteException;
    public String sendMessage(String clientMessage) throws RemoteException;
}
