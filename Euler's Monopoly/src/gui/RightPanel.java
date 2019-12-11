package gui;
import domain.game.GameControler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class RightPanel extends JPanel{
	private static final long serialVersionUID = -5552814572981865482L;
	JLabel labelUltimate;
	JPanel players;
	JPanel ultimate;
	private ArrayList<JTextArea> areas = new ArrayList<JTextArea>();
	final JTextArea textArea_Player0 = new JTextArea();
	//
	public RightPanel(){	 
		setLayout(new BorderLayout());
		setLocation(1000, 0);
		setSize(300,700);
		setBackground(Color.BLACK);
		ultimate = new JPanel();
		ultimate.setLayout(new BorderLayout());
		ultimate.setLocation(0, 0);
		ultimate.setSize(300,150);
		add(ultimate, BorderLayout.NORTH);
		players = new JPanel();
		players.setLayout(new BorderLayout());
		players.setLocation(0, 150);
		players.setSize(300,400);
		players.setBackground(Color.black);
		add(players);
		labelUltimate = new JLabel();
		labelUltimate.setVisible(true);
		ImageIcon imageIconUltimate = new javax.swing.ImageIcon(getClass().getResource("/ultimate300x150.jpg"));
		Image imageUltimate= imageIconUltimate.getImage(); // transform it 
		Image newimgUltimate = imageUltimate.getScaledInstance(300, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIconUltimate = new ImageIcon(newimgUltimate);  // transform it back
		labelUltimate.setIcon(imageIconUltimate);	
		ultimate.add(labelUltimate,BorderLayout.NORTH);
			
		
		JPanel info =new JPanel();
		info.setPreferredSize(new Dimension(300, 700));
		info.setBackground(Color.black);
		add(info , BorderLayout.EAST);

		JPanel others =new JPanel();
		others.setLayout(new BorderLayout());
		others.setLayout(new GridLayout(4, 2));
		others.setPreferredSize(new Dimension(300,550));
		others.setBackground(Color.black);
		info.add(others,BorderLayout.SOUTH);
		
		JPanel Player0 =new JPanel();
		Player0.setLayout(new BorderLayout());
		Player0.setBorder(BorderFactory.createTitledBorder("Player 1"));
		others.add(Player0,BorderLayout.WEST);
		textArea_Player0.setEditable(false);
		textArea_Player0.setBackground(Color.black);
		Player0.setBackground(Color.white);
		JScrollPane scroll0 = new JScrollPane(textArea_Player0);
		scroll0.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll0.setBackground(Color.black);
		textArea_Player0.setLineWrap(true);
		Player0.add(scroll0);	
		textArea_Player0.setForeground(Color.white);
		scroll0.setSize(new Dimension(300/2,100));
		areas.add(textArea_Player0);
		

		JPanel Player1 =new JPanel();
		Player1.setLayout(new BorderLayout());
		Player1.setPreferredSize(new Dimension(300/2,100));
		Player1.setBorder(BorderFactory.createTitledBorder("Player 2"));
		others.add(Player1,BorderLayout.CENTER);
		final JTextArea textArea_Player1 = new JTextArea();
		textArea_Player1.setEditable(false);
		textArea_Player1.setBackground(Color.black);
		Player1.setBackground(Color.white);
		JScrollPane scroll1 = new JScrollPane(textArea_Player1);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll1.setBackground(Color.black);
		textArea_Player1.setLineWrap(true);
		Player1.add(scroll1);	
		textArea_Player1.setForeground(Color.white);
		scroll1.setSize(new Dimension(300/2,100));
		areas.add(textArea_Player1);
		
		JPanel Player2 =new JPanel();
		Player2.setLayout(new BorderLayout());
		Player2.setPreferredSize(new Dimension(300/2,100));
		Player2.setBorder(BorderFactory.createTitledBorder("Player 3"));
		others.add(Player2,BorderLayout.EAST);
		final JTextArea textArea_Player2= new JTextArea();
		textArea_Player2.setEditable(false);
		textArea_Player2.setBackground(Color.black);
		Player2.setBackground(Color.white);
		JScrollPane scroll2 = new JScrollPane(textArea_Player2);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setBackground(Color.black);
		textArea_Player2.setLineWrap(true);
		Player2.add(scroll2);	
		textArea_Player2.setForeground(Color.white);
		scroll2.setSize(new Dimension(300/2,100));
		areas.add(textArea_Player2);

		JPanel Player3 =new JPanel();
		Player3.setLayout(new BorderLayout());
		Player3.setPreferredSize(new Dimension(300/2,100));
		Player3.setBorder(BorderFactory.createTitledBorder("Player 4"));
		others.add(Player3,BorderLayout.SOUTH);
		final JTextArea textArea_Player3 = new JTextArea();
		textArea_Player3.setEditable(false);
		textArea_Player3.setBackground(Color.black);
		Player3.setBackground(Color.white);
		JScrollPane scroll3 = new JScrollPane(textArea_Player3);
		scroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll3.setBackground(Color.black);
		textArea_Player3.setLineWrap(true);
		Player3.add(scroll3);	
		textArea_Player3.setForeground(Color.white);
		scroll3.setSize(new Dimension(300/2,100));
		areas.add(textArea_Player3);
		
		JPanel Player4 =new JPanel();
		Player4.setLayout(new BorderLayout());
		Player4.setPreferredSize(new Dimension(300/2,100));
		Player4.setBorder(BorderFactory.createTitledBorder("Player 5"));
		others.add(Player4,BorderLayout.WEST);
		final JTextArea textArea_Player4 = new JTextArea();
		textArea_Player4.setEditable(false);
		textArea_Player4.setBackground(Color.black);
		Player4.setBackground(Color.white);
		JScrollPane scroll4 = new JScrollPane(textArea_Player4);
		scroll4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll4.setBackground(Color.black);
		textArea_Player4.setLineWrap(true);
		Player4.add(scroll4);	
		textArea_Player4.setForeground(Color.white);
		scroll4.setSize(new Dimension(300/2,100));
		areas.add(textArea_Player4);
		
		JPanel Player5 =new JPanel();
		Player5.setLayout(new BorderLayout());
		Player5.setPreferredSize(new Dimension(300/2,100));
		Player5.setBorder(BorderFactory.createTitledBorder("Player 6"));
		others.add(Player5,BorderLayout.CENTER);
		final JTextArea textArea_Player5 = new JTextArea();
		textArea_Player5.setEditable(false);
		textArea_Player5.setBackground(Color.black);
		Player5.setBackground(Color.white);
		JScrollPane scroll5 = new JScrollPane(textArea_Player5);
		scroll5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll5.setBackground(Color.black);
		textArea_Player5.setLineWrap(true);
		Player5.add(scroll5);	
		textArea_Player5.setForeground(Color.white);
		scroll5.setSize(new Dimension(300/2,100));
		areas.add(textArea_Player5);
		
		JPanel Player6 =new JPanel();
		Player6.setLayout(new BorderLayout());
		Player6.setPreferredSize(new Dimension(300/2,100));
		Player6.setBorder(BorderFactory.createTitledBorder("Player 7"));
		others.add(Player6,BorderLayout.EAST);
		final JTextArea textArea_Player6= new JTextArea();
		textArea_Player6.setEditable(false);
		textArea_Player6.setBackground(Color.black);
		Player6.setBackground(Color.white);
		JScrollPane scroll6 = new JScrollPane(textArea_Player6);
		scroll6.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll6.setBackground(Color.black);
		textArea_Player6.setLineWrap(true);
		Player6.add(scroll6);	
		textArea_Player6.setForeground(Color.white);
		scroll6.setSize(new Dimension(300/2,100));
		areas.add(textArea_Player6);

		JPanel Player7 =new JPanel();
		Player7.setLayout(new BorderLayout());
		Player7.setPreferredSize(new Dimension(300/2,100));
		Player7.setBorder(BorderFactory.createTitledBorder("Player 8"));
		others.add(Player7,BorderLayout.SOUTH);
		final JTextArea textArea_Player7 = new JTextArea();
		textArea_Player7.setEditable(false);
		textArea_Player7.setBackground(Color.black);
		Player7.setBackground(Color.white);
		JScrollPane scroll7 = new JScrollPane(textArea_Player7);
		scroll7.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll7.setBackground(Color.black);
		textArea_Player7.setLineWrap(true);
		Player7.add(scroll7);	
		textArea_Player7.setForeground(Color.white);
		scroll7.setSize(new Dimension(300/2,100));
		areas.add(textArea_Player7);
		
		updateTextAreas();
	}
	

	public void updateTextAreas() {
		int capacity= GameControler.getInstance().getPlayerNumber();
		for(int i=0;i<capacity;i++) {
			updateArea(areas.get(i), i);
		}
	}
	
	public void updateArea(JTextArea text,int id) {
		text.setText("Name: ");
		text.append(GameControler.getInstance().getPlayerName(id));
		text.append("\n");
		text.append("Location: ");
		text.append(GameControler.getInstance().getPlayerLocation(id));
		text.append("\n");
		text.append("Money: ");
		text.append(Double.toString(GameControler.getInstance().getPlayerMoney(id)));
		text.append("\n");
		text.append("Tiles: ");
		text.append(GameControler.getInstance().getTiles(id));
		text.append("\n");
		text.append("Assets: ");
		text.append(GameControler.getInstance().getAssets(id));
	}

}
