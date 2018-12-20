import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;

public class Server{

    public static void main(String args[]) throws IOException {

        int pid = 0;
        String registry = "localhost";
        String[] _neighborhood = new String[0];
        boolean initiator = false;
        //String file;
        //String adress;

        try {
            if(args[0].equals("Start")){
                Registry reg = LocateRegistry.getRegistry("localhost");
                try {
                    for (String nodeName : reg.list()){
                        INode node = (INode) reg.lookup(nodeName);
                        System.out.print("\n\nNode: " + String.valueOf(node.getId()) + "\n" );
                        for(String neighbor : node.getNeighborhood()){
                            System.out.print("My neighbor:" + neighbor + "\n");
                        }
                    }
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            }

            if (args.length >= 1) {
                pid = Integer.parseInt(args[0]);

                if (args.length == 2) {
                    _neighborhood = args[1].split(",");
                }

                if(args.length == 3){
                    initiator = args[2].equals("true");
                }

            } else {

                System.out.println("Usage: java EchoAlgorithm <pid> <neighborhood> <initiator?> <encrypted_file> <ip_adress>");
                System.exit(1);

            }
        }catch (NumberFormatException nfe){

            System.out.println("Usage: java EchoAlgorithm <pid> <pid> <neighborhood> <initiator?> <encrypted_file> <ip_adress>\n <pid> is a number");
            System.exit(1);

        }


        try {

            ArrayList<String> Neighborhood = new ArrayList<>(Arrays.asList(_neighborhood));
            NodeBody node = new NodeBody(pid, Neighborhood, initiator);
            Registry reg = LocateRegistry.getRegistry(registry);
            reg.bind(String.valueOf(pid),node);

        } catch (AlreadyBoundException e) {

            e.printStackTrace();

        }

    }

}