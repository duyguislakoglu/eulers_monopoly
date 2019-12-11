package domain.board.tiles;

import java.io.Serializable;

public class TilePosition implements Serializable {
	private int lane;
	private int tileIndex;
	
	public TilePosition(int lane, int tileIndex) {
		this.lane = lane;
		this.tileIndex = tileIndex;
	}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public int getTileIndex() {
		return tileIndex;
	}

	public void setTileIndex(int tileIndex) {
		this.tileIndex = tileIndex;
	}
	public TilePosition getNextTile() {
		if(this.getLane() == 0 && this.getTileIndex() == 23) {
			return new TilePosition(this.getLane(), 0);
		}
		if(this.getLane() == 1 && this.getTileIndex() == 39) {
			return new TilePosition(this.getLane(), 0);
		}
		if(this.getLane() == 2 && this.getTileIndex() == 55) {
			return new TilePosition(this.getLane(), 0);
		}
		return new TilePosition(this.getLane(), this.getTileIndex() + 1);
	}
	public TilePosition getPreviousTile() {
		if(this.getLane() == 0 && this.getTileIndex() == 0) {
			return new TilePosition(this.getLane(), 23);
		}
		if(this.getLane() == 1 && this.getTileIndex() == 0) {
			return new TilePosition(this.getLane(), 39);
		}
		if(this.getLane() == 2 && this.getTileIndex() == 0) {
			return new TilePosition(this.getLane(), 55);
		}
		return new TilePosition(this.getLane(), this.getTileIndex() - 1);
	}

	@Override
	public String toString() {
		String s= "TilePosition: lane: " + lane + " ,tileIndex: " + tileIndex;
		return s;
	}
}
