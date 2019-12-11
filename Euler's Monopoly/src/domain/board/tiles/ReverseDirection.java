package domain.board.tiles;

import domain.game.Player;

public class ReverseDirection extends Tile {

	public ReverseDirection(int ID, String name, TilePosition position) {
		super(ID, name, position);
		this.setType(TileType.ReverseDirection);
	}
}
