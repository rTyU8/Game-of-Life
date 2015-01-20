package MROMIG_CSCI2120_FINAL;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/** Name: Matthew Romig
  * ID: 2444120
  * Course: CSCI 2120
  * Date: 12/3/13
  * 
  * This class extends JPanel
  * and will serve as a visual representation
  * of the Grid
  */
public class GridPanel extends JPanel {
  private Point selectedCell;
  private Grid grid;
  private ArrayList<Point> cellUpdateList;
  
  public GridPanel(Grid grid)
  {
    this.grid = grid; //access array representation of the grid
    setVisible(true); 
    
    //This list holds the points which will need to be
    //redrawn every iteration of the game during play
    cellUpdateList = new ArrayList<Point>();  
  }
  
  /** Command to change currently selected cell
    * The selected cell will be redrawn
    * @require  point.x >= 0 && point.x <= 74
    *           point.y >= 0 && point.y <= 74
    */
  public void selectCell(Point point) {
    selectedCell = point;
  }
  
  /** Add to cellUpdateList with a point to be redrawn
    * while game is in play 
    * @require  point.x >= 0 && point.x <= 74
    *           point.y >= 0 && point.y <= 74 
    */
  public void addToUpdateList(Point point) {
    cellUpdateList.add(point);
  }
  
  @Override
  /** Overrides paintComponent for JPanel
    * Draws the grid, colors in cells when they
    * are clicked, and updates the grid 
    * every iteration of the game */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    int width = this.getWidth();
    int height = this.getHeight();
    int xOffset; //used to draw lines for grid
    int yOffset;
    int columns = width/75;
    int rows = height/75;
    
    //draws vertical lines
    for(int i = 1; i <= 74; i = i+1) {
      xOffset = i*(columns);
      g.drawLine(xOffset, 0, xOffset, getHeight());
    }
    
    //draws horizontal lines
    for(int j = 1; j <= 74; j = j+1) {
      yOffset = j*(rows);
      g.drawLine(0, yOffset, getWidth(), yOffset);
    }
    
    /* Colors in a cell that is clicked
     * Only done when game is not being played
     * Note:selectedCell is only null before any cell is clicked
     */
    if (selectedCell != null) {
      if (grid.getCopyOfGrid()[selectedCell.y][selectedCell.x] == 0) {
        g.setColor(Color.BLACK);
        g.fillRect(columns*selectedCell.x,rows*selectedCell.y,rows,columns);
      }
      else {
        g.setColor(Color.WHITE);
        g.fillRect(columns*selectedCell.x,rows*selectedCell.y,rows,columns);
        g.setColor(Color.BLACK);
        g.drawRect(columns*selectedCell.x,rows*selectedCell.y,rows,columns);
      }
    }
    
    /** Updates board every turn/move of the game
      * Looks at each cell in cellUpdateList
      * and recolors it depending on its current color
      * Note: this is only done when cellUpdateList is 
      * non-empty, and it is non-empty only while the 
      * game is being played
      */
    if (cellUpdateList.size() > 0) {
      for (int i = 0; i < cellUpdateList.size(); i = i + 1) {
        int xValue = cellUpdateList.get(i).x;
        int yValue = cellUpdateList.get(i).y;
        if (grid.getCopyOfGrid()[yValue][xValue] == 0) {
          g.setColor(Color.WHITE);
          g.fillRect(columns*xValue,rows*yValue,rows,columns);
          g.setColor(Color.BLACK);
          g.drawRect(columns*xValue,rows*yValue,rows,columns);
        }
        else {
          g.setColor(Color.BLACK);
          g.fillRect(columns*xValue,rows*yValue,rows,columns);
        }
      }
      cellUpdateList.clear(); //clears list - done after every iteration
    }
    
  }
}
