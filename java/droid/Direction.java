/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

/**
 *
 * @author LovART
 */

class Direction {
    private static Direction up,right,down,left;
    
    public static Direction[] ALL_DIRECTIONS={
     up,right,down,left
    };

   private  Integer offsetX;		// Possible values are -1, 0 and 1
   private  Integer offsetY;		// Possible values are -1, 0 and 1
    
   public void setoffsetX(int x){
       this.offsetX=x;
   }
   public void setoffsetY(int y){
       this.offsetY=y;
   }
   public int getoffsetX(){
           return offsetX;       
   }
    public int getoffsetY(){
       return offsetY;
   }
    public boolean isEmpty(){
        return offsetX == 0 && offsetY == 0;
    }
}
