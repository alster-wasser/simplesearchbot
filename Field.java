import java.util.ArrayList;

public class Field {
    //set these to protected so the classes in the package can
    //access them without the hassle of getter methods
    protected int x;
    protected int y;
    protected FieldType type;
    protected char value;
    protected boolean visited;
    protected int costToGetHere;
    protected int costToGetToGoal;
    protected Field parent;

    protected ArrayList<Integer> goalDistance;


    /**
     * Constructor to create a new Field, setting the location and the value.
     * Depending on the value, it also sets a type for the field.
     */
    public Field(int xValue, int yValue, char character) {
        this.x = xValue;
        this.y = yValue;
        this.value = character;
        costToGetHere = 0;
        costToGetToGoal = 0;
        goalDistance = new ArrayList<>();
        parent = null;

        switch (character){
            case 'x':
                this.type = FieldType.BORDER;
                break;
            case ' ':
                this.type = FieldType.NORMAL;
                break;
            case 's':
                this.type = FieldType.START;
                this.visited = true;
                break;
            case 'g':
                this.type = FieldType. GOAL;
                break;
            //more than five types of portals seem unlikely
            case '1':
                this.type = FieldType.PORTAL1;
                break;
            case '2':
                this.type = FieldType.PORTAL2;
                break;
            case '3':
                this.type = FieldType.PORTAL3;
                break;
            case '4':
                this.type = FieldType.PORTAL4;
                break;
            case '5':
                this.type = FieldType.PORTAL5;
                break;
            default:
                break;
        }

    }

    public String toString(){
        return "(" + x + "," + y + ")";
    }

    public boolean isGoal(){
        return this.type == FieldType.GOAL;
    }
    public FieldType getFieldType(){
        return type;
    }
}

    
    