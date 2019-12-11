package domain.game;
import java.util.ArrayList;
import domain.network.LobbyPlayer;
public class Bot extends Player{

	BuyingStrategy buyingStrategy;
	
	public Bot(LobbyPlayer p, BotTypes type) {
		super(p);
		if(type == BotTypes.GREEDY) { buyingStrategy = new GreedyBuyingStrategy(); System.out.println("GreedyBorn");}
		if(type == BotTypes.STINGY) { buyingStrategy = new StingyBuyingStrategy(); System.out.println("StingyBorn");}
		if(type == BotTypes.RANDOM) { buyingStrategy = new RandomBuyingStrategy(); System.out.println("RandomBorn");}
	}
	
	public void performBuying() {
		buyingStrategy.buyTile();
	}
	
	public void setBuyingBehavior(BuyingStrategy newBuyingBehavior) {
		buyingStrategy= newBuyingBehavior;
	}
	
	public void takeTurn() {
		while(GameControler.getInstance().getLegalActions().contains(ActionTypes.ROLL_DICE)) {
			GameControler.getInstance().rollDice();
		//  if you want to change your behavior dynamically, uncomment this.	
		//	buyingBehavior = currentlyApplicableStrategies.get(r.nextInt(currentlyApplicableStrategies.size())+1);
			performBuying();
		//	GameControler.getInstance().endTurn();
	}}
}