package domain.board.tiles;

import java.awt.Color;

public class TileFactory {
	private static TileFactory instance = null;

	private TileFactory() {

	}

	public static TileFactory getInstance() {
		if (instance == null) {
			instance = new TileFactory();
		}
		return instance;
	}

	public  Tile getTile(TileType type, int id, TilePosition position, String name) {  
		Tile tile = null;
		if (type.equals(TileType.Jail))  
			tile = new Jail(id, name, position);  
		else if(type.equals(TileType.FreeParking))  
			tile = new FreeParking(id, name, position);  
		else if(type.equals(TileType.Go))
			tile = new Go(id, name, position);
		else if(type.equals(TileType.HollandTunnel))
			tile = new HollandTunnel(id, name, position);
		else if(type.equals(TileType.PayDay))
			tile = new PayDay(id, name, position);
		else if(type.equals(TileType.ReverseDirection))
			tile = new ReverseDirection(id, name, position);
		else if(type.equals(TileType.Subway))
			tile = new Subway(id, name, position);
		else if(type.equals(TileType.TaxRefund))
			tile = new TaxRefund(id, name, position);
		else if(type.equals(TileType.ChanceCard))
			tile = new ChanceTile(id, name, position);
		else if(type.equals(TileType.CommunityCard))
			tile = new CommunityTile(id, name, position);
		else if(type.equals(TileType.Bonus))
			tile = new Bonus(id, name, position);
		else if(type.equals(TileType.RollThreeTile))
			tile = new RollThreeTile(id, name, position);
		else if(type.equals(TileType.TaxRefund))
			tile = new TaxRefund(id, name, position);
		else if(type.equals(TileType.BirthdayTile))
			tile = new BirthdayTile(id, name, position);
		return tile;
	}
	public OwnableTiles getTile(TileType type, int id, TilePosition position, String name, int color, int price) {
		OwnableTiles tile = null;
		if(type == TileType.RegularTiles)
			tile = new RegularTiles(id, name, position, color,price);
		else if(type == TileType.Railroad)
			tile = new Railroad(id, name, position,price);
		else if(type == TileType.UtilityTiles)
			tile = new UtilityTiles(id, name, position, price);

		return tile;
	}


}
