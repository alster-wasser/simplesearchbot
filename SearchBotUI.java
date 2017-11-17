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
    private SearchBot bot;
    private JFrame frame;
    private JButton[][] buttons;
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JButton breadthFirst = new JButton("Breitensuche");
    private JButton depthFirst = new JButton("Tiefensuche");

    /**
     * Initializes the SearchbotUI
     */
    public SearchBotUI(SearchBot b)
    {
        bot = b;   
        frame = new JFrame("Searchbot");
        frame.setSize(1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.add(panel1, BorderLayout.PAGE_START);
        pane.add(panel2, BorderLayout.CENTER);
        panel1.add(breadthFirst);
        panel1.add(depthFirst);
        frame.setVisible(true);
        breadthFirst.addActionListener(event ->{
                bot.breadthFirstWithUI();});
        depthFirst.addActionListener(event ->{
                bot.depthFirstWithUI();}); 
    }
    public void sizeLabyrinth(int width, int height)
    {
        GridLayout grid = new GridLayout(height, width);
        panel2.setLayout(grid);
        buttons = new JButton[width][height];
    }
    public void fillLabyrinth(FieldType f, int x, int y)
    {
        JButton button = new JButton();
        button.setSize(20, 20);
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
            case PORTAL1:
            button.setBackground(new Color(0, 97, 255));
            break;
            case PORTAL2:
            button.setBackground(new Color(0, 187, 255));
            break;
            case PORTAL3:
            button.setBackground(new Color(0, 246, 255));
            break;
            case PORTAL4:
            button.setBackground(new Color(131, 0, 255));
            break;
            case PORTAL5:
            button.setBackground(new Color(195, 0, 255));
            break;
            default:
            break;
        }
        panel2.add(button);
        frame.pack();
    }
    public void showGoalpath(SearchPath p){
        for(Field f : p.path){
            JButton button = buttons[f.x][f.y];
            button.setBackground(Color.YELLOW);
            breadthFirst.setEnabled(false);
            depthFirst.setEnabled(false);
        }
    }
    public void markVisitedField(Field field){
        JButton button = buttons[field.x][field.y];
        button.setBackground(Color.WHITE);
    }
}