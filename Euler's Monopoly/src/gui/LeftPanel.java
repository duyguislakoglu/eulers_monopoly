package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import domain.game.GameControler;


public class LeftPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2214947872942931388L;
	JPanel euler;
	ButtonPanel buttonPanel;
	JPanel dicePanel;
	CardPanel cardPanel;
	JLabel diceArea_3;
	JLabel diceArea_2;
	JLabel diceArea_1;
	JLabel labelEuler;
	ImageIcon diceIcon1;
	ImageIcon diceIcon2;
	ImageIcon diceIcon3;
	ImageIcon diceIcon4;
	ImageIcon diceIcon5;
	ImageIcon diceIcon6;
	ImageIcon mrMonopoly;
	JTextArea currentPlayerArea;
	JTextArea poolArea;
	
	private void init(){
		diceIcon1 = help("/1.png",50,50);
		diceIcon2 = help("/2.png",50,50);
		diceIcon3 = help("/3.png",50,50);
		diceIcon4 = help("/4.png",50,50);
		diceIcon5 = help("/5.png",50,50);
		diceIcon6 = help("/6.png",50,50);
	//	mrMonopoly = help("/mrmonopoly.png", 50, 50);
		euler = new JPanel();
		dicePanel = new JPanel();
		diceArea_1 = new JLabel();
		diceArea_2 = new JLabel();
		diceArea_3 = new JLabel();
		cardPanel = new CardPanel();
	}

	public LeftPanel(){
			buttonPanel = new ButtonPanel();
			init();
			setLayout(new BorderLayout());
			setLocation(0, 0);
			setSize(300,700);
			setBackground(Color.BLACK);

			euler.setLayout(new BorderLayout());
			euler.setLocation(0, 0);
			euler.setSize(300,150);
			add(euler);

			labelEuler = new JLabel();
			labelEuler.setVisible(true);
			labelEuler.setIcon(help("/eulers.jpg", 262, 150));	
			euler.add(labelEuler);
			euler.setBackground(Color.black);

			add(buttonPanel);

			dicePanel.setLayout(new FlowLayout());
			dicePanel.setLocation(0,350);
			dicePanel.setSize(300,65);
			dicePanel.setBackground(Color.black);
			dicePanel.add(diceArea_1);
			dicePanel.add(diceArea_2);
			dicePanel.add(diceArea_3);
			diceArea_1.setIcon(diceIcon1);
			diceArea_2.setIcon(diceIcon2);
			diceArea_3.setIcon(diceIcon3);
			add(dicePanel);
		
			JPanel info=new JPanel();
			info.setLayout(new GridLayout(2,1));
			info.setBackground(Color.BLACK);
			currentPlayerArea = new JTextArea();
			currentPlayerArea.setFont(new Font("Calibri", Font.PLAIN, 14));
			currentPlayerArea.setText("Current Player: ");
			currentPlayerArea.setBackground(Color.BLACK);
			currentPlayerArea.setEditable(false);
			currentPlayerArea.setSize(new Dimension (300,30));
			currentPlayerArea.setForeground(Color.white);
			currentPlayerArea.setVisible(true);
			info.add(currentPlayerArea);
			info.setSize(300,60);
			info.setLocation(0,415);

			
			setLayout(null);
			poolArea = new JTextArea();
			poolArea.setFont(new Font("Calibri", Font.PLAIN, 14));
			poolArea.setText("Pool: $");
			poolArea.setBackground(Color.BLACK);
			poolArea.setEditable(false);
			poolArea.setSize(new Dimension (300,30));
			poolArea.setForeground(Color.white);
			poolArea.setVisible(true);
			info.add(poolArea);
			add(info);
			
			
			setLayout(new BorderLayout());
			cardPanel = new CardPanel();
			cardPanel.setLocation(0,435);			
	
			JPanel bottom =new JPanel();

			bottom.setBackground(Color.BLACK);
			bottom.add(cardPanel);
			cardPanel.setLocation(0, 0);
	
			
			bottom.setSize(300,240);
			add(bottom,BorderLayout.SOUTH);

	}

	// helper method for loading the image, transferring it and scaling it.
	protected ImageIcon help (String s, int width, int height){
		ImageIcon imageIcon = new javax.swing.ImageIcon(getClass().getResource(s)); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); 
		return new ImageIcon(newimg);
	}
	
	public void setDefaultCard(){
		cardPanel.updateCard("defaultCard");
	};
	
	private ImageIcon getIcon(int n){
		switch (n){
			case 1:
				return diceIcon1;
			case 2:
				return diceIcon2;
			case 3:
				return diceIcon3;
			case 4:
				return diceIcon4;
			case 5:
				return diceIcon5;
			case 6:
				return diceIcon6;
			default:
				return diceIcon1;
		}
	}
	
	public void updateResults(int[] diceResult){
		diceArea_1.setIcon(getIcon(diceResult[0]));
		diceArea_2.setIcon(getIcon(diceResult[1]));
		diceArea_3.setIcon(getIcon(diceResult[2]));
	}
	
/*	
	public Icon getIconForSpeedDie(int faceValue) {
		if(faceValue ==1 ) return getIcon(1);
		if(faceValue ==2 ) return getIcon(2);
		if(faceValue ==3 ) return getIcon(3);
		else return mrMonopoly;
	}
*/
	
	public void updateCurrentPlayer(){
		currentPlayerArea.setText("                  Current Player: ");
		currentPlayerArea.append(GameControler.getInstance().getPlayerName(GameControler.getInstance().getCurrentPlayerOrder()));
	}

	public void updatePool(){
		poolArea.setText("                  Pool: $");
		poolArea.append(Integer.toString(GameControler.getInstance().getPoolMoney()));
	}

}
