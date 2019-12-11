package domain.board.tiles;

import domain.game.Player;

public class Subway extends Tile {

	public Subway(int ID, String name, TilePosition position) {
		super(ID, name, position);
		this.setType(TileType.Subway);
	}
	
	public void landedOn(Player p, int x) {
		// TODO: how to take tileID that player chose???
		p.getPiece().teleport(x);
	}
}
