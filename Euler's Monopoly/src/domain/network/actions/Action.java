package domain.network.actions;

import java.io.Serializable;

public abstract class Action implements Serializable{
    public abstract void applyAction(GameCommunicationInterface gameCommunicationInterface);

}
