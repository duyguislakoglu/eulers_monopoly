package domain.network.actions;

public class ResumeGameAction extends Action {
    public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
        gameCommunicationInterface.resumeGame();
    }
}
