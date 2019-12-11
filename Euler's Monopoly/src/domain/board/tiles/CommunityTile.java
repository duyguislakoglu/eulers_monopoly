package domain.board.tiles;

import java.util.Random;

import domain.game.CardFactory;
import domain.game.Cards;
import domain.game.GameControler;
import domain.game.Player;

public class CommunityTile extends Tile{
	
	public CommunityTile(int ID, String name, TilePosition position) {
		super(ID, name, position);
	}
		
	public void landedOn(Player p) {
		drawCard().applyCard(p);
	}
	
	private Cards drawCard() {
		// TODO: Draw random card from the community card list
		Random r = new Random();
		return GameControler.getInstance().monopolyGame.getCommunityCardList().get(r.nextInt(2));
	}
}
