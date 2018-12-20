import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NodeBody extends UnicastRemoteObject implements INode {

    private int _node_id;
    private boolean _initiator; // _initiator : Initiator node
    private boolean _engaged; // _engaged : Visited node
    private int _num_messages; // _num_messages : Number of messages
    private int _origin; // _origin : Origin node
    private ArrayList<String> _neighborhood;

    public NodeBody(int id, ArrayList<String> neighborhood, boolean initiator) throws RemoteException {
        super();
        this._node_id = id;
        this._initiator = initiator;
        this._neighborhood = neighborhood;
        _engaged = false;
        _num_messages = 0;
        _origin = 0;
    }

    // GETTERS
    @Override
    public int getId() throws RemoteException{
        return _node_id;
    }
    @Override
    public boolean getEngaged() throws RemoteException {
        return _engaged;
    }
    @Override
    public boolean getInitiator() throws RemoteException {
        return _initiator;
    }
    @Override
    public int getMessages() throws RemoteException {
        return _num_messages;
    }
    @Override
    public int getOrigin() throws RemoteException {
        return _origin;
    }
    @Override
    public ArrayList<String> getNeighborhood() throws RemoteException {
        return _neighborhood;
    }


    //SETTERS
    @Override
    public void setEngaged(int c) throws RemoteException {
        _node_id = c;
    }
    @Override
    public void setNumber(int n) throws RemoteException {
        _node_id = n;
    }
    @Override
    public void setOrigin(int o) throws RemoteException {
        _node_id = o;
    }

    @Override
    public void firstWave() throws RemoteException {

    }

    /*@Override
    public void run() {

        while (true);

    }*/
}

