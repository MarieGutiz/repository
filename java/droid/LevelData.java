/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javafx.scene.Parent;

/**
 *
 * @author LovART
 */
public class LevelData {
    public static int ENEMY_HOME_WIDTH = 8;
    public static int ENEMY_HOME_HEIGHT = 5;
    
    public static int WIDTH=28;
    public static int HEIGHT=31;//-0..31
    
   public static Integer levelNumber=-1;
   public static ArrayList<Wall> maze= new ArrayList<>();
   public static ArrayList<Dot> dots= new ArrayList<>();
   public static Coord droidInitCoord;
   public static Coord enemyHome;
   public static Vector levelData= new Vector();
    
   
   public static boolean canMove(Parent node, Direction direction){
        if (node.getTranslateX() % Config.CELL_SIZE != 0) {
            //System.out.println("lveldataX "+node.getTranslateX() % Config.CELL_SIZE);
            return direction.getoffsetY()==0;
        }

        if (node.getTranslateY() % Config.CELL_SIZE != 0) {
//            System.out.println("node.getTranslateY() "+node.getTranslateY());
//             System.out.println("lveldataY mod"+node.getTranslateY() % 16);
          return direction.getoffsetX() == 0;
        }
        int x = (int) ((node.getTranslateX() / Config.CELL_SIZE) +
            direction.getoffsetX());

        int  y= (int) (node.getTranslateY() / Config.CELL_SIZE +
                   direction.getoffsetY());

        if (x < 0) {
            x = WIDTH - 1;
        }

        if (x >= WIDTH) {
            x = 0;
        }

        if (y < 0) {
            y = HEIGHT - 1;
        }

        if (y >= HEIGHT) {
            y = 0;
        }
//        System.out.println("x "+(x));//<-- 12.11..
//        System.out.println("y "+(y));//25
//        System.out.println("index dot "+(levelData.elementAt(x + y*WIDTH) instanceof Dot));
       // System.out.println("lveldata "+!(levelData.elementAt(x + y*WIDTH) instanceof Wall));
        return !(levelData.elementAt(x + y*WIDTH) instanceof Wall);
   }
    public static Integer getLevelsCount() {
       return (LEVELS_DATA.length/HEIGHT);
       
    }

