/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author LovART
 */
public class Enemy extends Parent {
    public static final int STATE_HUNTING = 0;
    public static final int STATE_SAFE = 1;
    public static final int STATE_DEFEAT = 2;
   
    public static final int TYPE_0 = 0;//Yellow
    public static final int TYPE_1 = 1;//Red
    public static final int TYPE_2 = 2;//Blue
    public static final int TYPE_3 = 3;//Green
    
    public static final int MAX_MOVE_COUNT = 100;
    public int [] ENEMY_AGGRESSION = {9, 7, 5, 3}; 
    
    public Integer state;
    private Integer type;
    private Integer animationIndex=0;
    private Integer speed;
    private boolean soundctrl=false;
    private double safetyTime;//After bonus is eated

    // > 0: The enemy is parked in enemy home
    // = 0: The enemy is unparking
    // < 0: The enemy is outside of enemy home
    private double parkingTime;

    // This count increases after every move() invocation
    // It is used for accurate speed calculation
    Integer moveCount=0;
    
    Direction direction = new Direction ();
    
    ImageView image;

    Coord homeEntrance= new Coord();
    
    public Enemy(Integer type){       
        this.type=type;
        this.image = new ImageView();
        getChildren().add(image); 
    }
     public void initialize(LevelData level){
          state = STATE_HUNTING;
          homeEntrance.setCoord(level.enemyHome.getCoordX() * Config.CELL_SIZE + Config.CELL_SIZE * 7 / 2,
                             (level.enemyHome.getCoordY()-1)*Config.CELL_SIZE);//88,16
//          System.out.println("homeEntrance.getCoordX() "+homeEntrance.getCoordX());
//          System.out.println("homeEntrance.getCoordY() "+homeEntrance.getCoordY());
     if (type == TYPE_0) {
           parkingTime = -1;
            setTranslateX(homeEntrance.getCoordX());
            setTranslateY(homeEntrance.getCoordY());
            
            direction.setoffsetX(Utils.random(2) * 2 - 1);
            direction.setoffsetY(0);
        } else {
           if (type == TYPE_2) {
                parkingTime = 0;
            } else if (type == TYPE_1) {
                parkingTime = 4;
            } else {
               // System.out.println("GREEN");
                parkingTime = 8;//type 3=green
           }
            setTranslateX(homeEntrance.getCoordX() + Config.CELL_SIZE * (type - 2) * 2);
            setTranslateY(homeEntrance.getCoordY() + 3 * Config.CELL_SIZE);
           direction.setoffsetX(0);
           direction.setoffsetY(1);
        }
    
        // Calculate speed. In 20 level the fastest enemy has speed DUKE_SPEED * 1.2 //see 4 speed
        Integer coeff = (Main.mainFrame.state)- type;
        
       // double t=Main.mainFrame.state*0.1;      
        if (coeff < 0) {
            coeff = 0;
         }

        if (coeff > 20) {
            coeff = 20;
        }
     // System.out.println("time "+t +" cooef "+coeff);
        speed =(int)(Config.DROID_SPEED * MAX_MOVE_COUNT *
            (Main.mainFrame.d + 1.0 * coeff / MAX_MOVE_COUNT));//check speed
System.out.println("speed "+speed);
        prepareImage();

       //  image.getTransforms().add(Utils.OFFSET_TRANSFORM);
//        transforms = [
//            Utils.OFFSET_TRANSFORM
//        ];
     }

   public void setState(Integer state) {
        if (state == STATE_SAFE) {
            if (this.state == STATE_DEFEAT) {
                return;
            }           
            safetyTime = 8;
            soundctrl=true;
             System.out.println("stelnei safety "+safetyTime);
        }
          
        this.state = state;
    } 
  
