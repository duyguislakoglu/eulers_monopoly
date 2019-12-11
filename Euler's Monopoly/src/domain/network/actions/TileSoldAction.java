package domain.network.actions;

public class TileSoldAction extends Action {
    int playerId;
    int tileId;
    public TileSoldAction(int playerId, int tileId){
        this.playerId = playerId;
        this.tileId = tileId;
    }
    public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
        gameCommunicationInterface.playerSoldTile(playerId,tileId);
    }
}
