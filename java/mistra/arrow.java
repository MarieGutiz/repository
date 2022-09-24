package mistra;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class arrow extends JLabel {
	URL url= getClass().getResource("images/icons/arrow.png");

    private Direction direction;	
	 arrow(){
		this.setIcon(new ImageIcon(url));
	     }	
	 public void setDirection(Direction direction) {
		    this.direction = direction;
		  }
	 private  Direction getDirection() {
		    return direction;
	  }
	 protected void paintComponent(Graphics g) {
		    Graphics2D gr = (Graphics2D) g.create();
		    AffineTransform transform = new AffineTransform();
		   switch (getDirection()) {
		    case UP:
		    	transform.rotate(Math.toRadians(180), getHeight()/2, getHeight()/2);
				 gr.transform(transform);	
		    	break;
		    case RIGHT:
		    	 transform.rotate(Math.toRadians(270), getHeight()/2, getHeight()/2);
				 gr.transform(transform);		    
						    	
		    	break;
		    case LEFT:
		    	transform.rotate(Math.toRadians(90), getHeight()/2, getHeight()/2);
				 gr.transform(transform);	
				 
		    default:
		    	/** My default image position. */
		    	break;
		    }
		   super.paintComponent(gr);
	 }
	
}
