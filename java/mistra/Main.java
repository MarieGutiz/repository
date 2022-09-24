package mistra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JDialog;
/**
 *
 * @author LovART
 */
public class Main extends JApplet implements ActionListener {
    private Container cp;
    private  Frame f;
    private  GameEngine engine;
    private labelandbutton  btn;
    private JDialog d;  
    public void init() {
         setSize(899, 546);
	     cp=this.getContentPane();
	     cp.setBackground(new Color(70,70,70));   
	     cp.setLayout(null);
             btn= new labelandbutton(cp);
             CPU computer = new CPU(true);
	     MIGIWindow g= new MIGIWindow();
	     Myboard board= new Myboard();
             f = findParentFrame();
             d= new JDialog(f, "Initialize", false); 
             d.setSize(283, 145);
             d.setLocation(308, 153);	
             engine= new GameEngine(g,board,computer,btn,f,d);             
               for(int b=0;b<4;b++){
                    btn.btn[b].addActionListener(this); 
               }
             engine.setBounds(10, 10, 188, 476);
	     cp.add(engine);
	     g.setBounds(209,10,470,476);
	     cp.add(g);
	     board.setBounds(690,10,188,476);
	     cp.add(board);  
             this.setVisible(true);
        }
    // TODO overwrite start(), stop() and destroy() methods
     private Frame findParentFrame(){
      Container c = this;
      while(c != null){
       if (c instanceof Frame)
        return (Frame)c;
        c = c.getParent();
       }
        return (Frame)null;
     }
    @Override
    public void actionPerformed(ActionEvent e) {        
         if(e.getSource()==btn.btn[3]){
             engine.dndListener.random_setup();
                }
           if(e.getSource()==btn.btn[1]){
               if(!engine.dndListener.started){
               StringBuffer CooX;
               CooX = FileHandler.open();
               engine.anixe(CooX);
               }
               else
                   engine.messagebox("Game Started",0);
                }
           
            if(e.getSource()==btn.btn[0]){ 
                if(engine.dndListener.pieces_down<40){
			engine.messagebox("Complete your system first",0);
		}
            }
            if(e.getSource()==btn.btn[2]){
                engine.computer.cla.undo();
            }
    }
}
