package domain.network;

import java.io.Serializable;

public class LobbyPlayer implements Serializable {
    private String playerName;
    private int clientId;
    private boolean isBot;
    private int playerId;

    public LobbyPlayer(String playerName, int clientId, boolean isBot, int playerId) {
        this.playerName = playerName;
        this.clientId = clientId;
        this.isBot = isBot;
        this.playerId = playerId;
    }

    public boolean isBot() {
        return this.isBot;
    }
    
    public int getPlayerId() {
        return this.playerId;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setClientId(int newId) {
        this.clientId = newId;
    }

    public int getClientId(){
        return clientId;
    }

    @Override
    public String toString() {
        return "LobbyPlayer{" +
                "playerName='" + playerName + '\'' +
                ", clientId=" + clientId +
                ", isBot=" + isBot +
                ", playerId=" + playerId +
                '}';
    }
}
