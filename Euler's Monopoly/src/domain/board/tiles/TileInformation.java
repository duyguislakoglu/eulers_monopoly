package domain.board.tiles;

import java.awt.Color;
import java.io.FileReader;
import java.io.Serializable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TileInformation implements Serializable {

	private JSONArray tileList;
	private static TileInformation instance;

	public static TileInformation getInstance(){
		if(instance == null) instance = new TileInformation();
		return instance;
	}

	private TileInformation() {
		this.tileList = importTileJSON();

	}


	public String getName(int tileId) {
		return ((JSONObject) tileList.get(tileId)).get("name").toString();
	}
	public TilePosition getTilePosition(int tileId) {
		TilePosition position = new TilePosition(this.getLane(tileId),this.getIndex(tileId));
		return position;
	}
	public TilePosition getSecondTilePosition(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("position2");
		int lane = Integer.valueOf(pricesJA.get("lane2").toString());
		int tileIndex = Integer.valueOf(pricesJA.get("index2").toString());
		TilePosition position = new TilePosition(lane,tileIndex);
		return position;
	}
	public int getLane(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("position");
		return Integer.valueOf(pricesJA.get("lane").toString());
	}
	public int getIndex(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("position");
		return Integer.valueOf(pricesJA.get("index").toString());
	}
	public int getColor(int tileId) {
		int color = -1;
		if(this.getType(tileId).equals(TileType.RegularTiles)) {
			color = Integer.valueOf(((JSONObject) tileList.get(tileId)).get("color").toString());
			}
		return color;
	}
	public TileType getType(int tileId) {
		String typeString  = ((JSONObject) tileList.get(tileId)).get("type").toString();
		if (typeString.equals("Jail"))  
			return TileType.Jail; 
		else if(typeString.equals("FreeParking"))  
			return TileType.FreeParking;
		else if(typeString.equals("Go"))
			return TileType.Go;
		else if(typeString.equals("HollandTunnel"))
			return TileType.HollandTunnel;
		else if(typeString.equals("PayDay"))
			return TileType.PayDay;
		else if(typeString.equals("ReverseDirection"))
			return TileType.ReverseDirection;
		else if(typeString.equals("Subway"))
			return TileType.Subway;
		else if(typeString.equals("Chance"))
			return TileType.ChanceCard;
		else if(typeString.equals("CommunityChest"))
			return TileType.CommunityCard;
		else if(typeString.equals("RegularTile"))
			return TileType.RegularTiles;
		else if(typeString.equals("Railroad"))
			return TileType.Railroad;
		else if(typeString.equals("BirthdayTile"))
			return TileType.BirthdayTile;
		else if(typeString.equals("Bonus"))
			return TileType.Bonus;
		else if(typeString.equals("RollThreeTile"))
			return TileType.RollThreeTile;
		else if(typeString.equals("TaxRefund"))
			return TileType.TaxRefund;
		else if(typeString.equals("UtilityTile"))
			return TileType.UtilityTiles;
		else return TileType.FreeParking;
	}
	public int getTilePrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("tile");
		return Integer.valueOf(price.toString());
	}
	public int getRentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("r");
		return Integer.valueOf(price.toString());
	}
	public int get1HRentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("h1r");
		return Integer.valueOf(price.toString());
	}
	public int get2HRentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("h2r");
		return Integer.valueOf(price.toString());
	}
	public int get3HRentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("h3r");
		return Integer.valueOf(price.toString());
	}
	public int get4HRentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("h4r");
		return Integer.valueOf(price.toString());
	}
	public int getHotelRentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("hotelr");
		return Integer.valueOf(price.toString());
	}
	public int getSkyRentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("skyr");
		return Integer.valueOf(price.toString());
	}
	public int getMortgagePrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("mortgage");
		return Integer.valueOf(price.toString());
	}
	public int getHousePrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("h");
		return Integer.valueOf(price.toString());
	}
	public int getHotelPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("hotel");
		return Integer.valueOf(price.toString());
	}
	public int getSkyPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("sky");
		return Integer.valueOf(price.toString());
	}
	public int get1RentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("1r");
		return Integer.valueOf(price.toString());
	}
	public int get2RentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("2r");
		return Integer.valueOf(price.toString());
	}
	public int get3RentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("3r");
		return Integer.valueOf(price.toString());
	}
	public int get4RentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("4r");
		return Integer.valueOf(price.toString());
	}
	public int get5RentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("5r");
		return Integer.valueOf(price.toString());
	}
	public int get6RentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("6r");
		return Integer.valueOf(price.toString());
	}
	public int get7RentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("7r");
		return Integer.valueOf(price.toString());
	}
	public int get8RentPrice(int tileId) {
		JSONObject pricesJA = (JSONObject) ((JSONObject) tileList.get(tileId)).get("prices");
		Object price = pricesJA.get("8r");
		return Integer.valueOf(price.toString());
	}
	public int getId(TilePosition pos) {
		boolean notFound = true;
		int count = -1;
		while (notFound && count < 120) {
			count++;
			if (this.getIndex(count) == pos.getTileIndex() && this.getLane(count) == pos.getLane()) {
				notFound = false;
			}
		}
		return count;
	}

	private JSONArray importTileJSON() {
		JSONArray tilesJA = null;
		try {
			JSONParser parser = new JSONParser();
			JSONObject parsed = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/lib/TileJSON.json"));
			tilesJA = (JSONArray) parsed.get("tiles");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tilesJA;

	}
}
