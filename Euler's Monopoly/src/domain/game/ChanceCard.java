package domain.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import domain.board.tiles.RegularTiles;
import domain.board.tiles.Tile;
import domain.board.tiles.TilePosition;

public class ChanceCard extends Cards implements Observable{
	private ArrayList<Observer> observerList = new ArrayList<Observer>();
	public ChanceCard(int id, String name) {

		super(id, name);		
	}

	public void applyCard(Player player) {
		if (this.getID() == 0) {
			TilePosition pos = new TilePosition(1, 10);		
			player.getPiece().teleport(GameControler.getInstance().monopolyGame.getTileIndex(pos));
		}
		else if (this.getID() == 1) {
			TilePosition pos = new TilePosition(1, 11);
			player.getPiece().teleport(GameControler.getInstance().monopolyGame.getTileIndex(pos));
		} else {

			ArrayList <Integer> colorList = new ArrayList<Integer>();
			ArrayList <Integer> realColorList = new ArrayList<Integer>();
			Random r = new Random();
			for (Player p : GameControler.getInstance().monopolyGame.getPlayerList()) {
				if (!p.getOwnedTileList().isEmpty()) {
					for (Tile tile : p.getOwnedTileList()) {
						RegularTiles rTile = (RegularTiles)tile;
						if (!colorList.contains(rTile.getColor())) colorList.add(rTile.getColor());
					}				
					realColorList.add(colorList.get(r.nextInt(colorList.size())));
					
					for (int color : realColorList) {
						for (Tile tile : GameControler.getInstance().monopolyGame.getTileList()) {
							RegularTiles rTile = (RegularTiles)tile;
							if (color == rTile.getColor()) {
								//rTile.removeBuilding();
							}
						}
					}
				}
			}
		}
		notify(UpdateTypes.CARD,cardToString());
	}

	@Override
	public String cardToString() {		
		return "chance" + this.getID();
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
