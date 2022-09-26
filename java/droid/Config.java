
package droi;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.util.Duration;


/**
 *
 * @author LovART
 */
class Config {
   public static  final Duration ANIMATION_TIME =  Duration.millis(40);
   public static String IMAGE_DIR = "images/desktop/";
   public static double SCREEN_WIDTH = 480;
   public static double SCREEN_HEIGHT =640;
   public static Integer CELL_SIZE =  16;
   public static int DROID_SPEED = 4;
 public static int BOTTOM_AREA_HEIGHT =  CELL_SIZE * 3;
public static double GAME_FIELD_WIDTH =  LevelData.WIDTH * CELL_SIZE;
public static double GAME_FIELD_HEIGHT = LevelData.HEIGHT * CELL_SIZE;
public static double GAME_FIELD_X = (SCREEN_WIDTH - GAME_FIELD_WIDTH) / 2;
public static double GAME_FIELD_Y = SCREEN_HEIGHT - GAME_FIELD_HEIGHT -
    BOTTOM_AREA_HEIGHT;
    public static void initialize() {
      //  System.out.println("INITIALIZED");
       for (String imageName : IMAGES_NAMES) {
            Image image = new Image(Config.class.getResourceAsStream(IMAGE_DIR + imageName));
            if (image.isError()) {
                System.out.println("Image " + imageName + " not found");
            }
            images.add(image);
        }
          for (String imageName : IMAGES_ENEMY) {
            final String url = IMAGE_DIR + imageName;
            Image image = new Image(Config.class.getResourceAsStream(url));
            if (image.isError()) {
                System.out.println("Image " + url + " not found");
            }
            images_enemy.add(image);
        }
           for (String imageName : IMAGES_DROID) {
            final String url = IMAGE_DIR + imageName;
            Image image = new Image(Config.class.getResourceAsStream(url));
            if (image.isError()) {
                System.out.println("Image " + url + " not found");
            }
            images_droid.add(image);
        }
             for (String imageName : WALL_IMAGE_NAMES) {//Wall
            final String url = IMAGE_DIR + "wall/" + imageName+".png";
            Image image = new Image(Config.class.getResourceAsStream(url));
            
           if (image.isError()) {
              System.out.println("Image " + url + " not found");
           }else if (wallImages.size() > 0 &&
               (image.getWidth() != CELL_SIZE || image.getHeight() != CELL_SIZE)) {
           // Check size of images only for last ones
           System.out.println("Image {image.url} has invalid size");
        }
           wallImages.add(image);
           ex.put(imageName, image);
        //  System.out.println("ex "+imageName);
        }
          for(String soundName: SOUNDS){          
           try {
               final String url = IMAGE_DIR+soundName; 
             // URL thing= ;
              AudioClip  audio = new AudioClip(Config.class.getResource(url).toURI().toString());
             sounds.add(audio);
           } catch (URISyntaxException ex) {
               System.out.println(ex.getMessage());
               Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
           }
          }
    }
    
 public static final int IMAGE_NORMAL_ENE0=0;//Normal 0
 public static final int IMAGE_NORMAL_ENE1=1;
 public static final int IMAGE_NORMAL_ENE2=2;
 public static final int IMAGE_NORMAL_ENE3=3;
 public static final int IMAGE_NORMAL_ENE4=4;//Normal 1
 public static final int IMAGE_NORMAL_ENE5=5;
 public static final int IMAGE_NORMAL_ENE6=6;
 public static final int IMAGE_NORMAL_ENE7=7;
 public static final int IMAGE_NORMAL_ENE8=8;//Normal 2
 public static final int IMAGE_NORMAL_ENE9=9;
 public static final int IMAGE_NORMAL_ENE10=10;
 public static final int IMAGE_NORMAL_ENE11=11;
 public static final int IMAGE_NORMAL_ENE12=12;//normal3 Green
 public static final int IMAGE_NORMAL_ENE13=13;
 public static final int IMAGE_NORMAL_ENE14=14;
 public static final int IMAGE_NORMAL_ENE15=15;
 public static final int IMAGE_SAFE_ENE0=16;//safe
 public static final int IMAGE_SAFE_ENE1=17;
 public static final int IMAGE_SAFE_ENE2=18;
 public static final int IMAGE_SAFE_ENE3=19;

public static final int IMAGE_DEFEAT_ENE0=20;//go home
public static final int IMAGE_DEFEAT_ENE1=21;
public static final int  IMAGE_DEFEAT_ENE2=22;
public static final int IMAGE_DEFEAT_ENE3=23;

 public static final int IMAGE_BONUS_200 = 0;//bonus
 public static final int IMAGE_BONUS_400 = 1;
 public static final int IMAGE_BONUS_800 = 2;
 public static final int IMAGE_BONUS_1600 =3;
 public static int IMAGE_GET_READY=4;
 public static int IMAGE_GAME_OVER =5;
// public static final int IMAGE_BACKGROUND=4;//del

