package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardPanel extends JPanel {
  JLabel cardL = new JLabel();
  ImageIcon cardIcon = new ImageIcon();
  
  
  public CardPanel(){
    setLayout(new BorderLayout());
    setLocation(0,470);
    setSize(300,230);
    setBackground(Color.black);
    add(cardL);  
    updateCard("defaultCard");
  }

  public void updateCard(String cardName){
    String folder= "/gui/cardImages/";
    cardIcon= help(folder+ cardName+".png",300,200);
    cardL.setIcon(cardIcon);
  }
  
  
  private ImageIcon help (String s, int width, int height){
    ImageIcon imageIcon = new javax.swing.ImageIcon(getClass().getResource(s)); 
    Image image = imageIcon.getImage(); 
    Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); 
    return new ImageIcon(newimg);
  }
  
  
  
}