    public static LevelData getLevelData(int level) {
     
        if (level < 1 || level > getLevelsCount()) {
          return null;
         } else {
            ArrayList<Wall> maze= new ArrayList<Wall>();
            ArrayList<Dot> dots = new ArrayList<Dot>();
            Vector levelData = new Vector();
             Coord droidInitCoord = null;
             Coord enemyHome = null;
             Wall enemyHomeWall=null;
             

        Integer levelPos = (level - 1) * (HEIGHT);//0*31,1*31
        String[] levelDataArr=Arrays.copyOfRange(LEVELS_DATA, levelPos, (levelPos + HEIGHT)); //0..31
    
       // System.out.println(levelDataArr.length); //Each line
        
        Integer k=0;
        for(int y=0; y<HEIGHT;y++){
           for(int x=0; x<WIDTH;x++){
           String type = levelDataArr[y].substring(x, x+ 1);
          
           int xPos = x * 16;
           int yPos = y * 16;
          
           Parent levelDataElement= new Parent() {};
           
           if (enemyHome != null &&
                    x >= enemyHome.getCoordX() && x < enemyHome.getCoordX() + ENEMY_HOME_WIDTH &&
                    y >= enemyHome.getCoordY() && y < enemyHome.getCoordY() + ENEMY_HOME_HEIGHT) {
                if (type.equals(ENEMY_HOME)) {
                        levelDataElement = enemyHomeWall;
                       //   System.out.println("enemy "+enemyHome.y + "k "+k);//1..39
                    } else {
                        System.out.println("enemy home Invalid level data. Level = "+level+", x = "+x+" , y = "+y);
                    }
            
               
           }
           else{
               if (type.equals(WALL)) {
               //k++;
                  String wallType = "wall";
                  ArrayList<String> edges= new ArrayList();
                   
                  for(int i=0;i<4;i++){
                    Integer offsetX = NEIGHBOUR_X[i];
                    Integer offsetY = NEIGHBOUR_Y[i];
                    
                   Integer neighbourX = x + offsetX;
                   Integer neighbourY = y + offsetY;
                   
                   if (neighbourX < 0) {
                            neighbourX = (WIDTH - 1);
                        } else if (neighbourX >= WIDTH) {
                            neighbourX = 0;
                        }

                        if (neighbourY < 0) {
                            neighbourY = (HEIGHT - 1);
                        } else if (neighbourY >= HEIGHT) {
                            neighbourY = 0;
                        }
                   
                        String neighbour1 = levelDataArr[y].substring(neighbourX, neighbourX + 1);
                        String neighbour2 = levelDataArr[neighbourY].substring(neighbourX, neighbourX + 1);
                        String neighbour3 = levelDataArr[neighbourY].substring(x, x + 1);
                        System.out.println("neighbour1 " +neighbour1);
                        System.out.println("neighbour2 " +neighbour2);
                        System.out.println("neighbour3 " +neighbour3);
                        
                        if(!(neighbour1.equals(WALL))||
                                !(neighbour2.equals(WALL))||
                                !(neighbour3.equals(WALL))){
                            wallType += "0";
                        }
                        else{
                            wallType += "1";
                            
                             // Add maze edge if needed
                            if ((x == 0 && i % 3 == 0) ||
                                    (x == WIDTH - 1 && i % 3 != 0)) {
                                edges.add("edgevert"+i);
                               // insert "edgevert{i}" into edges;
                            }

                            if ((y == 0 && i < 2) ||
                                    (y == HEIGHT - 1 && i >= 2)) {
                                 edges.add("edgehor"+i);
                                //insert "edgehor{i}" into edges;
                            }
                        }
                        
                  }//for
                  ArrayList<String> mine= new ArrayList();
                  mine.add(wallType);
                  for(String s:edges){
                       mine.add(s);
                  }
                 
                 Wall wall= new Wall(mine);
                  wall.setTranslateX(xPos);
                  wall.setTranslateY(yPos);
                  
                  maze.add(wall);
                  levelDataElement=wall;
                }//wall
               else if (type.equals(DOT) || type.equals(BIG_DOT)) {
                  Dot dot= new Dot(type.equals(DOT));
                  if (type.equals(DOT)){
                  dot.setTranslateX(xPos);
                  dot.setTranslateY(yPos);}
                  else{
                      dot.setTranslateX(xPos - Config.CELL_SIZE / 2);
                      dot.setTranslateY(yPos - Config.CELL_SIZE / 2);
                  }

                     dots.add(dot);
                     levelDataElement = dot;
               }//dot
               else if (type.equals(DROID_START)) {
                    if (droidInitCoord== null) {
                        droidInitCoord= new Coord();
                        droidInitCoord.setCoord(xPos,yPos);  
                    } else {
                        if ((droidInitCoord.getCoordX()== (x - 1) * Config.CELL_SIZE) && (droidInitCoord.getCoordY()== yPos)) { // x:13, y:25
                            droidInitCoord.setCoord(droidInitCoord.getCoordX() + (Config.CELL_SIZE / 2), yPos);
                            
                        } else {
                            System.out.println("droidstart Invalid level data. Level = "+level+", x = " +x+", y = "+y);
                        }
                    }
                }//Droidstart 
               else if (type.equals(ENEMY_HOME)) {
                    if (enemyHome == null) {
                        enemyHome= new Coord();  
                        //System.out.println("X "+x);
                        enemyHome.setCoord(x, y);
                        ArrayList<String> mine= new ArrayList<String>();
                        mine.add("enemyhome");
                        enemyHomeWall= new Wall(mine);
                        enemyHomeWall.setTranslateX(xPos + Config.CELL_SIZE / 2);
                        enemyHomeWall.setTranslateY(yPos + Config.CELL_SIZE / 2);
                        maze.add(enemyHomeWall);
                        
                        levelDataElement = enemyHomeWall;
                    } else {
                       System.out.println("enemyhome Invalid level data. Level = "+level+", x = " +x+", y = "+y);
                    }
                }//Enemyhome
                else if (!type.equals(EMPTY)) {
                    System.out.println("empty Invalid level data. Level = "+level+", x = " +x+", y = "+y);
                }//empty
           }//else
          levelData.addElement(levelDataElement);
         } //For
        }//For
        if (droidInitCoord == null) {
            System.out.println("Droid start position was not found. Level = {"+level+"}");
        }
        LevelData data= new LevelData();
        data.levelNumber=level;
        data.maze=maze;
        data.dots=dots;
        data.droidInitCoord=droidInitCoord;
        data.enemyHome= enemyHome;
        data.levelData=levelData;
         return data;//check return
             }//else
    }
private static Integer[] NEIGHBOUR_X = {-1, 1, 1, -1};
private static Integer[] NEIGHBOUR_Y = {-1, -1, 1, 1};

private static String EMPTY = " ";
private static String WALL = "X";
private static String DOT = ".";
private static String BIG_DOT = "o";
private static String DROID_START = "S";
private static String ENEMY_HOME = "E";

private static String []LEVELS_DATA = {
    "XXXXXXXXXXXXXXXXXXXXXXXXXXXX",//1
    "X..........................X",
    "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
    "XoXXXX.XXXXX.XX.XXXXX.XXXXoX",
    "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
    "X...XX.......XX.......XX...X",
    "XXX.XX.XXXXX.XX.XXXXX.XX.XXX",
    "XXX.XX.XXXXX.XX.XXXXX.XX.XXX",
    "XXX.XX.XXXXX.XX.XXXXX.XX.XXX",
    "   ..........XX.........    ",
    "XXX.XXXXXXXX.XX.XXXXXXXX.XXX",
    "XXX.XXXXXXXX.XX.XXXXXXXX.XXX",
    "X......XX....  ....XX......X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X.XXXX....EEEEEEEE....XXXX.X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X......XX..........XX......X",
    "XXX.XXXXXXXX.XX.XXXXXXXX.XXX",
    "XXX.XXXXXXXX.XX.XXXXXXXX.XXX",
    "   ..........XX..........   ",
    "XXX.XX.XXXXX.XX.XXXXX.XX.XXX",
    "XXX.XX.XXXXX.XX.XXXXX.XX.XXX",
    "XXX.XX.XXXXX.XX.XXXXX.XX.XXX",
    "X...XX.......SS.......XX...X",
    "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
    "XoXXXX.XXXXX.XX.XXXXX.XXXXoX",
    "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
    "X..........................X",
    "XXXXXXXXXXXXXXXXXXXXXXXXXXXX", //0..31   
    "XXXXXXXXXXXXXXXXXXXXXXXXXXXX",//2 //32 lines
    "X....  ....................X",
    "X.EEEEEEEE.XX.XXXXX.XXXXXX.X",
    "X.EEEEEEEE.XX.XXXXX.XXXXXX.X",
    "X.EEEEEEEE.XXo......XXXXXX.X",
    "X.EEEEEEEE.XXXXXXXX........X",
    "X.EEEEEEEE.XXXXXXXX.XXXXXX.X",
    "X...................XXXXXX.X",
    "X.XXXXX.XX.XX.XX.XX.XX.....X",
    "X.XXXXX.XX.XX.XX.XX.XX.XXX.X",
    "X......oXX.XX.XX.XX.XX.XXX.X",
    "X.XXXXX.XX.XX.XX.XX.XX.XX..X",
    "X.XXXXX.XX.XX.XX.XX.XX.XX.XX",
    "X....XX.XX.XX.XX.XX.XX.XX.XX",
    "XXXX.XX.XX.XX....XX.XX.XX.XX",
    "XXXX.XX.XX.XXXXXXXX.XX.XX.XX",
    "XXXX.XX.XX.XXXXXXXX.XX.XX.XX",
    "  ........................  ",
    "XX.XXXXXXXXX.XX.XXXXXXXXX.XX",
    "XX.XXXXXXXXX.XX.XXXXXXXXX.XX",
    "XX...........XX...........XX",
    "XXXX.XX.XXXXXXXXXXXX.XX.XXXX",
    "XXXX.XX.XXXXXXXXXXXX.XX.XXXX",
    "X....XX......SS......XX....X",
    "X.XXXXXXXX.XXXXXX.XXXXXXXX.X",
    "X.XXXXXXXX.XXXXXX.XXXXXXXX.X",
    "X....XX..............XX....X",
    "XXXX.XX.XXXX.XX.XXXX.XX.XXXX",
    "XXXX.XX.XXXX.XX.XXXX.XX.XXXX",
    "XXo .........XX......... oXX",
    "XXXXXXXXXXXXXXXXXXXXXXXXXXXX",    
    "XXXX XXXXXXXXXXXXXXXXXX XXXX",//3=32+31=63
    "X..........................X",
    "XXXXXXX.XXXXXXXXXXXX.XXXXXXX",
    "XXXXXXX.XXXXXXXXXXXX.XXXXXXX",
    "Xo...........SS...........oX",
    "X.XXXXXXXXXX.XX.XXXXXXXXXX.X",
    "X.XXXXXXXXXX.XX.XXXXXXXXXX.X",
    "X.XXXX.......XX.......XXXX.X",
    "X.XXXX.XX.XXXXXXXX.XX.XXXX.X",
    "X......XX.XXXXXXXX.XX......X",
    "X.XXXXXXX. ...... .XXXXXXX.X",
    "X.XXXXXXX.XXXXXXXX.XXXXXXX.X",
    "X.........XXXXXXXX.........X",
    "XXXX.XXXX....  ....XXXX.XXXX",
    "XXXX.XXXX.EEEEEEEE.XXXX.XXXX",
    "X....XXXX.EEEEEEEE.XXXX....X",
    "X.XX.XXXX.EEEEEEEE.XXXX.XX.X",
    "X.XX......EEEEEEEE......XX.X",
    "X.XXXXXXX.EEEEEEEE.XXXXXXX.X",
    "X.XXXXXXX..........XXXXXXX.X",
    "X.........XXXXXXXX.........X",
    "X.XXXXX.XXXXXXXXXXXX.XXXXX.X",
    "X.XXXXX.XXXX....XXXX.XXXXX.X",
    "X............XX............X",
    "XXXX.XXXXXXX.XX.XXXXXXX.XXXX",
    "XXXX.XXXXXXX.XX.XXXXXXX.XXXX",
    "X............XX............X",
    "X.XX.XX.XXXXXXXXXXXX.XX.XX.X",
    "X.XX.XX.XXXXXXXXXXXX.XX.XX.X",
    "X....XXo............oXX....X",
    "XXXX XXXXXXXXXXXXXXXXXX XXXX", 
    
    "XXXXXXXXX XXXXXXXX XXXXXXXXX",//4
    "X.........XXXXXXXX.........X",
    "X.XXXX.XX.XXXXXXXX.XX.XXXX.X",
    "X.XXXX.XX..........XX.XXXX.X",
    "X....o.XX.XXXXXXXX.XX.o....X",
    "X.XXXXXXX.XXXXXXXX.XXXXXXX.X",
    "X.XXXXXXX..........XXXXXXX.X",
    "X......XX.EEEEEEEE.XX......X",
    "XXX.XX.XX.EEEEEEEE.XX.XX.XXX",
    "XXX.XX.XX.EEEEEEEE.XX.XX.XXX",
    "X...XX.XX.EEEEEEEE.XX.XX...X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X.XXXX................XXXX.X",
    "X......XX.XX.XX.XX.XX......X",
    "X.XXXXXXX.XX.XX.XX.XXXXXXX.X",
    "X.XXXXXXX.XX....XX.XXXXXXX.X",
    "X.XX......XX.XX.XX......XX.X",
    "X.XX.XXXX.XX.XX.XX.XXXX.XX.X",
    "X.XX.XXXX....XX....XXXX.XX.X",
    "X.........XXXXXXXX.........X",
    "XXXX.XXXX.XXXXXXXX.XXXX.XXXX",
    "XXXX.XXXX..........XXXX.XXXX",
    "X......XX.XX.XX.XX.XX......X",
    "X.XXXX.XX.XX.XX.XX.XX.XXXX.X",
    "X.XXXX.XX.XX.SS.XX.XX.XXXX.X",
    " ......XX.XXXXXXXX.XX...... ",
    "X.XXXX....XXXXXXXX....XXXX.X",
    "X.XXXX.XX....XX....XX.XXXX.X",
    "X.XXXX.XX.XX.XX.XX.XX.XXXX.X",
    "X..o...XX.XX....XX.XX...o..X",
    "XXXXXXXXX XXXXXXXX XXXXXXXXX",
    
    "XXXXXXXXXXXX XX XXXXXXXXXXXX",//5
    "Xo........XX.XX.XX........oX",
    "XX.XXXXXX.XX.XX.XX.XXXXXX.XX",
    "XX.XXXXXX.XX.XX.XX.XXXXXX.XX",
    "XX.....XX....XX....XX.....XX",
    " ..XXX.XX.XX.XX.XX.XX.XXX.. ",
    "X.XXXX....XX....XX....XXXX.X",
    "X.XXXX.XXXXXXXXXXXXXX.XXXX.X",
    "X......XXXXXXXXXXXXXX......X",
    "XXX.XX.XX....SS....XX.XX.XXX",
    "XXX.XX.XX.XX.XX.XX.XX.XX.XXX",
    "XXX.XX.XX.XX.XX.XX.XX.XX.XXX",
    "XXX.XX.XX.XX.XX.XX.XX.XX.XXX",
    "XXX.XX.XX.XX.XX.XX.XX.XX.XXX",
    "X...XX....XX....XX....XX...X",
    "X.XXXXXXXXXX.XX.XXXXXXXXXX.X",
    "X.XXXXXXXXXX.XX.XXXXXXXXXX.X",
    "X............XX............X",
    "XXXX.XXXXXX.XXXX.XXXXXX.XXXX",
    "XXXX.XXXXXX.XXXX.XXXXXX.XXXX",
    "X..........................X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    " ......XX.EEEEEEEE.XX...... ",
    "X.XX.XXXX.EEEEEEEE.XXXX.XX.X",
    "X.XX.XXXX.EEEEEEEE.XXXX.XX.X",
    "X......XX..........XX......X",
    "X.XXXX.XX.XXXXXXXX.XX.XXXX.X",
    "X.XXXX.XX.XXXXXXXX.XX.XXXX.X",
    "X..o.........XX.........o..X",
    "XXXXXXXXXXXX XX XXXXXXXXXXXX",
    
    "XXXXXXXXX XXXXXXXX XXXXXXXXX",//6
    "X............XX............X",
    "X.XXXXX.XXXX.XX.XXXX.XXXXX.X",
    "X.XXXXX.XXXX.XX.XXXX.XXXXX.X",
    " ....XXoXX........XXoXX.... ",
    "X.XX.XX.XX.XXXXXX.XX.XX.XX.X",
    "X.XX.......XXXXXX.......XX.X",
    "X.XX.XXXXX.XXXXXX.XXXXX.XX.X",
    "X.XX.XXXXX.XXXXXX.XXXXX.XX.X",
    "X..........................X",
    "X.XXXX.XXX.XXXXXX.XXX.XXXX.X",
    "X.XXXX.XXX.XXXXXX.XXX.XXXX.X",
    "X......XX..........XX......X",
    "XXX.XX.XX.EEEEEEEE.XX.XXXX.X", 
    "XXX.XX.XX.EEEEEEEE.XX.XXXX.X",
    "X...XX....EEEEEEEE....XX...X",
    "X.XXXX.XX.EEEEEEEE.XX.XX.XXX",
    "X.XXXX.XX.EEEEEEEE.XX.XX.XXX",
    "X......XX..........XX......X",
    "X.XXXX.XXXX.XXXX.XXXX.XXXX.X",
    "X.XXXX.XXXX.XXXX.XXXX.XXXX.X",
    " .......................... ",
    "X.XX.XXX.XXXXXXXXXX.XXX.XX.X",
    "X.XX.XXX.XXXXXXXXXX.XXX.XX.X",
    "X.XX.....XXX.SS.XXX.....XX.X",
    "X.XX.XXX.XXX.XX.XXX.XXX.XX.X",
    "X..o.XXX.....XX.....XXX.o..X",
    "X.XXXXXXX.XX.XX.XX.XXXXXXX.X",
    "X.XXXXXXX.XX.XX.XX.XXXXXXX.X",
    "X.........XX....XX.........X",
    "XXXXXXXXX XXXXXXXX XXXXXXXXX",
    
    "XXXXXXX XXXXXXXXXXXX XXXXXXX",//7
    "X..........................X",
    "X.XXXXX.XXXXXXXXXXXX.XXXXX.X",
    "X.XXXXX.XXXXXXXXXXXX.XXXXX.X",
    "X.XX.o................o.XX.X",
    "X.XX.XXXXX.XXXXXX.XXXXX.XX.X",
    "X.XX.XXXXX.XXXXXX.XXXXX.XX.X",
    "X.XX....XX........XX....XX.X",
    "X.XX.XX.XX.XXXXXX.XX.XX.XX.X",
    "X.XX.XX.XX.XXXXXX.XX.XX.XX.X",
    "X.XX.XX..............XX.XX.X",
    " ....XX.XXXXXXXXXXXX.XX.... ",
    "X.XX.XX.XXXXXXXXXXXX.XX.XX.X",
    "X.XX.XX..............XX.XX.X",
    "X.XX.XXXX.EEEEEEEE.XXXX.XX.X",
    "X.XX.XXXX.EEEEEEEE.XXXX.XX.X",
    "X.XX......EEEEEEEE......XX.X",
    "X.XX.XXXX.EEEEEEEE.XXXX.XX.X",
    "X.XX.XXXX.EEEEEEEE.XXXX.XX.X",
    " ....XXXX..........XXXX.... ",
    "X.XX.XXXXX.XXXXXX.XXXXX.XX.X",
    "X.XX.XXXXX.XXXXXX.XXXXX.XX.X",
    "X.XX.XX....XXXXXX....XX.XX.X",
    "X.XX....XX........XX....XX.X",
    "X.XX.XXXXX.XXXXXX.XXXXX.XX.X",
    "X.XX.XXXXX.XXXXXX.XXXXX.XX.X",
    "X.XX.........SS.........XX.X",
    "X.XXXXX.XXXXXXXXXXXX.XXXXX.X",
    "X.XXXXX.XXXXXXXXXXXX.XXXXX.X",
    "X.o......................o.X",
    "XXXXXXX XXXXXXXXXXXX XXXXXXX",
    
    "XXXXXXXXXXXX XX XXXXXXXXXXXX",//8
    "X............XX............X",
    "X.XX.XXXXXXX.XX.XXXXXXX.XX.X",
    "X.XX.XXXXXXX.XX.XXXXXXX.XX.X",
    "X.XXoXXXXX........XXXXXoXX.X",
    " .XX.......XXXXXX.......XX. ",
    "X.XXXXX.XX.XXXXXX.XX.XXXXX.X",
    "X.XXXXX.XX.XXXXXX.XX.XXXXX.X",
    "X.......XX........XX.......X",
    "X.XXXXX.XX.XXXXXX.XX.XXXXX.X",
    "X.XXXXX.XX.XXXXXX.XX.XXXXX.X",
    "X.XX.......XXXXXX.......XX.X",
    "X.XX.XX.XX.XXXXXX.XX.XX.XX.X",
    "X.XX.XX.XX........XX.XX.XX.X",
    "X.XX.XX.XXXX.XX.XXXX.XX.XX.X",
    " ....XX.XXXX.XX.XXXX.XX.... ",
    "X.XX.XX......SS......XX.XX.X",
    "X.XX.XXXX.XXXXXXXX.XXXX.XX.X",
    "X.XX.XXXX.XXXXXXXX.XXXX.XX.X",
    "X.XX....................XX.X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X...o..XX.EEEEEEEE.XX..o...X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X.XXXX.XX.EEEEEEEE.XX.XXXX.X",
    "X.XX...XX..........XX...XX.X",
    "X.XX.XXXX.XXXXXXXX.XXXX.XX.X",
    "X.XX.XXXX.XXXXXXXX.XXXX.XX.X",
    "X.XX.XXXX.XX....XX.XXXX.XX.X",
    "X............XX............X",
    "XXXXXXXXXXXX XX XXXXXXXXXXXX",
    
    "XXXXXXXX XXXXXXXXXX XXXXXXXX",//9
    "X..........................X",
    "X.XXXXXX.XXXXXXXXXX.XXXXXX.X",
    "X.XXXXXX.XXXXXXXXXX.XXXXXX.X",
    "X...o.XX.....SS.....XX.o...X",
    "X.XXX.XX.XX.XXXX.XX.XX.XXX.X",
    "X.XXX.XX.XX.XXXX.XX.XX.XXX.X",
    " ........XX.XXXX.XX........ ",
    "X.XX.XXXXXX......XXXXXX.XX.X",
    "X.XX.XXXXXXX.XX.XXXXXXX.XX.X",
    "X......XXXXX.XX.XXXXX......X",
    "XXX.XX................XX.XXX",
    "XXX.XX.XXXX.XXXX.XXXX.XX.XXX",
    " ...XX.XXXX.XXXX.XXXX.XX... ",
    "XXX.XX.XX..........XX.XX.XXX",
    "XXX.XX.XX.EEEEEEEE.XX.XX.XXX",
    "XXX....XX.EEEEEEEE.XX....XXX",  
    "XXX.XX.XX.EEEEEEEE.XX.XX.XXX",
    "XXX.XX.XX.EEEEEEEE.XX.XX.XXX",
    "XXX.XX.XX.EEEEEEEE.XX.XX.XXX",
    "X..........................X",
    "X.XXX.XXXXX.XXXX.XXXXX.XXX.X",
    "X.XXX.XXXXX.XXXX.XXXXX.XXX.X",
    " ........XX......XX........ ",
    "X.XXX.XX.XX.XXXX.XX.XX.XXX.X",
    "X.XXX.XX.XX.XXXX.XX.XX.XXX.X",
    "X..o..XX............XX..o..X",
    "X.XXX.XX.XXX.XX.XXX.XX.XXX.X",
    "X.XXX.XX.XXX.XX.XXX.XX.XXX.X",
    "X............XX............X",
    "XXXXXXXX XXXXXXXXXX XXXXXXXX",

};

    public void move(Parent node, Direction direction) {
       double newX = node.getTranslateX() + direction.getoffsetX();
       double newY = node.getTranslateY() + direction.getoffsetY();       
       int half = Config.CELL_SIZE / 2;
       double maxX = Config.GAME_FIELD_WIDTH - Config.CELL_SIZE - half - 1;
       double maxY = Config.GAME_FIELD_HEIGHT - Config.CELL_SIZE - half - 1;
     // System.out.println("direction.getoffsetX() "+direction.getoffsetX());
        if (newX < half) {
            newX = maxX;
        } else if (newX > maxX) {
            newX = half;
        }

        if (newY < half) {
            newY = maxY;
        } else if (newY > maxY) {
            newY = half;
        }
        node.setTranslateX(newX);
        node.setTranslateY(newY);
    }
    

   

}
