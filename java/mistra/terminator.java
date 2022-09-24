package mistra;
public class terminator implements Memento {
	 private int x_coord;
	 private int y_coord;
	 private player_type player;
	 private piece_type type;
	 private boolean visible;
	 private int cs;
	terminator(int x_coord,int y_coord,piece_type the_type, player_type the_player,boolean visible, int cs){
		this.x_coord= x_coord;
		this.y_coord= y_coord;
		this.type=the_type;
		this.player=the_player;
		this.cs=cs;
	}
    @Override
	 public player_type getPlayerType(){
			return player;
		  }

    @Override
	 public piece_type getPieceType(){
			return type;
		   }

    @Override
		   public int getX(){
			return x_coord;
		   }

    @Override
		   public int getY(){
			return y_coord;
		   }
		   
    @Override
    public boolean isVisible(){
				return visible;
			    }
    
    @Override
    public int collisionstate(){
		return cs;
	    }
    public String del(){
        String s = "";
        return s;
    }
	
}

