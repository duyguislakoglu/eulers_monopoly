package domain.game;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import domain.board.tiles.*;
import domain.network.Lobby;
import domain.network.LobbyPlayer;
import domain.network.NetworkController;
import domain.network.actions.*;


public class MonopolyGame implements Observable, Serializable {
	private transient ArrayList<Observer> observerList= new ArrayList<>();
	private ArrayList<Player> playerList;
	private Board board;
	private Player currentPlayer;
	private boolean gamePaused= false;
	private int gamePausedByPlayerId = 0;
	private int testvar=0;
	private DiceState diceState = DiceState.TURN_START; // change to determine roll order when it is implemented
	private int doublesInRow = 0;


	public boolean isGamePaused() {
		return gamePaused;
	}

	public int getGamePausedByPlayerId() {
		return gamePausedByPlayerId;
	}

	public void initiliazeSerializedGame(){
		observerList = new ArrayList<>();
		for(Player player: playerList){
			//player.setClientId(NetworkController.getInstance().getMyClientId()); // run all the players on host client
		}
	}

	public MonopolyGame(){
		this.initializePlayers();
		this.board = new Board(playerList);
		currentPlayer = playerList.get(0);
	}
	public void clientFailure(int failedId){
		for(Player player: playerList){
			if(player.getClientId() == failedId) {
				player.setClientId(NetworkController.getInstance().getMyClientId()); // run all the players on host client
			}
		}
	}

	public void initializePlayers(){
		playerList = new ArrayList<>();
		ArrayList<LobbyPlayer> lobbyList = Lobby.getInstance().getPlayers();
		Player p;
		BotTypes botType = BotTypes.GREEDY;
		for (int i = 0; i < lobbyList.size(); i++) {
			if((i % 3) == 0) botType= BotTypes.GREEDY;
			if((i % 3) == 1) botType= BotTypes.STINGY;
			if((i % 3) == 2) botType= BotTypes.RANDOM;
			if(lobbyList.get(i).isBot()) {p = new Bot(lobbyList.get(i), botType);}
			else p = new Player(lobbyList.get(i));
			p.setOrder(i);
			playerList.add(p);
		}	
	}
	public void movePlayer(int diceResult) {
		TilePath path = board.move(currentPlayer, diceResult);
		// Send movement path over the network
		Action action= new MovePlayerAction(currentPlayer.getPlayerID(), path);
		NetworkController.getInstance().sendAction(action);
		// update gui
		notify(UpdateTypes.PIECE_POSITION, currentPlayer.getPiece().getCurrentPosition());
		notify(UpdateTypes.PATH, path);
	}
	public void movePlayerByPath(int playerId, TilePath path){ // TODO: fix
		currentPlayer.getPiece().setCurrentPosition(path.getFinalPosition()); // TODO: fix
		notify(UpdateTypes.PIECE_POSITION, currentPlayer.getPiece().getCurrentPosition());
		notify(UpdateTypes.PATH, path);
	}
	public void endTurn(){
		// change current player here
		currentPlayer = playerList.get((currentPlayer.getOrder()+1) % playerList.size());
		// new turn state resets
		doublesInRow = 0;
		if(!currentPlayer.isJailed()){
			diceState = DiceState.TURN_START;
		}else{
			diceState = DiceState.ROLLING_TO_GET_OUT_OF_JAIL;
		}
 		notify(UpdateTypes.CURRENT_PLAYER, currentPlayer.getID());
		notify(UpdateTypes.UPDATE_LEGALACTIONS,getLegalActions());
		
		// Violation of many things.
		if (isCurrentPlayerOnMyClient() && currentPlayer.isBot()) {
			System.out.println("1 Bot detected.");
			((Bot)currentPlayer).takeTurn(); // TODO: fix
			}
	}
	public void endTurnPressed(){
		Action action= new EndTurnAction();
		NetworkController.getInstance().sendAction(action);
		endTurn();
	}
	public void pauseGamePressed(){
		Action action= new PauseGameAction();
		NetworkController.getInstance().sendAction(action);
		pauseGame();
	}
	public void resumeGamePressed(){
		Action action= new ResumeGameAction();
		NetworkController.getInstance().sendAction(action);
		resumeGame();
	}
	

