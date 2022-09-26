/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author LovART
 */
public class Dot extends Parent {
    public Boolean small;
    private Integer animationIndex=0;
    private Text text;
    private Integer r;
    private Circle circle;
    private Group mygroup = new Group();;
    
    public Dot(Boolean b){
        this.small=b;
        
         if (small) {
           r=4;
           circle= new Circle(9,8,r);
           circle.setFill(Color.CHOCOLATE);
            mygroup.getChildren().add(circle);
        }
         else{
             text= new Text();
             text.setTranslateX(10);
             text.setTranslateY(23);
             text.setStyle("-fx-font-size: 20;"+"-fx-font-family: Times New Roman Bold;"+"-fx-text-style: Bold;");  
             text.setText("G");
             mygroup.getChildren().add(text);
         }
        
          getChildren().add(mygroup);
         
    }
     public void animate() {
        animationIndex++;

        if (animationIndex >= 20) {
            animationIndex = 0;
        }
     //   System.out.println(text);
        if (animationIndex < 10)
           text.setFill(Color.BLACK); 
        else text.setFill(Color.RED);
    }
    
}
