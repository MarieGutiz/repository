package mistra;

import javax.swing.JOptionPane;
public class Pane {
	private static final long serialVersionUID = conf.Mariela;
	public Pane(String string,  final dragdrop dnd, int i, final boolean b) {
	switch(i){
	case 1:
		int n = JOptionPane.showConfirmDialog(
			    dnd.game.f, 
			    string, 
			    "Game Over",
			    JOptionPane.YES_NO_OPTION);
		 if (n == JOptionPane.YES_OPTION) {	
                     dnd.game.clock.destroyed();	
			 dnd.game.counter.destroyed();
		  dnd.game.reset(dnd);			 
         }
		break;			
	}	
	}

}