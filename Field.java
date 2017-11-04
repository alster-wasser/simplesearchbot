public class Field {
    private int x; 
    private int y;

    //a Field can only be one of these types. "passable" means a white field
    private enum FieldType {BORDER, PASSABLE, START, GOAL, FUEL}
    private FieldType type; 
    private char value;

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
            //more than five types of fuel seem unlikely
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
                this.type = FieldType.FUEL;
                break;
            default:
                break;
        }
       
            
    }
}

