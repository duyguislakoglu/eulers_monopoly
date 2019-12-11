package domain.network.actions;

public class EndTurnAction extends Action {
    public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
        gameCommunicationInterface.endTurn();
    }
}
