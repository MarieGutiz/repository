/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;

/**
 *
 * @author LovART
 */
public class Wall extends Parent{
    //type of wall
    private ArrayList <String>type;
    private ImageView image;
    private Group group= new Group();
    public Wall(ArrayList<String> type){
        this.type=type;
       // System.out.println("size "+type.size()); //2
        if(type.size() == 1){
          image = new ImageView();  
          image.setImage(Config.getwall(type.get(0))); 
           getChildren().add(image);
           // System.out.println("UNO");
         }
        else{
          for(String t:type){     
            image = new ImageView();  
           // System.out.println("t "+t);
            image.setImage(Config.getwall(t)); 
             group.getChildren().add(image);
           }
            getChildren().add(group);
        }
      
        setMouseTransparent(true);
    }

   
}