	public void rollDice(){
		Cup.getInstance().roll();
		int sum = Cup.getInstance().getTotal(); // Modify cup if this isn't giving the desired result
		switch (diceState){
		case NO_MORE_ROLLS:
			System.out.println("ROLLED DIE WHEN WE HAD NO MORE ROLLS. ERROR");
			break;
		case ROLL_FOR_THREE:
			// implement
			break;
		case ROLL_AGAIN:
			diceState = DiceState.NO_MORE_ROLLS;
			if(Cup.getInstance().isDouble()){
				doublesInRow++;
				diceState = DiceState.ROLL_AGAIN;
			}
			if(doublesInRow != 3){
				// movement function here
				movePlayer(sum);
			}else{
				diceState = DiceState.NO_MORE_ROLLS;
				// call jail player function here
				currentPlayer.setJailed(true);
			}
			break;
		case TURN_START:
			diceState= DiceState.NO_MORE_ROLLS;
			if(Cup.getInstance().isDouble()){
				doublesInRow=1;
				diceState = DiceState.ROLL_AGAIN;
			}
			// movement function here
			movePlayer(sum);
			break;
		case ROLLING_TO_GET_OUT_OF_JAIL:
			// implement logic
			diceState= DiceState.NO_MORE_ROLLS;
			if(Cup.getInstance().isDouble()){
				currentPlayer.setJailed(false);
				movePlayer(sum);
			}
			break;
		}
		// do turn logic
		// do movement logic
		notify(UpdateTypes.UPDATE_LEGALACTIONS,getLegalActions());
	}
	public int getPlayerCount(){
		return playerList.size();
	}
	public int getCurrentPlayerOrder(){
		return currentPlayer.getOrder();
	}

	public void currentPlayerBuysTile() {
		if (playerCanBuyTile()) {
			Action action = new TileBoughtAction();
			NetworkController.getInstance().sendAction(action);
			TilePosition position = currentPlayer.getPiece().getCurrentPosition();
			board.buyTile(currentPlayer);
			currentPlayer.addProperty(board.getPlayerPositionTile(currentPlayer));
			currentPlayer.decreaseMoney(TileInformation.getInstance().getTilePrice(getTileIndex(position)));
			notify(UpdateTypes.PLAYER_TILE, currentPlayer.getOwnedTileListToString());
			notify(UpdateTypes.UPDATE_LEGALACTIONS, getLegalActions());
		}
	}
	public void setPlayerMoneyByID(int playerId, int money){
		getPlayerById(playerId).setMoney(money);
		notify(UpdateTypes.PLAYER_MONEY,null);
	}

	private Player getPlayerById(int playerId){
		for(Player player: playerList){
			if(player.getPlayerID() == playerId){
				return player;
			}
		}
		System.err.println("No player with given id: " + playerId);
		return null;
	}

	public void playerBoughtTile() { // implemented as current player right now, used for network call
		board.buyTile(currentPlayer);
		currentPlayer.addProperty(board.getPlayerPositionTile(currentPlayer));
		notify(UpdateTypes.PLAYER_TILE, currentPlayer.getOwnedTileListToString());
	}

