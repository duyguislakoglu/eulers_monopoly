package domain.board.tiles;

import java.util.Random;

import domain.game.CardFactory;
import domain.game.Cards;
import domain.game.GameControler;
import domain.game.Player;

public class ChanceTile extends Tile{

	public ChanceTile(int ID, String name,TilePosition position) {
		super(ID, name, position);
	}

	public void landedOn(Player p) {
		drawCard().applyCard(p);
	}
	
	private Cards drawCard() {
		
		Random r = new Random();		
		return GameControler.getInstance().monopolyGame.getChanceCardList().get(r.nextInt(3));
	}
}
