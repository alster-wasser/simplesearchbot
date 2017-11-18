import java.io.*;
import java.util.ArrayList;

public class Labyrinth {
    private int width = 0;
    private int height = 0;


    private Field[][] fields;
    private Field startField;
    private Field goalField;
    private char visited = 'v';
    private ArrayList<Field> goalFieldsList;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Field getStartField(){
        return startField;
    }

    public Field getFieldAt(int x, int y){
        return fields[x][y];
    }

    public char getCharAt(int x, int y){

        return fields[x][y].value;
    }

    public void setVisited(int x, int y){

        fields[x][y].visited = true;
        fields[x][y].value = visited;
    }
    public void setSpectated(int x, int y){
        fields[x][y].value = '>';
    }
    public void setWalkedOn(int x, int y){
        fields[x][y].value = '~';
    }

    public void setGoalWay(int x, int y){
        fields[x][y].value = '*';
    }



    //not quite sure if we actually need this method; but it's good to have at least for testing
    public Field getGoalField(){
        return goalField;
    }

    //the constructor reads a text representation and instantiates the Labyrinth with
    //a matrix of Field values. The index 0,0 is in the top left!
    public Labyrinth(String filename){
        try {
            // regarding this mess of buffered readers: I really don't know
            // why it's so cumbersome in Java to read lines from a file
            goalFieldsList = new ArrayList<>();
            //calculate width
            BufferedReader readerOnlyForWidth = new BufferedReader(new FileReader(filename));
            width = readerOnlyForWidth.readLine().length();
            readerOnlyForWidth.close();

            //calculate height
            BufferedReader readerOnlyForHeight = new BufferedReader(new FileReader(filename));
            while (readerOnlyForHeight.readLine() != null) {
                height++;
            }
            readerOnlyForHeight.close();

            //go through every character in the text file and create a Field
            //for each of them, add the Field to the Labyrinth
            fields = new Field[width][height];
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            for (int j = 0; j < height; j++){
                String currentLine = reader.readLine();

                for (int i = 0; i < width; i++){
                    char currentCharacter = currentLine.charAt(i);
                    Field currentField = new Field(i, j, currentCharacter);
                    fields[i][j] = currentField;

                    //set start and goal field
                    switch(currentCharacter){
                        case 's':
                            startField = currentField;
                            break;
                        case 'g':
                            goalField = currentField;
                            goalFieldsList.add(currentField);
                            break;
                        default:
                            break;
                    }
                }
            }
            reader.close();




            setGoalDistance();
            //@TODO: some proper exception handling
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
    * Makes the UI display the Labyrinth.
    */
    public void showLabyrinth(SearchBotUI s)
    {
        s.sizeLabyrinth(width, height);
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                s.fillLabyrinth((this.getFieldAt(j,i)).getFieldType(), j, i);
            }
        }
    }

    /**
     * Sets the distance from every Field to the Goal
     *
     * The Goal hast a Distance of 0 to itselfs, but also contains the Distance to other possible Goals.
     */
    public void setGoalDistance(){

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                for (Field goalField : goalFieldsList) {
                    Field field = fields[x][y];
                    field.goalDistance.add(manhattan(field.x, field.y, goalField.x, goalField.y));
                }
            }
        }
    }


    /**
     * Calculate the manhattan distance between a Field and the Goals
     *
     * @param xField
     * @param yField
     * @param xGoal
     * @param yGoal
     * @return
     */
    public int manhattan(int xField, int yField, int xGoal, int yGoal){

        return (Math.abs(xField - xGoal) + Math.abs(yField - yGoal));
    }

    // Wird nicht verwendet, aber wenn jemand lust hat es zu testen.
    /**
     * Calculate the euclidean distance between a Field and the Goals.
     *
     * Requires the goalDistance ListArray in Class Field to be Double.
     *
     * @param xField
     * @param yField
     * @param xGoal
     * @param yGoal
     * @return
     */
    public double euclidean(int xField, int yField, int xGoal, int yGoal){

        double yCord = Math.abs(xField - xGoal);
        double xCord = Math.abs(yField - yGoal);
        double result = Math.sqrt(Math.pow(xCord, 2) + Math.pow(yCord, 2));
        return (result);
    }

    /**
     * Switches the Portals
     */
    public void setPortals (){
        Field portal1 = null;
        Field portal2 = null;
        Field portal3 = null;
        Field portal4 = null;
        Field portal5 = null;
        for(Field[] f : fields){
            for(Field field: f){
                switch(field.getFieldType()){
                    case PORTAL1:
                    if(portal1 == null ){
                        portal1 = field;
                    }
                    else{
                        changeFields(field, portal1);
                    }
                    break;
                    case PORTAL2:
                    if(portal2 == null){
                        portal2 = field;
                    }
                    else{
                        changeFields(field, portal2);
                    }
                    break;
                    case PORTAL3:
                    if(portal3 == null){
                        portal3 = field;
                    }
                    else{
                        changeFields(field, portal3);
                    }
                    break;
                    case PORTAL4:
                    if(portal4 == null){
                        portal4 = field;
                    }
                    else{
                        changeFields(field, portal4);
                    }
                    break;
                    case PORTAL5:
                    if(portal5 == null){
                        portal5 = field;
                    }
                    else{
                        changeFields(field, portal5);
                    }
                    break;
                    default:
                    break;
                }
            }
        }
    }
    public void changeFields (Field a, Field b){
        fields[a.x][a.y] = b;
        fields[b.x][b.y] = a;
    }
}