import java.util.*;

class SearchBot {
    private Field startField;
   // private Field currentField;
    private Labyrinth labyrinth;

   // public enum Direction {UP, DOWN, LEFT, RIGHT}

    public SearchBot(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
        startField = labyrinth.getStartField();
       // currentField = startField;
    }

    private class SearchPath {
        private LinkedList<Field> path;

        public Field getHeadNode(){
            return path.getLast();
        }

    }

    private class Frontier{
        private ArrayList<SearchPath> pathsInFrontier;

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
    }

    public static void main(String[] args){
        Labyrinth labyrinth = new Labyrinth("./blatt3_environment.txt");
        SearchBot bot = new SearchBot(labyrinth);
        bot.printCurrentState();
    }
}