    public void move(LevelData level, Droid droid) {
        // Move the enemy
        Integer moveLength = (speed * (moveCount + 1) / MAX_MOVE_COUNT) -
            (speed * moveCount / MAX_MOVE_COUNT);
        
        if (state == STATE_SAFE) {
            moveLength /= 2;
        }

        if (moveCount < MAX_MOVE_COUNT - 1) {
            moveCount++;
        } else {
            moveCount = 0;
        }
//        if(Main.mainFrame.state<3){
//            moveLength--;
//        }
          //  System.out.println("moveLength "+moveLength);
        for (int i=0;i<moveLength;i++) {
            if (parkingTime > 0) {//evaluate parking time
             //   System.out.println("parking time >0");
           
                // The enemy is parked
                setTranslateY(getTranslateY()+direction.getoffsetY());

                if (getTranslateY() == (level.enemyHome.getCoordY() + 1.5) * Config.CELL_SIZE ||
                        getTranslateY() == (level.enemyHome.getCoordY() + 2.5) * Config.CELL_SIZE) {
                   // direction.setoffsetY = -direction.offsetY;
                      direction.setoffsetY(-direction.getoffsetY()); //see
                    //  directionChanged();
                }
            } else if (parkingTime == 0) {//blue
                // The enemy is unparking
//                System.out.println("parking time here <0");
                int offsetX = Utils.sign(homeEntrance.getCoordX() - getTranslateX());
               //  System.out.println("parking time here <0 "+offsetX);
                if (offsetX == 0) {
                setTranslateY(getTranslateY()-1);

                    if (getTranslateY() == homeEntrance.getCoordY()) {
                        parkingTime = -1;

                        direction.setoffsetX(Utils.random(2) * 2 - 1);
                      //  System.out.println("direction "+direction.getoffsetX());
                        direction.setoffsetY(0);

                      //  directionChanged();
                    }
                } else {
                   // translateX += offsetX;
                    setTranslateX(getTranslateX()+offsetX);
                }
            } else if (state == STATE_DEFEAT && getTranslateX() == homeEntrance.getCoordX() &&
                    getTranslateY() >= homeEntrance.getCoordY() &&
                    getTranslateY() < homeEntrance.getCoordY() + Config.CELL_SIZE * 3) {
                // Eated enemy is entering home
                System.out.println("mphke mesa sto spiti");
                setTranslateY(getTranslateY()+1);

                if (getTranslateY() == homeEntrance.getCoordY() + Config.CELL_SIZE * 3) {
                    state = STATE_HUNTING;
                    parkingTime = 0;
                }
            } else {
                // The enemy is outside of enemy home
                if (getTranslateX() % Config.CELL_SIZE == 0 &&
                    getTranslateY() % Config.CELL_SIZE == 0) {
                    // Collect all possible directions except reverse direction
                    ArrayList<Direction> possibleDirections= new ArrayList<Direction>();

                    for (Direction d : Direction.ALL_DIRECTIONS) {
                     // System.out.println("-d.getoffsetX() "+d.getoffsetX());
                    
                        if ((direction.getoffsetX() != -d.getoffsetX() ||
                                direction.getoffsetY() != -d.getoffsetY()) &&
                                level.canMove(this, d)) {
                            
                              if(state == STATE_DEFEAT){//ver
                            System.out.println("all directions-d.getoffsetX() "+d.getoffsetX());
                            System.out.println("all directions-d.getoffsety() "+d.getoffsetY());                  
                                        }
                           
                            possibleDirections.add(d);
                        }
                    }

                    if (possibleDirections.isEmpty()) {
                        // Revert direction
                       
                         if(state == STATE_DEFEAT){//ver
                           System.out.println(" direction.getoffsetX() empty "+ direction.getoffsetX());
                           System.out.println(" direction.getoffsetY() empty "+ direction.getoffsetY());                  
                                        }
                        direction.setoffsetX(-direction.getoffsetX());
                        direction.setoffsetY(-direction.getoffsetY());

                     //   directionChanged();
                    }
                    else {
                        Direction newDirection= null;
                       
                             
                        if ((state == STATE_HUNTING && Utils.random(10) < ENEMY_AGGRESSION[type]) ||
                                (state == STATE_DEFEAT && Utils.random(10) < 9)) {
                            // STATE_HUNTING: The enemy selects the best direction to the droid
                            // STATE_EATED: The enemy selects the best direction to home
                              int destX;
                              int destY;
                               if (state == STATE_HUNTING) {
                                destX = (int) droid.getTranslateX();
                                destY = (int) droid.getTranslateY();
                            } else {//going home                                
                                destX = level.enemyHome.getCoordX() * Config.CELL_SIZE +
                                     Config.CELL_SIZE * 7 / 2;
                              //  System.out.println("going home "+state +" destX "+destX);
                                destY = (level.enemyHome.getCoordY() - 1) * Config.CELL_SIZE;
                               // System.out.println("going home "+state +" destY "+destY);
                            }                   
                            for (Direction d :possibleDirections) {//evaluar x e y, crea un ciclo eterno
                                // Enemies with odd type have Y-axis priority,Impares y prioridad
                                // others - X-axis priority
                                if (d.getoffsetX() == 0 && d.getoffsetY() ==
                                        Utils.sign(destY - getTranslateY())) {
                                    if (newDirection == null || type % 2 != 0) {//type 1,3=red,green
                                      //  System.out.println("posible direction Y "+state+" type "+type);
                                         newDirection = d;
                                        if(state == STATE_DEFEAT){//ver
                                             System.out.println("No movement in X "+d.getoffsetX());
                                             System.out.println("movement in Y "+d.getoffsetY());
                                        }
                                    }
                                }

                                if (d.getoffsetY() == 0 && d.getoffsetX() ==
                                        Utils.sign(destX - getTranslateX())) {
                                    if (newDirection == null || type % 2 == 0) {//type 0,2=yellow,blue
                                      //  System.out.println("posible direction X"+state +" type "+type);
                                        newDirection = d;
                                         if(state == STATE_DEFEAT){//ver
                                            System.out.println("movement in X "+d.getoffsetX());
                                            System.out.println("No movement in Y "+d.getoffsetY());
                                        }
                                    }
                                }
                            }
                        }
                        if (newDirection == null) {
//                                    newDirection = possibleDirections.get(Utils.random(
//                                    possibleDirections.size())); 
                           
                             if(state == STATE_DEFEAT){//ver
                                 if(possibleDirections.size()>1){
                                     System.out.println("possible direction >1");
                                  int destX;
                                  int destY;                             
                                  destX = level.enemyHome.getCoordX() * Config.CELL_SIZE +
                                     Config.CELL_SIZE * 7 / 2;
                                     System.out.println("going home "+state +" destX "+destX);
                                  destY = (level.enemyHome.getCoordY() - 1) * Config.CELL_SIZE;
                                     System.out.println("going home "+state +" destY "+destY);
                                     Direction p;
//                                     p = possibleDirections.get(Utils.random(
//                                       possibleDirections.size()));
                                     for(int k=0; k<possibleDirections.size();k++){
                                         System.out.println("psaxnei");
                                     
                                         p = possibleDirections.get(k);
                                          if(p.getoffsetY()!= -(Utils.sign(destY-getTranslateY() ))){
                                              System.out.println("brhke "+" X "+p.getoffsetX() +" Y "+p.getoffsetY());                                                   
                                            newDirection=p;
                                             }
                                         }
                                          
                                       if(newDirection==null){//it didnt find choose a random one
                                         newDirection = possibleDirections.get(Utils.random(
                                         possibleDirections.size())); 
                                         }
                                     
                                  }
                                 else{
                                  newDirection = possibleDirections.get(0);  
                                 }
//                                            System.out.println("possible direction X "+newDirection.getoffsetX());
//                                            System.out.println("posible direction Y "+newDirection.getoffsetY());
                                        }
                             else{//another states
                                  newDirection = possibleDirections.get(Utils.random(
                                     possibleDirections.size())); 
                             }
                        }

                        direction.setoffsetX(newDirection.getoffsetX());
                        direction.setoffsetY(newDirection.getoffsetY());

                      //  directionChanged();
                    }//else
                }//if
               level.move(this, direction);//move this
            }//else
        }//for
      
        // System.out.println("Parking time "+parkingTime);
        // Update parkingTime
        if (parkingTime > 0) {//8s parking time
            parkingTime -= (Duration.millis(40).toMillis()/1000);
        // System.out.println("Parking time "+parkingTime);//not 4 the 1st
            if (parkingTime < 0) {                
                parkingTime = 0;
            }
        }

//        // Update state
        if (state == STATE_SAFE) {
            //System.out.println("State safe enemy "+safetyTime);//VER safety time
            safetyTime -= (Duration.millis(40).toMillis()/1000);
       
            if (safetyTime < 0) {
              //  System.out.println("pezei amesos ");
                state = STATE_HUNTING;
            }
        }

        if (animationIndex >= 11) {
            animationIndex = 0;
        } else {
            animationIndex++;
        }
        
        prepareImage();
    }
    private void prepareImage() {
        int index = 0;
        if (state == STATE_DEFEAT) {
            index = Config.IMAGE_DEFEAT_ENE0 + animationIndex / 3;
            if(soundctrl){
             //  if(! Config.getSounds().get(Config.SOUNDS_EAT_GHOST).isPlaying()){
                                 Config.getSounds().get(Config.SOUNDS_EAT_GHOST).play();//
                                 soundctrl=false;
                        //      }
            }
        } else if (state == STATE_SAFE && (safetyTime > 3 ||
                ((int)(safetyTime / 200) ) % 2 == 0)) {
          //  System.out.println("image safe " +(Config.IMAGE_SAFE_ENE0 + animationIndex / 3));
            index = Config.IMAGE_SAFE_ENE0 + animationIndex / 3;
        } else {            
          if (type == TYPE_0) {
             if(direction.getoffsetY()==1 && direction.getoffsetX()==0){//down
                  index = Config.IMAGE_NORMAL_ENE3;
                 }
             if(direction.getoffsetY()==-1 && direction.getoffsetX()==0){
                  index = Config.IMAGE_NORMAL_ENE2;
                 }            
             if(direction.getoffsetY()==0 && direction.getoffsetX()==1){
                  index = Config.IMAGE_NORMAL_ENE0;
                 }
              if(direction.getoffsetY()==0 && direction.getoffsetX()==-1){
                  index = Config.IMAGE_NORMAL_ENE1;
               }
            }
          if (type == TYPE_1) {
              // index = Config.IMAGE_NORMAL_ENE4 + animationIndex / 3;
               if(direction.getoffsetY()==1 && direction.getoffsetX()==0){//down
                  index = Config.IMAGE_NORMAL_ENE7;
                 }
             if(direction.getoffsetY()==-1 && direction.getoffsetX()==0){
                  index = Config.IMAGE_NORMAL_ENE6;
                 }
             if(direction.getoffsetY()==0 && direction.getoffsetX()==1){
                  index = Config.IMAGE_NORMAL_ENE4;
                 }
             if(direction.getoffsetY()==0 && direction.getoffsetX()==-1){
                  index = Config.IMAGE_NORMAL_ENE5;
               }

           }
          if (type == TYPE_2) {
             //  index = Config.IMAGE_NORMAL_ENE8 + animationIndex / 3;
              if(direction.getoffsetY()==1 && direction.getoffsetX()==0){//down
                  index = Config.IMAGE_NORMAL_ENE11;
                 }
             if(direction.getoffsetY()==-1 && direction.getoffsetX()==0){
                  index = Config.IMAGE_NORMAL_ENE10;
                 }
             if(direction.getoffsetY()==0 && direction.getoffsetX()==1){
                  index = Config.IMAGE_NORMAL_ENE8;
                 }
               if(direction.getoffsetY()==0 && direction.getoffsetX()==-1){
                  index = Config.IMAGE_NORMAL_ENE9;
               }

           }
          if (type == TYPE_3){
              // index = Config.IMAGE_NORMAL_ENE12 + animationIndex / 3;
             if(direction.getoffsetY()==1 && direction.getoffsetX()==0){//down
                  index = Config.IMAGE_NORMAL_ENE15;
                 }
             if(direction.getoffsetY()==-1 && direction.getoffsetX()==0){
                  index = Config.IMAGE_NORMAL_ENE14;
                 }
             if(direction.getoffsetY()==0 && direction.getoffsetX()==-1){
                  index = Config.IMAGE_NORMAL_ENE13;
                 }
             if(direction.getoffsetY()==0 && direction.getoffsetX()==1){
                  index = Config.IMAGE_NORMAL_ENE12;
                 }
               }

        }
        // System.out.println("image index " +index+" state "+state);
         image.setImage(Config.getimages_enemy().get(index));
    }
    
    
 //  private void directionChanged() {
//        if (direction.getoffsetX() > 0 || direction.getoffsetX()==0) {//right
////            transforms = [
////                Utils.OFFSET_TRANSFORM
////            ];
//            if(image.getTransforms().contains(Utils.FLIP_TRANSFORM)){
//                 image.getTransforms().remove(Utils.FLIP_TRANSFORM);
//                 image.getTransforms().remove(Utils.BIG_OFFSET_TRANSFORM);
//                }
//        }
//
//        if (direction.getoffsetX() < 0) {//left
////            transforms = [
////                Utils.FLIP_TRANSFORM,
////                Utils.BIG_OFFSET_TRANSFORM
////            ];
//             image.getTransforms().add(Utils.FLIP_TRANSFORM);
//             image.getTransforms().add(Utils.BIG_OFFSET_TRANSFORM);
//        }
 //   }
}
