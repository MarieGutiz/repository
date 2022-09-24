package mistra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
public class Myboard extends JPanel{
	private static final long serialVersionUID = conf.Mariela;
	private JLayeredPane layeredPane;
	private JPanel rightBoard;
    JPanel [] panels= new JPanel[40];
    JLabel [] labels = new JLabel[40];
    URL [] urls = new URL[40];
    public String dir="images/pieces/browns/"; 
	public Myboard(){
		 Dimension boardSize = new Dimension(188,470);	     
	     //  Use a Layered Pane for this this application
		 this.setBackground(new Color(70,70,70));  
	     layeredPane = new JLayeredPane();
	     this.add(layeredPane);
	     layeredPane.setPreferredSize(boardSize);
	     URL url1 = this.getClass().getResource("images/bgr/right.jpg");
	     JLabel boar= new JLabel(new ImageIcon(url1));
	     rightBoard = new JPanel();
	     layeredPane.add(rightBoard);
	     layeredPane.add(boar);
	     rightBoard.setLayout( new GridLayout(10, 4) );
	     boar.setBounds(0, 0, 188,470);
	     rightBoard.setBounds(0, 0, 188,470);
	     rightBoard.setOpaque(false);
	     for (int i = 0; i < 40; i++) {
	         JPanel square = new JPanel( new BorderLayout() );
	         square.setOpaque(false);
	         rightBoard.add( square );
	         square.setBorder(BorderFactory.createLineBorder(new Color(70,70,70)));
	     }	     
	     for(int k=0; k < conf.strurls.length;k++){
	    	   urls[k] = this.getClass().getResource(dir+conf.strurls[k]);
	    	   labels[k] = new JLabel( new ImageIcon(urls[k]) );
	    	   labels[k].setName(""+k);
	    	   panels[k] = (JPanel)rightBoard.getComponent(k);
	    	   panels[k].add(labels[k]);
	    	   
	     }
	}
}
