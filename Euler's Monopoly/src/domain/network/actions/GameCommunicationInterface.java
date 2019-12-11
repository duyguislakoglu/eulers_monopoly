package domain.network.actions;

import domain.board.tiles.TilePath;
import domain.storage.SavedGame;

public interface GameCommunicationInterface {
    void movePlayer(int playerId, TilePath path);
    void startGame();
    void endTurn();
    void pauseGame();
    void resumeGame();
    void setPlayerMoney(int playerId, int money);
    void playerBoughtTile();
    void playerSoldTile(int playerId, int tileId);
    // TODO: what about cards and buildings, need help with the other methods down here
    void setPlayerCards();
    void tileUpgraded(int tileId);
    void tileDowngraded(int tileId);
    void setLoadedGame(SavedGame savedGame);
}
