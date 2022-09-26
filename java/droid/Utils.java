/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package droi;

/**
 *
 * @author LovART
 */
import java.util.Random;
import javafx.scene.transform.*;
public class Utils {
    public static final Scale FLIP_TRANSFORM = Transform.scale(-1, 1);
    public static final Translate OFFSET_TRANSFORM = Transform.translate(0, 0);
    public static final Translate BIG_OFFSET_TRANSFORM =Transform.translate(-Config.CELL_SIZE * 3/2-5,0);
    //public static final Translate BIG_OFFSET_TRANSFORM =Transform.translate(-Config.CELL_SIZE * 3 / 2, -Config.CELL_SIZE / 2);
    
    public static Random RANDOM = new java.util.Random();
    
    // Returns random integer number from 0 to max - 1
    public static int random(int max){
    return (int) RANDOM.nextDouble() * max;
}

// Returns sign of the value
   public static int sign(double n) {
    if (n == 0) {
        return 0;
    }

    if (n > 0) {
        return 1;
    } else {
        return -1;
    }
}
}
