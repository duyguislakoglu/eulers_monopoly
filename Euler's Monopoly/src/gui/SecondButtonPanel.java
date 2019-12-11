package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.game.ActionTypes;
import domain.game.GameControler;

public class SecondButtonPanel extends JPanel{
	JButton saveB;
	JButton pauseB;
	JButton resumeB;
	
	public SecondButtonPanel(){
		setLayout(new GridLayout(1,3));
		setLocation(0,365);
		setSize(300,40);
		setBackground(Color.black);
		initButtons();
		add(saveB);
		add(pauseB);
		add(resumeB);
	}
	
	public void initButtons(){
		//TODO: Change these images to the right ones
		
		
		ArrayList<ActionTypes> legalActions = new ArrayList<ActionTypes>();
		//TODO: Implement legalActions for this class
	}
	
	public void disableIllegalButtons(ArrayList<ActionTypes> legalActions){
		//TODO
	}
		
	private ImageIcon help (String s, int width, int height){
		ImageIcon imageIcon = new javax.swing.ImageIcon(getClass().getResource(s)); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); 
		return new ImageIcon(newimg);
	}
}
