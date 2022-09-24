package mistra;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
public class flag {
	array ar= new array();
	Container c=null;
	public void paint(Color co, String s) {
		// TODO Auto-generated method stub
		ArrayList<Object> comp = null;
		if("player".equals(s)){
			 comp= ar.Components;	
		}
		else{
			comp=ar.Component_oponent;
		}
		paint(co);
		if(!comp.isEmpty()){
			if(comp.size()>1){
				  try {
					  for(int i=0;i<comp.size();i++){
						  if(i!=comp.size()-1){
							  Component c=(Component) comp.get(i);
								findmepan(c);
								paint(conf.gris);
								comp.remove(i);	
						  }
					  }
				    }
				    catch( Exception ex ) {
				    	System.out.println(ex.getMessage());
				    }
				
			}
			
		}
	}
	
	public void paint(Color co) {
		// TODO Auto-generated method stub
		((JComponent) c).setBorder(BorderFactory.createLineBorder(co));
	}

	public Container findmepan(Component source){
		return c=source.getParent();
		
	}
  
	public Container findmepan(dragdrop dnd, int x, int y) {
		// TODO Auto-generated method stub
		int coordX=47*x;
		int coordY=47*(9-y);
		for (int i = 0; i < 100; i++) {			
			if(coordX==dnd.game.w.panels[i].getX() && coordY==dnd.game.w.panels[i].getY()){
				c= (Container) dnd.game.w.panels[i];
		   }
		}
		return c;
	}
	 class array{
			ArrayList<Object> Components= new ArrayList<Object>();
			ArrayList<Object> Component_oponent= new ArrayList<Object>();
		void arraying(Component source,String s){
			ArrayList<Object> c;
			if("player".equals(s))
			 c= Components;
			else{
				c=Component_oponent;
			}				
			
			if(!c.contains(source)){
				c.add(source);
       	      }		
		}//
	
	}
}