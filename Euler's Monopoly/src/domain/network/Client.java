package domain.network;

import domain.network.messaging.MessageReceiveThread;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    LinkedBlockingQueue<Message> messageQueue;
    private int clientId;
    private MessageReceiveThread messageReceiveThread;
    private long lastHeartBeat;
    private static long DEFAULT_DISCONNECT_TIME_MILIS = 15000;

    public Client() {
        messageQueue = new LinkedBlockingQueue<>();
        clientId = new Random().nextInt(); // TODO: client id generation
        lastHeartBeat = System.currentTimeMillis();
    }

    public void addMessageReceiveThread(MessageReceiveThread thread){
        messageReceiveThread = thread;
    }
    public void interruptReceiveThread(){
        if(messageReceiveThread != null){
            messageReceiveThread.stopThread();
            messageReceiveThread = null;
        }
    }
    public void receivedHeartBeatFromClient(){
        lastHeartBeat= System.currentTimeMillis();
    }

    public void sendMessage(Message message){
        long timeFromLastHeartBeat = System.currentTimeMillis() - lastHeartBeat;
        if(timeFromLastHeartBeat > DEFAULT_DISCONNECT_TIME_MILIS){
            CommunicationProtocol.getInstance().clientFailure(this);
        }else {
            messageQueue.add(message);
        }
    }

    public int getClientId() {
        return clientId;
    }
    public void setClientId(int id){  clientId = id;}
}
