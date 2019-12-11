/**
 * The Board class is used to initialize several objects in order to use them 
 * in the game. The initialized objects list as follows:
 * <ul>
 * <li>Tiles
 * <li>Pieces belonging to the players
 * <li>Cards, chance, community and roll three cards
 * 
 * <p>
 * 
 * The boards contains all the information about the tiles, pieces and the cards
 * 
 */


package domain.game;

import java.io.Serializable;
import java.util.ArrayList;

import domain.board.tiles.OwnableTiles;
import domain.board.tiles.Railroad;
import domain.board.tiles.ReverseDirection;
import domain.board.tiles.Tile;

import domain.board.tiles.TileFactory;
import domain.board.tiles.TileInformation;
import domain.board.tiles.TilePath;
import domain.board.tiles.TilePosition;
import domain.board.tiles.TileType;

public class Board implements Serializable {
	ArrayList<Tile> tileList= new ArrayList<Tile>();
	static ArrayList<Piece> pieceList = new ArrayList<Piece>();
	static ArrayList<Cards> communityCardList = new ArrayList<Cards>();
	static ArrayList<Cards> chanceCardList  = new ArrayList<Cards>();
	static ArrayList<Cards> rollThreeCardList = new ArrayList<Cards>();
	static TileFactory tileFactory = TileFactory.getInstance();
	RollThreeInformation rollThreeInfo = new RollThreeInformation();
	ArrayList<Tile> lane0= new ArrayList<Tile>();
	ArrayList<Tile> lane1= new ArrayList<Tile>();
	ArrayList<Tile> lane2= new ArrayList<Tile>();
	private static final int lane0TileNumber = 23;
	private static final int lane1TileNumber = 39;
	private static final int lane2TileNumber = 58;

	/**
	 * This is the constructor for the Board class. Whenever a Board is created,
	 * it takes players as input and creates the tile, pieces and cards.
	 * @param playerList			players that are in the game
	 * @effects creates a board object containing tile, piece and card lists
	 * @see Player
	 */
	public Board(ArrayList<Player> playerList){
		initializeTiles();
		initializePieces(playerList);
		initializeCards();
	}

	/**
	 * This method initialize the tiles and puts them to the 
	 * tile list. 
	 * It uses the tile factory to create the tiles and take
	 * the necessary information from the JSON file containing
	 * the information of the tiles.
	 * @modifies tileList
	 * @effects tileList is populated
	 * @see TileFactory
	 * @see Tile
	 */
	private void initializeTiles() {
		tileList.addAll(initializeLane1());
		tileList.addAll(initializeLane0());
		tileList.addAll(initializeLane2());
		for (int i = 0; i < tileList.size(); i++) {
			System.out.println(tileList.get(i).getID());
			System.out.println(tileList.get(i).getName());
		}
	}

	/**
	 * This method initialize cards by using the Card Factory
	 * Adds the created cards to the respective cards list.
	 * @modifies cardList
	 * @effects cardList is populated
	 * @see Cards
	 */
	private void initializeCards(){
		chanceCardList.add(CardFactory.createCard("chance", 0, "jail"));
		chanceCardList.add(CardFactory.createCard("chance", 1, "stCharles"));
		chanceCardList.add(CardFactory.createCard("chance", 2, "hurricane"));
		communityCardList.add(CardFactory.createCard("community", 0, "pay"));
		communityCardList.add(CardFactory.createCard("community", 1, "birthday"));

		for (int i = 0; i < 18; i++) {
			rollThreeCardList.add(CardFactory.createCard("rollThree", i, rollThreeInfo.getNumbers(i)));
		}
	}

	private ArrayList<Tile> initializeLane0(){
		int firstIndex = lane1TileNumber;
		for(int i = firstIndex; i < firstIndex + lane0TileNumber; i++) {
			if(TileInformation.getInstance().getInstance().getType(i).equals(TileType.Railroad) || TileInformation.getInstance().getType(i).equals(TileType.RegularTiles) || TileInformation.getInstance().getType(i).equals(TileType.UtilityTiles)) {
				lane0.add(tileFactory.getTile(TileInformation.getInstance().getType(i), i, TileInformation.getInstance().getTilePosition(i), TileInformation.getInstance().getName(i), TileInformation.getInstance().getColor(i), TileInformation.getInstance().getTilePrice(i)));
			}
			else {
				lane0.add(tileFactory.getTile(TileInformation.getInstance().getType(i), i ,TileInformation.getInstance().getTilePosition(i) , TileInformation.getInstance().getName(i)));		
			}

		}	
		return lane0;
	}

	private ArrayList<Tile> initializeLane1(){
		int firstIndex = 0;
		for(int i = firstIndex; i < firstIndex + lane1TileNumber; i++) {
			if(TileInformation.getInstance().getType(i).equals(TileType.Railroad) || TileInformation.getInstance().getType(i).equals(TileType.RegularTiles) || TileInformation.getInstance().getType(i).equals(TileType.UtilityTiles)) {
				lane1.add(tileFactory.getTile(TileInformation.getInstance().getType(i), i, TileInformation.getInstance().getTilePosition(i), TileInformation.getInstance().getName(i), TileInformation.getInstance().getColor(i), TileInformation.getInstance().getTilePrice(i)));
			}
			else {
				lane1.add(tileFactory.getTile(TileInformation.getInstance().getType(i), i ,TileInformation.getInstance().getTilePosition(i) , TileInformation.getInstance().getName(i)));		
			}
		}	
		return lane1;
	}

