/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

import droi.Main.MainFrame;
import java.util.ArrayList;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author LovART
 */

public class Level extends Parent{
    private static final MainFrame mainFrame = Main.getMainFrame();
    
    private static int[] BONUS_SCORE = {200, 400, 800, 1600};
    private int WAIT_FOR_KEY=0;
    private int STATE_GET_READY = 1;
    private int STATE_PLAY = 2;
    private int STATE_GAME_OVER = 3;
    
    public static LevelData level;
    private static Droid droid = new Droid();
    private static Direction keyboardDirection = new Direction();
    private boolean waitForStart=true;
    private ArrayList<Enemy> enemies= new ArrayList<>();    
    //texts
    private static Text levelCaption = new Text();
    private static Text scoreCaption= new Text();
    private static Text scoreValue = new Text();
    private static Text highScoreCaption= new Text();
    private static Text highScoreValue= new Text();
    //image
    private static ImageView message= new ImageView();
    private static Group messageBox= new Group();
    Text txt = new Text();//messageBox text
    //
    private int state=WAIT_FOR_KEY;
    private double stateTime;
    private ArrayList<Dot> animatedDots= new ArrayList<>();
    private Integer bonusIndex;
    public ArrayList<Bonus> bonuses= new ArrayList<>();
    private Integer dotCount;
    private ArrayList<ImageView> lives= new ArrayList<>();
    private Group group;
    private Group group1= new Group();//droid
    private Group group2 = new Group();    
   //TimeLine
    private Timeline timeline;
    public Level() {
        group = new Group();
        getChildren().add(group);
    }

   public void stop() {
     //  System.out.println("bonuses "+bonuses.size());
       if(bonuses.size()>0){
         for(Bonus b: bonuses){
                b.setVisible(false);
                 mainFrame.root.getChildren().remove(b);
              //   bonuses.remove(b);
             //  break;           
               }  
       }
       // System.out.println("lives "+lives.size());
          if(lives.size()>0){
              for(ImageView life: lives){
                  System.out.println("remove "+life);
                  life.setVisible(false);            
                   mainFrame.root.getChildren().remove(life);
                 //  lives.remove(life);
                 //  break;
              }
          }
                timeline.stop();
    }

    public void start() {
        System.out.println("restart game get lives size "+lives.size());
//        if(lives.size()==0 ){
//            
//        }
        initContent();
    }

