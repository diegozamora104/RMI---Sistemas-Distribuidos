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
            System.out.println("Recibi un mensaje explorador de  "+ String.valueOf(pid));
            engaged = true;
            origin = pid;
            for (String neighbor : neighborhood) {
                Registry reg = LocateRegistry.getRegistry();

                if (!neighbor.equals(String.valueOf(origin))){
                    try{

                        INode stub = (INode) reg.lookup(neighbor);

                        System.out.println("Le envio un mensaje a este tio: " + neighbor);

                        stub.firstWave(node_id);

                    } catch (NotBoundException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            System.out.println("Lo siento, nodo: " + pid + "ya estoy comprometido con: " + origin + " u-u");
        }

        System.out.println("Contador de mensajes: " + num_messages);
        num_messages = num_messages + 1;

        if (num_messages == neighborhood.size()){
            System.out.println("Enviando un eco a :" + origin);
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

        System.out.println("Recibi un eco de : " + pid);

        num_messages = num_messages + 1;
        Registry reg = LocateRegistry.getRegistry();

        if ( num_messages == neighborhood.size()){

            engaged = false;

            if (initiator){

                System.out.println("Echo Algorithm finished!");
                System.out.println("Elected node: " + String.valueOf(node_id));

                try {

                    String decipherText;
                    String textcipher = null;
                    InterfaceServer server = (InterfaceServer) Naming.lookup("//" + address + "/PublicKey");
                    //Este try/catch es para leer el texto cifrado del archivo que damos como input

                    try{
                        BufferedReader brTexto = new BufferedReader(new FileReader(path));

                        String sCurrentLine;

                        while ((sCurrentLine = brTexto.readLine()) != null) {
                            textcipher = sCurrentLine;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String publicKey = server.getKey("grupo_5") ;
                    System.out.println("Llave obtenida del server: "+ publicKey);
                    System.out.println("Texto cifrado del server: "+ textcipher);
                    decipherText = server.decipher("grupo_5", textcipher, publicKey );
                    System.out.println("Texto descifrado del server: "+ decipherText);

                }catch(Exception e){
                    System.out.println(e);
                }

                INode stub;
                for (String neighbor : neighborhood) {
                    try{
                        System.out.println("Responding to node: " + neighbor);
                        stub = (INode) reg.lookup(neighbor);
                        //stub.election(neighbor , String.valueOf(pid));


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
}

