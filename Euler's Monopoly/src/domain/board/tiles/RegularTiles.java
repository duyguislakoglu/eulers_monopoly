package domain.board.tiles;

import domain.game.Player;
import domain.network.NetworkController;
import domain.network.actions.Action;
import domain.network.actions.TileDowngradeAction;

public class RegularTiles extends OwnableTiles {
	private int color;
	private int houseNumber = 0;
	private int hotelNumber = 0;
	private int skyscraperNumber = 0;

	public RegularTiles(int ID, String name, TilePosition position, int color, int price) {
		super(ID, name, position,price);
		this.setType(TileType.RegularTiles);
		this.color = color;
	}

	public int getColor() {
		return this.color;
	}

	public int getRent() {
		if(isOwned()) {
			if(this.getHouseNumber()  == 0 && this.getHotelNumber() == 0 && this.getSkyscraperNumber() == 0) {
				return TileInformation.getInstance().getRentPrice(this.getID());
			}
			else if(this.getHouseNumber() == 1) {
				return TileInformation.getInstance().get1HRentPrice(this.getID());
			}
			else if(this.getHouseNumber() == 2) {
				return TileInformation.getInstance().get2HRentPrice(this.getID());
			}
			else if(this.getHouseNumber() == 3) {
				return TileInformation.getInstance().get3HRentPrice(this.getID());
			}
			else if(this.getHouseNumber() == 4) {
				return TileInformation.getInstance().get4HRentPrice(this.getID());
			}
			else if(this.getHotelNumber() == 1) {
				return TileInformation.getInstance().getHotelRentPrice(this.getID());
			}
			else if(this.getSkyscraperNumber() == 1) {
				return TileInformation.getInstance().getSkyRentPrice(this.getID());
			}
		}
		return 5;
	}



	public void landedOn(Player player) {
		if (this.isOwned() && this.getOwner() != player && !this.isMortgaged()) {
			payRent(player);
		}
	}
	public void removeBuilding() {
		Action action= new TileDowngradeAction(getID());
		NetworkController.getInstance().sendAction(action);
		buildingRemoved();
	}
	public void buildingRemoved(){
		if (skyscraperNumber == 0) {
			if (hotelNumber == 0) {
				if (houseNumber != 0) {
					sellHouse();
				} else return;
			} else {
				sellHotel(); return;
			}
		} else {
			sellSkyscraper();
		}
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	private void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public void buildHouse() {

		this.setHouseNumber(this.getHouseNumber() + 1);
	}

	public int getHotelNumber() {
		return hotelNumber;
	}

	private void setHotelNumber(int hotelNumber) {
		this.hotelNumber = hotelNumber;
	}

	public void buildHotel() {
		this.setHouseNumber(0);
		this.setHotelNumber(this.getHotelNumber() + 1);
	}

	public int getSkyscraperNumber() {
		return skyscraperNumber;
	}

	private void setSkyscraperNumber(int skyscraperNumber) {
		this.skyscraperNumber = skyscraperNumber;
	}
	public void buildSkyscraper() {
		this.setHotelNumber(0);
		this.setSkyscraperNumber(this.getSkyscraperNumber() + 1);
	}

	public void sellHouse() {
		this.setHouseNumber(this.getHouseNumber()-1);
	}
	public void sellHotel() {
		this.setHotelNumber(0);
		this.setHouseNumber(4);
	}
	public void sellSkyscraper() {
		this.setSkyscraperNumber(0);
		this.setHotelNumber(1);
	}
	public String AssetsToString() {		
		return "\n" + this.getName() + "\n# of House: " + Integer.toString(this.houseNumber)
		+ "\n# of Hotel: " + Integer.toString(this.hotelNumber)
		+ "\n# of Skyscraper: " +Integer.toString(this.skyscraperNumber)  ;
	}


}
