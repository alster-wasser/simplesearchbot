public class Field {
    //set these to protected so the classes in the package can 
    //access them without the hassle of getter methods
    protected int x; 
    protected int y;
    protected FieldType type; 
    protected char value;

    /**
     * Constructor to create a new Field, setting the location and the value.
     * Depending on the value, it also sets a type for the field.
     */
    public Field(int xValue, int yValue, char character) {
        this.x = xValue;
        this.y = yValue;
        this.value = character;
        switch (character){
            case 'x':
                this.type = FieldType.BORDER;
                break;
            case ' ':
                this.type = FieldType.PASSABLE;
                break;
            case 's':
                this.type = FieldType.START;
                break;
            case 'g':
                this.type = FieldType. GOAL;
                break;
            //more than five types of portals seem unlikely
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
                this.type = FieldType.PORTAL;
                break;
            default:
                break;
        }
                  
    }

    public boolean isGoal(){
        if (this.type == FieldType.GOAL){
            return true;
        }
        else {
            return false;
        }
    }
    
    public FieldType getFieldType(){
        return type;
    }

}

