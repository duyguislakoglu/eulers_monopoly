package domain.network;

import domain.game.GameControler;
import domain.network.actions.Action;
import domain.network.actions.GameCommunicationInterface;
import domain.network.messaging.MessageType;

import java.util.ArrayList;

public class CommunicationProtocol {
    private static CommunicationProtocol instance;
    private GameCommunicationInterface gameCommunicationInterface = GameControler.getInstance();
    public static synchronized CommunicationProtocol getInstance(){
        if(instance == null){
            instance = new CommunicationProtocol();
        }
        return instance;
    }
    private CommunicationProtocol(){

    }
    public void clientFailure(Client client){ // runs on servers thread
        System.out.println("Client Failure");
        client.interruptReceiveThread();

        // TODO: move players from client to our client. Update legal actions afterwards in some method
        GameControler.getInstance().clientFailure(client.getClientId());
    }
    public void networkFailure(){ // runs on clients thread
        System.err.println("Connection to server has dropped");

    }

    public void receivedMessage(Message message, boolean isServer, Client client){
        System.out.println("received message");
        MessageType type = message.getType();
        int clientId = message.getClientId();
        switch (type){
            case ACTION:
                System.out.println("received action");
                Action action = (Action) message.getBlob();
                if(isServer){
                    // Redistribute message
                    System.out.println("distributing action");
                    NetworkController.getInstance().distributeMessageToOtherClients(message,client);
                }else{
                    System.out.println("applying action");
                    action.applyAction(gameCommunicationInterface);
                }

                break;
            case STRING:
                String string = (String) message.getBlob();
                //System.out.println(string);
                if(isServer){
                    // Redistribute message
                    NetworkController.getInstance().distributeMessageToOtherClients(message,client);
                }else {
                    NetworkController.getInstance().receivedNewMessage(clientId, string);
                }
                break;
            case PLAYERLIST:
                Lobby.getInstance().setPlayers((ArrayList<LobbyPlayer>) message.getBlob());
                System.out.println("Received LobbyPlayer list: " + Lobby.getInstance().getPlayers());
                NetworkController.getInstance().updatedPlayerList(Lobby.getInstance().getPlayersAsStringArray());
                break;
            case ADD_PLAYER:
                Lobby.getInstance().addPlayer((LobbyPlayer) message.getBlob());
                Message m = new Message(Lobby.getInstance().getPlayers());
                NetworkController.getInstance().distributeMessageToOtherClients(m,null);
                break;
            case CLIENT_ID:
                int c = (int) message.getBlob();
                client.setClientId(c);
                break;
            case HEART_BEAT:
                client.receivedHeartBeatFromClient();
               // System.out.println("Received heart beat from Client: " + client.getClientId());
        }
    }

}
