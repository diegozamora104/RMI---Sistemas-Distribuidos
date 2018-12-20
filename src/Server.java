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

            if (initiator){

                //Se envia el mensaje explorar al vecino del nodo iniciador
                for (String nodeName : Neighborhood){
                    try{
                    INode stub = (INode) reg.lookup(nodeName);
                    System.out.print("\nTransmitting to the node: " + String.valueOf(stub.getId()) + "\n" );
                    stub.firstWave(pid);
                    } catch (NotBoundException e){
                        e.printStackTrace();
                    }
                }
            }

        } catch (AlreadyBoundException e) {

            e.printStackTrace();

        }

    }

}