	public void currentPlayerUpgradesTile() {
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		int price = upgradeTile(tileIndex);
		if(price == 0) return; // if we don't upgrade anything
		Action action= new TileUpgradeAction(tileIndex);
		NetworkController.getInstance().sendAction(action);
		currentPlayer.decreaseMoney(price);
		notify(UpdateTypes.PLAYER_ASSETS, currentPlayer.getAssetListToString());
	}
	public int upgradeTile(int tileIndex){ // returns price
		int price = 0;
		if(playerCanBuildHouse()) {

			((RegularTiles) board.tileList.get(tileIndex)).buildHouse();

			price = TileInformation.getInstance().getHousePrice(tileIndex);


		}
		else if(playerCanBuildRailroad()) {
			Railroad railroad = (Railroad) board.tileList.get(tileIndex);
			railroad.buildRailroad();
			price = 100;
		}
		else if(playerCanBuildHotel()) {
			RegularTiles regularTile = (RegularTiles) board.tileList.get(tileIndex);
			regularTile.buildHotel();
			price = TileInformation.getInstance().getHotelPrice(tileIndex);

		}
		else if(playerCanBuildSkyscraper()) {
			RegularTiles regularTile = (RegularTiles) board.tileList.get(tileIndex);
			regularTile.buildSkyscraper();
			price = TileInformation.getInstance().getSkyPrice(tileIndex);
		}
		notify(UpdateTypes.PLAYER_ASSETS,null);
		return price;
	}


	public void currentPlayerDowngradesTile() {
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		
		int price = 0;
		if(playerCanSellHouse()) {
			RegularTiles regularTile = (RegularTiles) board.tileList.get(tileIndex);
			regularTile.sellHouse();
			price = (int)TileInformation.getInstance().getHousePrice(tileIndex)/2;
		}
		else if(playerCanSellRailroad()) {
			Railroad railroad = (Railroad) board.tileList.get(tileIndex);
			railroad.removeRailroad();
			price = 50;
		}
		else if(playerCanSellHotel()) {
			RegularTiles regularTile = (RegularTiles) board.tileList.get(tileIndex);
			regularTile.sellHotel();
			price = (int)TileInformation.getInstance().getHotelPrice(tileIndex)/2;
		}

		else if(playerCanSellSkyscraper()) {
			RegularTiles regularTile = (RegularTiles) board.tileList.get(tileIndex);
			regularTile.sellSkyscraper();
			price = (int)TileInformation.getInstance().getSkyPrice(tileIndex)/2;
		}

		currentPlayer.increaseMoney(price);
	}
	public int getCurrentPlayerId() {
		return currentPlayer.getID();
	}

	private boolean playerCanRollDice(){
		return !diceState.equals(DiceState.NO_MORE_ROLLS);
	}
	private boolean playerCanBuyTile(){
		if(diceState.equals(DiceState.TURN_START)) return false;
		// TODO: now check player money and tile ownership
		Tile tile = board.getPlayerPositionTile(currentPlayer);
		if(tile instanceof OwnableTiles) {
			OwnableTiles ownableTile = (OwnableTiles) tile;
			if(!tile.isOwned()) {
				int money = currentPlayer.getMoney();
				int price = ownableTile.getPrice();
				if(money>=price) return true;
			}
		}
		return false;
	}
	private boolean playerCanSellTile() {
		if(diceState.equals(DiceState.TURN_START)) return false;
		Tile tile = board.getPlayerPositionTile(currentPlayer);
		if(tile instanceof OwnableTiles) {
			OwnableTiles ownableTile = (OwnableTiles) tile;
			if(tile.isOwned() && ownableTile.getOwner().equals(currentPlayer)) {
				if(ownableTile instanceof RegularTiles) {
					RegularTiles regularTile = (RegularTiles) ownableTile;
					if(regularTile.getHotelNumber() > 0 || regularTile.getHouseNumber() > 0 || 
							regularTile.getSkyscraperNumber() > 0) {
						return false;
					}
					else return true;
				}
				else if (ownableTile instanceof Railroad) {
					Railroad railroad = (Railroad) ownableTile;
					if(railroad.isRailroadExists()) return false;
					else return true;
				}

			}
		}

		return false;
	}