    private void initContent() {
        
         ImageView background = new ImageView();
         background.setFocusTraversable(true);
        // background.setImage(Config.getImages().get(Config.IMAGE_BACKGROUND));
         background.setFitWidth(Config.SCREEN_WIDTH);
         background.setFitHeight(Config.SCREEN_HEIGHT);
         background.setOnKeyPressed(new EventHandler<KeyEvent>(){

            public void handle(KeyEvent t) {
                    if (state == STATE_GAME_OVER) {
               //mainFrame.startGame();//Gameover
                        System.out.println("End");
                        mainFrame.startGame();//GameOver
                
                } else if (state == WAIT_FOR_KEY||state==STATE_PLAY) {///start game
                    updateKeyboardDirection(t.getCode(), true);
                }
              }
               
        });
         Group Content= new Group();
         group.getChildren().add(Content);
         Content.getChildren().add(background);
         levelCaption.setText("LEVEL "+ mainFrame.state);
         levelCaption.setTranslateX(Config.SCREEN_WIDTH -103);
         levelCaption.setTranslateY(((Config.GAME_FIELD_Y-20) / 6)+20);
         levelCaption.setStyle("-fx-font-size: 18;"+" -fx-text-fill: #000000;"+"-fx-font-family: Arial;"+"-fx-text-style: Bold;");
         scoreCaption.setText("YOUR SCORE: ");
         scoreCaption.setTranslateX((Config.SCREEN_WIDTH / 8)-40);
         scoreCaption.setTranslateY((Config.GAME_FIELD_Y-20) / 6 + (Config.GAME_FIELD_Y-20) / 4);
         scoreCaption.setStyle(levelCaption.getStyle());
         
         scoreValue.setTranslateX((Config.SCREEN_WIDTH * 2 / 3)-150);
         scoreValue.setTranslateY(scoreCaption.getTranslateY());
         scoreValue.setStyle(levelCaption.getStyle());
         
         highScoreCaption.setText("HIGH SCORE: ");
         highScoreCaption.setTranslateX((Config.SCREEN_WIDTH / 8)-32);
         highScoreCaption.setTranslateY((Config.GAME_FIELD_Y-20) / 6 + (Config.GAME_FIELD_Y-20) / 2);
         highScoreCaption.setStyle(levelCaption.getStyle());
         
         highScoreValue.setText(mainFrame.highScore+"");
         highScoreValue.setTranslateX(scoreValue.getTranslateX());
         highScoreValue.setTranslateY(highScoreCaption.getTranslateY());
         highScoreValue.setStyle(levelCaption.getStyle());
         
        // state = STATE_GET_READY;
         message.setImage(Config.getImages().get(Config.IMAGE_GET_READY));
         message.setOpacity(0);
         message.setTranslateX(Config.GAME_FIELD_X + (Config.GAME_FIELD_WIDTH -
            message.getImage().getWidth()) / 2);//unfinished
         message.setTranslateY((Config.GAME_FIELD_Y-20) +
           (( (level.enemyHome.getCoordY() + LevelData.ENEMY_HOME_HEIGHT) * Config.CELL_SIZE) +
            ((Config.CELL_SIZE - message.getImage().getHeight()) / 2))); 
         initLevel();//
         initTimeline();//timeline
         ///messageBox
         Rectangle rect = new Rectangle(112,368,304,80);
         rect.setStroke(Color.WHITE);
         rect.setStrokeWidth(5);
         rect.setFill(Color.MAGENTA);
         rect.setOpacity(0.4);
         rect.setArcWidth(20);
         rect.setArcHeight(20);
         
         
         txt.setTranslateX(128);
         txt.setTranslateY(416);
         txt.setFill(Color.WHITE);
         txt.setText("");
         txt.setStyle("-fx-font-size: 15;"); 
         txt.setText("         PRESS ANY KEY TO START!");//15
         
         messageBox.setTranslateX(-24);
         messageBox.getChildren().addAll(rect,txt);
         messageBox.setVisible(true);        
        group.getChildren().addAll(levelCaption,scoreCaption,scoreValue,highScoreCaption,highScoreValue,message,messageBox);
   
        dotCount =  level.dots.size();
        
        timeline.play();  //
        background.requestFocus();
        updateScore(0);
        updateLives();
         startGame();//
    }
    private void updateKeyboardDirection(KeyCode code, boolean  pressed) {
      
   //  System.out.println("start" +code.getName());
       if ( waitForStart &&state==WAIT_FOR_KEY && code== KeyCode.getKeyCode(code.getName())) {
            messageBox.setVisible(false);
            state = STATE_GET_READY;
            stateTime = 3;
            message.setOpacity(1);
       }
      if(!waitForStart&&state==STATE_PLAY){
           int offsetX = 0;
           int offsetY = 0;
           if (code == KeyCode.UP) {
            offsetY = -1;
        } else if (code == KeyCode.RIGHT) {
            offsetX = 1;
        } else if (code == KeyCode.DOWN) {
            offsetY = 1;
        } else if (code == KeyCode.LEFT) {
            offsetX = -1;
        }
       else if(code ==  KeyCode.PAUSE || code==KeyCode.P || code==KeyCode.SPACE){
             System.out.println("pause" +timeline.getStatus());
           if (timeline.getStatus() ==  Status.PAUSED){
             resumeGame();}
           else{
             pauseGame();
           }
        }

        if (pressed) {           
         keyboardDirection.setoffsetX(offsetX);
         keyboardDirection.setoffsetY(offsetY);
        } else {//den mpenei mesa pote
            if (offsetX != 0) {                
               keyboardDirection.setoffsetX(0);
            }
            if (offsetY != 0) {
               keyboardDirection.setoffsetY(0);
            }
        }
      }//state play        
            }//function

