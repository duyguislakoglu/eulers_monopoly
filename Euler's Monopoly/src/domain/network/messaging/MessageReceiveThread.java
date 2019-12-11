package domain.network.messaging;

import domain.network.Client;
import domain.network.CommunicationProtocol;
import domain.network.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessageReceiveThread extends Thread
{
    protected ObjectInputStream is;
    protected Socket socket;
    private boolean isServer;
    private Client client;
    private boolean working = true;

    /**
     * Creates a server thread on the input socket
     *
     * @param s input socket to create a thread on
     */
    public MessageReceiveThread(Socket s)
    {
        this.socket = s;
        isServer =false;
    }
    public MessageReceiveThread(Socket s, Client c){
        this.socket = s;
        this.client = c;
        isServer = true;
    }

    public synchronized void stopThread(){
        working= false;
    }

    /**
     * The server thread, echos the client until it receives the QUIT string from the client
     */
    public void run()
    {
        try
        {
            is = (new ObjectInputStream(socket.getInputStream()));

        }
        catch (IOException e)
        {
            System.err.println("Server Thread. Run. IO error in receiver thread");
            e.printStackTrace();
        }

        try
        {
            Message message = (Message) is.readObject();
            while (working)
            {
                if(isServer){
                    CommunicationProtocol.getInstance().receivedMessage(message, isServer, client);
                }else{
                    CommunicationProtocol.getInstance().receivedMessage(message, isServer, null);
                }
                message = (Message) is.readObject();
            }
        }
        catch (IOException e)
        {
            System.err.println("Server Thread. Run. IO Error/ Client " + this.getName() + " terminated abruptly");
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            System.err.println("Server Thread. Run.Client " + this.getName() + " Closed");
            e.printStackTrace();
        }
        catch (Exception e){
            System.err.println("Server Thread. General exception " + this.getName() + " Closed");
            e.printStackTrace();
        }
        finally
        {
            // client closed game or client has disconnected
            if(client == null){
                CommunicationProtocol.getInstance().networkFailure();
            }else{
                CommunicationProtocol.getInstance().clientFailure(client);
            }


            try
            {

                System.out.println("Closing the connection");
                if (is != null)
                {
                    is.close();
                    System.err.println(" Socket Input Stream Closed");
                }

                if (socket != null)
                {
                    socket.close();
                    System.err.println("Socket Closed");
                }

            }
            catch (IOException ie)
            {
                System.err.println("Socket Close Error");
            }
        }//end finally
    }
}
