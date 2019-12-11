package domain.game;

public class GreedyBuyingStrategy implements BuyingStrategy {
	public void buyTile() {
		if(GameControler.getInstance().getLegalActions().contains(ActionTypes.BUY_TILE)) {
			GameControler.getInstance().getMonopolyGame().currentPlayerBuysTile();
	}}
}
