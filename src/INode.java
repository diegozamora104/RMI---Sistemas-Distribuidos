import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface INode extends Remote {

    //GETTERS
    int getId() throws RemoteException;
    boolean getEngaged() throws RemoteException;
    boolean getInitiator() throws RemoteException;
    int getNumMessages() throws RemoteException;
    int getOrigin() throws RemoteException;
    ArrayList<String> getNeighborhood() throws RemoteException;


    //SETTERS
    void setEngaged(Boolean value) throws RemoteException;
    void setNumMessages(int n) throws RemoteException;
    void setOrigin(int origin) throws RemoteException;

    //TODO Echo functions!

    void active() throws RemoteException;
    void firstWave(int pid) throws RemoteException;
    boolean echo(String origin) throws RemoteException;
    void election(String originPid , String electedPid) throws RemoteException;
}