package domain.network.actions;

import java.io.Serializable;

public class PlayerMoneyChangedAction extends Action {
    int playerId;
    int money;
    public PlayerMoneyChangedAction(int playerId, int money){
        this.playerId = playerId;
        this.money = money;
    }
    public void applyAction(GameCommunicationInterface gameCommunicationInterface) {
        gameCommunicationInterface.setPlayerMoney(playerId, money);
    }
}
