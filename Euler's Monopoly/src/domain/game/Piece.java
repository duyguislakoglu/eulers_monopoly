package domain.game;

import java.io.Serializable;
import java.util.ArrayList;

import domain.board.tiles.TileInformation;
import domain.board.tiles.TilePosition;



public class Piece implements Observable, Serializable {
	private TilePosition currentPosition;
	private int direction;
	private int lane;
	private int id;
	private transient ArrayList<Observer> observerList =new ArrayList<Observer> () ;
	
	public Piece(int id) {
		this.id = id;
		this.currentPosition = new TilePosition(1,0);
		this.direction = 1;
		this.lane = 0;
	}

	public TilePosition getCurrentPosition() {
		return currentPosition;
	}
	public void setCurrentPosition(TilePosition pos) {
		this.currentPosition = pos;
	    notify(UpdateTypes.PIECE_POSITION, this.currentPosition);
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getLane() {
		return lane;
	}
	public void setLane(int lane) {
		this.lane = lane;
	}
	public void teleport(int tileID) {
		TilePosition pos = TileInformation.getInstance().getTilePosition(tileID);
		this.setCurrentPosition(pos);
	}


	@Override
	public void notify(UpdateTypes type, Object obj) {
		// TODO Auto-generated method stub
		if(observerList == null)return;
		if(!observerList.isEmpty())
		observerList.forEach(observer->observer.update(type, obj));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		if(observerList== null)observerList= new ArrayList<>();
		observerList.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observerList.remove(observer);
	}
	
}
