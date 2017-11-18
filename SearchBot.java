import java.lang.reflect.Array;
import java.util.*;

class SearchBot {
    private Field startField;
    private Labyrinth labyrinth;
    private SearchBotUI searchbotUI;
    private Frontier frontier;
    private boolean hasReachedGoal = false;


    /**
     * Creates a new bot set on the labyrinth.
     * The bot sits down on the start field
     * and creates a frontier with just the start field node in it.
     *
     * @param labyrinth
     */
    public SearchBot(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
        startField = labyrinth.getStartField();
        frontier = new Frontier(new SearchPath(startField));
        searchbotUI = new SearchBotUI(this);
        labyrinth.showLabyrinth(searchbotUI);
        labyrinth.setPortals();
    }



    /**
     * Selecting all possible neighbor nodes, adding only the ones that are passable.
     * I decided to go from the right, clockwise - chosen absolutely arbitrarily
     *
     * @param field
     * @return
     */
    public ArrayList<Field> getNeighborNodes(Field field) {
        int x = field.x;
        int y = field.y;
        ArrayList<Field> listOfNeighbors = new ArrayList<Field>();

        Field rightNeighbor = this.labyrinth.getFieldAt(x + 1, y);
        if (rightNeighbor.type != FieldType.BORDER && (rightNeighbor.visited == false)) {
            listOfNeighbors.add(rightNeighbor);
        }
        Field bottomNeighbor = this.labyrinth.getFieldAt(x, y + 1);
        if (bottomNeighbor.type != FieldType.BORDER && (bottomNeighbor.visited == false)) {
            listOfNeighbors.add(bottomNeighbor);
        }
        Field leftNeighbor = this.labyrinth.getFieldAt(x - 1, y);
        if (leftNeighbor.type != FieldType.BORDER && (leftNeighbor.visited == false)) {
            listOfNeighbors.add(leftNeighbor);
        }
        Field topNeighbor = this.labyrinth.getFieldAt(x, y - 1);
        if (topNeighbor.type != FieldType.BORDER && (topNeighbor.visited == false)) {
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
    public void printCurrentState() {
        SearchPath currentPath = frontier.getCurrentPath();
        int x = currentPath.getHeadNode().x;
        int y = currentPath.getHeadNode().y;
        int pathLength = currentPath.getPathLength();
        String ifTerminated = (hasReachedGoal) ? "t" : "f";
        String output = "<" + x + ", " + y + ", " + pathLength + ", " + ifTerminated + ">";
        System.out.println(output);
    }

    // The current visual state of the Labyrinth
    public void printCurrentLabyrinth() {
        String rows = "";
        for (int y = 0; y < labyrinth.getHeight(); y++) {
            for (int x = 0; x < labyrinth.getWidth(); x++) {
                rows = rows + (String.valueOf(labyrinth.getCharAt(x, y)));
            }
            System.out.println(rows);
            rows = "";
        }
    }


    /**
     * Ein Schritt richtung Ziel in der BREITENSUCHE
     * Funktion:
     * Den Frontier kopieren, NICHT referenzieren.
     * Forschleife (Alle Pfade im letzten Frontier)
     * {Wenn die HeadNode des Pfades Nachbarn hat, Pfad entfernen und neue hinzufügen
     *     Feld auf visited setzen
     *     UI über Änderung benachrichtigen
     *  Wenn dabei das Ziel getroffen wird, Pfad in UI markieren}
     */
    public void breadthFirstWithUI(){
        ArrayList<SearchPath> latestFrontier = new ArrayList<SearchPath>(frontier.getFrontierSize());
        for(SearchPath path : frontier.getCurrentFrontier()){
            latestFrontier.add(path);
        }
        int newNeighbours = 0;
        for(SearchPath path : latestFrontier){
            ArrayList<Field> neighbors = getNeighborNodes(path.getHeadNode());
            if(!neighbors.isEmpty()){
                frontier.removePathFromFrontier(path);
                for(Field node : neighbors){
                    newNeighbours++;
                    if(node.isGoal()){
                        searchbotUI.showGoalpath(path, frontier);
                        return;
                    }
                    else{
                        SearchPath newPath = path.clone();
                        newPath.addNode(node);
                        frontier.addPathToFrontier(newPath);
                        node.visited = true;
                        searchbotUI.markVisitedField(node);
                    }
                }
            }
        }
        //Wenn keine neuen Nachbarn hinzugefügt werden konnten, terminiert die Suche
        if(newNeighbours == 0){
            searchbotUI.noGoalFound();
        }
    }
    /**
     * Ein Schritt richtung Ziel in der TIEFENSUCHE
     * Funktion:
     * Den ersten Pfad aus dem Frontier nehmen und löschen
     * Wenn die Headnode des Pfades Nachbarn hat, diese zu den Enden neuer Pfade machen
     * Wenn nicht, wurde der Pfad schon gelöscht
     * Wenn das Ziel getroffen wird, Pfad in UI markieren
     */
    public void depthFirstWithUI(){
        //Wenn der Frontier leer ist, terminieren:
        if(frontier.isEmpty())
        {
            searchbotUI.noGoalFound();
            return;
        }
        SearchPath path = frontier.getCurrentPath();
        frontier.removeLastEntry();
        for(Field node : getNeighborNodes(path.getHeadNode())){
            if(node.isGoal()){
                     searchbotUI.showGoalpath(path, frontier);
                     return;
                    }
                    else
                    {SearchPath newPath = path.clone();
                     newPath.addNode(node);
                     frontier.addPathToFrontier(newPath);
                     node.visited = true;
                     searchbotUI.markVisitedField(node);
            }
        }
    }

    public void aStarSearch(){

        ArrayList<Field> openList = new ArrayList<>();
        ArrayList<Field> closedList = new ArrayList<>();
        openList.add(startField);
        Field cheapestField;

        while(!openList.isEmpty()){

            cheapestField = getCheapestFieldToGoal(openList);
            System.out.println("CheapestField");
            System.out.println(cheapestField);
            System.out.println("Open List");
            System.out.println(openList);
            openList.remove(cheapestField);
            System.out.println("Open List ohne den billigsten");
            System.out.println(openList);

            ArrayList<Field> neighbors = getNeighborNodes(cheapestField);
            // Neighbor in this case means successor
            for (Field neighbor : neighbors) {
                neighbor.parent = cheapestField;
                if (neighbor.isGoal()){
                    openList.clear();
                    for (Field field : closedList) {

                        labyrinth.setGoalWay(field.x, field.y);
                        printCurrentLabyrinth();
                    }
                    System.out.println("Maximale Knoten im Frontier " + frontier.maxNodesInFrontier);
                    System.out.println("Expansion Operations im Frontier " + frontier.expansionOperations);
                    return;
                }
                else {
                    neighbor.costToGetHere = neighbor.parent.costToGetHere + labyrinth.manhattan(neighbor.x, neighbor.y, cheapestField.x, cheapestField.y);
                    neighbor.costToGetToGoal = neighbor.costToGetHere + neighbor.goalDistance.get(0);

                    if (!listContainsFieldCheaper(openList, neighbor) && !listContainsFieldCheaper(closedList,neighbor)){
                        openList.add(neighbor);
                        labyrinth.setSpectated(neighbor.x, neighbor.y);
                    }
                }
            }
            closedList.add(cheapestField);
            labyrinth.setWalkedOn(cheapestField.x, cheapestField.y);
            printCurrentLabyrinth();
        }
        System.out.println(closedList);

    }

    private boolean listContainsFieldCheaper(ArrayList<Field> openList, Field neighbor){

        //System.out.println(openList);
        for (Field field : openList) {

            if (field.x == neighbor.x && field.y == neighbor.y){

                if (field.costToGetToGoal <= neighbor.costToGetToGoal){
                    System.out.println("<" + "Billigstes Feld  :" + "x:" +  field.x + "y:" + field.y + ">");
                    return true;
                }
            }
        }
        return false;
    }

    private Field getCheapestFieldToGoal(ArrayList<Field> openList) {

        Field cheapestField = openList.get(0);
        for (Field field : openList) {

            System.out.println("Field  :" + field.costToGetToGoal);
            System.out.println("Cheapest Field  :" + cheapestField.costToGetToGoal);
            if (field.costToGetToGoal < cheapestField.costToGetToGoal){

                cheapestField = field;
            }
        }
        return cheapestField;
    }


    /**
     * Breitensuche
     */
    public void breadthFirstSearch(){

        Field currentNode;
        ArrayList<SearchPath> goalPath = new ArrayList<>();
        do {

            currentNode = this.frontier.getFirstEntry().getHeadNode();
            ArrayList<Field> neighbors = getNeighborNodes(currentNode);
            goalPath.add(this.frontier.getFirstEntry());
            System.out.println(goalPath);
            this.frontier.removeFirstEntry();

            if (!neighbors.isEmpty()) {
                // in case of neighbors list is not Empty, adds the Path to the end of the Frontier list
                for (Field field : neighbors) {

                    if (field.isGoal()) {

                        currentNode = field;
                        hasReachedGoal = true;
                        return;
                    } else {

                        System.out.println(field);
                        SearchPath newPath = new SearchPath(field);
                        this.frontier.addPathToFrontier(newPath);
                        labyrinth.setVisited(field.x, field.y);
                    }
                }
            }

            currentNode = this.frontier.getFirstEntry().getHeadNode();

            printCurrentLabyrinth();
        }while (currentNode != labyrinth.getGoalField() && !this.frontier.isEmpty());
    }


    /**
     * Tiefensuche
     */
    public void depthFirstSearch() {

        SearchPath currentPath = this.frontier.getCurrentPath();
        this.frontier.removePathFromFrontier(currentPath);
        Field currentNode = currentPath.getHeadNode();

        //@TODO: specify an order
        //For Now the Order is specified by the Neighbor's method itself ( Priories as follows: Top, Left, Down, Right)
        do { //Do-While loop, because he has to go through the loop at least once


            ArrayList<Field> neighbors = getNeighborNodes(currentNode);

            if (neighbors.isEmpty()) {
                this.frontier.removeLastEntry();
            }

            for (Field field : neighbors) {
                if (field.isGoal()) {
                    SearchPath goalPath = currentPath;
                    goalPath.addNode(field);
                    this.frontier.addPathToFrontier(goalPath);
                    hasReachedGoal = true;

                    printCurrentState();
                    printCurrentLabyrinth();
                    return;
                } else {
                    SearchPath newPath = new SearchPath(currentPath.getHeadNode());
                    newPath.addNode(field);
                    this.frontier.addPathToFrontier(newPath);
                }
            }

            currentNode = this.frontier.getCurrentPath().getHeadNode();
            labyrinth.setVisited(currentNode.x, currentNode.y);
            printCurrentLabyrinth();
            System.out.println("Frontier after Neighbors loop:" + this.frontier);
        }
        while (currentNode != labyrinth.getGoalField() && !this.frontier.isEmpty());
    }

    //you can call the bot with "java SearchBot %filename%"
    public static void main(String[] args) {
        String filename = (args.length == 0) ? "./beispiel1.txt" : args[0];
        Labyrinth labyrinth = new Labyrinth(filename);
        SearchBot bot = new SearchBot(labyrinth);
        bot.aStarSearch();
    }
}