	private boolean playerCanSellHouse() {
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		Tile tile = board.tileList.get(tileIndex);
		boolean result = false;
		if(tile instanceof RegularTiles) {
			if(majorityOwner(currentPlayer, tileIndex) && ((RegularTiles) tile).getHouseNumber()>=1) {
				ArrayList<RegularTiles> tileList = getSameColorTiles(tileIndex);
				for(int i = 0; i < tileList.size(); i++) {
					RegularTiles regularTile = tileList.get(i);
					if(regularTile.getOwner().equals(currentPlayer)) {
						if(((RegularTiles) tile).getHouseNumber() - regularTile.getHouseNumber() <=1) {
							result = true;
						}
						else return false;
					}
				}

			}
		}
		return result;
	}
	private boolean playerCanSellHotel() {
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		Tile tile = board.tileList.get(tileIndex);
		boolean result = false;
		if(tile instanceof RegularTiles) {
			if(majorityOwner(currentPlayer,tileIndex)) {
				if(((RegularTiles) tile).getHotelNumber() == 1) {
					ArrayList<RegularTiles> tileList = getSameColorTiles(tileIndex);
					for(int i = 0; i < tileList.size(); i++) {
						RegularTiles regularTile = tileList.get(i);
						if(regularTile.getOwner().equals(currentPlayer)) {
							if(regularTile.getHouseNumber() == 4 || regularTile.getHotelNumber() == 1) {
								result = true;
							}
							else return false;
						}
					}
				}
				else {
					return false;
				}
			}
		}
		return result;
	}
	private boolean playerCanSellSkyscraper() {
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		Tile tile = board.tileList.get(tileIndex);
		boolean result = false;
		if(tile instanceof RegularTiles) {
			ArrayList<RegularTiles> colorTileList = getSameColorTiles(tileIndex);
			if(monopolyOwner(currentPlayer, tileIndex) && ((RegularTiles) tile).getSkyscraperNumber() == 1) {
				for(int i = 0; i < colorTileList.size(); i++) {
					if(colorTileList.get(i).getHotelNumber() == 1 || colorTileList.get(i).getSkyscraperNumber() == 1) result = true;
					else return false;
				}
			}
		}
		return result;
	}
	private boolean playerCanSellRailroad() {
		boolean result = false;
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		Tile tile = board.tileList.get(tileIndex);
		if(tile instanceof Railroad) {
			Railroad railroad = (Railroad) tile;
			if(railroad.isOwned() && railroad.getOwner().equals(currentPlayer) && 
					railroad.isRailroadExists()) {
				result = true;
			}
		}
		return result;
	}

