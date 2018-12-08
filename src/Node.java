import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Node extends ImplNode{

    public Node() {}

    public static void main(String args[]){
        try{
            ImplNode implementation = new ImplNode();

            InterfaceNode stub = (InterfaceNode) UnicastRemoteObject.exportObject(implementation, 0);

            Registry registry = LocateRegistry.getRegistry();

            registry.bind("Print", stub);

            System.err.println("Server ready");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
