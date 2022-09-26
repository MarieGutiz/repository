/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;

/**
 *
 * @author LovART
 */
public class Droid extends Parent{
    private static ImageView image= new ImageView();
    private Direction direction= new Direction();
    private boolean lastRightDirection=true;
    private boolean lastUpDirection=false;
    private Integer animationIndex=0;
    public Droid (){
         getChildren().add(image);
    }
    public void initialize(LevelData level){
         this.setTranslateX(level.droidInitCoord.getCoordX()+2) ;
         this.setTranslateY(level.droidInitCoord.getCoordY());

        direction.setoffsetX(0);
        direction.setoffsetY(0);

      prepareImage(true);
     }

    public void move(LevelData level, Direction keyboardDirection) {
         boolean standing = true;
          for(int i=0;i<Config.DROID_SPEED;i++){
              // Try keyboard direction
              if(! keyboardDirection.isEmpty() && level.canMove(this, keyboardDirection)){
                  direction.setoffsetX(keyboardDirection.getoffsetX());
                  direction.setoffsetY(keyboardDirection.getoffsetY());
              }
              else if(direction.isEmpty() || !level.canMove(this, direction)){
                 
                   direction.setoffsetX(0);
                   direction.setoffsetY(0);
                   break;
              }
              level.move(this, direction);
            if (direction.getoffsetX() != 0) {
                lastRightDirection = direction.getoffsetX() > 0;
            }
             if (direction.getoffsetY() != 0) {//up
                lastUpDirection = direction.getoffsetY() > 0;
            }
            
            standing = false;
          }
        prepareImage(standing);
    }

    private void prepareImage(boolean standing) {
          animationIndex++;
        if (standing) {
            // Prepare transforms if needed
//          if(image.getTransforms().contains(Utils.FLIP_TRANSFORM)){
//                 image.getTransforms().remove(Utils.FLIP_TRANSFORM);
//                 image.getTransforms().remove(Utils.BIG_OFFSET_TRANSFORM);
//                }
            if (animationIndex > 11) {
                animationIndex = 0;
           }
            if (animationIndex >= 9) {//4
              image.setImage(Config.getimages_droid().get(Config.IMAGE_DROIDFRONT0));   
            }
            else{//repeat 4..7
               image.setImage(Config.getimages_droid().get(Config.IMAGE_DROIDFRONT0 + animationIndex / 3));
            }
        } 
        else {
            // Prepare transforms if needed
            if (lastRightDirection) {//right
               // System.out.println("right 1 ");
                if(image.getTransforms().contains(Utils.FLIP_TRANSFORM)||image.getTransforms().contains(Utils.BIG_OFFSET_TRANSFORM)){
                  //  System.out.println("right ");
                 image.getTransforms().remove(Utils.FLIP_TRANSFORM);
                 image.getTransforms().remove(Utils.BIG_OFFSET_TRANSFORM);
                }
            } else {
                  if(image.getTransforms().size() !=2){
                  //    System.out.println("left ");
              image.getTransforms().add(Utils.FLIP_TRANSFORM);
              image.getTransforms().add(Utils.BIG_OFFSET_TRANSFORM);
             
           }
            }
            if (animationIndex > 6 * 3 - 1) {
               animationIndex = 0;
            }

          int index = animationIndex / 3;
       //
            if (index <= 2){
              //  System.out.println("index <= 2 "+index);
                image.setImage(Config.getimages_droid().get(Config.IMAGE_DROID0 + (index)));  
            }
                    
            else{
              //   System.out.println("index else "+index +" : "+(Config.IMAGE_DROID0 + 5 - (index)));
                image.setImage(Config.getimages_droid().get(Config.IMAGE_DROID0 + 5 - (index)));
            }
                
                 }
    }//prepare
    
     // Deterrmines intersection with other node
    public boolean isIntersected(Parent node) {
        return this.getTranslateX()+ Config.CELL_SIZE > node.getTranslateX() &&
            this.getTranslateX() < node.getTranslateX() + Config.CELL_SIZE &&
            this.getTranslateY() + Config.CELL_SIZE > node.getTranslateY() &&
            this.getTranslateY() < node.getTranslateY() + Config.CELL_SIZE;
    }
}
