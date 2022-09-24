package mistra;
import java.util.ArrayList;
public class clasify {
     private int x;
     private int y;
     private gamePiece p;
     private dragdrop dnd;
     private int indentificator; 
     protected int idx;
     private int cs;//collision state
     public ArrayList<Memento> AI;
     public ArrayList<Memento> AI_FX;
     public ArrayList<Memento> USER;	
     public ArrayList<Memento> USER_FX;
	 private boolean mybool=true;
     private boolean entranc=true;
      clasify(){
    	 AI= new ArrayList<Memento>();
         AI_FX= new ArrayList<Memento>();
         USER= new ArrayList<Memento>();	
         USER_FX= new ArrayList<Memento>();
    	 idx=0;
     }
	public void clasifying(int x,int y,gamePiece p, int iden, dragdrop dnd, int cs){
		this.x=x;
		this.y=y;
		this.p=p;
		this.indentificator=iden;
		this.idx=dnd.game.computer.move_count; 
		this.dnd=dnd;
		this.cs=cs;
		setThis(); 
	}
	private void setThis() {
		// TODO Auto-generated method stub
		Memento ter= new terminator(x,y,p.getPieceType(),p.getPlayerType(),p.isVisible(),cs);
		ArrayList<Memento> temp = null;
	  switch(this.indentificator){	  
	  case 1://AI
		  temp=AI;
		  break;
	  case 2://AI_FX
          temp=AI_FX;
		  break;
	  case 3://USER
		  temp=USER;
		  break;
	  case 4://USER_FX
		  temp=USER_FX;
		  break;
	  }
			Do(ter,temp);
		
	}
	public void undo(){
		int x_from;
		int y_from;
		player_type type;
		boolean visible1;
		boolean visible2;
		int x_to;
		int y_to;
		System.out.println("idx clasify "+idx);
        if(mybool){
        	mybool=false;
      	  if(CanUndo()&& !dnd.game_over){
    		  if (idx==0)
    			{
    				try {
    					throw new Exception("Invalid index.");
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			--idx;
    			 dnd.move_count-- ;
    		  Memento m;		  
    		  m=Undo(AI);
    		  x_from=m.getX();
    		  y_from=m.getY();
    		  visible1=m.isVisible();
    		  
    		  m=Undo(AI_FX);
    		  type=m.getPlayerType();//affected
    		  visible2=m.isVisible();
    		  x_to=m.getX();
    		  y_to=m.getY();
    		  //undo ai's
    		  System.out.println("dnd.move_count antes ai's " +dnd.move_count);
    		  System.out.println("type clasify ais " + type +" x_from "+x_from +" y_from  "+y_from +" x_to "+x_to +" y_to "+y_to + " collisionst "+  m.collisionstate());
    		  dnd.game.computer.undoing(x_from, y_from, x_to, y_to, m.collisionstate(), type, dnd,visible1,visible2);
    		  dnd.move_count-- ;//1st ai's
    		
    		  //undo user's
    		  m=Undo(USER);
    		  x_from=m.getX();
    		  y_from=m.getY();
    		  visible1=m.isVisible();
    		  
    		  m=Undo(USER_FX);
    		  type=m.getPlayerType();//affected
    		  visible2=m.isVisible();
    		  x_to=m.getX();
    		  y_to=m.getY();
    		  System.out.println("dnd.move_count user despues user's " +dnd.move_count);
    		  System.out.println("type clasify user " + type +" x_from "+x_from +" y_from  "+y_from +" x_to "+x_to +" y_to "+y_to);
    		  dnd.game.computer.undoing(x_from, y_from, x_to, y_to, m.collisionstate(), type, dnd,visible1,visible2);//work with boolean
    	    	  }
       }
	}
	public void clear(){
		AI.clear();
		AI_FX.clear();
		USER.clear();
		USER_FX.clear();
		idx=0;
	}
	private boolean CanUndo()
	{
		return idx > 0;
	}

	private void Do(Memento mem,ArrayList<Memento> buffer)//insert
	{
		if(buffer.size()<=idx && !entranc){
			entranc=true;
			//System.out.println("buffer.size()<=idx");
		}
		if (buffer.size() > idx && entranc) //pass 1 time
		 { entranc=false; 
		/*System.out.println("removed element " + USER.size());
		System.out.println("idx " + idx);*/
			removeRange(idx, USER.size()-idx,USER);  
			removeRange(idx, USER_FX.size()-idx,USER_FX);  
			removeRange(idx, AI.size()-idx,AI);  
			removeRange(idx, AI_FX.size()-idx,AI_FX);  
			System.out.println("USER.size() " +USER.size()+" USER_FX.size() "+USER_FX.size()+" AI.size()"+AI.size()+" AI_FX.size() "+ AI_FX.size());
		}
	  //  System.out.println("pass ");
		buffer.add(mem);
	}
	private void removeRange(int idx2, int i, ArrayList<Memento> buffer) {
		// TODO Auto-generated method stub
		//System.out.println("ba "+buffer.size());
		for(int d=i-1;d>=idx2;d--){
			System.out.println("d => " +d);
			System.out.println("buffer" +buffer.get(d)); 
			buffer.remove(d);
		}
	}
	private Memento Undo(ArrayList<Memento> buffer)//undo
	{		return (Memento)buffer.get(idx);
	}
	public void tomake() {
		// TODO Auto-generated method stub
		mybool=true;
	}
}
