import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    public int getNumMessages() throws RemoteException {
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
    public void setEngaged(Boolean value) throws RemoteException {
        _engaged = value;
    }
    @Override
    public void setNumMessages(int n) throws RemoteException {
        _num_messages = n;
    }
    @Override
    public void setOrigin(int origin) throws RemoteException {
        _origin = origin;
    }

    @Override
    public void active() throws RemoteException {

        System.out.println("Starting algorithm in node: " + _node_id);

    }




    @Override
    public void firstWave(NodeBody node) throws RemoteException {

        Registry reg = LocateRegistry.getRegistry();

        if (!node.getEngaged()){
            System.out.println("Explorer message from node: " + String.valueOf(node.getId()) + " established as origin.");
            node.setEngaged(true);
            node.setOrigin(node.getId());
            for (String neighbor : node.getNeighborhood()) {



                if (!neighbor.equals(String.valueOf(node.getOrigin()))){
                    try{

                        INode stub = (INode) reg.lookup(neighbor);

                        System.out.println("Sending a message to: " + neighbor);

                        stub.firstWave(object);

                    } catch (NotBoundException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            System.out.println("Explorer message from node: " + node.getOrigin() + " received and extinguished");
        }

        node.setNumMessages(node.getNumMessages() + 1);

        if (node.getNumMessages() == node.getNeighborhood().size()){
            System.out.println("Sending a echo message from node:" + node.getOrigin());

            try {
                INode stub2 = (INode) reg.lookup(String.valueOf(node.getOrigin()));

                //stub2.echo(object);

            } catch (NotBoundException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean echo(NodeBody node) throws RemoteException {

        System.out.println("Echo message from node: " + node.getId());

        node.setNumMessages(node.getNumMessages() + 1);
        Registry reg = LocateRegistry.getRegistry();

        if ( node.getNumMessages() == node.getNeighborhood().size()){

            node.setEngaged(false);

            if (node.getInitiator()){

                System.out.println("Echo Algorithm finished!");
                System.out.println("Elected node: " + node.getId());


                INode stub;
                for (String neighbor : node.getNeighborhood()) {
                    try{
                        System.out.println("Responding to node: " + node.getId());
                        stub = (INode) reg.lookup(neighbor);
                        stub.election(neighbor , String.valueOf(node.getId()));


                        //Aqui deberia ir el consultar al server

                    } catch (NotBoundException e){
                        e.printStackTrace();
                    }
                }


                return true;

            }
            else {

                INode stub;
                try{
                    stub = (INode) reg.lookup(String.valueOf(node.getOrigin()));
                    //stub.echo(object);
                } catch (NotBoundException e){
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void election(String originPid, String electedPid) throws RemoteException {

        /*if (!representante_establecido){
            representante_establecido = true;
            proceso_representante = id_representante;
            System.out.println("El Proceso Representante es " +  id_representante);
            for (int i = 0; i < nodos_vecinos.size(); i++){
                Registry reg = LocateRegistry.getRegistry();
                EchoInterface stub;
                if (!nodos_vecinos.get(i).equals(nodoOrigen)){
                    try{
                        stub = (EchoInterface) reg.lookup(nodos_vecinos.get(i));
                        System.out.println("Enviando ID del Proceso Representante a " + nodos_vecinos.get(i));
                        stub.establecer_representante(nodoId ,  id_representante);
                    } catch (NotBoundException e){
                        e.printStackTrace();
                    }
                }
            }
        }*/

    }

    /*@Override
    public void run() {

        while (true);

    }*/
}

