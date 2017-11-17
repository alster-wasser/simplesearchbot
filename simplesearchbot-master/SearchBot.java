import java.util.*;

class SearchBot {
    private Field startField;
    private Labyrinth labyrinth;
    private SearchBotUI searchbotUI;
    private Frontier frontier;
    private boolean hasReachedGoal = false;

    //Creates a new bot set on the labyrinth.
    //The bot sits down on the start field
    //and creates a frontier with just the start field node in it.
    public SearchBot(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
        startField = labyrinth.getStartField();
        frontier = new Frontier(new SearchPath(startField));
        searchbotUI = new SearchBotUI();
        labyrinth.showLabyrinth(searchbotUI);
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
        //@TODO: implement a way to check if this node is already in the frontier as head of another path
        void addNode(Field field){
            path.add(field);
            if (field.isGoal()){
                hasReachedGoal = true;
            }
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

        //might not actually be needed but nice to have for testing
        int getFrontierSize(){
            return pathsInFrontier.size();
        }
        //returns the path at the end of the frontier
        SearchPath getCurrentPath(){
            return pathsInFrontier.get(pathsInFrontier.size()-1);
        }
        boolean isEmpty(){
            return pathsInFrontier.isEmpty();
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

    // The current state of the bot consists of:
    // 1) x,y of current field
    // 2) length of path currently visiting
    // 3) whether it has terminated
    // The output should look like this: <4, 5, 8, f> with values meaning <x, y, length, terminated?>
    // and <8, 2, 7, t> in case the goal was at field (8,2) and the search path was 7 fields long.
    public void printCurrentState(){
        SearchPath currentPath = this.frontier.getCurrentPath();
        int x = currentPath.getHeadNode().x;
        int y = currentPath.getHeadNode().y;
        int pathLength = currentPath.getPathLength();
        String ifTerminated = (hasReachedGoal) ? "t" : "f";
        String output = "<" + x + ", " + y + ", " + pathLength + ", " + ifTerminated + ">";
        System.out.println(output);
    }




    //work in progress
    public void depthFirstSearch(){
       SearchPath currentPath = this.frontier.getCurrentPath();
       this.frontier.removePathFromFrontier(currentPath);
       Field currentNode = currentPath.getHeadNode();
       //@TODO: specify an order
       ArrayList<Field> neighbors = getNeighborNodes(currentNode);
       for (Field field : neighbors){
           if (field.isGoal()){
              SearchPath goalPath = currentPath;
              goalPath.addNode(field);
              this.frontier.addPathToFrontier(goalPath);
              printCurrentState();
              return;
           } else {
             SearchPath newPath = currentPath;
             newPath.addNode(field);
             this.frontier.addPathToFrontier(newPath);

           }
          
       }
       printCurrentState(); 
    }

    //you can call the bot with "java SearchBot %filename%"
    public static void main(String[] args){
        String filename = (args.length == 0) ? "./blatt3_environment.txt" : args[0];
        Labyrinth labyrinth = new Labyrinth(filename);
        SearchBot bot = new SearchBot(labyrinth);
        bot.depthFirstSearch();
    }
}
