package mistra;
public class applyarrow {

	private dragdrop d;
    private int x;
    private int y;
    private Direction dir;
	public applyarrow(dragdrop d) {
		// TODO Auto-generated constructor stub
		this.d= d;
		dir();	
		set();
	}
	private void set() {
		// TODO Auto-generated method stub
		d.game.w.myarrow.setDirection(dir);
		d.game.w.myarrow.setSize(47, 47);
        d.game.w.myarrow.setLocation(x,y);
        d.game.w.myarrow.setVisible(true);
	}

	private void dir() {
		// TODO Auto-generated method stub
		int xfrom = d.game.computer.x_from;
		int yfrom = d.game.computer.y_from;
		int xto = d.game.computer.x_to;
		int yto = d.game.computer.y_to;
		int coordX=47*xto;
		int coordY=47*(9-yto);
		if(xfrom== xto){
			if(yfrom <yto)//up
			{
				dir= Direction.UP;
				y=coordY+6;
			}
				
			else//down
			{   dir=Direction.DOWN;		
				y=coordY-6;
			}
			x=coordX;
			
		}
		else{
			if(xfrom <xto)//right
			{	dir= Direction.RIGHT;
				x=coordX-6;
			}
				
			else//down
			{  dir=Direction.LEFT;
				x=coordX+6;
			}

			y=coordY;	
		}
	}
}
