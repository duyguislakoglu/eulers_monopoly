package domain.board.tiles;

import domain.game.Player;

public class Jail extends Tile {

	// TODO: Ask whether we should implement getting out of jail here
	
	public Jail(int ID, String name, TilePosition position) {
		super(ID, name, position);
		this.setType(TileType.Jail);
	}
	
	public void goToJail(Player p){
		p.setJailed(true);
	}
	
	public void getOutOfJail(Player p) {
		p.setJailed(false);
	}
	
	public void landedOn(Player p) {
		sendToJail(p);
		goToJail(p);
	}
	
	public void sendToJail(Player p) {
		TilePosition position = this.getPosition();
		int id = TileInformation.getInstance().getId(position);
		int jail = 10;
		if(id != jail) {
			p.getPiece().teleport(jail);
		}
	}
}
