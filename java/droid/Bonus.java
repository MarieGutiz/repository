/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author LovART
 */
public class Bonus extends Parent{
    private static Integer type;
    private double animation;
    private Group group= new Group();
    private ImageView image;
    
    public Bonus(Integer type){
        this.type=type;
        image= new ImageView();
        image.setImage(Config.getImages().get(type));
        group.getChildren().add(image);
        getChildren().add(group);
    }
      public boolean animate() {
         animation += (Duration.millis(40).toMillis()/1000);
          // System.out.println("animation "+animation);
        if ( animation >4) {
            //System.out.println("return false "+animation);
            image.setVisible(false);
            return false;
        }

        if (animation > 2) {
          //  System.out.println("animation>2 "+animation);
            image.setOpacity((4000 - (animation*1000)) / 2000);
        }

        return true;
    }
}
