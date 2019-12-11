package domain.game;

import java.util.ArrayList;

import domain.board.tiles.TilePosition;

public class CommunityCards extends Cards implements Observable{
	private ArrayList<Observer> observerList = new ArrayList<Observer>();
	public CommunityCards(int id, String name) {
		
		super(id, name);
	}
	
	public void applyCard(Player player) {
		notify(UpdateTypes.CARD,cardToString());
		
		if (this.getID() == 0) {
			player.decreaseMoney(100);
			Pool.getInstance().addMoney(100);
		}
		else {
			for (Player x : GameControler.getInstance().monopolyGame.getPlayerList()) {
				x.decreaseMoney(10);
			}
			player.increaseMoney(GameControler.getInstance().monopolyGame.getPlayerList().size() * 10);
			TilePosition pos = new TilePosition(2, 51);
			player.getPiece().teleport(GameControler.getInstance().monopolyGame.getTileIndex(pos));
		}

	}
	
	@Override
	public String cardToString() {		
		return "community" + this.getID();
	}

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		observerList.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observerList.remove(observer);
	}

	@Override
	public void notify(UpdateTypes type, Object obj) {
		// TODO Auto-generated method stub
		if(!observerList.isEmpty())
		observerList.forEach(observer->observer.update(type, obj));
	}

}
