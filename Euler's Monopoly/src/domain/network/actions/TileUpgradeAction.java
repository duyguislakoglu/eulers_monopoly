package domain.network.actions;

public class TileUpgradeAction extends Action {
    int tileId;
    public TileUpgradeAction(int tileId){
        this.tileId = tileId;
    }

    @Override
    public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
        gameCommunicationInterface.tileUpgraded(tileId);
    }
}
