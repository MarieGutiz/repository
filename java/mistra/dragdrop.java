package mistra;

import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class dragdrop implements DragGestureListener, DragSourceListener,
DropTargetListener, Transferable,MouseListener  {
	 /* gaming */
	 boolean begin;
	 boolean started;
	 boolean game_over;
	 boolean turn;
	// boolean player_cpu;
	 int winner;
	 public int move_count;
	 int pieces_down;
		
	 int piece_button;
     int flag_ptr;
	 int one_ptr;
	 int two_ptr;
	 int three_ptr;
	 int four_ptr;
	 int five_ptr;
	 int six_ptr;
	 int seven_ptr;
	 int eight_ptr;
	 int nine_ptr;
	 int spy_ptr;
	 int bomb_ptr;
	 gamePiece[][] board= new gamePiece [10][10];
	 gamePiece[] player_pieces= new gamePiece [40];
	 gamePiece[] opponent_pieces= new gamePiece [40];
	 boolean [] added=new boolean[40];
	 gamePiece[][] positions= new gamePiece[10][10];
	 flag f= new flag();
 static final DataFlavor[] supportedFlavors = {null};
    
    static {
        try {
            supportedFlavors[0] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    Object object;
    GameEngine game;
	private URL unknown;
	private int mouse_x;
	private int mouse_y;
	private int mouse_tox;
	private int mouse_toy;
	public boolean random;
	
	
	
    public dragdrop(GameEngine gameEngine) {
		// TODO Auto-generated constructor stub
    	game= gameEngine;
    	unknown=this.getClass().getResource(game.rightBoard.dir+conf.url);;
	}

	// Transferable methods.
    public Object getTransferData(DataFlavor flavor) {
        if (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)) {
            return object;
        } else {
            return null;
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType);
    }
    // DragGestureListener method.
    public void dragGestureRecognized(DragGestureEvent ev) {
    //System.out.println ("dragGestureRecognized");

        ev.startDrag(null, this, this);
    }
    // DragSourceListener methods.
    public void dragDropEnd(DragSourceDropEvent ev) {
    }

    public void dragEnter(DragSourceDragEvent ev) {
    	
    }

    public void dragExit(DragSourceEvent ev) {
        }

    public void dragOver(DragSourceDragEvent ev) {
        object = ev.getSource();
       
    }

    public void dropActionChanged(DragSourceDragEvent ev) {
    }
    // DropTargetListener methods.
    public void dragEnter(DropTargetDragEvent ev) {
       }

    public void dragExit(DropTargetEvent ev) {
    }

    public void dragOver(DropTargetDragEvent ev) {
        dropTargetDrag(ev);
    }

    public void dropActionChanged(DropTargetDragEvent ev) {
        dropTargetDrag(ev);
    }

    void dropTargetDrag(DropTargetDragEvent ev) {
        ev.acceptDrag(ev.getDropAction());
           }

    public void drop(DropTargetDropEvent ev) {
        if(ev!=null){
        	ev.acceptDrop(ev.getDropAction());
        	 try {
                 Object target = ev.getSource();
                 Object source = ev.getTransferable().getTransferData(supportedFlavors[0]);
                 Component component = ((DragSourceContext) source).getComponent();//component  
                 Container oldContainer = component.getParent();//old container
                 Container container = (Container) ((DropTarget) target).getComponent();
                 if(source!=null &&  oldContainer.isEnabled()){
                	// System.out.println("Container "+ container);//container last - comprobar si existe E aqui               
                     piece_button=vale(component);//Retorna el tipo d pieza
                     if(!started && begin){
                    	 int b=findint(component);
                    	 if(9-(container.getY()/47) < 4 && board[oldContainer.getX()/47][9-(oldContainer.getY()/47)]!=null && added[b] ){   
                    		// System.out.println("algo  "+board[oldContainer.getX()/47][9-(oldContainer.getY()/47)].getPieceType());
                    		 if(board[container.getX()/47][9-(container.getY()/47)]!=null){
                    			System.out.println("container !null");
                    			 Component compo= find(container.getX()/47,9-(container.getY()/47));
                        		 Component compo1= find(oldContainer.getX()/47,9-(oldContainer.getY()/47));
                        		 int b1= findint(compo);
                        		 int b2= findint(compo1);
                        		 if(added[b1] && added[b2]){
                        			 int oldX=oldContainer.getX()/47;
                                     int oldY=9-(oldContainer.getY()/47);
                                     int newX=container.getX()/47;
                                     int newY=9-(container.getY()/47);
                                     gamePiece oldpc=board[oldX][oldY];//spy
                                     gamePiece newpc=board[newX][newY];//scout
                                     oldpc.update_coordinates(newX, newY);
                                     board[newX][newY]=oldpc;
                               		 add(component,oldContainer,container);
                               		 newpc.update_coordinates(oldX, oldY);
                                     board[oldX][oldY]=newpc;
                            		 add(compo,container,oldContainer); 
                        		 }
                    			
                    		 }
                    		 else{
                    			 int x=findint(component);
                    			 if(added[x]){
                    				 int oldX=oldContainer.getX()/47;
                                     int oldY=9-(oldContainer.getY()/47);
                                     int newX=container.getX()/47;
                                     int newY=9-(container.getY()/47);
                                     gamePiece oldpc=board[oldX][oldY];
                                     oldpc.update_coordinates(newX, newY);
                                     board[newX][newY]=oldpc;
                                     board[oldX][oldY]=null;
                    		        add(component,oldContainer,container);
        }                           
                    			 }
                    	 }
                    	 else if(9-(container.getY()/47) < 4 && board[container.getX()/47][9-(container.getY()/47)]==null){
                    		 int X=container.getX()/47;
                    		 int Y=9-(container.getY()/47);
                    		 switch(piece_button){
                    		 case 0:
                    			 if(flag_ptr<40){
                    				 int x= findint(component);
          							player_pieces[x].update_coordinates(X,Y);
          							board[X][Y]=player_pieces[x];
                    				inside(component,true);
         							flag_ptr++;
         							pieces_down++;	 
         							game.labl.output.setText("        flag set");
                    			 }
                    			 break;
                    		 case 1:
                    			 if(one_ptr<1){
                    				 int x= findint(component);
          							player_pieces[x].update_coordinates(X,Y);
          							board[X][Y]=player_pieces[x];
         							inside(component,true);
         							one_ptr++;
         							pieces_down++;
         							game.labl.output.setText("     marshal set");
         						}
                    			 break;
                    			 case 2:
             						if(two_ptr<2){
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
             							inside(component,true);
             							two_ptr++;
             							pieces_down++;
             							game.labl.output.setText("     general set");
             						}
             						break;
             					case 3:
             						if(three_ptr<4){
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
             							inside(component,true);
             							if(three_ptr==2)
             								game.labl.output.setText("   1 colonel set");
             							if(three_ptr==3)
             								game.labl.output.setText("   2 colonel set");
             							three_ptr++;
             							pieces_down++;
             						}
             						break;
             					case 4:
             						if(four_ptr<7){
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
             							inside(component,true);
             							if(four_ptr==4)
             								game.labl.output.setText("   1 Major set");
             							if(four_ptr==5)
             								game.labl.output.setText("   2 Major set");
             							if(four_ptr==6)
             								game.labl.output.setText("   3 Major set");
             							four_ptr++;
             							pieces_down++;
             						}
             						break;
             					case 5:
             						if(five_ptr<11){
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
             							inside(component,true);
             							if(five_ptr==7)
             								game.labl.output.setText("   1 captain set");
             							if(five_ptr==8)
             								game.labl.output.setText("   2 captain set");
             							if(five_ptr==9)
             								game.labl.output.setText("   3 captain set");
             							if(five_ptr==10)
             								game.labl.output.setText("   4 captain set");
             							five_ptr++;
             							pieces_down++;
             						}
             						break;
             					case 6:
             						if(six_ptr<15){
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
             							inside(component,true);
             							if(six_ptr==11)
             								game.labl.output.setText(" 1 lieutenant set");
             							if(six_ptr==12)
             								game.labl.output.setText(" 2 lieutenant set");
             							if(six_ptr==13)
             								game.labl.output.setText(" 3 lieutenant set");
             							if(six_ptr==14)
             								game.labl.output.setText(" 4 lieutenant set");
             							six_ptr++;
             							pieces_down++;
             						}
             						break;
             					case 7:
             						if(seven_ptr<19){
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
             							inside(component,true);
             							if(seven_ptr==15)
             								game.labl.output.setText(" 1 sergeant set");
             							if(seven_ptr==16)
             								game.labl.output.setText(" 2 sergeant set");
             							if(seven_ptr==17)
             								game.labl.output.setText(" 3 sergeant set");
             							if(seven_ptr==18)
             								game.labl.output.setText(" 4 sergeant set");
             							seven_ptr++;
             							pieces_down++;
             						}
             						break;
             					case 8:
             						if(eight_ptr<24){     							
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
             							inside(component,true);
             							if(eight_ptr==19)
             								game.labl.output.setText("    1 miner set");
             							if(eight_ptr==20)
             								game.labl.output.setText("    2 miner set");
             							if(eight_ptr==21)
             								game.labl.output.setText("    3 miner set");
             							if(eight_ptr==22)
             								game.labl.output.setText("    4 miner set");
             							if(eight_ptr==23)
             								game.labl.output.setText("    5 miner set");
             							eight_ptr++;
             							pieces_down++;
             						}
             						break;
             					case 9:
             						if(nine_ptr<32){
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
             							inside(component,true);
             							if(nine_ptr==24){
             								game.labl.output.setText("    1 scout set");
             							}
             							if(nine_ptr==25){
             								game.labl.output.setText("    2 scout set");
             							}
             							if(nine_ptr==26){
             								game.labl.output.setText("    3 scout set");
             								}
             							if(nine_ptr==27){
             								game.labl.output.setText("    4 scout set");
             								}
             							if(nine_ptr==28){
             								game.labl.output.setText("    5 scout set");
             								}
             							if(nine_ptr==29){
             								game.labl.output.setText("    6 scout set");
             								}
             							if(nine_ptr==30){
             								game.labl.output.setText("    7 scout set");
             								}
             							if(nine_ptr==31){
             								game.labl.output.setText("    8 scout set");
             								}
             							nine_ptr++;
             							pieces_down++;
             						}
             						break;
             					case 10:
             						if(spy_ptr<33){ 
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
              	                		inside(component,true);   
             							game.labl.output.setText("        spy set");
             							spy_ptr++;
             							pieces_down++;
             							
             						}
             						break;
             					case 11:
             						if(bomb_ptr<39){
             							int x= findint(component);
             							player_pieces[x].update_coordinates(X,Y);
             							board[X][Y]=player_pieces[x];
             							inside(component,true);
             							/*System.out.println("xx:" +x);
             							System.out.println("xx:" +container.getX()/47);
             							System.out.println("xx:" +( 9-(container.getY()/47)));*/
             							if(bomb_ptr==33)
             								game.labl.output.setText("    1 bomb set");
             							if(bomb_ptr==34)
             								game.labl.output.setText("    2 bomb set");
             							if(bomb_ptr==35)
             								game.labl.output.setText("    3 bomb set");
             							if(bomb_ptr==36)
             								game.labl.output.setText("    4 bomb set");
             							if(bomb_ptr==37)
             								game.labl.output.setText("    5 bomb set");
             							if(bomb_ptr==38)
             								game.labl.output.setText("    6 bomb set");
             							bomb_ptr++;
             							pieces_down++;
             						}
             						break;
                    		 }//switch
                    		 add(component,oldContainer,container); 
        		             component.addMouseListener(this);//retirar mouselistener
        		             if(pieces_down==40){
		            	 this.game.randonize("Would you like to initialize a game ?",true);
                                 game.d.setVisible(true);				
						}//piece down
                    	 }//initial pos
                     }//started & !begin
                      else if(started && turn){       
                    	 turn=false;
                    	 mouse_x=oldContainer.getX()/47;
        				 mouse_y=9-(oldContainer.getY()/47);
        				 mouse_tox=container.getX()/47;
        				 mouse_toy=9-(container.getY()/47);
        				 if(!game_over){
        						if(move_piece(player_type.User, mouse_x, mouse_y,mouse_tox, mouse_toy)){
        							if(!game_over){
        								if(moves_available(false)){
        									game.labl.output.setText("Computer making move");
        					  				game.computer.make_move(this);
        					  				 applyarrow arrow= new applyarrow(this);
        					  				 Progress pro= new Progress(this);	       
        					  			}
        					        	  else{
        										game.labl.output.setText("Challenger Wins");
        										this.game.counter.destroyed();
        										this.game.clock.stop();
        										Pane myJoptionpane= new Pane("You Won ! \n Would you like to play again ?",this,1,true);
        										winner=1;    										
        										game_over=true;
        									}	
        							}//gameover	
        							else{
        								draw();
        							}
        						}//mpc user
        						else
        							turn=true;
        				 }//gameover
                     }//started
                 }
              
                          
            } 
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("kati egine");
            }
            ev.dropComplete(true);
        }
       
    }

	public void inside(Component component, boolean b) {
		// TODO Auto-generated method stub
		int x= findint(component);
 		 added[x]=b;
	}

	public int findint(Component compo) {
		// TODO Auto-generated method stub
		int n = -1;
		int b= valor(compo);
		 for(int i=0; i<conf.strpcs.length; i++){
			 if(b== conf.strpcs[i]){
				 n=i;
			 }
		 }
		return n;
	}

	public Component find(int x, int y) {
		// TODO Auto-generated method stub
		Component c = null;
		Container cc=null;
		int coordX=47*x;
		int coordY=47*(9-y);
		for (int i = 0; i < game.w.panels.length; i++) {			
			if(coordX==game.w.panels[i].getX() && coordY==game.w.panels[i].getY()){
				cc=game.w.panels[i];
				try{
					c=cc.getComponent(0);	
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
		   }
		}
		return c;
	}

	void retire() {
		// TODO Auto-generated method stub
		for(int i=0; i<game.labels.length; i++){
			game.labels[i].addMouseListener(null);
		}
	}

	public boolean moves_available(boolean player) {//here
		// TODO Auto-generated method stub
		if(player){
			for(int j=0; j<40; j++){
				if(player_pieces[j].getX()!=-1 && player_pieces[j].getPieceType()!=piece_type.Bomb && player_pieces[j].getPieceType()!=piece_type.Flag){
					int x=player_pieces[j].getX();
					int y=player_pieces[j].getY();
					if(x-1>=0 && (board[x-1][y] == null || board[x-1][y].getPlayerType()==player_type.AI))
						return true;
					if(x+1<=9 && (board[x+1][y] == null || board[x+1][y].getPlayerType()==player_type.AI))
						return true;
					if(y-1>=0 && (board[x][y-1] == null || board[x][y-1].getPlayerType()==player_type.AI))
						return true;
					if(y+1<=9 && (board[x][y+1] == null || board[x][y+1].getPlayerType()==player_type.AI))
						return true;
				}
			}
			return false;
		}//true
		else{
			for(int j=0; j<40; j++){
				if(opponent_pieces[j].getX()!=-1 && opponent_pieces[j].getPieceType()!=piece_type.Bomb && opponent_pieces[j].getPieceType()!=piece_type.Flag){
					int x=opponent_pieces[j].getX();
					int y=opponent_pieces[j].getY();
					if(x-1>=0 && (board[x-1][y] == null || board[x-1][y].getPlayerType()==player_type.User))
						return true;
					if(x+1<=9 && (board[x+1][y] == null || board[x+1][y].getPlayerType()==player_type.User))
						return true;
					if(y-1>=0 && (board[x][y-1] == null || board[x][y-1].getPlayerType()==player_type.User))
						return true;
					if(y+1<=9 && (board[x][y+1] == null || board[x][y+1].getPlayerType()==player_type.User))
						return true;
				}
			}
			return false;
		}
	}

	public boolean move_piece(player_type p_type, int x_from, int y_from, int x_to, int y_to) {
		// TODO Auto-generated method stub		
	/*	System.out.println("movepiece " + board[x_from][y_from].getPieceType());
		System.out.println("piece m"+ x_from);
		System.out.println("piece m"+ y_from);
		System.out.println("piece m"+ x_to);
		System.out.println("piece m"+ y_to);*/
				
		String s;
		if(p_type==player_type.AI){
			s="player_";
		}					
		else{
			s="player";
		}
		Component c= find(x_from,y_from);
			f.ar.arraying(c,s);//for a new pc move
		if (move_count > 1000){
			game.labl.output.setText("Stalemate");
			game_over=true;
			winner=0;
			return true;
		}
		if(board[x_from][y_from].getPlayerType()==player_type.User){//my rules
			if(move_count>=6){//includ 3 movements no >
				//System.out.println("pasas "+ move_count);//ver
				if(game.computer.breaksTwoSquareRules(x_from,y_from,x_to,y_to,move_count,this)){
					System.out.println("You break two square rule");
					return false;
				}
			}
			
		}
		
		if(board[x_from][y_from]==null){
			System.out.println("No piece there");
			return false;
		}
		if(x_to==x_from && y_to==y_from){
			System.out.println("Moving zero spaces");
			return false;
		}
		if(x_to < 0 || x_from < 0 || x_to >=10 || x_from >=10 || y_to < 0 || y_from < 0 || y_to >=10 || y_from >=10){
			System.out.println("Out of bounds");
			return false;
		}
		if(board[x_from][y_from].getPlayerType() != p_type){
			System.out.println("Moving other player's pieces");
			return false;
		}
		if(board[x_from][y_from].getPieceType()==piece_type.Bomb || board[x_from][y_from].getPieceType()==piece_type.Flag){
			System.out.println("Moving a flag or bomb");
			return false;
		}
		if((y_to==4 || y_to==5) && (x_to == 2 || x_to == 3 || x_to == 6 || x_to == 7)){
			System.out.println("Moving into a lake");
			return false;
		}
		if(!((x_from==x_to && (y_from - y_to == 1 || y_from - y_to == -1)) || (y_from==y_to && (x_from - x_to == -1 || x_from - x_to == 1)))){
			if(board[x_from][y_from].getPieceType()!=piece_type.Scout){
				System.out.println("Moving more than one square");
				return false;
			}
			if(x_from!=x_to && y_from != y_to){
				System.out.println("Moving diagonal");
				return false;
			}
			if(x_from!=x_to){
				if((y_to==4 || y_to==5) && (x_from - x_to > 2 || x_from - x_to < -2)){
					System.out.println("Moving over a lake");
					return false;
				}
				if(x_to > x_from){
					for(int i=x_from+1; i<x_to; i++){
						if(board[i][y_to]!=null){
							System.out.println("Hopping a piece");
							return false;
						}
					}
				}
				else{
					for(int i=x_to+1; i<x_from; i++){
						if(board[i][y_to]!=null){
							System.out.println("Hopping a piece");
							return false;
						}
					}
				}
			}
			if(y_from!=y_to){
				if((x_to==2 || x_to==3 || x_to==6 || x_to == 7) && ((y_from < 4 && y_to > 5) || (y_to < 4 && y_from > 5))){
					System.out.println("Hopping a lake");
					return false;
				}
				if(y_to > y_from){
					for(int i=y_from+1; i<y_to; i++){
						if(board[x_to][i]!=null){
							System.out.println("Hopping a piece");
							return false;
						}
					}
				}
				else{
					for(int i=y_to+1; i<y_from; i++){
						if(board[x_to][i]!=null){
							System.out.println("Hopping a piece");
							return false;
						}
					}
				}
			}
		}//big
		if(board[x_to][y_to]!=null){//collision 			
			gamePiece move_piece = board[x_from][y_from];//My piece
			gamePiece collision_piece = board[x_to][y_to];//pc I go
			if (move_piece.getPlayerType()==collision_piece.getPlayerType()){
				System.out.println("Collided with own piece");
				return false;
			}
			else{
				if (collision_piece.getPieceType()==piece_type.Flag){
					f.findmepan(this,x_to,y_to);//trbjar
					f.paint(conf.red,s);
					f.findmepan(this,x_from,y_from);//trbjar
					f.paint(conf.gris,s);
					move_piece.makeVisible();
					move_piece.update_coordinates(x_to, y_to);
					board[x_to][y_to]=move_piece;
					board[x_from][y_from]=null;
					if(move_piece.getPlayerType()==player_type.AI){
						System.out.println("ai victory");//no presenta el panel
						game.labl.output.setText("I WON");//joptionpane retirar el panel anterior
						winner=2;
					}
					if(move_piece.getPlayerType()==player_type.User){
						System.out.println("user victory");
						winner=1;
						game.labl.output.setText("YOU WON");//joptionpane
					}
					game_over=true;
					return true;
				}
				else if (collision_piece.getPieceType() == piece_type.Bomb && move_piece.getPieceType().valuen() != 8){
					System.out.println("Bomb");//with out to put attack
					move_piece.update_coordinates(-1, -1);
					collision_piece.makeVisible();
					board[x_from][y_from]=null;
					game.computer.update_board(x_from, y_from, x_to, y_to, 2, move_piece.getPieceType(), collision_piece.getPieceType(),this);
				}
				//!cpu
				else if(collision_piece.getPieceType() == piece_type.Spy && move_piece.getPieceType().valuen() == 1){
					System.out.println("gnl attacks spy leaves");
					
					collision_piece.update_coordinates(-1, -1);
					move_piece.makeVisible();
					move_piece.update_coordinates(x_to, y_to);
					
					board[x_to][y_to]=move_piece;
					board[x_from][y_from]=null;	
					game.computer.update_board(x_from, y_from, x_to, y_to, 2, move_piece.getPieceType(), collision_piece.getPieceType(),this);
					//!cpu
				}
				else if(collision_piece.getPieceType().valuen() == 1 && move_piece.getPieceType() == piece_type.Spy){
					System.out.println("el spia gana");
					collision_piece.update_coordinates(-1, -1);//the new pc
					f.findmepan(this,x_to,y_to);
					f.paint(conf.red,s);
					move_piece.makeVisible();
					move_piece.update_coordinates(x_to, y_to);//the old				
					board[x_to][y_to]=move_piece;
					board[x_from][y_from]=null;	
					game.computer.update_board(x_from, y_from, x_to, y_to, 1, move_piece.getPieceType(), collision_piece.getPieceType(),this);
					//!cpu
				}
				else if(collision_piece.getPieceType().valuen() > move_piece.getPieceType().valuen()){
					System.out.println("piece< othrs pcs ver");
					f.findmepan(this,x_to,y_to);
					f.paint(conf.red,s);
					f.findmepan(this,x_from,y_from);
					f.paint(conf.gris,s);
					collision_piece.update_coordinates(-1, -1);
					move_piece.makeVisible();
					move_piece.update_coordinates(x_to, y_to);
					board[x_to][y_to]=move_piece;	
					board[x_from][y_from]=null;
					game.computer.update_board(x_from, y_from, x_to, y_to, 1, move_piece.getPieceType(), collision_piece.getPieceType(),this);
					//!cpu problems//
				}
				else if(collision_piece.getPieceType().valuen() < move_piece.getPieceType().valuen()){
					System.out.println("piece > othrs pcs");//trabjar
					move_piece.update_coordinates(-1, -1);
					collision_piece.makeVisible();
					board[x_from][y_from]=null;	
					game.computer.update_board(x_from, y_from, x_to, y_to, 2, move_piece.getPieceType(), collision_piece.getPieceType(),this);
					//!cpu 
				}
				else{
					System.out.println("The same value");//No nece gris por q retira
					f.findmepan(this,x_to,y_to);//trbjar despues
					f.paint(conf.red,s);
					move_piece.update_coordinates(-1, -1);
					collision_piece.update_coordinates(-1, -1);
					board[x_from][y_from]=null;
					board[x_to][y_to]=null;
					game.computer.update_board(x_from, y_from, x_to, y_to, 3, move_piece.getPieceType(), collision_piece.getPieceType(),this);
					//!cpu
				}
				
				draw();//update move fisically
				move_count++;
				this.game.counter.destroyed();				
				return true;
				
			}
		}//if !null
		
		gamePiece piece=board[x_from][y_from];
		piece.update_coordinates(x_to, y_to);
		board[x_to][y_to]=piece;
		board[x_from][y_from]=null;
		f.findmepan(this,x_to,y_to);
		f.paint(conf.red,s);
		f.findmepan(this,x_from,y_from);
		f.paint(conf.gris);
		game.computer.update_board(x_from, y_from, x_to, y_to, 0, piece_type.Blank,piece_type.Blank,this);
		draw();//makes move
		move_count++;
		this.game.counter.destroyed();
		return true;
		
	}
	public void undo(int x_from, int y_from, int x_to, int y_to, gamePiece piecestart, gamePiece piecefinish, int cs, boolean oldvisible, boolean affectedpc) {
		// TODO Auto-generated method stub
		if(cs==0){
			gamePiece piece1=piecestart;
			System.out.println("piece1 "+ piece1.getPieceType());
			piece1.update_coordinates(x_from, y_from);
			board[x_from][y_from]=piece1;
			board[x_to][y_to]=null;
			f.findmepan(this,x_to,y_to);
			f.paint(conf.gris);
		}
		if(cs>0){
			gamePiece piece1 = piecestart;//start
			gamePiece piece2 = piecefinish;//finish			
			if(cs==1){//piece1 attacks and wins piece2 leaves
				gamePiece winner=board[x_to][y_to];
				converter(winner,piece1);
				f.findmepan(this,x_to,y_to);
				f.paint(conf.gris);
				if(piece2!=null)
				converter(winner,piece1);
				winner.update_coordinates(x_from,y_from);
				board[x_from][y_from]=winner;
				piece2.update_coordinates(x_to,y_to);
				board[x_to][y_to]=piece2;	
				System.out.println("cs1"); System.out.println("piece2 "+ piece2.getPieceType());
				//System.out.println("winner " +board[x_from][y_from].getPieceType());
			}			
			if(cs==2){//piece1 leaves  piece2 remain
				converter(board[x_to][y_to],piece2);
				if(piece1!=null)
			    piece1.update_coordinates(x_from,y_from);
				board[x_from][y_from]=piece1;
				System.out.println("piece1 "+ piece1.getPieceType());
			}
			if(cs==3){
				piece1.update_coordinates(x_from, y_from);
				board[x_from][y_from]=piece1;
				piece2.update_coordinates(x_to, y_to);
				board[x_to][y_to]=piece2;
				System.out.println("cpu3 edw "+" x_from "+ x_from+" y_from "+y_from +" x_to "+ x_to +" y_to "+y_to);
				System.out.println("cs3 attaked " + board[x_to][y_to].getPieceType() + " attaks "+board[x_from][y_from].getPlayerType());
					
				}
		}
		
		draw();
		this.game.counter.destroyed();
		game.counterClock();
		game.counter.Time(game.labl.Counter);
		if(piecestart.getPlayerType()==player_type.AI){
			this.game.computer.move_count--;
		}	
		else{
			this.game.computer.cla.tomake();
		}
	}
	private void converter(gamePiece origen, gamePiece piece2) {
		// TODO Auto-generated method stub
		if(piece2.isVisible())
			origen.makeVisible();
		else
			origen.makeInvisible();
	}
	public int vale(Component component) {
		// TODO Auto-generated method stub
		Integer s =valor(component);
		Integer val = null;
		for(int k=0;k<conf.strpcs.length;k++){
			if(s== conf.strpcs[k]){
				val=player_pieces[k].getPieceType().valuen();
			}
		}
		return val;
	}
    private Integer valor(Component component){
    	String str=component.getName();
		Integer s;
		s=Integer.parseInt(str);
    	return s;
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	private Container parient(Component source, JPanel[] panels) {
		// TODO Auto-generated method stub
		Integer s =valor(source);
		Container c = null;
		for(int v=0;v<game.panels.length;v++){
			if(s==v){
				c=game.panels[v];
			}
		}
		return c;
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	 
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == MouseEvent.BUTTON3 && random){
				Component source= (Component) e.getSource();
				Container oldc= source.getParent();
				Container c = parient(source,game.w.panels);
                    Diminish diminish = new Diminish(this,source);
			    add(source,oldc,c); 
				((Component) e.getSource()).addMouseListener(null);	
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
    void draw(){
    	if(!game_over){
    		for(int x=0; x<40; x++){//drawpiece       
          		Search(opponent_pieces[x],x);
          		Search(player_pieces[x],x);
          	   }
    	}
    	if(game_over && winner!=0){
    		if(winner==1){//Challenger
    			game.labl.output.setText("YOU WON");
    			this.game.counter.destroyed();
			this.game.clock.stop();
                Pane myJoptionpane = new Pane("You Won ! \n Would you like to play again ?",this,1,true);
    		}
    		else if(winner==2){//Crear JOptiopane
    			game.labl.output.setText("I WON");
    			this.game.clock.stop();
    		Pane myJoptionpane=new Pane("I Won ! \n Would you like to play again ?",this,1,true);
    			//gnl clock stops
    		}//computer
    	}//gmeover
    	else if(game_over && winner==0){
    		game.labl.output.setText("DRAW !");
                this.game.counter.destroyed();
		this.game.clock.stop();
    		Pane myJoptionpane= new Pane("Draw ! \n Would you like to play again ?",this,1,true);
    	}///Stalemate
    }
	 void draw_image(int x, int y,JLabel object, URL unknown) {
		// TODO Auto-generated method stub
		ImageIcon ico= new ImageIcon(unknown);
		object.setIcon(ico);
		Component source=object;
		Container oldc= source.getParent();
		Container c= f.findmepan(this,x,y);
		add(source,oldc,c);		
	}
	
	void add(Component source, Container oldc, Container c) {
		// TODO Auto-generated method stub
		c.add(source);
		oldc.validate();
        oldc.repaint();
        c.validate();
        c.repaint();
	}
	void Search(gamePiece piece, int x){
		if(!piece.isVisible() && !game_over){		
			if(piece.getX()==-1 && piece.getY()==-1){				
				if(piece.getPlayerType()==player_type.AI){
				   check(this.game.computer.gone,x,this.game.computer.move_counter,move_count,game.computer.initiatedby1,player_type.AI,"dnd AI invisible"); 
                   retorno_panel(game.rightBoard.labels[conf.strpcs[x]],game.rightBoard.panels[conf.strpcs[x]],game.rightBoard.urls[conf.strpcs[x]]);       
				}
				if(piece.getPlayerType()==player_type.User){
					check(this.game.computer.gone,x,this.game.computer.move_counter,move_count,game.computer.initiatedby1,player_type.User,"dnd user invisible"); 
					retorno_panel(game.labels[conf.strpcs[x]],game.panels[conf.strpcs[x]],game.urls[conf.strpcs[x]]);
				}
				}//out
			else{
				draw_image(piece.getX(),piece.getY(),game.rightBoard.labels[conf.strpcs[x]],unknown);
			   }
			}//!visible
		else{
			if(piece.getPlayerType()==player_type.AI){
				if(piece.getX()==-1 && piece.getY()==-1){
					check(this.game.computer.gone,x,this.game.computer.move_counter,move_count,game.computer.initiatedby1,player_type.AI,"dnd AI visible");
					retorno_panel(game.rightBoard.labels[conf.strpcs[x]],game.rightBoard.panels[conf.strpcs[x]],game.rightBoard.urls[conf.strpcs[x]]);
								}
				else{
					draw_image(piece.getX(),piece.getY(),game.rightBoard.labels[conf.strpcs[x]],game.rightBoard.urls[conf.strpcs[x]]);
				}
				   }
			else{//user
				if(piece.getX()==-1 && piece.getY()==-1){
					check(this.game.computer.gone,x,this.game.computer.move_counter,move_count,game.computer.initiatedby1,player_type.User,"dnd user visible");
					retorno_panel(game.labels[conf.strpcs[x]],game.panels[conf.strpcs[x]],game.urls[conf.strpcs[x]]);
				}
				else{
					draw_image(piece.getX(),piece.getY(),game.labels[conf.strpcs[x]],game.urls[conf.strpcs[x]]);
				}
				}
		}//visible
	}
	
	public void check(ArrayList<Integer> retired, int x, ArrayList<Integer> move_counter, int mc,ArrayList<Object> list, player_type type, String temp) {
		// TODO Auto-generated method stub
		if(!retired.contains(x) && x!=-1){
			retired.add(x);//Eaz
			list.add(type);
			move_counter.add(mc);//mcounter
        } 
	}

	private void retorno_panel(JLabel jLabel, JPanel jPanel, URL unknown) {
		// TODO Auto-generated method stub
		ImageIcon ico= new ImageIcon(unknown);
		jLabel.setIcon(ico);
		Component source= (Component) jLabel;
		f.findmepan(source);
		f.paint(conf.gris);
		Container oldc= source.getParent();
		Container c = jPanel;
		add(source,oldc,c); 
	}

	void random_setup(){
		if(random){
			 random=false;
			//int num = -1;
			/* game.orderingpieces(this); 
			 for(int x=0; x<40; x++){//drawpiece       
		      		Search(opponent_pieces[x],x);
		      	   }*/
		        for(int i=0; i<40; i++){
		    		int x_coord=(int) (Math.random() * 10);
		    		int y_coord=(int) (Math.random() * 4);
		    		while(board[x_coord][y_coord]!=null){
		    			x_coord=(int) (Math.random() * 10);
		    			y_coord=(int) (Math.random() * 4);
		    		}
		    		player_pieces[i].update_coordinates(x_coord, y_coord);
		    		board[x_coord][y_coord]=player_pieces[i];  
		    		//System.out.println(player_pieces[i].getPieceType());
		    		    	      	
		    	}
 /* for(int i=0;i<40;i++){
		        	if(player_pieces[i].getX()==0 && player_pieces[i].getY()==3){
		        		System.out.println("i " +i);
		        		 num=i;
		        	}
		    	    if(i==25){
		    	    	int X=player_pieces[i].getX();
                        int Y=player_pieces[i].getY();
                        player_pieces[num].update_coordinates(X, Y);
    		    		board[X][Y]=player_pieces[num];
    		    		player_pieces[25].update_coordinates(0, 3);
 		    			board[0][3]=player_pieces[25];
		    	    }
		        
		        }*/
		        for(int i=0;i<40;i++){
		        	Search(player_pieces[i],i);
		    	//  game.labels[i].addMouseListener(this);
		        }
		        this.pieces_down=40;
		       game.randonize("Would you like to initialize a game ?",false);
                       game.d.setVisible(true);
		       //1,3
		 }
	}

	
}

