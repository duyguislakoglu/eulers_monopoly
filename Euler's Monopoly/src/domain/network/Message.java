package domain.network;

import domain.network.actions.Action;
import domain.network.messaging.MessageType;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements java.io.Serializable {
    private MessageType type;
    private Serializable blob;
    private int clientId;

    public MessageType getType() {
        return type;
    }

    public Serializable getBlob() {
        return blob;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public Message(int clientId){
        type = MessageType.CLIENT_ID;
        blob = clientId;
    }
    public Message(String string){
        type = MessageType.STRING;
        blob = string;
    }

    public Message(Action action){
        type = MessageType.ACTION;
        blob = action;
    }
    public Message(ArrayList<LobbyPlayer> lobbyPlayers){
        type = MessageType.PLAYERLIST;
        blob= lobbyPlayers;
    }
    public Message(LobbyPlayer player){
        type = MessageType.ADD_PLAYER;
        blob = player;
    }
    public Message(){
        type= MessageType.HEART_BEAT;
    }
}
