package domain.game;

import java.io.Serializable;

public abstract class Cards implements Observable, Serializable {
	// TODO: Use Factory for initialization
	// TODO: Use JSON in order to represent cards, their images etc.
	private int cardID;
	private String name;
	
	public Cards(int id, String name) {
		this.cardID = id;
		this.name = name;
	}
	public void applyCard(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	public int getID() {	
		return cardID;		
	}
	
	abstract public String cardToString();
	
	public void addObserver(Observer observer) {
	}

	@Override
	public void removeObserver(Observer observer) {
	}

	@Override
	public void notify(UpdateTypes type, Object obj) {
		// TODO Auto-generated method stub
	}
}
