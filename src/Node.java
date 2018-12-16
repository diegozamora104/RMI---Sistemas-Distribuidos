import java.util.ArrayList;
import java.util.List;

public class Node {

    private int ID;
    private int i; // i : Initiator
    private int c; // c : Committed
    private int n; // n : Number of messages
    private int o; // o : Origin node
    private List<Integer> neighborhood;

    public Node(int ID, int initiator, int origin){
        this.ID = ID;
        this.i = initiator;
        this.c = 0;
        this.n = 0;
        this.o = origin;
        this.neighborhood = new ArrayList<Integer>();   // hay que agregar a los vecinos
    }

// SUPUESTO: NO hay que inicializar tooooodos los nodos, incluyendo los vecinos de nodos ya inicializados
    // public send(vecinos,explorer){}

    // GETTERS
    public int getid(){
        return this.ID;
    }
    public int getCommitted(){
        return this.c;
    }
    public int getInitiator(){
        return this.i;
    }
    public int getNumber(){
        return this.n;
    }
    public int getOrigin(){
        return this.o;
    }
    public List<Integer> getNeighbot(){
        return this.neighborhood;
    }

    //SETTERS
    public void setCommitted(int c){
        this.ID = c;
    }
    public void setNumber(int n){
        this.ID = n;
    }public void setOrigin(int o){
        this.ID = o;
    }
    public void newNeighbor(int IdNeighbor){
        this.neighborhood.add(IdNeighbor);
    }
}

