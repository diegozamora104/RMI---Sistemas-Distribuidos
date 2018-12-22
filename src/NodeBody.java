import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NodeBody extends UnicastRemoteObject implements INode{

    int node_id;
    boolean initiator; // initiator : Initiator node
    boolean engaged; // engaged : Visited node
    int num_messages; // num_messages : Number of messages
    int origin; // origin : Origin node
    ArrayList<String> neighborhood;
    String path;
    String address;

    public NodeBody(int id, ArrayList<String> neighborhood, boolean initiator, String path, String address) throws RemoteException {
        super();
        this.node_id = id;
        this.initiator = initiator;
        this.neighborhood = neighborhood;
        this.path = path;
        this.address = address;
        engaged = false;
        num_messages = 0;
        origin = 0;
    }

    // GETTERS
    @Override
    public int getId() throws RemoteException{
        return node_id;
    }
    @Override
    public boolean getEngaged() throws RemoteException {
        return engaged;
    }
    @Override
    public boolean getInitiator() throws RemoteException {
        return initiator;
    }
    @Override
    public int getNumMessages() throws RemoteException {
        return num_messages;
    }
    @Override
    public int getOrigin() throws RemoteException {
        return origin;
    }
    @Override
    public ArrayList<String> getNeighborhood() throws RemoteException {
        return neighborhood;
    }


    //SETTERS
    @Override
    public void setEngaged(Boolean value) throws RemoteException {
        engaged = value;
    }
    @Override
    public void setNumMessages(int n) throws RemoteException {
        num_messages = n;
    }
    @Override
    public void setOrigin(int origin) throws RemoteException {
        this.origin = origin;
    }



    @Override
    public void active() throws RemoteException {

        System.out.println("Node Activated: " + node_id);

    }


    @Override
    public void firstWave(int pid) throws RemoteException {
        if (!engaged){
            System.out.println("I Received a message from node: "+ String.valueOf(pid));
            engaged = true;
            origin = pid;
            for (String neighbor : neighborhood) {
                Registry reg = LocateRegistry.getRegistry();

                if (!neighbor.equals(String.valueOf(origin))){
                    try{

                        INode stub = (INode) reg.lookup(neighbor);

                        System.out.println("Sending to the node: " + neighbor);

                        stub.firstWave(node_id);

                    } catch (NotBoundException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            System.out.println("Node: " + pid + " engaged with: " + origin + ", therefore, the message is extinguished.");
        }

        num_messages = num_messages + 1;

        if (num_messages == neighborhood.size()){
            System.out.println("Sending a echo message: " + origin);
            Registry reg = LocateRegistry.getRegistry();
            try {
                INode stub2 = (INode) reg.lookup(String.valueOf(origin));

                stub2.echo(String.valueOf(node_id));

            } catch (NotBoundException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean echo(String pid) throws RemoteException {

        System.out.println("I Received a echo message from node: " + pid);

        num_messages = num_messages + 1;
        String decipheredText = null;
        Registry reg = LocateRegistry.getRegistry();

        if ( num_messages == neighborhood.size()){

            engaged = false;

            if (initiator){

                System.out.println("Echo Algorithm finished!");
                System.out.println("Elected node: " + String.valueOf(node_id));

                try {
                    String text_from_server = null;
                    InterfaceServer server = (InterfaceServer) Naming.lookup("//" + address + "/PublicKey");

                    try{
                        BufferedReader buffer = new BufferedReader(new FileReader(path));
                        String line;
                        while ((line = buffer.readLine()) != null) {
                            text_from_server = line;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String publicKey = server.getKey("grupo_5") ;
                    decipheredText = server.decipher("grupo_5", text_from_server, publicKey );
                    System.out.println("Deciphered text: "+ decipheredText);

                }catch(Exception e){
                    System.out.println(e);
                }

                INode stub;
                for (String neighbor : neighborhood) {
                    try{
                        System.out.println("Responding to node: " + neighbor);
                        stub = (INode) reg.lookup(neighbor);
                        stub.result(decipheredText, String.valueOf(node_id));
                    } catch (NotBoundException e){
                        e.printStackTrace();
                    }
                }

                return true;

            }
            else {

                INode stub;
                try{
                    stub = (INode) reg.lookup(String.valueOf(origin));
                    stub.echo(String.valueOf(node_id));
                } catch (NotBoundException e){
                    e.printStackTrace();
                }
            }

        }

        return false;
    }

    @Override
    public void result(String deciphered_text, String electedPid) throws RemoteException {
        if (engaged){
            System.out.println("The elected node is: " + electedPid);
            System.out.println("The deciphered text is: " + deciphered_text);
            engaged = false;
            for (String neighbor : neighborhood) {
                Registry reg = LocateRegistry.getRegistry();

                if (!neighbor.equals(String.valueOf(origin))){
                    try{
                        INode stub = (INode) reg.lookup(neighbor);
                        stub.result(deciphered_text, electedPid);
                    } catch (NotBoundException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

