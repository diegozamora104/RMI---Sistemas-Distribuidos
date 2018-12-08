import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceNode extends Remote {
    void printMsg() throws RemoteException;
}
