package domain.board.tiles;

import domain.game.GameControler;
import domain.game.Piece;
import domain.game.Player;

public class HollandTunnel extends Tile {
	private boolean fromHollandTunnel;
	public HollandTunnel(int ID, String name, TilePosition position) {
		super(ID, name, position);
		this.setType(TileType.HollandTunnel);
		fromHollandTunnel = false;
	}
	@Override
	public void landedOn(Player p) {		
		if(!fromHollandTunnel) {
			Piece piece = p.getPiece();
			int secondTileId = getSecondHollandTunnel();
			piece.teleport(secondTileId);
			HollandTunnel otherHolland = (HollandTunnel) GameControler.monopolyGame.getTileList().get(secondTileId);
			otherHolland.setFromHolland(true);
		}
		setFromHolland(false);
	}

	private int getSecondHollandTunnel() {
		TilePosition secondTilePosition = TileInformation.getInstance().getSecondTilePosition(this.getID());
		
		return TileInformation.getInstance().getId(secondTilePosition);
	}
	public void setFromHolland(boolean b) {
		fromHollandTunnel = b;
	}
}
