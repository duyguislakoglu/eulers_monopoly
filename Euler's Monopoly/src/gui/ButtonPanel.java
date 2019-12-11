package gui;
import domain.game.ActionTypes;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import domain.game.GameControler;

public class ButtonPanel extends JPanel{
	JButton rollDiceB= new JButton();
	JButton buyTileB= new JButton();
	JButton endTurnB= new JButton();
	JButton upgradeB= new JButton();
	JButton saveB= new JButton();
	JButton pauseB= new JButton();
	JButton resumeB= new JButton();
	JButton testB;
    static JButton sellB= new JButton();
	SellBDialog sellBDialog = new SellBDialog(new ArrayList<String>());

	public ButtonPanel(){
	//	setLayout(new GridLayout(3,3));
		setLayout(new GridLayout(4,2));
		setLocation(0,150);
		setSize(300,200);
		setBackground(Color.black);
		initButtons();
		add(rollDiceB);
		add(buyTileB);
		add(endTurnB);
		add(upgradeB);
		add(sellB);
		add(saveB);
		add(pauseB);
		add(resumeB);
	//	add(testB);
	}

	public void initButtons(){
		rollDiceB = new JButton(generateIcon("/rollDice.jpg", 150, 50));
		buyTileB = new JButton(generateIcon("/buyTile.jpg", 150, 50));
		endTurnB = new JButton(generateIcon("/endTurn.jpg", 150, 50));
		upgradeB = new JButton(generateIcon("/upgradeTile.jpg", 150, 50));
		sellB = new JButton(generateIcon("/sellTile.jpg", 150, 50));
		rollDiceB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				GameControler.getInstance().rollDice();
			}
		});	
		
		buyTileB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				GameControler.getInstance().buyTilePressed();
			}
		});	
		
		upgradeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				GameControler.getInstance().upgradePressed();
			}
		});	
		
		
		sellB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sellBDialog==null) sellBDialog = new SellBDialog(GameControler.getInstance().
																getOwnedTileListAsStringList(
																GameControler.getInstance().getCurrentPlayerOrder()));
				sellBDialog.run();
				sellB.setEnabled(false);				
			}
		});	
		saveB = new JButton(generateIcon("/save.jpg", 150, 50));
		pauseB = new JButton(generateIcon("/pause.jpg", 150, 50));
		resumeB = new JButton(generateIcon("/resume.jpg", 150, 50));
		saveB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				GameControler.getInstance().saveGame();
			}
		});	
		pauseB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameControler.getInstance().pauseButtonPressed();
			}
		});	
		resumeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameControler.getInstance().resumeButtonPressed();
			}
		});
		endTurnB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameControler.getInstance().endTurnPressed();
			}
		});


		//TODO: Add sellB here. Shouldn't be enabled if the player has no tiles to sell

	}

	public void updateIllegalButtons(List<ActionTypes> legalActions){
		if(!legalActions.contains(ActionTypes.ROLL_DICE)){
			disableButton(rollDiceB,"rollDice");
		}else enableButton(rollDiceB,"rollDice");
		
		if(!legalActions.contains(ActionTypes.END_TURN)){
			disableButton(endTurnB,"endTurn");
		}else enableButton(endTurnB,"endTurn");
		
		if(!legalActions.contains(ActionTypes.BUY_TILE)){
			disableButton(buyTileB,"buyTile");
		}else enableButton(buyTileB,"buyTile");
		
		if(!legalActions.contains(ActionTypes.UPGRADE)){
			disableButton(upgradeB,"upgradeTile");
		}else enableButton(upgradeB,"upgradeTile");
		
		if(!legalActions.contains(ActionTypes.SELL_TILE)){
			disableButton(sellB,"sellTile");
		}else enableButton(sellB,"sellTile");
		
		if(!legalActions.contains(ActionTypes.SAVE)){
			disableButton(saveB,"save");
		}else enableButton(saveB,"save");
		
		if(!legalActions.contains(ActionTypes.PAUSE)){
			disableButton(pauseB,"pause");
		}else enableButton(pauseB,"pause");
		
		if(!legalActions.contains(ActionTypes.RESUME)){
			disableButton(resumeB,"resume");
		}else enableButton(resumeB,"resume");
	}

	public void disableButton(JButton button, String buttonName) {
		button.setEnabled(false);
		button.setIcon(generateIcon("/" +buttonName+"Unabled.jpg", 150, 50));
	}

	public void enableButton(JButton button, String buttonName) {
		button.setEnabled(true);
		button.setIcon(generateIcon("/" +buttonName+".jpg", 150, 50));
	}
	
	private ImageIcon generateIcon (String s, int width, int height){
		ImageIcon imageIcon = new ImageIcon();
		imageIcon= new javax.swing.ImageIcon(getClass().getResource(s));
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
}
