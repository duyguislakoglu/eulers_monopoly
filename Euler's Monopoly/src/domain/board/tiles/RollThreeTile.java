package domain.board.tiles;

import java.util.ArrayList;
import java.util.Random;

import domain.game.Cards;
import domain.game.Cup;
import domain.game.GameControler;
import domain.game.Player;
import domain.game.RollThree;

public class RollThreeTile extends Tile{

	
	public RollThreeTile(int ID, String name, TilePosition position) {
		super(ID, name, position);
	}

	public void landedOn(Player p) {
		Cards card = drawCard();
		p.addCard(card);
		//card.applyCard(p);
		
		ArrayList<Player> pList = GameControler.monopolyGame.getPlayerList();
		int counter;

		for (Player p2 : pList) {
			counter = 0;

			Cup.getInstance().roll();
			int[] diceResult = Cup.getInstance().getDiceResult();
			for (Cards card2 : p2.getOwnedCardList()) {
				RollThree rCard = (RollThree) card2;
				for (int i = 0; i <= 2; i++) {
					if (rCard.getNumbers()[i] == diceResult[i]) counter++;
				}
				if (counter == 1) {
					p2.increaseMoney(50);
				} else if (counter == 2) { 
					p2.increaseMoney(200);
				} else if (counter == 3) {
					p2.increaseMoney(1000);
					if (p2.getID() == p.getID()) p.increaseMoney(500);
				}
			}		
		}	
	}
	
	private Cards drawCard() {		
		Random r = new Random();		
		return GameControler.getInstance().monopolyGame.getRollThreeCardList().get(r.nextInt(18));
	}
}