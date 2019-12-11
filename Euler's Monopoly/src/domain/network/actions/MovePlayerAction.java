package domain.network.actions;

import domain.board.tiles.TilePath;

public class MovePlayerAction extends Action {

    int playerId;
    TilePath path;

    public MovePlayerAction(int playerId, TilePath path) {
        this.playerId = playerId;
        this.path = path;
    }

    public void applyAction(GameCommunicationInterface i){
        i.movePlayer(playerId,path);
    }
}
