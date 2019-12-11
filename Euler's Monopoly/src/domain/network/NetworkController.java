package domain.network;

import domain.game.GameControler;
import domain.network.actions.StartGameAction;
import gui.Application;
import domain.network.actions.Action;

public class NetworkController {
    private CommunicationServer communicationServer;
    private CommunicationClient communicationClient;
    private static NetworkController instance;

    private PreGameCommInterface mInterface = new PreGameCommInterface() {
        @Override
        public void connectionSuccesful() {
            System.out.println("connection succesful");
        }

        @Override
        public void connectionFailed() {
            System.out.println("conn failed");
        }

        @Override
        public void updatePlayerClientList(String[] playerClientStrings) {
            for(int i=0; i<playerClientStrings.length; i++){
                System.out.println(playerClientStrings[i]);
            }
        }

        @Override
        public void newMessage(int clientId, String message) {
            System.out.println("new msg from client: " + clientId + " , " + message);
        }
    };

    public static synchronized NetworkController getInstance(){
        if(instance == null){
            instance = new NetworkController();
        }
        return instance;
    }

    private NetworkController() {

    }
    protected void connectionSuccessful(){
        mInterface.connectionSuccesful();
    }
    protected void connectionFailed(){
        mInterface.connectionFailed();
    }
    protected void receivedNewMessage(int clientId, String string){
        mInterface.newMessage(clientId,string);
    }
    protected void updatedPlayerList(String[] strings){
        mInterface.updatePlayerClientList(strings);
    }
    protected void startGame(){
        // TODO: notify game controller!
        Application.initializeApplication(); // Todo: fix
    }


    public void createServer(int port){
        communicationServer = new CommunicationServer(port);
        connectToServer("localhost" ,port, 999);
    }
    public void sendMessage(Message message){
        if(communicationClient != null){
            communicationClient.sendMessage(message);
        }
    }
    public void sendAction(Action action){
        if(communicationClient != null)
        communicationClient.sendAction(action);
    }
    public void connectToServer(String serverAddress,int serverPort, int clientId ){
        communicationClient= new CommunicationClient(serverAddress,serverPort, clientId);
        communicationClient.connect();
    }
    protected void distributeMessageToOtherClients(Message message, Client excludedClient){
        // Sends message from server to all the clients other than the excludedClient. Sends to everyone if excludedClient is null
        communicationServer.distributeMessageToOtherClients(message,excludedClient);
    }
    public void addAndSendPlayer(String playerName, boolean isBot){
        communicationClient.createAndSendPlayer(playerName,isBot);
    }
    public void sendStartGame(){
        Action action= new StartGameAction();
        communicationClient.sendAction(action);
        GameControler.getInstance().startGame();
    }
    public void setPreGameCommInterface(PreGameCommInterface preGameCommInterface){
        mInterface = preGameCommInterface;
    }

    public int getMyClientId(){
        if(communicationClient == null){
            return 999; // PLACEHOLDER FOR TESTING
        }else{
            return communicationClient.getClientId();
        }
    }


}
