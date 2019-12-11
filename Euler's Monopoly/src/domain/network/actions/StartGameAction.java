package domain.network.actions;

public class StartGameAction extends Action {
    @Override
    public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
        gameCommunicationInterface.startGame();
    }
}
