package domain.network.actions;

public class TileDowngradeAction extends Action {
        int tileId;
    public TileDowngradeAction(int tileId){
            this.tileId = tileId;
        }

        @Override
        public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
            gameCommunicationInterface.tileDowngraded(tileId);
        }
}
