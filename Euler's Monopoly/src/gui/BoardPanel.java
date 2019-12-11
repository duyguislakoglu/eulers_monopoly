package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 640413654387940509L;
	private Image boardImage;

	public BoardPanel(){
		setLayout(new BorderLayout());		
		setLocation(300, 0);
		setSize(700,700);
		addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) { 
	            System.out.println("X: " + me.getX() + "Y: " + me.getY()); 
	          } 
	        }); 

		boardImage = new ImageIcon(getClass().getResource("/Board700x700.jpg")).getImage();
	}


	public void paint(Graphics g) {
		g.drawImage(boardImage, 0, 0, null);
	}
}
