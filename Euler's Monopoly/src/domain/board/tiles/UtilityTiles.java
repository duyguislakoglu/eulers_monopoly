package domain.board.tiles;

import java.util.ArrayList;

import domain.game.GameControler;
import domain.game.Player;

public class UtilityTiles extends OwnableTiles {

	public UtilityTiles(int id, String name, TilePosition position, int price) {
		super(id, name, position, price);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRent() {
		int ownedUtilityNumber = getNumberOfUtilitiesPlayerHave(this.getOwner());
		int[] dice = GameControler.getInstance().getDiceResult();
		int diceResult = dice[0] + dice[1];
		if(dice[2] < 4) diceResult+= dice[2];
		
		if(ownedUtilityNumber == 1) {
			return TileInformation.getInstance().get1RentPrice(this.getID()) * diceResult;
		}
		else if(ownedUtilityNumber == 2) {
			return TileInformation.getInstance().get2RentPrice(this.getID()) * diceResult;
		}
		else if(ownedUtilityNumber == 3) {
			return TileInformation.getInstance().get3RentPrice(this.getID()) * diceResult;
		}
		else if(ownedUtilityNumber == 4) {
			return TileInformation.getInstance().get4RentPrice(this.getID()) * diceResult;
		}
		else if(ownedUtilityNumber == 5) {
			return TileInformation.getInstance().get5RentPrice(this.getID()) * diceResult;
		}
		else if(ownedUtilityNumber == 6) {
			return TileInformation.getInstance().get6RentPrice(this.getID()) * diceResult;
		}
		else if(ownedUtilityNumber == 7) {
			return TileInformation.getInstance().get7RentPrice(this.getID()) * diceResult;
		}
		else if(ownedUtilityNumber == 8) {
			return TileInformation.getInstance().get8RentPrice(this.getID()) * diceResult;
		}
		
		return 0;
	}
	
	private int getNumberOfUtilitiesPlayerHave(Player player) {
		Player owner = this.getOwner();
		int ownedUtilityNumber = 0;
		ArrayList<Tile> playersTiles = owner.getOwnedTileList();
		for (int i = 0; i < playersTiles.size(); i++) {
			if(playersTiles.get(i).getType().equals(TileType.UtilityTiles) && !playersTiles.get(i).equals(this)) {
				ownedUtilityNumber++;
			}
		}
		return ownedUtilityNumber;
	}

}
