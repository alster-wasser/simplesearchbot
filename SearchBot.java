import java.util.*;

class SearchBot {
    private Field startField;
    private Labyrinth labyrinth;
    private Frontier frontier;

    //Creates a new bot set on the labyrinth.
    //The bot sits down on the start field
    //and creates a frontier with just the start field node in it.
    public SearchBot(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
        startField = labyrinth.getStartField();
        frontier = new Frontier(new SearchPath(startField));
    }

    //Since no other class is going to use the SearchPath and Frontier classes,
    //it seemed appropriate to define them as local classes.
    private class SearchPath {
        private LinkedList<Field> path;

        //constructor for private class SearchPath, creates 
        //a new path with just one node in it
        SearchPath(Field field){
            path = new LinkedList<Field>();
            path.add(field);
        }
        Field getHeadNode(){
            return path.getLast();
        }
        void addNode(Field field){
            path.add(field);
        }

        int getPathLength(){
            return path.size();
        }

    }
    //class Frontier keeps all the search paths
    private class Frontier{
        private ArrayList<SearchPath> pathsInFrontier;
        
        Frontier(SearchPath path){
            pathsInFrontier = new ArrayList<SearchPath>();
            pathsInFrontier.add(path);
        }
        void addPathToFrontier(SearchPath path){
            pathsInFrontier.add(path);
        }
        void removePathFromFrontier(SearchPath path){
            pathsInFrontier.remove(path);
        }

        //might not actually be needed but nice to have for testing, see main 
        int getFrontierSize(){
            return pathsInFrontier.size();
        }
    }

    //Selecting all possible neighbor nodes, adding only the ones that are passable.
    //I decided to go from the right, clockwise - chosen absolutely arbitrarily
    public ArrayList<Field> getNeighborNodes(Field field){
        int x = field.x;
        int y = field.y;
        ArrayList<Field> listOfNeighbors = new ArrayList<Field>();
        
        Field rightNeighbor = this.labyrinth.getFieldAt(x+1, y);
        if (rightNeighbor.type == FieldType.PASSABLE){
            listOfNeighbors.add(rightNeighbor);
        }
        Field bottomNeighbor = this.labyrinth.getFieldAt(x, y+1);
        if (bottomNeighbor.type == FieldType.PASSABLE){
            listOfNeighbors.add(bottomNeighbor);
        }
        Field leftNeighbor = this.labyrinth.getFieldAt(x-1, y);
        if (leftNeighbor.type == FieldType.PASSABLE){
            listOfNeighbors.add(leftNeighbor);
        }
        Field topNeighbor = this.labyrinth.getFieldAt(x, y-1);
        if (topNeighbor.type == FieldType.PASSABLE){
            listOfNeighbors.add(topNeighbor);
        }
      
        return listOfNeighbors;
        
    } 






    //@TODO: implement a way to output the labyrinth with the current search state
    public void printCurrentState(){
        System.out.println("test");
    }

    public static void main(String[] args){
        Labyrinth labyrinth = new Labyrinth("./blatt3_environment.txt");
        SearchBot bot = new SearchBot(labyrinth);
        System.out.println(bot.frontier.getFrontierSize());
        bot.printCurrentState();
    }
}