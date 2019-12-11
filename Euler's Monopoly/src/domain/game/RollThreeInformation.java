package domain.game;

import java.io.FileReader;
import java.io.Serializable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RollThreeInformation implements Serializable {
	
	private JSONArray rollThreeList;
	
	public RollThreeInformation() {
		
		this.rollThreeList = importTileJSON();
		
	}
	
	public String getNumbers(int rollThreeId) {
		return ((JSONObject) rollThreeList.get(rollThreeId)).get("name").toString();

	}
	
	private JSONArray importTileJSON() {
		JSONArray cardsJA = null;
		try {
			JSONParser parser = new JSONParser();
			JSONObject parsed = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/lib/rollThreeJSON.json"));
			cardsJA = (JSONArray) parsed.get("rollThree");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cardsJA;

	}

	
	
	
}