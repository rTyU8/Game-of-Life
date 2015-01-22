package GameOfLife;

/** Name: Matthew Romig
  * Course: CSCI 2120
  * Date: 12/1/13
  * 
  * This class holds an array representing the grid
  * It contains the algorithms which will implement
  * the rules of the game. Specifically, each cell 
  * will hold either a 0 or a 1. Cells that hold 0
  * will be colored white, while cells that hold
  * 1 will be colored black. Updates panel which holds
  * copy of grid.
  */
public class Grid { 
  
  private int[][] grid; 
  private GridPanel panel; //Panel on which grid is painted
  
  /** Constructor for initializing grid
    * All cells will hold 0
    */
  public Grid() {
    grid = new int[75][75]; //double array for 75 x 75 grid
    
    for(int i = 0; i < grid.length; i++){ //for each row
      for(int j = 0; j < grid[0].length; j++){ //for each column
        grid[i][j] = 0;
      }
    }
    
    panel = new GridPanel(this); //panel takes this grid
  }
  
  /** Returns copy of current state of grid
    */
  public int[][] getCopyOfGrid(){
    int[][] result = new int[75][75];
    
    for(int i = 0; i < grid.length; i++){
      for(int j = 0; j < grid[0].length; j++){
        result[i][j] = grid[i][j];
      }
    }
    
    return result;
  }
  
  /** Returns a reference to this grid's GridPanel
   */
  public GridPanel getPanel() {
    return panel;
  }
  
  /** Command to change number held by grid
    * Changes to 0 if cell holds 1, and vice
    * versa. Paints cell. Precondition guaranteed by GUI
    * @require  x >= 0 && x <= 74
    *           y >= 0 && y <= 74
    */
  public void updateCell(int x, int y) {
    int cellWidth = panel.getWidth()/75; //cell width
    int cellHeight = panel.getHeight()/75; //cell height
    if (grid[x][y] == 0) {
      panel.selectCell(new java.awt.Point(y,x));
      panel.paintImmediately(cellWidth*y,cellHeight*x,cellWidth,cellHeight);
      grid[x][y] = 1;
    }
    else {
      panel.selectCell(new java.awt.Point(y,x));
      panel.paintImmediately(cellWidth*y,cellHeight*x,cellWidth,cellHeight);
      grid[x][y] = 0;
    }
  }
  
  /** Command to update the entire grid
    * This method allows the game
    * to be played. It is called by a timer every few
    * seconds as long as the game is running.
    * Examines cases for each of the rules
    * Adds cells to be recolored to update list,
    * repaints panel at the end
    */
  public void updateGrid() {
    //store old copy of grid to check against
    int[][] oldGrid = getCopyOfGrid(); 
    
    for(int i = 0; i < grid.length; i++){
      for(int j = 0; j < grid[0].length; j++){
        
        //If old cell held 1. If it has 2 or 3 live neighbors, it lives,
        //else it dies
        if (oldGrid[i][j] == 1) {
          if (liveNeighbors(i,j, oldGrid) == 2 || liveNeighbors(i,j, oldGrid) == 3) {
            panel.addToUpdateList(new java.awt.Point(j,i));
            grid[i][j] = 1;
          }
          else {
            panel.addToUpdateList(new java.awt.Point(j,i));
            grid[i][j] = 0;
          }
        }
        
        /* If cell was empty, it comes to life if it has
         * three live neighbors. liveNeighbors method returns
         * 1 minus the correct number of live neighbors, so 1 must
         * be added when checking*/
        else {
          if (liveNeighbors(i,j, oldGrid) + 1 == 3) {
            panel.addToUpdateList(new java.awt.Point(j,i));
            grid[i][j] = 1;
          }
        }
      }
    }
    panel.repaint(); //Repaints all cells added to update list
  }
  
  /** Private query to return number of live neighbors
    * a particular cell has.
    * @require 0 <= i <= 74
    *          0 <= j <= 74
    */
  private int liveNeighbors(int row, int column, int[][] grid) {
    int count = 0;
    
    /* This loop looks at a 3-by-3 square
     * with the selected cell at the center
     * Increments count if a cell holds a 1
     * This also considers edge cases */
    for(int x = -1; x <= 1; x = x+1){
      for(int y = -1; y <= 1; y = y+1){
        count = count + grid[(75 + row + x)%75][(75 + column + y)%75];
      }
    }
    
    /* The above loop may count one more cell than it should
     * If the cell being checked is live, that cell itself is
     * included in the count. Return count - 1.
     * If the cell is dead, 1 will have to be added to the result
     * of this query (as done above in updateGrid()). */
    return count - 1;
  }
}