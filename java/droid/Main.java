/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;



import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;


/**
 *
 * @author LovART
 */
public final class Main extends Application {

    public static MainFrame mainFrame;
// private static MediaPlayer audioMediaPlayer;
//    private static URL thing;

    public static MainFrame getMainFrame() {
        return mainFrame;
    }
    
    @Override
    public void start(Stage stage) {       
            Config.initialize();
            Group root = new Group();
            mainFrame = new MainFrame(root);
            stage.setTitle("Droid");
            stage.setResizable(false);
            stage.setWidth(Config.SCREEN_WIDTH);
            stage.setHeight(Config.SCREEN_HEIGHT);
     
            Scene scene = new Scene(root);
                LinearGradient gradient1 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop[] {
                new Stop(0.0, Color.web("#707070")),
                new Stop(0.2, Color.web("#AAAAAA")),
                new Stop(0.8, Color.web("#AAAAAA")),
                new Stop(1.0, Color.web("#707070")),
                
            });
            scene.setFill(gradient1);//change
            stage.setScene(scene);
            stage.show();
              mainFrame.startGame();             // Run game
       
    }
   
    public static void main(String[] args) {
        Application.launch(args);
    }

    public class MainFrame {
        // Instance of scene root node

        public Group root;
        private Level level;
        public int lifeCount;
        public int score;
        public int highScore = 10000;

        private MainFrame(Group root) {
            this.root = root;
        }

        public void startGame() {
            lifeCount = 2;
            score = 0;
            changeState(1);
        }
        
        public int state;
        public double d=0.4;
        public void changeState(int newState) {
            this.state = newState;
            if(this.state==1){
                d=0.4;
            }
            if(this.state%2!=0){
                d=d+0.1;
                if(d > 1.0){
                   d=1.0;
                }
            }
            
            if (level != null) {            
                level.stop();                  
               root.getChildren().remove(level); level=null;
            }
            if ( state > 0) {
                level = new Level();
                level.level=LevelData.getLevelData((state - 1) %
                       LevelData.getLevelsCount() + 1);
              //  Level.level=LevelData.getLevelData(this.state);
                 root.getChildren().add(level);
                 level.start();
            }
        }
    }
}