    private void resumeGame() {
         txt.setText("");
         txt.setStyle("-fx-font-size: 15;"); 
         txt.setText("         PRESS ANY KEY TO START!");//15
        messageBox.setVisible(false);
        timeline.play();
    }

    private void pauseGame() {
        txt.setText("");
          txt.setStyle("-fx-font-size: 13;"); 
          txt.setText("PRESS 'PAUSE', 'SPACE' or 'P' KEY TO RESUME");//13
         messageBox.setVisible(true);
        timeline.pause();
    }
     private void updateScore(int inc) {
        int oldScore =  mainFrame.score;

         mainFrame.score += inc;
         scoreValue.setText(mainFrame.score+"");
     //System.out.println("oldScore "+(oldScore / 10000)+" mainFrame.score "+(mainFrame.score / 10000));
        if (oldScore / 10000 !=  mainFrame.score / 10000) {
            mainFrame.lifeCount++;
          System.out.println("+ life");
            updateLives();
        }
    }

    private void updateLives() {
          // Remove lives
       // System.out.println("mainFrame.lifeCount "+(lives.size()>mainFrame.lifeCount) +" lifecounter "+lives.size()+" mainFrame.lifeCount"+mainFrame.lifeCount);
        while(lives.size() >mainFrame.lifeCount){
            ImageView life = lives.get(lives.size() - 1);
            life.setVisible(false);            
            mainFrame.root.getChildren().remove(life);
            lives.remove(life);
            break;
        }
        
     //   System.out.println("counter "+lives.size() +" lifecount "+mainFrame.lifeCount);
        for(int i=lives.size();i<java.lang.Math.min(mainFrame.lifeCount,9);i++){
           ImageView life= new ImageView();
           life.setImage(Config.getimages_droid().get(Config.IMAGE_DROIDFRONT0));
           life.setTranslateX(Config.GAME_FIELD_X + life.getImage().getWidth() * i); 
           life.setTranslateY((Config.SCREEN_HEIGHT - Config.BOTTOM_AREA_HEIGHT) +
                 ((Config.BOTTOM_AREA_HEIGHT - life.getImage().getHeight())/ 2)-20); 
           lives.add(life);
                 mainFrame.root.getChildren().add(life);             
        }
    }
    private void initTimeline() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
       // timeline.setCycleCount(state);
         KeyFrame kf = new KeyFrame(Duration.millis(38), new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                for (Dot dot: animatedDots) {
                        dot.animate();
                    }
                 // Animate bonuses
                for(Bonus b: bonuses){
                    if(!b.animate()){   
                        b.setVisible(false);
                        mainFrame.root.getChildren().remove(b);
                         bonuses.remove(b);
                        break;
                   }
               }
                if (state == STATE_GET_READY) {                   
                         stateTime -= (Duration.millis(40).toMillis()/1000);
                        if (stateTime < 1) {
                            message.setOpacity (stateTime);
                        }
                        if (stateTime < 0) {
                            state = STATE_PLAY;
                            message.setVisible(false);
                            waitForStart = false;
                        }

                        return;
                    }//ready
                 if (state == STATE_GAME_OVER) {                     
                      stateTime -= (Duration.millis(40).toMillis()/1000);
                        if (stateTime < 0) {//-1000
                           mainFrame.startGame();//GameOver
                        }
                        return;
                    }//gameover
                  if(waitForStart==false && state == STATE_PLAY){
                       // Process droid
                     droid.move(level, keyboardDirection);
                    // Collect dot
                    int x=(int) ((droid.getTranslateX() + Config.CELL_SIZE /2)/ Config.CELL_SIZE) ;
                    int y=(int) (( droid.getTranslateY()+ Config.CELL_SIZE /2) / Config.CELL_SIZE);
                    int index = x + y* LevelData.WIDTH;
//                     System.out.println("x "+x);//<-- 12.11..
//                     System.out.println("y "+y);//25                    
                   Parent node = (Parent) level.levelData.elementAt(index) ;
                    if (node instanceof Dot) {
                       level.levelData.setElementAt(null, index);
                        if (((Dot)node).small) {
                            updateScore(10);
                            //troe mikra chocolatakia
                            if(! Config.getSounds().get(Config.SOUND_EAT_CHOCOLATe).isPlaying()){
                            Config.getSounds().get(Config.SOUND_EAT_CHOCOLATe).play();//
                         }
                       } else {
                            bonusIndex = 0;
                           updateScore(50);
                           //eat big bonus
                                 if(! Config.getSounds().get(Config.SOUNDS_EAT_BONUS).isPlaying()){
                                Config.getSounds().get(Config.SOUNDS_EAT_BONUS).play();//
                         } // Mark enemies as safe
                            for (Enemy enemy: enemies) {
                               enemy.setState(Enemy.STATE_SAFE);//Droid can eat, gray dogs
                            }
                       }
                        node.setVisible(false);
                        animatedDots.remove((Dot)node);
                        dotCount--;
                        if (dotCount == 0) {
                            // Go to the next level
                            System.out.println("Next level state "+mainFrame.state++);                            
                            mainFrame.changeState(mainFrame.state++);
                            return;
                        }
                    }

                  // Move enemy
                    for (Enemy enemy : enemies) {
                         enemy.move(level, droid);
                         if(enemy.state==Enemy.STATE_SAFE){
                            //play gray song 
                         if(! Config.getSounds().get(Config.SOUNDS_GRAY).isPlaying()){
                          Config.getSounds().get(Config.SOUNDS_GRAY).play();//
                         }
                         }
                         else{//colored
                            if(! Config.getSounds().get(Config.SOUNDS_COLOR).isPlaying()){
                            Config.getSounds().get(Config.SOUNDS_COLOR).play();//
                         } 
                         }
                        if (droid.isIntersected(enemy)) {
                            if (enemy.state == Enemy.STATE_HUNTING) {//catch droid
                                // Droid is catched
                                Main.mainFrame.lifeCount--;
                                 if(! Config.getSounds().get(Config.SOUND_ENEMY_EATS_DROID).isPlaying()){
                                Config.getSounds().get(Config.SOUND_ENEMY_EATS_DROID).play();//
                         }
                                if(! Config.getSounds().get(Config.SOUND_GAME_OVER).isPlaying()){
                                Config.getSounds().get(Config.SOUND_GAME_OVER).play();//
                         }
                                if (Main.mainFrame.lifeCount < 0) {                                     
                                    gameOver();
                                } else {
                                    updateLives();

                                    startGame();
                                }

                                return;
                            } 
                            
                            else if (enemy.state == Enemy.STATE_SAFE) {//win bonus
                                 //eat ghost
//                                 if(! Config.getSounds().get(Config.SOUNDS_EAT_GHOST).isPlaying()){
//                                 Config.getSounds().get(Config.SOUNDS_EAT_GHOST).play();//
//                                 }
                                enemy.setState(Enemy.STATE_DEFEAT);//ver state defeat
                                updateScore(BONUS_SCORE[bonusIndex]);
                                Bonus bonus= new Bonus(Config.IMAGE_BONUS_200 + bonusIndex);
                                bonus.setTranslateX(enemy.getTranslateX());
                                bonus.setTranslateY(enemy.getTranslateY());
                                bonuses.add(bonus);
                                mainFrame.root.getChildren().add(bonus);//to work
                                bonusIndex++;
                            return;
                                //mainFrame.changeState(mainFrame.state++);
//                                Main.mainFrame.lifeCount++;
//                               updateLives();
//                            return;
                            }
                        }
                    }

                  }//state play
            }//evt             
         });
         timeline.getKeyFrames().add(kf);
    }
      private void gameOver() {
          stateTime=3;
            state = STATE_GAME_OVER;
            message.setImage(Config.getImages().get(Config.IMAGE_GAME_OVER)); 
            message.setOpacity(1);
            message.setVisible(true);
            
        // Update high scores
        if (Main.mainFrame.score > Main.mainFrame.highScore) {
            Main.mainFrame.highScore = Main.mainFrame.score;
        } 
            }
    private void initLevel() {
        //Get Maze
       for(Parent p :level.maze){
           group.getChildren().add(p);
           p.setTranslateX(p.getTranslateX()+ Config.GAME_FIELD_X+2) ;
           p.setTranslateY(p.getTranslateY()+ Config.GAME_FIELD_Y-20) ;
        }
       /* int m=0;
        for(Parent p :level.maze){
            if(m<10){
                group.getChildren().add(p);
                System.out.println("p.getTranslateX() "+p.getTranslateX()+ " p.getTranslateY() "+p.getTranslateY());
           p.setTranslateX(p.getTranslateX()+ Config.GAME_FIELD_X+2) ;
           p.setTranslateY(p.getTranslateY()+ Config.GAME_FIELD_Y-20) ;
            }
           
           m++;
        }*/
        //Get Dots
        int k=0,s=0;
        for(Dot p:level.dots){
            group.getChildren().add(p);
              p.setTranslateX(p.getTranslateX()+ Config.GAME_FIELD_X+2) ;
              p.setTranslateY(p.getTranslateY()+ Config.GAME_FIELD_Y-20) ;
              if(!p.small){
                  animatedDots.add(level.dots.get(k));
                  s++;
              }
              k++;
        }
        //Direction
         Direction.ALL_DIRECTIONS[0]= new Direction();
         Direction.ALL_DIRECTIONS[0].setoffsetX(0);//up
         Direction.ALL_DIRECTIONS[0].setoffsetY(-1);         
        
         Direction.ALL_DIRECTIONS[1]= new Direction();
         Direction.ALL_DIRECTIONS[1].setoffsetX(1);//right
         Direction.ALL_DIRECTIONS[1].setoffsetY(0);
         
         Direction.ALL_DIRECTIONS[2]= new Direction();
         Direction.ALL_DIRECTIONS[2].setoffsetY(1);//down
         Direction.ALL_DIRECTIONS[2].setoffsetX(0);
         
         Direction.ALL_DIRECTIONS[3]= new Direction();
         Direction.ALL_DIRECTIONS[3].setoffsetX(-1);//left
         Direction.ALL_DIRECTIONS[3].setoffsetY(0);          
         //enemies         
       Enemy e0= new Enemy(Enemy.TYPE_0);
       Enemy e1= new Enemy(Enemy.TYPE_1);
       Enemy e2= new Enemy(Enemy.TYPE_2); 
       Enemy e3= new Enemy(Enemy.TYPE_3);
        enemies.add(e0);
        enemies.add(e1);//red
        enemies.add(e2);//blue
        enemies.add(e3);
      group1.setTranslateX(Config.GAME_FIELD_X-5);//droid
      group1.setTranslateY(Config.GAME_FIELD_Y-27);
      group2.setTranslateX(Config.GAME_FIELD_X-6);
      group2.setTranslateY(Config.GAME_FIELD_Y-27);
      group.getChildren().add(group1);
      group.getChildren().add(group2);
       for(Enemy e:enemies){
           group2.getChildren().add(e);
       }
       //droid
       group1.getChildren().add(droid);     
    }
    private void startGame() {
        droid.initialize(level);
        keyboardDirection.setoffsetX(0);
        keyboardDirection.setoffsetY(0); 
        
         for (Enemy enemy: enemies) {
            enemy.initialize(level);
        }
    }
    
}
