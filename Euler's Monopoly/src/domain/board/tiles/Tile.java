/**
h * Tile is the abstract base class for all the tiles we will create.
 * A tile object contains all the information that is needed for 
 * various operations.  
 * Tiles are contained in the board and they allow players to move on
 * the board.
 * <p>
 * @author Hazal Meng�aslan
 * 
 */


package domain.board.tiles;

import domain.game.GameControler;
import domain.game.Player;

import java.io.Serializable;

public abstract class Tile implements Serializable {
	private int tileID;
	private String name;
	private boolean ownable = false;
	private TileType type;
	private static TilePosition position;
	/**
	 * Constructor for the tile object. 
	 * 
	 * @param id			tile id
	 * @param name			name of the tile
	 * @param position		position on the board
	 * 
	 * @see					TilePosition
	 */
	public Tile(int id, String name, TilePosition position) {
		this.tileID = id;
		this.name = name;
		this.position = position;
		this.setType(type);
	}
	
	/**
	 * Returns the id of the tile
	 * @return				tile id as integer
	 */
	public int getID() {
		return this.tileID;
	}
	
	/**
	 * Returns the name of the tile 
	 * @return				name as String
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns whether that tile can be purchased by player
	 * 
	 * @return				boolean ownable depending on the subclass' declaration
	 */
	public boolean isOwnable() {
		return ownable;
	}
	
	/**
	 * 
	 * @param ownable
	 */
	protected void setOwnable(boolean ownable) {
		this.ownable = ownable;
	}

	/**
	 * 
	 * @return type			type from the TileType enum
	 * @see TileType
	 */
	public TileType getType() {
		return type;
	}
	
	/**
	 * This method is used to set the type of the tile in the constructor.
	 * @param type			type of tile that is created
	 * 
	 * @see TileType
	 */
	protected void setType(TileType type) {
		this.type = type;
	}
	
	/**
	 * This method is used in the ownable tiles, Railroad and the RegularTiles
	 * @return				return false for default, overriden when necessary
	 * 
	 */
	public boolean isOwned() {
		return false;
	}
	
	/**
	 * This method returns the position of the tile which is assigned
	 * depending on the lane and the index of the tile on that lane.
	 * @return				position as TilePosition
	 * 
	 * @see TilePosition
	 */
	public TilePosition getPosition() {
		return position;
	}
	
	/**
	 * This method sets the position of a tile whenever a tile is created.
	 * @param position		the position to add the tile
	 * 
	 * @see TilePosition
	 */
	public void setPosition(TilePosition position) {
		this.position = position;
	}
	

	/**
	 * This method decides the operation depending on the type
	 * of the tile.
	 * This method is overridden in all subclasses. 
	 * @param player		player that landed on the tile
	 * 
	 * @see Player
	 */
	public void landedOn(Player player) {
		
	}
	
	/**
	 * This method is applied when a player passes on a tile
	 * that have a special condition while passing on.
	 * @param player		player that passes on the tile
	 * 
	 * @see Player
	 */
	public void passedOn(Player player) {
		// TODO: Implement the passedOn method, make the necessary changes in the subclasses
	}
	
	public boolean repOk() {
		if((tileID >= 0) && (position.getLane() == 0 || position.getLane() == 1 || position.getLane() == 2) && 
				(position.getTileIndex() >= 0 && position.getTileIndex() <=39))
			return true;
		else {
			return false;
		}
	}
	@Override
	public String toString() {
		return "Tile [tileId= " + tileID + ", name= " + name + ", ownable= " + ownable + ", type= " + type + ", position= " + position
				+ "]";
	}
	
	public String assetInfoToString() {
		if (this.getType().equals(TileType.RegularTiles)) {
			return ((RegularTiles)this).AssetsToString();
		}
		else if (this.getType().equals(TileType.Railroad)) {
			return ((Railroad)this).AssetsToString();
		}
		else return " ";
	}

}