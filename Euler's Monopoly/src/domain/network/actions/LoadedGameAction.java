package domain.network.actions;

import domain.storage.SavedGame;

public class LoadedGameAction extends Action {
    private SavedGame savedGame;
    public LoadedGameAction(SavedGame savedGame){
        this.savedGame = savedGame;
    }

    @Override
    public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
        gameCommunicationInterface.setLoadedGame(savedGame);
        System.out.println("Applied LOADEDGAME");
    }
}
