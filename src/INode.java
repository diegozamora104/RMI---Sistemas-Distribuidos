import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface INode extends Remote {

    //GETTERS
    int getId() throws RemoteException;
    boolean getEngaged() throws RemoteException;
    boolean getInitiator() throws RemoteException;
    int getMessages() throws RemoteException;
    int getOrigin() throws RemoteException;
    ArrayList<String> getNeighborhood() throws RemoteException;


    //SETTERS
    void setEngaged(int c) throws RemoteException;
    void setNumber(int n) throws RemoteException;
    void setOrigin(int o) throws RemoteException;

    //TODO Echo functions!
}