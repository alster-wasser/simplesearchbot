import java.io.*;

public class Labyrinth {
    private int width = 0;
    private int height = 0;
    private Field[][] fields;
    private Field startField;
    private Field goalField;

    public Field getStartField(){
      return startField;
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

        //calculate width
        BufferedReader readerOnlyForWidth = new BufferedReader(new FileReader(filename));
        width = readerOnlyForWidth.readLine().length();
        readerOnlyForWidth.close();
        //System.out.println(width);

        //calculate height
        BufferedReader readerOnlyForHeight = new BufferedReader(new FileReader(filename));
        while (readerOnlyForHeight.readLine() != null) {
          height++;
        }
        readerOnlyForHeight.close();
        //System.out.println(height);


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
                break;
              default:
                break;
            }
          }
        }
        reader.close();
        //System.out.println(fields[0][1].value); //prints the top, second from left value
        
        //@TODO: some proper exception handling
      } catch (IOException e) {
        e.printStackTrace();
      }
      

    }

    public static void main(String[] args) {
      //just checking if start and goal have been instantiated properly
      Labyrinth testLabyrinth = new Labyrinth("./blatt3_environment.txt");
      System.out.println("start: " + testLabyrinth.getStartField());
      System.out.println("goal: " + testLabyrinth.getGoalField());
    }

    
}