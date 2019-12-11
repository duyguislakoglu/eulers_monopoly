package domain.board.tiles;

import domain.game.Board;
import domain.game.GameControler;
import domain.game.Piece;
import domain.game.Player;

public class Railroad extends OwnableTiles {
	private boolean railroadExists = false;

	public Railroad(int ID, String name, TilePosition position, int price) {
		super(ID, name, position, price);
		this.setType(TileType.Railroad);
	}
	@Override
	public int getRent() {
		int rent = 0;
		if(this.isOwned() && this.isRailroadExists()) {
			rent = TileInformation.getInstance().get2RentPrice(getID());
		}
		else if(this.isOwned()) {
			rent = TileInformation.getInstance().get1RentPrice(getID());
		}
		return rent;
	}
	
	public void landedOn(Player player) {
		if (isOwned() && this.getOwner() != player && railroadExists) {
			payRent(player);
		}
	}
	
	@Override 
	public void passedOn(Player player) {
		changeLane(player);
	}
	public void changeLane(Player p) { 
		int[] dice = GameControler.getInstance().getDiceResult();
		int diceResult = dice[0] + dice[1];
		if(dice[2] < 4) diceResult+= dice[2];
		Piece piece = p.getPiece();
		int secondPositionId = this.getOtherHalfRailroadId();
		if((diceResult%2) == 0) {
			piece.teleport(secondPositionId);
		}
	}

	public void buildRailroad() {
		this.setRailroadExists(true);
		Railroad secondTile = getOtherRailroad();
		secondTile.setRailroadExists(true);
	}
	
	@Override
	public void buyTile(Player player) {
		super.buyTile(player);
		Railroad otherRailroad = this.getOtherRailroad();
		otherRailroad.setOwned(true);
		otherRailroad.setOwner(player);
		player.addProperty(otherRailroad);
	}
	public void removeRailroad() {
		this.railroadExists = false;
	}
	public boolean isRailroadExists() {
		return railroadExists;
	}
	public void setRailroadExists(boolean status) {
		this.railroadExists = status;
	}
	
	private int getOtherHalfRailroadId() {
		TilePosition secondPosition = TileInformation.getInstance().getSecondTilePosition(this.getID());
		int secondTileId = TileInformation.getInstance().getId(secondPosition);
		return secondTileId;
	}
	
	private Railroad getOtherRailroad() {
		Railroad railroad = (Railroad) GameControler.getInstance().getTileList().get(this.getOtherHalfRailroadId());
		return railroad;
	}
	public String AssetsToString() {		
		String assets =  "\n" + this.getName() + "Train Depot: " ;
		if(this.isRailroadExists()) {
			return assets + "exists.";
		}
		else return assets + "does not exists.";
	}
}