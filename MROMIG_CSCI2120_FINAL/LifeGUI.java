package MROMIG_CSCI2120_FINAL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Name: Matthew Romig
 * ID: 2444120
 * Course: CSCI 2120
 * Date: 12/3/13
 * 
 * GUI for Conway's Game of Life
 * Displays game with 75 x 75 grid
 */
public class LifeGUI extends JFrame {
  
  private GridPanel lifeGrid; //visual representation of grid
  private Grid grid; //holds state of game, responsible for updating grid
  private Timer timer; //timer to update grid while game is played
  private JComboBox<String> initialStates; //select an initial state for the grid
  
  /**
   * Creates new LifeGUI
   */
  public LifeGUI() {
    grid = new Grid();
    lifeGrid = grid.getPanel(); //refers to grid's copy of Panel
    
    setTitle("Game of Life"); //window title
    setSize(700,700); //size of window
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
    //next two lines enable the panel to respond to "s" and "e"
    //buttons and mouse events
    lifeGrid.setFocusable(true); 
    lifeGrid.requestFocusInWindow();
    
    //size of the grid - dimensions are multiples of 75
    lifeGrid.setPreferredSize(new Dimension(600, 600));
    setResizable(false); //cannot be resized
    lifeGrid.setBackground(Color.WHITE);
    lifeGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
    //initialize and set up combo box
    String[] states = {"Select an initial state", "Glider Gun", "Pulsar"};
    initialStates = new JComboBox<String>(states);
    initialStates.setFocusable(false);
    initialStates.setPreferredSize(new Dimension(20,20));
    
    //Timer - updates grid every second once game starts
    //initial delay - 300 ms
    timer = new Timer(300, new ActionListener(){
      public void actionPerformed(ActionEvent e){
        //Grid will be updated and cells will be redrawn
        grid.updateGrid();
      }
    });
    
    /* Allows for user to change the state of the game 
     * by clicking on the grid. Note: points selected are
     * checked to have integer coordinates between
     * 0 and 74, thus assuring preconditions are upheld
     * in GridPanel and Grid 
     */
    lifeGrid.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (timer.isRunning() == false) {
          int cellWidth = lifeGrid.getWidth()/75; //cell width
          int cellHeight = lifeGrid.getHeight()/75; //cell height
          
          int column = (int)e.getX() / cellWidth; //get column clicked
          int row = (int)e.getY() / cellHeight; //get row clicked
          
          //Now take the selected cell and repaint it
          grid.updateCell(row,column); //update array/Panel
        }
      }
    });
    
    /* Mouse Motion Listener for
     * turning multiple cells on/off with one click
     */
    lifeGrid.addMouseMotionListener(new MouseAdapter() {
      private Point lastCellHit; //variable holds last cell dragged over
      public void mouseDragged(MouseEvent e) {
        if (timer.isRunning() == false) {
          int cellWidth = lifeGrid.getWidth()/75; //cell width
          int cellHeight = lifeGrid.getHeight()/75; //cell height
          
          int column = (int)e.getX() / cellWidth; //get column clicked
          int row = (int)e.getY() / cellHeight; //get row clicked
          
          //Check to make sure mouse is within bounds of grid
          //and prevents rapid toggling of cell on and off
          if (0 <= column && column <= 74 && 0 <= row && row <= 74) {
            if (lastCellHit == null || (lastCellHit.x != column || lastCellHit.y != row)) {
              lastCellHit = new Point(column,row);
              grid.updateCell(row,column); //update array/Panel
            }
          }
        }
      }
    });
    
    /* Key listener
     * If "s" is pressed, the timer (and game) starts
     * If "e" is pressed, the timer stops */
    lifeGrid.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
          if (timer.isRunning() == false) {
            initialStates.setEnabled(false); //disable drop-down menu
            lifeGrid.selectCell(null); //disable clicking new cells
            timer.start();
          }
        }
        else if (e.getKeyCode() == KeyEvent.VK_E) {
          if (timer.isRunning() == true) {
            initialStates.setEnabled(true);
            timer.stop();
          }
        }
      }
    });
    
    /* Action Listener for drop-down panel to select
     * two initial states. Glider gun and pulsar are included
     * Draws the initial states on the grid if game is not 
     * being played
     */
    initialStates.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        @SuppressWarnings("unchecked")
        JComboBox<String> combo = (JComboBox<String>)e.getSource();
        if (((String)combo.getSelectedItem()).equals("Glider Gun")) {
          Point[] cells = {new Point(25,25), new Point(25,26), new Point(26,25), 
            new Point(26,26), new Point(35,25), new Point(35,26), 
            new Point(35,27), new Point(36,24), new Point(36,28),
            new Point(37,23), new Point(37,29), new Point(38,23),
            new Point(38,29), new Point(39,26), new Point(40,24),
            new Point(40,28), new Point(41,25), new Point(41,26),
            new Point(41,27), new Point(42,26), new Point(45,25),
            new Point(45,24), new Point(45,23), new Point(46,25),
            new Point(46,24), new Point(46,23), new Point(47,26),
            new Point(47,22), new Point(49, 22), new Point(49,21),
            new Point(49,26), new Point(49,27), new Point(59,23),
            new Point(59,24), new Point(60,23), new Point(60,24)};
          
          //Paint each point on grid - checks to make sure cells aren't
          //already painted in
          for (int i = 0; i < cells.length; i++) {
            if (grid.getCopyOfGrid()[cells[i].y][cells[i].x] == 0) {
              grid.updateCell(cells[i].y,cells[i].x); //update array/Panel
            }
          }
        } 
        else if (((String)combo.getSelectedItem()).equals("Pulsar")) {
          Point[] cells = {new Point(30,40), new Point(31,40), new Point(32,40), 
            new Point(30,45), new Point(31,45), new Point(32,45), 
            new Point(30,47), new Point(31,47), new Point(32,47),
            new Point(30,52), new Point(31,52), new Point(32,52),
            new Point(36,40), new Point(37,40), new Point(38,40),
            new Point(36,45), new Point(37,45), new Point(38,45),
            new Point(36,47), new Point(37,47), new Point(38,47),
            new Point(36,52), new Point(37,52), new Point(38,52),
            new Point(28,42), new Point(28, 43), new Point(28,44),
            new Point(33,42), new Point(33, 43), new Point(33,44),
            new Point(35,42), new Point(35, 43), new Point(35,44),
            new Point(40,42), new Point(40, 43), new Point(40,44),
            new Point(28,48), new Point(28, 49), new Point(28,50),
            new Point(33,48), new Point(33, 49), new Point(33,50),
            new Point(35,48), new Point(35, 49), new Point(35,50),
            new Point(40,48), new Point(40, 49), new Point(40,50)};
          for (int i = 0; i < cells.length; i++) {
            if (grid.getCopyOfGrid()[cells[i].y][cells[i].x] == 0) {
              grid.updateCell(cells[i].y,cells[i].x); //update array/Panel
            }
          }
        } 
      }
    });
    
    //Sets layout of window
    this.setLayout(new BorderLayout());
    this.add(lifeGrid, BorderLayout.CENTER);
    this.add(initialStates, BorderLayout.SOUTH);
    
    pack();
    
    /* this if-statement deals with an issue where 
     * the pack() statement randomly adds 10
     * to width and height - in which
     * case pack() must be called again */
    if(getWidth() > 600){
      pack();
    }    
  }                                                              
}
