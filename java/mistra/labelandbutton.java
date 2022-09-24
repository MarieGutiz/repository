package mistra;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class labelandbutton {
	Container f;
    JLabel[] abc;
    URL[] uri= new URL[5];
    JButton[] btn= new JButton[5];
    JLabel output= new JLabel();//label to setText
    JLabel Counter= new JLabel();//Counter
    JLabel Clock= new JLabel();//Gnl clock
	public labelandbutton(Container cp) {
		// TODO Auto-generated constructor stub
		this.f=cp;
		JPanel a = new JPanel();
		a.setLayout(null);
		a.setBounds(196, 16, 12, 469);
		a.setBackground(new Color(70,70,70));
		abc= new JLabel[10];
		 for(int l=0;l<10;l++){
			 abc[l]= new JLabel(""+(l+1),JLabel.CENTER);
			 abc[l].setFont(new Font("Serif", Font.BOLD, 10));
				abc[l].setBounds(0, l*(47)+18, 15,7);
				abc[l].setForeground(Color.gray);
				a.add(abc[l]);
		 } 
		/* deutero plaisio*/
		cp.add(a);
		 for(int l=0;l<10;l++){
			 abc[l]= new JLabel(""+conf.column[l],JLabel.RIGHT);
			 abc[l].setFont(new Font("Serif", Font.BOLD, 10));
				abc[l].setBounds(l*(47)+209+12, 487,15,7);
				abc[l].setForeground(Color.gray);
				cp.add(abc[l]);
		 }
		
		/*Btons*/
		     for(int b=0;b<4;b++){
		    	 uri[b] = this.getClass().getResource("images/icons/"+conf.icons[b]); 
				  ImageIcon ico=new ImageIcon(uri[b]);
			         btn[b]= new JButton();  
			         btn[b].setIcon(ico);
			         btn[b].setBorder(null);
				     btn[b].setBounds((b*40)+10, 487, 20, 20);
				     btn[b].setBackground(new Color(70,70,70));
				     btn[b].setCursor(new Cursor(Cursor.HAND_CURSOR));
				     btn[b].setToolTipText(conf.texts[b]);
				     cp.add(btn[b]);
		     }
		     
		     /*label output*/
		     output.setBounds(160, 484, 560,50);
		     output.setHorizontalAlignment(JLabel.CENTER);
		     output.setForeground(new Color(236,247,245));	 
		     output.setFont(new Font("Serif", Font.BOLD, 15));
             cp.add(output);  
             
             /*label Counter*/
             Counter.setBounds(-20, 507,250,50);
             Counter.setHorizontalAlignment(JLabel.CENTER);
             Counter.setForeground(new Color(236,247,245));	 
             Counter.setFont(new Font("Serif", Font.BOLD, 15));
             cp.add(Counter); 
             
             /*label Clock*/
             Clock.setBounds(310, 507,250,50);
             Clock.setHorizontalAlignment(JLabel.CENTER);
             Clock.setForeground(new Color(236,247,245));	 
             Clock.setFont(new Font("Serif", Font.BOLD, 15));
             cp.add(Clock); 
	}

	
}
