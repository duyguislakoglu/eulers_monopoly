package domain.board.tiles;

public class FreeParking extends Tile{

	public FreeParking(int ID, String name, TilePosition position) {
		super(ID, name, position);
		this.setType(TileType.FreeParking);
	}

}
