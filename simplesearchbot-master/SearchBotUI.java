import javax.swing.*;
import java.awt.*;
/**
 * User interface for the Searchbot.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SearchBotUI
{
    private JFrame frame;
    private JButton[][] buttons;

    /**
     * Initializes the SearchbotUI
     */
    public SearchBotUI()
    {
            frame = new JFrame("Searchbot");
            frame.setSize(500,400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);   
    }
    public void sizeLabyrinth(int width, int height)
    {
        GridLayout grid = new GridLayout(height, width);
        //grid.ComponentOrientation(horizontal);
        frame.setLayout(grid);
        buttons = new JButton[width][height];
    }
    public void fillLabyrinth(FieldType f, int x, int y)
    {
        JButton button = new JButton();
        buttons[x][y] = button;
        
        switch (f){
            case BORDER:
            button.setBackground(Color.BLACK);
            break;
            case START:
            button.setBackground(Color.GREEN);
            break;
            case GOAL:
            button.setBackground(Color.YELLOW);
            break;
            case PORTAL:
            button.setBackground(Color.BLUE);
            break;
            default:
            break;
        }
        frame.add(button);
        frame.pack();
    }
}
