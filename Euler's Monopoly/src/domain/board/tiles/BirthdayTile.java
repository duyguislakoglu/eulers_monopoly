package domain.board.tiles;

import domain.game.Player;

public class BirthdayTile extends Tile {
	private final static int BIRTHDAY_GIFT = 100;
	public BirthdayTile(int id, String name, TilePosition position) {
		super(id, name, position);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void landedOn(Player p) {
		p.getMoneyFromOthers(BIRTHDAY_GIFT);
	}
	
}
