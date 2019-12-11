package domain.network.messaging;

import domain.network.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;


public class MessageSendThread extends Thread{
    ObjectOutputStream os;
    Socket s;
    LinkedBlockingQueue<Message> messageQueue;

    public MessageSendThread(Socket s, LinkedBlockingQueue messageQueue) {
        this.s = s;
        this.messageQueue = messageQueue;
    }
    public void run(){
        try
        {
            os = new ObjectOutputStream(s.getOutputStream());


        }
        catch (IOException e)
        {
            System.err.println("MST new printwriter");
        }
        try{
            Message s= messageQueue.take();
            while (true){ // TODO: find a way to terminate later
                os.writeObject(s);
                os.flush();
/*                if(os.checkError()) // TODO: find a way to check error for object sending
                {
                    throw new Exception("Error transmitting data.");
                }*/
                System.out.println("sent message");
                s = messageQueue.take(); // waits for a message to be added to messageQueue
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally
        {
            try
            {
                System.out.println("Closing the connection");
                if (os != null)
                {
                    os.close();
                    System.err.println("Socket Out Closed");
                }
                if (s != null)
                {
                    s.close();
                    System.err.println("Socket Closed");
                }

            }
            catch (IOException ie)
            {
                System.err.println("Socket Close Error");
            }
        }//end finally

        // TODO: send message when called by some fuction
    }
}
