package mistra;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
public class MIGIWindow extends JPanel{
    JLayeredPane layeredPane;
    JPanel straBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;
    JPanel[] panels= new JPanel[100];
    arrow myarrow;
	public MIGIWindow(){
	     Dimension boardSize = new Dimension(470,470);
	        this.setBackground(new Color(70,70,70));  
	        layeredPane = new JLayeredPane();
	        this.add(layeredPane);
	        layeredPane.setPreferredSize(boardSize);
	        URL url= this.getClass().getResource("images/bgr/board.jpg");
	        JLabel boar= new JLabel(new ImageIcon(url));
	        straBoard = new JPanel();
                myarrow= new arrow();
	        layeredPane.add(myarrow);	
	        layeredPane.add(straBoard);
	        layeredPane.add(boar);
	        straBoard.setLayout( new GridLayout(10, 10) );
	        boar.setBounds(0, 0, 470,470);
	        straBoard.setBounds(0, 0, 470,470);
	        straBoard.setOpaque(false);
	        for (int i = 0; i < 100; i++) {
	            JPanel square = new JPanel( new BorderLayout() );
	            square.setOpaque(false);
	            straBoard.add( square );
	            panels[i] = (JPanel)straBoard.getComponent(i);
	            if(i!=42 && i!=43 && i!=52 && i!=53 && i!=46 && i!=47 && i!=56 && i!=57){
		        	 square.setBorder(BorderFactory.createLineBorder(conf.gris));
		        	  square.setCursor(new Cursor(Cursor.HAND_CURSOR));
		          }
	        }
	      
	}
	    }