	private ArrayList<Tile> initializeLane2(){
		int firstIndex = lane1TileNumber + lane0TileNumber;
		for(int i = firstIndex; i < firstIndex + lane2TileNumber; i++) {
			if(TileInformation.getInstance().getType(i).equals(TileType.Railroad) || TileInformation.getInstance().getType(i).equals(TileType.RegularTiles) || TileInformation.getInstance().getType(i).equals(TileType.UtilityTiles)){
				lane2.add(tileFactory.getTile(TileInformation.getInstance().getType(i), i, TileInformation.getInstance().getTilePosition(i), TileInformation.getInstance().getName(i), TileInformation.getInstance().getColor(i), TileInformation.getInstance().getTilePrice(i)));
			}
			else {
				lane2.add(tileFactory.getTile(TileInformation.getInstance().getType(i), i ,TileInformation.getInstance().getTilePosition(i) , TileInformation.getInstance().getName(i)));		
			}
		}	
		return lane2;
	}

	/**
	 * This method initializes the pieces according to player list given
	 * Also it associates the pieces to the players
	 * @param playerList			the list of the players that will play
	 * @requires player list is not null
	 * @modifies pieceList
	 * @effects pieceList is populated
	 * @see Player
	 * @see Piece
	 */
	private void initializePieces(ArrayList<Player> playerList) {
		Piece piece;
		for (int i = 0; i < playerList.size(); i++) {
			piece = new Piece(i);
			pieceList.add(piece);
			playerList.get(i).setPiece(piece);
		}

	}


	/**
	 * This method returns the tile given id of
	 * @param id					the id of the tile
	 * @return tile				the tile according to the tile id
	 * @requires tileList is not null
	 * 
	 * @see Tile
	 */
	private Tile getTile(int id) {
		return tileList.get(id);
	}

	public Tile getPlayerPositionTile(Player player) {
		TilePosition position = player.getPiece().getCurrentPosition();
		int tileIndex = TileInformation.getInstance().getId(position);
		return getTile(tileIndex);
	}

	public void buyTile(Player player) {
		OwnableTiles tile = (OwnableTiles) getPlayerPositionTile(player);
		tile.buyTile(player);
	}
	public void sellTile(Player player, Tile tile) {
		OwnableTiles ownableTile = (OwnableTiles) tile;
		ownableTile.sellTile(player);
	}

	public TilePath move(Player player, int diceResult) {
		TilePosition currentPosition = player.getPiece().getCurrentPosition();
		TilePath path = new TilePath();
		TilePosition nextPosition = currentPosition;
		boolean direction = true; // Default direction is forward.
		int nextTileId = TileInformation.getInstance().getId(nextPosition);
		Tile nextTile = tileList.get(nextTileId);
		path.addTilePosition(nextPosition);

		if(nextTile instanceof Railroad) {
			nextTile.passedOn(player);
			if(!player.getPiece().getCurrentPosition().equals(nextPosition)) {
				nextPosition = player.getPiece().getCurrentPosition();
				path.addTilePosition(nextPosition);
			}
		}

		if(nextTile instanceof ReverseDirection) {
			direction = false;
		}
		for(int i = 0; i < diceResult - 1; i++) {
			if (direction) {
				nextPosition = nextPosition.getNextTile();
			} else {
				nextPosition = nextPosition.getPreviousTile();
			}
			player.getPiece().setCurrentPosition(nextPosition);
			nextTileId = TileInformation.getInstance().getId(nextPosition);
			nextTile = tileList.get(nextTileId);

			nextTile.passedOn(player);

			path.addTilePosition(nextPosition);
			if(!player.getPiece().getCurrentPosition().equals(nextPosition)) {
				nextPosition = player.getPiece().getCurrentPosition();
				path.addTilePosition(nextPosition);
			}
		}

		TilePosition lastPosition = nextPosition.getNextTile();
		Tile lastTile = tileList.get(TileInformation.getInstance().getId(lastPosition)); // What is this doing?
		path.addTilePosition(lastPosition); // last position wasn't being added, added it here
		player.getPiece().setCurrentPosition(lastPosition);
		lastTile.landedOn(player);
		if(!player.getPiece().getCurrentPosition().equals(lastPosition)) {
			path.addTilePosition(player.getPiece().getCurrentPosition());
			TilePosition position = player.getPiece().getCurrentPosition();
			lastTile = tileList.get(TileInformation.getInstance().getId(position));
			lastTile.landedOn(player);
			nextPosition = player.getPiece().getCurrentPosition();
			path.addTilePosition(nextPosition);

		}
		return path;
	}

	public boolean repOk() {

		if(!tileList.isEmpty() && !pieceList.isEmpty() && !communityCardList.isEmpty() && !chanceCardList.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	public String toString() {
		return "Board [\ntileList= " + tileList.toString() + "\n pieceList= " + pieceList.toString() + "\n communityCardList= "
				+ communityCardList.toString() + "\nchanceCardList " + chanceCardList.toString();
	}


}