	private boolean playerCanBuildRailroad() {
		boolean result = false;
		int priceRailroad = 100;
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		Tile tile = board.tileList.get(tileIndex);
		if(tile instanceof Railroad) {
			Railroad railroad = (Railroad) tile;
			if(railroad.isOwned() && railroad.getOwner().equals(currentPlayer) && 
					!railroad.isRailroadExists() && 
					currentPlayer.getMoney() >= priceRailroad){
				result = true;
			}
		}
		return result;
	}
	private boolean playerCanBuildHouse() {
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		Tile tile = board.tileList.get(tileIndex);
		boolean result = false;
		if(tile instanceof RegularTiles) {
			RegularTiles regularTile = (RegularTiles) tile;
			if(regularTile.isOwned() && regularTile.getOwner().equals(currentPlayer)) {
				
				if(majorityOwner(currentPlayer, tileIndex) && currentPlayer.getMoney() >= TileInformation.getInstance().getHousePrice(tileIndex)) {
					if(((RegularTiles)tile).getHouseNumber()==4 || ((RegularTiles)tile).getHotelNumber()>0) {
						
						return false;
					}
					else {
						ArrayList<RegularTiles> tileList = getSameColorTiles(tileIndex);
						for(int i = 0; i < tileList.size(); i++) {
							RegularTiles regTile = tileList.get(i);
							if(regTile.getOwner().equals(currentPlayer)) {
								if(regTile.getHouseNumber() - regularTile.getHouseNumber() ==0 || regTile.getHouseNumber() - regularTile.getHouseNumber() ==1) {
									result = true;
								}
								else return false;
							}
						}
					}
				}
			}
		}	
		return result;
	}
	private boolean playerCanBuildHotel() {
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		Tile tile = board.tileList.get(tileIndex);
		boolean result = false;
		if(tile instanceof RegularTiles) {
			RegularTiles regularTile = (RegularTiles) tile;
			if(regularTile.isOwned() && regularTile.getOwner().equals(currentPlayer)) {
				if(majorityOwner(currentPlayer,tileIndex) && currentPlayer.getMoney() >= TileInformation.getInstance().getHotelPrice(tileIndex)) {
					if(regularTile.getHotelNumber() == 1) {
						return false;
					}
					else {
						ArrayList<RegularTiles> tileList = getSameColorTiles(tileIndex);
						for(int i = 0; i < tileList.size(); i++) {
							RegularTiles regTile = tileList.get(i);
							if(regTile.getOwner().equals(currentPlayer)) {
								if(regTile.getHouseNumber() == 4 || regTile.getHotelNumber() == 1) {
									result = true;
								}
								else return false;
							}
						}
					}
				}
			}
		}
		return result;
	}
	private boolean playerCanBuildSkyscraper() {
		int tileIndex = getTileIndex(currentPlayer.getPiece().getCurrentPosition());
		Tile tile = board.tileList.get(tileIndex);
		boolean result = false;
		if(tile instanceof RegularTiles) {
			RegularTiles regularTile = (RegularTiles) tile;
			if(regularTile.isOwned() && regularTile.getOwner().equals(currentPlayer)) {
				ArrayList<RegularTiles> colorTileList = getSameColorTiles(tileIndex);

				if(monopolyOwner(currentPlayer, tileIndex) && currentPlayer.getMoney() >= TileInformation.getInstance().getSkyPrice(tileIndex)) {
					for(int i = 0; i < colorTileList.size(); i++) {
						if(colorTileList.get(i).getHotelNumber() == 1 || colorTileList.get(i).getSkyscraperNumber() == 1) result = true;
						else return false;
					}
				}
			}
		}
		return result;
	}
	private boolean playerCanEndTurn(){
		return (diceState.equals(DiceState.NO_MORE_ROLLS));
	}

	public List<ActionTypes> getLegalActions(){
		ArrayList<ActionTypes> list = new ArrayList<>();
		if(!isCurrentPlayerOnMyClient()) return list; // No legal actions if we don't own the player
		if(playerCanSave()) list.add(ActionTypes.SAVE);
		if(playerCanPause()) list.add(ActionTypes.PAUSE);
		if(playerCanResume()) list.add(ActionTypes.RESUME);
		if(gamePaused) return list; // if game is pause we can't do the other things

		if(playerCanBuyTile()) list.add(ActionTypes.BUY_TILE);
		if(playerCanEndTurn()) list.add(ActionTypes.END_TURN);
		if(playerCanRollDice()) list.add(ActionTypes.ROLL_DICE);
		if (playerCanUpgrade()) list.add(ActionTypes.UPGRADE);
		
	//	if(playerCanSell())
	//		list.add(ActionTypes.SELL_TILE);
		
		return list;
	}

	private boolean playerCanUpgrade(){
		return (playerCanBuildHouse() || playerCanBuildHotel() || playerCanBuildSkyscraper() || playerCanBuildRailroad());
	}
	
	private boolean playerCanPause(){
		return !gamePaused;
	}
	private boolean playerCanResume(){
		return (gamePaused && (currentPlayer.getPlayerID() == gamePausedByPlayerId));
	}
	private boolean playerCanSave(){
		return gamePaused;
	}
	public void pauseGame(){
		gamePausedByPlayerId = currentPlayer.getPlayerID();
		gamePaused= true;
		notify(UpdateTypes.GAME_PAUSED, currentPlayer.getName());
		notify(UpdateTypes.UPDATE_LEGALACTIONS, getLegalActions());
	}
	public void resumeGame(){
		gamePaused = false;
		notify(UpdateTypes.GAME_RESUMED, null);
		notify(UpdateTypes.UPDATE_LEGALACTIONS, getLegalActions());

	}

