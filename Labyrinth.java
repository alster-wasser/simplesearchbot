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

    public Field getFieldAt(int x, int y){
      return fields[x][y];
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
                break;
              default:
                break;
            }
          }
        }
        reader.close();

        
        //@TODO: some proper exception handling
      } catch (IOException e) {
        e.printStackTrace();
      }
      

    }



    
}