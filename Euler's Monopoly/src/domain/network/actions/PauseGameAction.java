package domain.network.actions;

public class PauseGameAction  extends  Action{
    public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
        gameCommunicationInterface.pauseGame();
    }
}
