package domain.network;


import domain.network.messaging.*;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicationServer {
    private ServerSocket serverSocket;
    private ArrayList<Client> clients = new ArrayList<Client>();
    public CommunicationServer(int port) {
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Opened up a server socket on " + Inet4Address.getLocalHost());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Server class.Constructor exception on opening a server socket");
        }
/*        while (true)
        {
            ListenAndAccept();
        }*/
        Runnable r= new Runnable() {
            @Override
            public void run() {
                while (true){
                    ListenAndAccept();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();

    }


    private void ListenAndAccept() {
        Socket s;
        try
        {
            s = serverSocket.accept();
            System.out.println("A connection was established with a client on the address of " + s.getRemoteSocketAddress());
            Client c = new Client();
            MessageReceiveThread mrt = new MessageReceiveThread(s,c);
            MessageSendThread mst = new MessageSendThread(s,c.messageQueue);
            mrt.start();
            mst.start();
            c.addMessageReceiveThread(mrt);
            addClient(c);
        }

        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Server Class.Connection establishment error. Is the port blocked or another instance of the server running?");
        }
    }
    private void addClient(Client c){
        clients.add(c); // TODO change
    }
    public void sendMessageToAllClients(Message message){
        for(int i=0; i<clients.size();i++){
            clients.get(i).sendMessage(message);
        }
    }
    public void distributeMessageToOtherClients(Message message, Client client){
        for(int i=0; i<clients.size();i++){
            Client c= clients.get(i);
            if(client == null || c.getClientId() != client.getClientId()){
                clients.get(i).sendMessage(message);
            }

        }
    }
}
