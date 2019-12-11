package domain.game;
import java.util.ArrayList;
import java.util.List;

import domain.board.tiles.Tile;
import domain.board.tiles.TilePath;
import domain.network.actions.GameCommunicationInterface;
import domain.storage.SavedGame;
import domain.storage.StorageController;
import gui.Application;

public class GameControler implements GameCommunicationInterface{
	public static MonopolyGame monopolyGame;
	private static GameControler instance;
	private GameControler(){

	}

	public static GameControler getInstance() {
		if(instance == null){
			instance = new GameControler();
		}
		return instance;
	}

	public void rollDice() {
		monopolyGame.rollDice();
		//currentPlayer.getPiece().move(diceResult());
		// monopolyGame.board.getTile(currentPlayer.getPiece().getCurrentPosition()).landedOn(currentPlayer);
	}
	
	public void buyTilePressed() {
		monopolyGame.currentPlayerBuysTile();
	}
	
	public void upgradePressed() {
		monopolyGame.currentPlayerUpgradesTile();
	}
	
	public ArrayList<Tile> getTileList() {
		return monopolyGame.getTileList();
	}
	//public void sellTilePressed() {
	//}
	
	//public void upgradeTilePressed() {
	//}

	
	public int getPlayerNumber() {
		return monopolyGame.getPlayerCount();
	}
	public ArrayList<Player> getPlayerList() {
		return monopolyGame.getPlayerList();
	}
	
	public void endTurnPressed(){
		monopolyGame.endTurnPressed();
	}
	public void pauseButtonPressed(){
		monopolyGame.pauseGamePressed();
	}
	public void resumeButtonPressed(){
		monopolyGame.resumeGamePressed();
	}
	public void saveGame(){
		StorageController.getInstance().saveGame("testSaveGame.save");
		System.out.println("Saved game to file testSaveGame.save");
	}

	@Override
	public void setLoadedGame(SavedGame savedGame) {
		GameInitializer.setLoadedGame(savedGame);
	}

	@Override
	public void movePlayer(int playerId, TilePath path) {
		monopolyGame.movePlayerByPath(playerId,path);
	}
	
	@Override
	public void startGame() {
		System.out.println("Starting game.");
		GameInitializer.initializeGame();
		monopolyGame = GameInitializer.getMonopolyGame();
		Application.initializeApplication();
	}

	@Override
	public void endTurn() {
		monopolyGame.endTurn();
	}

	@Override
	public void pauseGame() {
		monopolyGame.pauseGame();
	}

	@Override
	public void resumeGame() {
		monopolyGame.resumeGame();
	}
	
	public String getPlayerName(int order){
		return monopolyGame.getPlayerList().get(order).getName();
	}
	
	public int getCurrentPlayerId(){
		return monopolyGame.getCurrentPlayerId();
	}
	
	public int getCurrentPlayerOrder(){
		return monopolyGame.getCurrentPlayerOrder();
	}
	
	public String getPlayerLocation(int order){
		return monopolyGame.getPlayerPositionTile(monopolyGame.getPlayerList().get(order)).getName();
	}
	
	public double getPlayerMoney(int order){
		return monopolyGame.getPlayerList().get(order).getMoney();
	}
	public String getTiles(int order){
		return monopolyGame.getPlayerList().get(order).getOwnedTileListToString();
	}
	public String getAssets(int order){
		return monopolyGame.getPlayerList().get(order).getAssetListToString();
	}
	
	
//	public String getAssets(int order){
//		return monopolyGame.getPlayerList().get(id).getOwnedTileListNames();
//	}
	public void clientFailure(int clientId){
		monopolyGame.clientFailure(clientId);
	}

	@Override
	public void setPlayerMoney(int playerId, int money) {
		monopolyGame.setPlayerMoneyByID(playerId,money);
	}

	@Override
	public void playerBoughtTile() {
        monopolyGame.playerBoughtTile();
	}

	@Override
	public void playerSoldTile(int playerId, int tileId) {

	}

	@Override
	public void setPlayerCards() {

	}

	@Override
	public void tileUpgraded(int tileId) {
		monopolyGame.upgradeTile(tileId);
	}

	@Override
	public void tileDowngraded(int tileId) {
        monopolyGame.removeBuilding(tileId);
	}

	public int getPoolMoney() {
		return Pool.getInstance().getMoney();
	}
	public static void addObserverToAll(Observer observer) {
		monopolyGame.addObserver(observer);
		monopolyGame.observerIsAdded();
		Cup.getInstance().addObserver(observer);
		Pool.getInstance().addObserver(observer);

		for(int i=0;i < monopolyGame.getCommunityCardList().size();i++) {
			monopolyGame.getCommunityCardList().get(i).addObserver(observer);
		}
		for(int i=0;i < monopolyGame.getChanceCardList().size();i++) {
			monopolyGame.getChanceCardList().get(i).addObserver(observer);
		}
		
		for(int i=0;i < monopolyGame.getPlayerCount();i++) {
			monopolyGame.getPlayerList().get(i).addObserver(observer);
		}
	}
	public MonopolyGame getMonopolyGame() {
		return monopolyGame;
	}
	public ArrayList<String> getOwnedTileListAsStringList(int order){
		return monopolyGame.getPlayerList().get(order).getOwnedTileListAsStringList();
	}
	
	public List<ActionTypes>  getLegalActions(){
		return monopolyGame.getLegalActions();
	}
	
	public int[] getDiceResult() {
		return Cup.getInstance().getDiceResult();
	}
}