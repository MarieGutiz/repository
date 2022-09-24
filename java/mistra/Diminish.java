package mistra;
import java.awt.Component;
public class Diminish {
        private dragdrop dnd;
	private Component label;
	private int piece_button;
	public Diminish(dragdrop dragdrop, Component source) {
		// TODO Auto-generated constructor stub
		this.dnd=dragdrop;
		this.label=source;
		make();		
	}
	private void make() {
		// TODO Auto-generated method stub
		piece_button= dnd.vale(label);
		 switch(piece_button){
		 case 0:
			 if(dnd.flag_ptr==40){
				 dnd.flag_ptr--;
				 int x= dnd.findint(label);
				 clean(x);	
				 dnd.game.labl.output.setText("        flag retired");
					
			 }
			 break;
		 case 1:
			 if(dnd.one_ptr==1){
				 dnd.one_ptr--;
				 int x= dnd.findint(label);
				 clean(x);	
				 dnd.game.labl.output.setText("     marshal retired");
				}
			 break;
		 case 2:
				if(dnd.two_ptr==2){
					dnd.two_ptr--;
					int x= dnd.findint(label);
					 clean(x);	
					dnd.game.labl.output.setText("     general retired");
				}
				break;
		 case 3:
				if(dnd.three_ptr>2){
						dnd.three_ptr--;
						int x= dnd.findint(label);
						 clean(x);
						dnd.game.labl.output.setText("    1 colonel retired");
									
				}
				break;
			case 4:
					if(dnd.four_ptr>4){
							dnd.game.labl.output.setText("    1 Major retired");
							dnd.four_ptr--;
							int x= dnd.findint(label);
							 clean(x);
					}
					break;
					
			case 5:
					if(dnd.five_ptr>7){
						dnd.game.labl.output.setText("    1 captain retired");
						dnd.five_ptr--;
						int x= dnd.findint(label);
						 clean(x);
					}
					break;	
			case 6:
					if(dnd.six_ptr>11){
						dnd.game.labl.output.setText("    1 lieutenant retired");
						dnd.six_ptr--;
						int x= dnd.findint(label);
						 clean(x);
					}
					break;
			case 7:
					if(dnd.seven_ptr>15){
						dnd.game.labl.output.setText("    1 sergeant retired");
						dnd.seven_ptr--;
						int x= dnd.findint(label);
						 clean(x);
					}
					break;
			case 8:
					if(dnd.eight_ptr>19){
							dnd.game.labl.output.setText("    1 miner retired");
							dnd.eight_ptr--;
							int x= dnd.findint(label);
							 clean(x);
					}
					break;
			case 9:
					if(dnd.nine_ptr>25){
							dnd.game.labl.output.setText("     1 scout retired");
							dnd.nine_ptr--;
							int x= dnd.findint(label);
							 clean(x);
					}
					break;
		 case 10:
			 if(dnd.spy_ptr==33){
			 dnd.spy_ptr--;
			 int x= dnd.findint(label);
			 clean(x);
		         dnd.game.labl.output.setText("        spy retired");
			 }
			 break;
			case 11:
					if(dnd.bomb_ptr>33){
							dnd.game.labl.output.setText("     1 bomb retired");
							dnd.bomb_ptr--;
							int x= dnd.findint(label);
							clean(x);
					}
					break;	 
		 }
	}

	private void clean(int x) {
		// TODO Auto-generated method stub		
			dnd.pieces_down--;
			int X=dnd.player_pieces[x].getX();
			int Y=dnd.player_pieces[x].getY();
			if(X!=-1&& Y!=-1){
				dnd.board[X][Y]=null;
			    dnd.player_pieces[x].update_coordinates(-1,-1);
			    dnd.inside(label, false);	
			}
	}	
}//class
