package mistra;

import java.io.Serializable;


public class gamePiece implements Serializable {
    private int x_coord;
    private int y_coord;
    private boolean visible;
    public player_type player;
    public piece_type type;


  public gamePiece(piece_type the_type, player_type the_player){
		this.type = the_type;
		this.player = the_player;
		if(the_player==player_type.User)
			visible=true;
		if(the_player==player_type.AI)
			visible=false;
   
                
     }
  gamePiece(){}
   public void update_coordinates(int x, int y){
	x_coord=x;
	y_coord=y;
   }

   public boolean isVisible(){
	return visible;
    }
   player_type getPlayerType(){
	return player;
  }

   piece_type getPieceType(){
	return type;
   }

   public int getX(){
	return x_coord;
   }

   public int getY(){
	return y_coord;
   }

   public void makeVisible(){
	visible=true;
   }

   public void makeInvisible(){
	visible=false;
   }


}