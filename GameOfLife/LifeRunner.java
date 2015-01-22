package GameOfLife;

/** Name: Matthew Romig
  * Course: CSCI 2120
  * Date: 12/3/13
  * 
  * Runner for Conway's Game of Life
  */
public class LifeRunner {
  
  public static void main(String args[]) {
    /* Create and display the window */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new LifeGUI().setVisible(true);
      }
    });
  }            
} 