class SearchBot {
    private Field startField;
    private Field currentField;
    private Labyrinth labyrinth;

    private enum Direction {UP, DOWN, LEFT, RIGHT}

    public SearchBot(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
        startField = labyrinth.getStartField();
        currentField = startField;
    }

    public moveOneStep(Direction direction){
        currentField = labyrinth.getNeighbor(currentField, direction);
    }

    //@TODO: implement a way to output the labyrinth with the current search state
    public void printCurrentState(){
    }

    public static void main(String[] args){
        Labyrinth labyrinth = new Labyrinth("./blatt3_environment.txt");
        SearchBot bot = new SearchBot(labyrinth);
        bot.moveOneStep(Direction.RIGHT);
        bot.printCurrentState();
    }
}