package domain.game;
import java.util.Random;

public class RandomBuyingStrategy implements BuyingStrategy{
	private Random r = new Random();
	public void buyTile() {
		 if(r.nextInt(2)+1 % 2 == 0) { if(GameControler.getInstance().getLegalActions().contains(ActionTypes.BUY_TILE)) {
				GameControler.getInstance().getMonopolyGame().currentPlayerBuysTile();
		}
	}}
}
