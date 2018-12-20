import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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

        Registry reg = LocateRegistry.getRegistry("localhost");

        try {
            switch (args[0]) {
                case "Start":

                    try {

                        for (String nodeName : reg.list()) {
                            INode node = (INode) reg.lookup(nodeName);
                            node.active();

                            if(node.getInitiator()) {

                                for (String neighbor : node.getNeighborhood()) {
                                    node.firstWave(Integer.parseInt(neighbor));
                                }
                            }
                        }

                    } catch (NotBoundException e) {

                        e.printStackTrace();

                    }

                    break;
                case "Stop":

                    try {
                        for (String nodeName : reg.list()) {
                            INode node = (INode) reg.lookup(nodeName);
                            reg.unbind(String.valueOf(node.getId()));
                        }
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }

                    System.out.print("EchoAlgorithm stopped!");
                    System.exit(0);

                default:

                    if (args.length == 3) {
                        pid = Integer.parseInt(args[0]);

                        _neighborhood = args[1].split(",");

                        initiator = args[2].equals("true");

                    } else {

                        System.out.println("Usage: java EchoAlgorithm <pid> <neighborhood> <initiator?> <encrypted_file> <ip_adress>");
                        System.exit(1);

                    }

                    //System.exit(0);

                    break;
            }
        }catch (NumberFormatException nfe){

            System.out.println("Usage: java EchoAlgorithm <pid> <pid> <neighborhood> <initiator?> <encrypted_file> <ip_adress>\n <pid> is a number");
            System.exit(1);

        }


        try {

            ArrayList<String> Neighborhood = new ArrayList<>(Arrays.asList(_neighborhood));
            NodeBody node = new NodeBody(pid, Neighborhood, initiator);
            //Registry reg = LocateRegistry.getRegistry(registry);
            reg.bind(String.valueOf(pid),node);

        } catch (AlreadyBoundException e) {

            e.printStackTrace();

        }

    }

}