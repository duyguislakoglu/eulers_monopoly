package domain.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import domain.board.tiles.Tile;
import domain.network.LobbyPlayer;
import domain.network.NetworkController;
import domain.network.actions.Action;
import domain.network.actions.PlayerMoneyChangedAction;

public class Player implements Observable, Serializable {
	private final static int DEFAULT_MONEY = 3200;
	private transient ArrayList<Observer> observerList= new ArrayList<Observer>();
	private int playerID;
	private String name;
	private ArrayList<Tile> ownedTileList;
	private int money;
	private Piece piece; 
	private ArrayList<Cards> ownedCardList;
	private boolean isJailed;
	private int order;
	private boolean isBot;
	private int clientId;


	/**
	 * This is the class used to represent a real-world user.
	 */

	/**
	 * A constructor for a player
	 * @param ID , player's id
	 * @param name , player's name
	 */
	public Player(int ID, String name) {
		this.playerID = ID;
		this.name = name;
		this.ownedTileList = new ArrayList<Tile>();
		this.money = DEFAULT_MONEY;
		this.ownedCardList = new ArrayList<Cards>();
		this.isJailed = false;
		this.piece = new Piece(ID);
	}

	/** 
	 * Other constructor for a player
	 * @param p
	 */
	public Player(LobbyPlayer p) {
		this.playerID = p.getPlayerId();
		this.name = p.getPlayerName();
		this.ownedTileList = new ArrayList<Tile>();
		this.money = DEFAULT_MONEY;
		this.ownedCardList = new ArrayList<Cards>();
		this.isJailed = false;
		this.piece = new Piece(p.getPlayerId());
		this.isBot = p.isBot();
		this.clientId = p.getClientId();
	}


	/**
	 * @return player's name
	 */
	public String getName() {
		return this.name;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return this.order;
	}
	/**
	 * @return player's id
	 */
	public int getID() {
		return playerID;
	}
	/**
	 * @return player's ownedTileList
	 */
	public ArrayList<Tile> getOwnedTileList() {
		return ownedTileList;
	}

	public String getOwnedTileListToString(){
		String tileNames= "\n";
		for(int i=0;i<ownedTileList.size();i++) tileNames += (ownedTileList.get(i).getName() + "\n");
		return tileNames;
	}


	public String getAssetListToString(){
		String assetList= "\n";
		//  Get building number etc from here.
		for(int i=0;i<ownedTileList.size();i++) 
		{
			if (ownedTileList.get(i).getClass().equals(domain.board.tiles.RegularTiles.class)){
				assetList += (((domain.board.tiles.RegularTiles) (ownedTileList.get(i))).AssetsToString() + "\n");
				System.out.println(assetList);
			}
		}

		return assetList;
	}

	public ArrayList<String> getOwnedTileListAsStringList(){
		ArrayList<String> tileNames= new ArrayList<String>();
		for(int i=0;i<ownedTileList.size();i++) tileNames.add(ownedTileList.get(i).getName());
		return tileNames;
	}


	/**
	 * Adds the tile to the player's owned tiles.
	 * @param tile
	 * @modifies ownedTileList
	 * @effects ownedTileList now contains the argument tile
	 */

	public void addProperty(Tile tile){
		this.ownedTileList.add(tile);
	}

	/**
	 * Removes the tile from the player's owned tiles.
	 * @param tile
	 * @modifies ownedTileList
	 * @effects ownedTileList now doesn't contain the argument tile
	 */

	public void removeProperty(Tile tile) {
		this.ownedTileList.remove(this.ownedTileList.indexOf(tile));
	}

	/**
	 * @return player's money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Increases the player's money by the specified amount.
	 * @param price: the amount that the player's money'll change
	 * @modifies money
	 * @effects money now increased by price
	 */

	public void increaseMoney(int price) {
		this.money = this.money + price;
		notify(UpdateTypes.PLAYER_MONEY, this.money);
		Action action= new PlayerMoneyChangedAction(playerID,this.money);
		NetworkController.getInstance().sendAction(action);
	}

	public void getMoneyFromOthers(int price) {
		ArrayList<Player> playerList = GameControler.getInstance().monopolyGame.getPlayerList();
		int moneyTaken = 0;
		for (int i = 0; i < playerList.size(); i++) {
			if(playerList.get(i).equals(this)) {
				continue;
			}
			else {
				playerList.get(i).decreaseMoney(price);
				moneyTaken += price;
			}
		}
		this.increaseMoney(moneyTaken);
	}

	/**
	 * Decreases the player's money by the specified amount.
	 * @param price
	 * @modifies money
	 * @effects money now increased by price
	 */


	public void decreaseMoney(int price) {
		this.money = this.money - price;
		notify(UpdateTypes.PLAYER_MONEY, this.money);
		Action action= new PlayerMoneyChangedAction(playerID,this.money);
		NetworkController.getInstance().sendAction(action);
	}
	/**
	 * @return piece
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * @param p, the new piece
	 * @modifies piece
	 * @effects changes the piece to the argument 
	 */
	public void setPiece(Piece p){
		this.piece = p;
	}

	/**
	 * @return ownedCardList
	 */
	public ArrayList<Cards> getOwnedCardList() {
		return ownedCardList;
	}

	/**
	 * 
	 * @param card
	 * @modifies ownedCardList
	 * @effects ownedCardList now includes the parameter, card
	 */

	public boolean isBot() {
		return this.isBot;
	}
	public void addCard(Cards card){
		this.ownedCardList.add(card);
		notify(UpdateTypes.CARD, card.cardToString());
	}

	/**
	 * 
	 * @param card
	 * @modifies ownedCardList
	 * @effects ownedCardList now doesn't include the parameter, card
	 */
	public void removeCard(Cards card) {
		this.ownedCardList.remove(this.ownedCardList.indexOf(card));
	}

	/**
	 * @return isJailed
	 */
	public boolean isJailed() {
		return isJailed;
	}

	/**
	 * @param isJailed,   the new boolean of whether the player is jailed or not
	 * @modifies isJailed
	 * @effects sets isJailed to the parameter
	 */
	public void setJailed(boolean isJailed) {
		this.isJailed = isJailed;
	}

	public boolean repOk() {
		if(!ownedTileList.isEmpty() && (money == (int) money && money >= 0) && !ownedCardList.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	@Override
	public String toString() {
		return "Player [playerId= " + playerID + ", name= " + name + "\n ownedTileList= " + ownedTileList.toString() +
				"\n money= " + money + ", piece= " + piece.toString() + "\n ownedCardList= " + ownedCardList.toString() + 
				"\n isJailed= " + isJailed;
	}

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		if(observerList == null) observerList = new ArrayList<>();
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

	public int getPlayerID() {
		return playerID;
	}

	public int getClientId() {
		return clientId;
	}

	public void setMoney(int money){ // DOES NOT NOTIFY NETWORK! BEWARE
		this.money = money;
	}

	public void setClientId(int clientId){
		this.clientId= clientId;
	}
}
