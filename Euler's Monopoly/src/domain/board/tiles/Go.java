package domain.board.tiles;

import domain.game.Player;

public class Go extends Tile {
	private static final int GO_MONEY = 200;
	public Go(int ID, String name, TilePosition position) {
		super(ID, name, position );
		this.setType(TileType.Go);
	}
	
	@Override
	public void landedOn(Player p) {
		p.increaseMoney(GO_MONEY);
	}
	@Override
	public void passedOn(Player p) {
		p.increaseMoney(GO_MONEY);
	}
}
