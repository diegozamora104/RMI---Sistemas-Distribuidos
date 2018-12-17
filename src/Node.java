import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {

    private static int _node_id;
    private static boolean _initiator; // _initiator : Initiator node
    private static boolean _engaged; // _engaged : Visited node
    private static int _num_messages; // _num_messages : Number of messages
    private static int _origin; // _origin : Origin node
    private static List<Integer> _neighborhood;

    public Node(int id, boolean initiator){
        _node_id = id;
        _initiator = initiator;
        _engaged = false;
        _num_messages = 0;
        _origin = -1;
        _neighborhood = new ArrayList<>();   // Hay que agregar a los vecinos
    }

    // SUPUESTO: NO hay que inicializar todos los nodos, incluyendo los vecinos de nodos ya inicializados
    // public send(vecinos,explorer){}

    // GETTERS
    public int getId(){
        return _node_id;
    }
    public boolean getEngaged(){
        return _engaged;
    }
    public boolean getInitiator(){
        return _initiator;
    }
    public int getMessages(){
        return _num_messages;
    }
    public int getOrigin(){
        return _origin;
    }
    public List<Integer> getNeighborhood(){
        return _neighborhood;
    }

    //SETTERS
    public void setCommitted(int c){
        _node_id = c;
    }
    public void setNumber(int n){
        _node_id = n;
    }
    public void setOrigin(int o){
        _node_id = o;
    }
    public void newNeighbor(int IdNeighbor){
        _neighborhood.add(IdNeighbor);
    }
}

