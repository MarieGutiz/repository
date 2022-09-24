package mistra;

import java.text.DecimalFormat;
import java.util.Calendar;
import javax.swing.JLabel;
public class StartTime implements Runnable {

	public static Thread TheThread;
	private Calendar Counter;
	private boolean alive= true;
	private int seconds;
	private int minutes;
	private int hours;
	DecimalFormat myFormatter = new DecimalFormat("00");
	public String time;
	private JLabel lb;
	 
	public void Time(JLabel clock){
		this.lb= clock;
		 TheThread= new Thread(this);
		 TheThread.start();		 
	 }
	 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 reset();
		 while (alive == true)   {
		        //Counter		        
			    Counter.add(Calendar.SECOND, 1);
			    seconds = Counter.get(Calendar.SECOND);
				minutes = Counter.get(Calendar.MINUTE);
				hours =   Counter.get(Calendar.HOUR);
			    time = myFormatter.format(hours) +" : "+myFormatter.format(minutes)+" : "+seconds;
		        this.lb.setText(time);
		        try   {
		           Thread.sleep(1000);
		        }
		        catch (InterruptedException e)   {
		           System.out.println(e);
		        }
		     }
	}

	private void reset() {
		// TODO Auto-generated method stub
		 Counter= Calendar.getInstance();
	     Counter.set(Calendar.SECOND, 0);
	     Counter.set(Calendar.MINUTE, 0); 
	     Counter.set(Calendar.HOUR, 0);
	     Counter.set(Calendar.MILLISECOND,0);
	}
	public void stop(){
		 alive =false;
		 TheThread=null;
	  }
   	  public void destroyed(){
		  if(this.lb!=null){
			  this.lb.setText("");
		  }
	    alive = false;
	    TheThread=null;
	  }
}