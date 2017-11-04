public class Node extends Field {
    public Node(int x, int y, char character){
        super(x, y, character);
    }

    public ArrayList<Node> findNeighbors(){
        ArrayList<Node> listOfNeighbors = new ArrayList<Node>();
        //@TODO: walk through all neighbors of current field, add those that
        //are "passable" to the list
        //note: there can be less than four neighbors, for example in a corner
        return listOfNeighbors;
    }
}