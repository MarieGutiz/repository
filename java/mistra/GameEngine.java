package mistra;


import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class GameEngine extends JPanel {
	private static final long serialVersionUID = conf.Mariela;
   private JLayeredPane layeredPane;
   private JPanel leftBoard;
   String dir="images/pieces/greens/";
   MIGIWindow w;
   Myboard rightBoard;
   CPU computer;
   CPU computer2; 
   labelandbutton labl;
   URL [] urls= new URL[40];
   JLabel [] labels= new JLabel[40];
   JPanel []panels = new JPanel[40];
  private int x;
  public Frame f;
  public JDialog d;
  public final dragdrop dndListener;
  private StringBuffer CooX= new StringBuffer();
  public String str;
  public StartTime clock,counter;
public GameEngine(MIGIWindow window, Myboard board, CPU computer, labelandbutton btn,Frame f,JDialog d){
     Dimension boardSize = new Dimension(188,470);
     w= window;
     rightBoard= board;
     this.computer=computer;
     this.labl= btn;
     this.f=f;
     this.d=d;
	 this.setBackground(new Color(70,70,70));  
     layeredPane = new JLayeredPane();
     this.add(layeredPane);
     layeredPane.setPreferredSize(boardSize);
     URL url1 = this.getClass().getResource("images/bgr/left.jpg");
     JLabel boar= new JLabel(new ImageIcon(url1));
     leftBoard = new JPanel();
     layeredPane.add(leftBoard);
     layeredPane.add(boar);
     leftBoard.setLayout( new GridLayout(10, 4) );
     boar.setBounds(0, 0, 188,470);
     leftBoard.setBounds(0, 0, 188,470);
     leftBoard.setOpaque(false);
     for (int i = 0; i < 40; i++) {
         JPanel square = new JPanel( new BorderLayout() );
         square.setOpaque(false);
         leftBoard.add( square );
         square.setBorder(BorderFactory.createLineBorder(new Color(70,70,70)));
     }           
     dndListener = new dragdrop(this);
     DragSource dragSource = new DragSource();
     
     leftBoard.setCursor(new Cursor(Cursor.MOVE_CURSOR));
     JPanel [] boardpanel= new JPanel[100];
     DropTarget [] dropTarget= new DropTarget[100];
     for (int i = 0; i < 100; i++) {
         if(i!=42 && i!=43 && i!=52 && i!=53 && i!=46 && i!=47 && i!=56 && i!=57){
	        	boardpanel[i] = (JPanel)w.straBoard.getComponent(i);
	        	dropTarget[i]=new DropTarget(boardpanel[i], DnDConstants.ACTION_MOVE, 
	                    dndListener);
	          }
         }
     DragGestureRecognizer [] dragRecognizer= new DragGestureRecognizer[40];
      for(int k=0; k < conf.strurls.length;k++){
    	   urls[k] = this.getClass().getResource(dir+conf.strurls[k]);
    	   labels[k] = new JLabel( new ImageIcon(urls[k]) );
    	   labels[k].setName(""+k);
    	   panels[k] = (JPanel)leftBoard.getComponent(k);
    	   panels[k].add(labels[k]);
    	   dragRecognizer[k]=dragSource.
           createDefaultDragGestureRecognizer(labels[k], DnDConstants.ACTION_MOVE, dndListener);
      }
      initing(dndListener);   
   }//constructor

    public void reset(dragdrop dnd){
  		dnd.f.ar.Component_oponent.clear();
  		dnd.f.ar.Components.clear();  		
  		this.computer.cla.clear();
  		
  	    Component[] source= new Component[40];
  		Container[] c= new Container[40];
  		Container[] oldc= new Container[40];
  		   	
  		for(int l=0; l<conf.strurls.length;l++){
  			dnd.game.labels[l].setIcon(new ImageIcon(dnd.game.urls[l])); 
  			source[l]=dnd.game.labels[l];
  	  		c[l]=(Container)dnd.game.panels[l];
  	  		oldc[l]=source[l].getParent();
  	  	    dnd.add(source[l], oldc[l], c[l]);
  	  	
  		}
  		
  		Component[] sourc= new Component[40];
  		Container[] cc= new Container[40];
  		Container[] old= new Container[40]; 
  		for(int l=0; l<conf.strurls.length;l++){
  		dnd.game.rightBoard.labels[l].setIcon(new ImageIcon(dnd.game.rightBoard.urls[l]));
	  	 sourc[l]=dnd.game.rightBoard.labels[l];
	  	 cc[l]=(Container)dnd.game.rightBoard.panels[l];
	  	 old[l]=sourc[l].getParent();	  
	  	  dnd.add(sourc[l], old[l], cc[l]);
  		}
  		for (int i = 0; i < 100; i++) {		
		 if(i!=42 && i!=43 && i!=52 && i!=53 && i!=46 && i!=47 && i!=56 && i!=57){
			dnd.game.w.panels[i].setBorder(BorderFactory.createLineBorder(conf.gris));
         }			  
	}  		
  		initing(dnd);  		
  	}
	private void initing(dragdrop dndListener) {
		// TODO Auto-generated method stub		
		this.labl.output.setText("place a piece on the board");
		  	 dndListener.begin=true;
		  	 dndListener.started=false;
		  	 dndListener.game_over=false;
		  	 dndListener.random=true;
		  	 dndListener.turn=true;
		  	 dndListener.move_count=0;
		  	 dndListener.pieces_down=0;     
		         dndListener.one_ptr=0;
		 	 dndListener.two_ptr=1;
		 	 dndListener.three_ptr=2;
		 	 dndListener.four_ptr=4;
		 	 dndListener.five_ptr=7;
		 	 dndListener.six_ptr=11;
		 	 dndListener.seven_ptr=15;
		 	 dndListener.eight_ptr=19;
		 	 dndListener.nine_ptr=24;
		 	 dndListener.spy_ptr=32;
		 	 dndListener.bomb_ptr=33;
		 	 dndListener.flag_ptr=39;
		 
		 	computer.reset();
		 	for(x=0; x<10; x++){
	  			for(int y=0; y<10; y++)
	  				dndListener.board[x][y]=null;
	  		}
		 	for(int x=0; x<40; x++){
	  			dndListener.player_pieces[x]=null;
	  			dndListener.opponent_pieces[x]=null;
	  		}
		 	dndListener.player_pieces[0]=new gamePiece(piece_type.Marshal,  player_type.User);
		 	dndListener.opponent_pieces[0]=new gamePiece(piece_type.Marshal,player_type.AI);
		 	dndListener.added[0]=false;
		 	dndListener.player_pieces[1]=new gamePiece(piece_type.General,player_type.User);
			dndListener.opponent_pieces[1]=new gamePiece(piece_type.General, player_type.AI);
			dndListener.added[1]=false;
			for(x=2; x<=3; x++){
				dndListener.player_pieces[x]=new gamePiece(piece_type.Colonel, player_type.User);
				dndListener.opponent_pieces[x]=new gamePiece(piece_type.Colonel, player_type.AI);
				dndListener.added[x]=false;
			}
			for(x=4; x<=6; x++){
				dndListener.player_pieces[x]=new gamePiece(piece_type.Major, player_type.User);
				dndListener.opponent_pieces[x]=new gamePiece(piece_type.Major, player_type.AI);
				dndListener.added[x]=false;
			}
			for(x=7; x<=10; x++){
				dndListener.player_pieces[x]=new gamePiece(piece_type.Captain, player_type.User);
				dndListener.opponent_pieces[x]=new gamePiece(piece_type.Captain, player_type.AI);
				dndListener.added[x]=false;
			}
			for(x=11; x<=14; x++){
				dndListener.player_pieces[x]=new gamePiece(piece_type.Lieutenant, player_type.User);
				dndListener.opponent_pieces[x]=new gamePiece(piece_type.Lieutenant, player_type.AI);
				dndListener.added[x]=false;
			}
			for(x=15; x<=18; x++){
				dndListener.player_pieces[x]=new gamePiece(piece_type.Sergeant, player_type.User);
				dndListener.opponent_pieces[x]=new gamePiece(piece_type.Sergeant, player_type.AI);
				dndListener.added[x]=false;
			}
			for(x=19; x<=23; x++){
				dndListener.player_pieces[x]=new gamePiece(piece_type.Miner, player_type.User);
				dndListener.opponent_pieces[x]=new gamePiece(piece_type.Miner, player_type.AI);
				dndListener.added[x]=false;
			}
			for(x=24; x<=31; x++){
				dndListener.player_pieces[x]=new gamePiece(piece_type.Scout, player_type.User);
				dndListener.opponent_pieces[x]=new gamePiece(piece_type.Scout,player_type.AI);
				dndListener.added[x]=false;
			}
			dndListener.player_pieces[32]=new gamePiece(piece_type.Spy, player_type.User);
			dndListener.opponent_pieces[32]=new gamePiece(piece_type.Spy, player_type.AI);
			dndListener.added[32]=false;
			for(x=33; x<=38; x++){
				dndListener.player_pieces[x]=new gamePiece(piece_type.Bomb,player_type.User);
				dndListener.opponent_pieces[x]=new gamePiece(piece_type.Bomb,player_type.AI);
				dndListener.added[x]=false;
			}
			dndListener.player_pieces[39]=new gamePiece(piece_type.Flag, player_type.User);
			dndListener.opponent_pieces[39]=new gamePiece(piece_type.Flag, player_type.AI);
			dndListener.added[x]=false;
                        
                        createThread();
		
	}
	private void createThread() {
		// TODO Auto-generated method stub
		clock= new StartTime();	
		counterClock();
	}
	public void counterClock() {
		// TODO Auto-generated method stub
		counter= new StartTime();
	}
    void orderingpieces(dragdrop dndListener){
		//opponent 
			for(int i=0; i<40; i++){
				dndListener.opponent_pieces[i].makeInvisible();
			}
			gamePiece[] pieces = computer.place_pieces();
			
			int i=0;
			while(i<40){
				if(dndListener.board[pieces[i].getX()][pieces[i].getY()]==null && pieces[i].getX() >= 0 && pieces[i].getX() <= 9 && pieces[i].getY() >= 6 && pieces[i].getY() <= 9){
					dndListener.board[pieces[i].getX()][pieces[i].getY()]=dndListener.opponent_pieces[i];//
					dndListener.opponent_pieces[i].update_coordinates(pieces[i].getX(), pieces[i].getY());
					i++;
				}
				else{
					 pieces = computer.place_pieces();
					i=0;
				}
			}
	}

    void randonize(String s,final boolean b) {
        final JOptionPane optionPane = new JOptionPane(
                s,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
               d.setContentPane(optionPane);
               d.setDefaultCloseOperation(
			    JDialog.DO_NOTHING_ON_CLOSE);
               
               optionPane.addPropertyChangeListener(
    new PropertyChangeListener() {
    	public void propertyChange(PropertyChangeEvent e) {
            String prop = e.getPropertyName();            
            if (d.isVisible() 
             && (e.getSource() == optionPane)
             && (prop.equals(JOptionPane.VALUE_PROPERTY))
              ) {
            	int value = ((Integer)optionPane.getValue()).intValue();
            	if (value == JOptionPane.YES_OPTION) {   
            		if(dndListener.pieces_down==40){
            			d.setVisible(false);
            			if(b){
                                try {
                                    sesame();
                                } catch (IOException ex) {
                                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                                }
                               
            			}            			
            			dndListener.game.orderingpieces(dndListener); 
                		dndListener.retire();
            			 for(int x=0; x<40; x++){//drawpiece       
            		      		dndListener.Search(dndListener.opponent_pieces[x],x);
            		      	   }
                	 dndListener.started=true; 
                	 dndListener.game.labl.output.setText("Please make a move");    
                           //gnl clock
                	 dndListener.game.clock.Time(dndListener.game.labl.Clock);
                	 dndListener.game.counter.Time(dndListener.game.labl.Counter);                       
            		}            		
            	} 
            }
        }

            private void sesame() throws IOException {
                int n = JOptionPane.showConfirmDialog(
					f, 
				    "Would you like to save this system ?", 
				    "Save",
				    JOptionPane.YES_NO_OPTION);
			 if (n == JOptionPane.YES_OPTION) {
                         sose();
                              FileHandler.filehandle(dndListener);
		              FileHandler.saveAs();
                   
	         }
                         
            }
              
            private void sose() {
               int x, y;
               for(int i=0;i<dndListener.player_pieces.length;i++){
                  x=dndListener.player_pieces[i].getX();
                  y=dndListener.player_pieces[i].getY();
                  CooX.append(x);
                  CooX.append(y);
               }
                 str=CooX.toString();        
            }
		
    }); 
     
    }
    void anixe(StringBuffer cooX) { 
        if(cooX.length()<81||cooX.length()>81){
           // messagebox("System "+cooX.length() ,0);
            JOptionPane.showMessageDialog (
			            		f,
			              "IO error in opening file!!", "File Open Error",
			              JOptionPane.ERROR_MESSAGE
			            );
        }
        else{
           // messagebox("lenght "+cooX.length(),0);             
            int x=0;int y=1; int c=0; int xx,yy;
        while(x<cooX.length()&&y<cooX.length()){
            xx=Character.getNumericValue(cooX.charAt(x));
            yy=Character.getNumericValue(cooX.charAt(y));
             dndListener.player_pieces[c].update_coordinates(xx, yy);
             dndListener.board[xx][yy]=dndListener.player_pieces[c];  
	     dndListener.Search(dndListener.player_pieces[c],c);
            // messagebox("y "+y,0);//0-40 x=78 y=81
            x=x+2;
            y=y+2;           
            c++; 
         }
        // messagebox("ti egeine kato ",0);
        dndListener.pieces_down=40;
        this.randonize("Would you like to initialize a game ?",false);
        d.setVisible(true);
        }
      
    }
  void messagebox(String s,int n){
      switch(n){
          case 0://empty
              JOptionPane.showMessageDialog(f, s);
              break;
              case 1://GameOver
             int num= JOptionPane.showConfirmDialog(
			   f, 
			    s, 
			    "Game Over",
			    JOptionPane.YES_NO_OPTION);
		 if (num == JOptionPane.YES_OPTION) {			
		  this.reset(dndListener);			 
         }
              break;
      }
      
  }
   
  
  
}