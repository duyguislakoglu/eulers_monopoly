package domain.network.actions;

public class TileBoughtAction extends Action {
    public TileBoughtAction(){
    }
    public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
        gameCommunicationInterface.playerBoughtTile();
    }
}