	public void removeBuilding(int tileId) {
		RegularTiles regularTile = (RegularTiles) board.tileList.get(tileId);
		regularTile.buildingRemoved();
		notify(UpdateTypes.PLAYER_ASSETS,null);
	}

	private enum DiceState{
		TURN_START,
		ROLL_AGAIN,
		NO_MORE_ROLLS,
		ROLL_FOR_THREE,
		ROLLING_TO_GET_OUT_OF_JAIL
	}
	private boolean majorityOwner(Player player, int tileIndex) {
		int numOfOwnedThatColor = 0;
		ArrayList<RegularTiles> tileList = getSameColorTiles(tileIndex);
		int numOfColor = tileList.size();
		for (int i = 0; i < tileList.size(); i++) {
			RegularTiles tile = tileList.get(i);
			if(tile.isOwned()&&tile.getOwner().equals(player)){
				numOfOwnedThatColor++;
			}
		}

		if(numOfColor == 2 && numOfOwnedThatColor == 2) {
			if(numOfOwnedThatColor == 2) return true;
			else return false;
		}
		else {
			if(numOfColor - numOfOwnedThatColor <= 1) {
				return true;
			}
			else return false;
		}
	}

	private boolean monopolyOwner(Player player, int tileIndex) {
		int numOfOwnedThatColor = 0;
		ArrayList<RegularTiles> tileList = getSameColorTiles(tileIndex);
		int numOfColor = tileList.size();
		for(int i = 0; i < tileList.size(); i++) {
			RegularTiles tile = tileList.get(i);
			if(tile.isOwned()&&tile.getOwner().equals(player)) numOfOwnedThatColor++;
		}
		if(numOfColor == numOfOwnedThatColor) {
			return true;
		}
		else {
			return false;
		}
	}
	private boolean isCurrentPlayerOnMyClient(){ // Checks if the current player is on our computer
		int clientId= NetworkController.getInstance().getMyClientId();
		return currentPlayer.getClientId() == clientId;
	}

	private ArrayList<RegularTiles> getSameColorTiles(int tileIndex) {
		ArrayList<RegularTiles> colorTileList = new ArrayList<RegularTiles>();
		int colorOfTheTile = TileInformation.getInstance().getColor(tileIndex);
		ArrayList<Tile> tileList = board.tileList;
		for (int i = 0; i < tileList.size(); i++) {
			Tile tile = tileList.get(i);
			if(tile instanceof RegularTiles && ((RegularTiles) tile).getColor() == colorOfTheTile) {
				colorTileList.add((RegularTiles) tile);
			}
		}
		return colorTileList;
	}
	public ArrayList<Tile> getTileList() {
		// TODO Auto-generated method stub
		return board.tileList;
	}

	public ArrayList<Player> getPlayerList() {
		// TODO Auto-generated method stub
		return playerList;
	}
	
	public void observerIsAdded() {
		notify(UpdateTypes.UPDATE_LEGALACTIONS,getLegalActions());
	}

	public ArrayList<Cards> getChanceCardList() {
		// TODO Auto-generated method stub
		return board.chanceCardList;
	}

	public ArrayList<Cards> getCommunityCardList() {
		// TODO Auto-generated method stub
		return board.communityCardList;
	}

	public int getTileIndex(TilePosition pos) {
		return TileInformation.getInstance().getId(pos);
	}
	public Tile getPlayerPositionTile(Player player){
		return	board.getPlayerPositionTile(player);
	}
	public ArrayList<Cards> getRollThreeCardList() {
		return board.rollThreeCardList;
	}

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		observerList.add(observer);
	}
	
	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observerList.remove(observer);
	}

	@Override
	public void notify(UpdateTypes type, Object obj) {
		// TODO Auto-generated method stub
		if(!observerList.isEmpty())
		observerList.forEach(observer->observer.update(type, obj));
	}
	
	
}