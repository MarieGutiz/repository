package mistra;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class Progress implements  
PropertyChangeListener  {
	public JProgressBar progressBar;
	private dragdrop d;
	public Task task;
	JPanel panel= new JPanel();
	private boolean turn;
	public Progress(dragdrop dnd) {
		// TODO Auto-generated constructor stub
		this.d= dnd;
		turn=false;
		 progressBar = new JProgressBar(0, 100);
	     progressBar.setValue(0);
	     progressBar.setStringPainted(true);
	     progressBar.setBorder(null);
	     panel.setBounds(760, 487,60,18);
	     panel.setBackground(new Color(70,70,70));
	     d.game.labl.f.add(panel); 
	     progressBar.setBounds(0, 0, 60, 18);
	     progressBar.setFont(new Font("Serif", Font.BOLD, 10));
	     progressBar.setBackground(new Color(70,70,70));
	     progressBar.setForeground(Color.MAGENTA);
	     progressBar.setBorder(null);
	     panel.add(progressBar);

	     task = new Task();
	     task.addPropertyChangeListener(this);
	     task.execute();
	     d.game.labl.f.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
	
//swingworker
public class Task extends SwingWorker<Void, Void> {	
	    @Override
	    public Void doInBackground() {
	        Random random = new Random();
	        int progress = 0;
	        //Initialize progress property.
	        setProgress(0);
	        while (progress < 120) {
	            try {
	                Thread.sleep(random.nextInt(100));
	            } catch (InterruptedException ignore) {}
	            //Make random progress.
	            progress += random.nextInt(10);
	            setProgress(Math.min(progress, 100));
	        }
	        return null;
	    }
	    @Override
	    public void done() {
	    	// Toolkit.getDefaultToolkit().beep();
		        d.game.labl.f.setCursor(null); //turn off the wait cursor
		        panel.setVisible(false);
		        d.game.w.myarrow.setVisible(false);
		        turn=true;
	        if(turn){
		    	if( d.move_piece(player_type.AI,d.game.computer.x_from, d.game.computer.y_from, d.game.computer.x_to, d.game.computer.y_to)){
		    		 if(!d.game_over){
		    		  if(d.moves_available(true)){//cut
			  				d.game.labl.output.setText("Please make a move");
			  				 d.game.counterClock();
			  				 d.game.counter.Time(d.game.labl.Counter);
			  			  }
			  	       else{
							d.game.labl.output.setText("Challenger Loses");
							d.game.clock.stop();
						    new Pane("I Won ! \n Would you like to play again ?",d,1,true);
							d.winner=2;
							d.game_over=true;
						}
		  	       d.turn=true;
		        }//!gameover
		    		 else{
		    			 d.draw();
		    		 }
	        }//if	  
		      }
	      
	    }//done
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		 if ("progress".equals(evt.getPropertyName())) {
	            int progress = (Integer) evt.getNewValue();
	            progressBar.setValue(progress);
	           
	        } 
	}
}
