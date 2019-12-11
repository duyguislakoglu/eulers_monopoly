package domain.board.tiles;

import domain.game.Player;

public abstract class OwnableTiles extends Tile {
	private boolean isOwned = false;
	private Player owner;
	private boolean isMortgaged = false;
	private int price;
	public OwnableTiles(int id, String name, TilePosition position, int price) {
		super(id, name, position);
		this.setOwnable(true);
		this.setPrice(price);
	}

	private void setPrice(int price) {
		this.price = price;
	}
	public boolean isOwned() {
		return isOwned;
	}
	public boolean setOwned(boolean isOwned) {
		return this.isOwned = isOwned;
	}
	public void setOwner(Player player) {
		this.owner = player;
	}
	public Player getOwner() {
		return this.owner;
	}
	public boolean isMortgaged() {
		return isMortgaged;
	}

	public void setMortgaged(boolean isMortgaged) {
		this.isMortgaged = isMortgaged;
	}

	public void buyTile(Player player) {
		this.setOwner(player);
		this.setOwned(true);
		if(player.isBot()) System.out.println("Bot buyed here.");
	}

	public void sellTile(Player player) {

		this.setOwner(null);
		this.setOwned(false);

	}

	protected void payRent(Player player) {
		if(this.isOwned && !this.getOwner().equals(player) &&
				!this.getOwner().isJailed()&&
				player.getMoney()>= this.getRent()) {
			player.decreaseMoney(this.getRent());
			this.getOwner().increaseMoney(this.getRent());
		}
	}

	public int getPrice() {
		return TileInformation.getInstance().getTilePrice(this.getID());
	};
	
	public abstract int getRent();

}
