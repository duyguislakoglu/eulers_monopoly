package domain.network;

import domain.game.GameControler;
import domain.network.actions.Action;
import domain.network.actions.StartGameAction;
import domain.network.messaging.MessageReceiveThread;
import domain.network.messaging.MessageSendThread;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class CommunicationClient {
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";  // "172.16.128.187";
    public static final int DEFAULT_SERVER_PORT = 5555;
    private LinkedBlockingQueue<Message> messageQueue;
    private String serverAddress;
    private int serverPort;
    private int clientId;
    private static long DEFAULT_HEART_BEAT_WAIT = 3000;



    public CommunicationClient(String serverAddress, int serverPort, int clientId){
        if(serverAddress == null) serverAddress = "localhost";
        this.serverAddress= serverAddress;
        this.serverPort= serverPort;
        messageQueue = new LinkedBlockingQueue<>();
        this.clientId = clientId; // TODO: client id generation
    }

    public void connect()
    {
        try
        {
            // TODO: redundant code, refactor
            Socket s= new Socket(serverAddress, serverPort);
            MessageReceiveThread mrt = new MessageReceiveThread(s);
            MessageSendThread mst = new MessageSendThread(s,messageQueue);
//            c.setMessageReceiveThread(mrt);
//            c.setMessageSendThread(mst);
            mrt.start();
            mst.start();


            System.out.println("Successfully connected to " + serverAddress + " on port " + serverPort);
            // TODO: send client id here?
            sendClientIdToServer();
            NetworkController.getInstance().connectionSuccessful();
            startHeartBeatSender(); // send heartbeats to make sure connection is alive
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.err.println("Error: no server has been found on " + serverAddress + "/" + serverPort);
            NetworkController.getInstance().connectionFailed();
        }
    }
    protected void sendMessage(Message message){
        message.setClientId(clientId);
        messageQueue.add(message);
    }
    private void sendClientIdToServer(){
        Message message= new Message(clientId);
        sendMessage(message);
    }
    protected void createAndSendPlayer(String playerName, boolean isBot){
        // create playerId
        int playerId = new Random().nextInt();
        LobbyPlayer player = new LobbyPlayer(playerName, clientId, isBot, playerId);
        Message message = new Message(player);
        sendMessage(message);
    }
    protected void sendAction(Action action){
        Message message= new Message(action);
        sendMessage(message);
    }

    private void startHeartBeatSender(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(DEFAULT_HEART_BEAT_WAIT);
                        Message message = new Message();
                        sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }

    protected int getClientId(){
        return clientId;
    }

}