 public static final int IMAGE_DROID0=0;//droid
 public static final int IMAGE_DROID1=1;
 public static final int IMAGE_DROID2=2;
 public static final int IMAGE_DROIDFRONT0=4;
 public static final int IMAGE_DROIDFRONT1=5;
 public static final int IMAGE_DROIDFRONT2=6;
 public static final int IMAGE_DROIDFRONT3=7;
    //// Sounds
 public static final int SOUNDS_COLOR=0;
 public static final int SOUNDS_GRAY=1;
 public static final int SOUNDS_EAT_GHOST=2;
 public static final int SOUND_EAT_CHOCOLATe=3;//
 public static final int SOUND_ENEMY_EATS_DROID=4;//
 public static final int SOUND_GAME_OVER=5;//
 public static final int SOUNDS_EAT_BONUS=6; 
   private static String[] IMAGES_NAMES= { 
    "bonuses/200.png",
    "bonuses/400.png",
    "bonuses/800.png",
    "bonuses/1600.png",
    "getready.png",
    "gameover.png",};
   
   private static String[] IMAGES_ENEMY={
    "enemy/enemy0/enemy_right.png",//Yellow
    "enemy/enemy0/enemy_left.png",
    "enemy/enemy0/enemy_up.png",
    "enemy/enemy0/enemy_down.png",
    "enemy/enemy1/enemy_right.png",//red
    "enemy/enemy1/enemy_left.png",
    "enemy/enemy1/enemy_up.png",
    "enemy/enemy1/enemy_down.png",
    "enemy/enemy2/enemy_right.png",//blue
    "enemy/enemy2/enemy_left.png",
    "enemy/enemy2/enemy_up.png",
    "enemy/enemy2/enemy_down.png",
    "enemy/enemy3/enemy_right.png",//green
    "enemy/enemy3/enemy_left.png",
    "enemy/enemy3/enemy_up.png",
    "enemy/enemy3/enemy_down.png",
    "enemy/hallow/hallow2_right.png",//hallow safe 16
    "enemy/hallow/hallow2_left.png",
    "enemy/hallow/hallow2_up.png",
    "enemy/hallow/hallow2_down.png",
    "enemy/hallow/hallow0_right.png",//hallow defeat 20
    "enemy/hallow/hallow0_left.png",
    "enemy/hallow/hallow0_up.png",
    "enemy/hallow/hallow0_down.png",
    };
   private static final String[] IMAGES_DROID = new String[] {
        "droid/droid0.png",
        "droid/droid1.png",
        "droid/droid2.png",
        "droid/front0.png",
        "droid/front1.png",
        "droid/front2.png",
        "droid/front3.png",
    };
   private static final String[] SOUNDS= new String[]{
       "sounds/colored.wav",//koutavia se xrowma
       "sounds/greyed.wav",//dogs in gray
       "sounds/KUAK.wav",//troe fantasmatikia ..
       "sounds/OSO.wav",//troe chocolatakia
       "sounds/GLUG.wav",//ton troe droid
       "sounds/END.wav",//otan kanei gameover, teliose paixnidi
       "sounds/UAK.wav",//troe bonus
   };
   private static ObservableList<Image> images = javafx.collections.FXCollections.<Image>observableArrayList();

    public static ObservableList<Image> getImages() {
        return images;
    }
     private static ObservableList<Image> images_enemy = javafx.collections.FXCollections.<Image>observableArrayList();

    public static ObservableList<Image> getimages_enemy() {
        return images_enemy ;
    }
      private static ObservableList<Image> images_droid = javafx.collections.FXCollections.<Image>observableArrayList();

    public static ObservableList<Image> getimages_droid() {
        return images_droid ;
    }
    
    private static ObservableList<Image> wallImages = javafx.collections.FXCollections.<Image>observableArrayList();
    private static ObservableList<AudioClip> sounds  = javafx.collections.FXCollections.<AudioClip>observableArrayList();
   public static ObservableList<AudioClip> getSounds() {
       
        return sounds ;
    } 
   public static  Image getwall(String type){
 //  System.out.println("getWall" +type);
//        Image n = ex.get(type);
//   if (n != null) {
//     System.out.println("image " + n);
//   }
       return (Image) ex.get(type);
   }
// Four digits correspondent to near cell:
//   <top-left><top-right><bottom-right><bottom-left>
static String[] WALL_IMAGE_NAMES = {
    "enemyhome",
    "wall0001",
    "wall0010",
    "wall0011",
    "wall0100",
    "wall0110",
    "wall0111",
    "wall1000",
    "wall1001",
    "wall1011",
    "wall1100",
    "wall1101",
    "wall1110",
    "wall1111",
    "edgehor0",
    "edgehor1",
    "edgehor2",
    "edgehor3",
    "edgevert0",
    "edgevert1",
    "edgevert2",
    "edgevert3",
};

private static Hashtable<String, Image> ex= new Hashtable<String, Image>();
    
}
