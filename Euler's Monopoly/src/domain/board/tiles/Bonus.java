package domain.board.tiles;

import domain.game.Player;

public class Bonus extends Tile {
	private static final int BONUS_PASS = 250;
	private static final int BONUS_LAND = 300;
	
	public Bonus(int id, String name, TilePosition position) {
		super(id, name, position);
		// TODO Auto-generated constructor stub
	}
	
	public void passedOn(Player player) {
		player.increaseMoney(BONUS_PASS);
	}
	
	public void landedOn(Player player) {
		player.increaseMoney(BONUS_LAND);
	}
}
