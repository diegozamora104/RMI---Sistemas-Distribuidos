import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {}

    public static void main(String[] args) {
        try {

            // Looking up the registry for the remote object
            /*Registry reg = LocateRegistry.getRegistry("localhost");

            for (String nodeName : reg.list()){
                INode node = (INode) reg.lookup(nodeName);
                System.out.print("\n\nNode: " + String.valueOf(node.getId()) + "\n" );
                for(String neighbor : node.getNeighborhood()){
                    System.out.print("My neighbor:" + neighbor + "\n");
                }
            }*/


            // Calling the remote method using the obtained object
            //System.out.println("Server Answer: " + stub.printMsg());

            // System.out.println("Remote method invoked");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

