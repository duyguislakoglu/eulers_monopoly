package domain.board.tiles;

import domain.game.GameControler;
import domain.game.Player;

public class PayDay extends Tile{
	private static final int ODD_PAYDAY = 300;
	private static final int EVEN_PAYDAY = 400;

	public PayDay(int ID, String name, TilePosition position) {
		super(ID, name, position);
		this.setType(TileType.PayDay);
	}
	@Override
	public void passedOn(Player p) {
		p.increaseMoney(EVEN_PAYDAY);
		System.out.println(p.getMoney());
		addSalary(p);
		System.out.println("Added Money");
		System.out.println(p.getMoney());
	}
	@Override
	public void landedOn(Player p) {
		System.out.println("Landed on Payday");
		p.increaseMoney(EVEN_PAYDAY);
	}
	
	private void addSalary(Player p) {
		int[] dice = GameControler.getInstance().getDiceResult();
		int diceResult = dice[0] + dice[1];
		if(dice[2] < 4) diceResult+= dice[2];
		
		if (diceResult%2 == 0) {
			p.increaseMoney(EVEN_PAYDAY);
		}
		else {
			p.increaseMoney(ODD_PAYDAY);
		}
	}
}
