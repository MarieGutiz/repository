/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

/**
 *
 * @author LovART
 */
public class Coord {
   private int x;
   private int y;

  public Coord(){
        
    }
   public void setCoord(int x, int y) {
        this.x=x;
        this.y=y;
    }
   public int getCoordX(){
       return x;
   }
   public int getCoordY(){
       return y;
   }
}
