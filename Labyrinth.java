import java.io.*;

public class Labyrinth {
    private int width = 0;
    private int height = 0;
    public Field[][] fields;

    //this reads a text representation and instantiates the Labyrinth with
    //a matrix of Field values. The index 0,0 is in the top left!
    public void readFromTextFile(String filename){
      try {
        // regarding this mess of buffered readers: I really don't know 
        // why it's so cumbersome in Java to read lines from a file
        BufferedReader readerOnlyForWidth = new BufferedReader(new FileReader(filename));
        //calculate width
        width = readerOnlyForWidth.readLine().length();
        readerOnlyForWidth.close();
        System.out.println(width);

        //calculate height
        BufferedReader readerOnlyForHeight = new BufferedReader(new FileReader(filename));
        while (readerOnlyForHeight.readLine() != null) {
          height++;
        }
        readerOnlyForHeight.close();

        //System.out.println(height);

        fields = new Field[width][height];

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        for (int j = 0; j < height; j++){
          String currentLine = reader.readLine();

          for (int i = 0; i < width; i++){
            Field field = new Field(i, j, currentLine.charAt(i));
            fields[i][j] = field;
            
          }
        }
        reader.close();
        //System.out.println(fields[0][1].value);
      } catch (IOException e) {
        e.printStackTrace();
      }
      

    }

    public static void main(String[] args) {
      
      new Labyrinth().readFromTextFile("./blatt3_environment.txt");
    }

    
}