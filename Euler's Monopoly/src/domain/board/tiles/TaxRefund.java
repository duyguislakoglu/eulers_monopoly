package domain.board.tiles;

import domain.game.Player;
import domain.game.Pool;

public class TaxRefund extends Tile {

	public TaxRefund(int ID, String name, TilePosition position) {
		super(ID, name, position);
		this.setType(TileType.TaxRefund);
		}
	
	public void taxRefund(Player player, Pool pool) {
		int amount = pool.getMoney()/2;
		if (amount != (int)amount) {
			amount = (int)Math.ceil(amount);
		}
		player.increaseMoney(amount);
		pool.decrementMoney(amount);
	}
}
