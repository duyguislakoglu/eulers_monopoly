package domain.board.tiles;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class TilePath implements Serializable {
	
	private LinkedList<TilePosition> positionList = new LinkedList<TilePosition>();

	public TilePath() {
		
	}
	
	public boolean hasNextPosition() {
		return !positionList.isEmpty();
	}
	
	public TilePosition nextPosition() {
		return positionList.remove();
	}
	
	public void addTilePosition(TilePosition pos) {
		positionList.add(pos);
	}

	public TilePosition getFinalPosition(){
		return positionList.getLast();
	}
	
}
