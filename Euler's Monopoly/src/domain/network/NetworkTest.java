package domain.network;

import java.util.Scanner;

public class NetworkTest {


public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s;
        s= sc.nextLine();
        while(s != "QUIT!"){
            if(s.equals("connect")){
               // NetworkController.getInstance().connectToServer("localhost",5555);
            }else if(s.equals("create")){
                NetworkController.getInstance().createServer(5555);
            }else if(s.equals("add player")){
                NetworkController.getInstance().addAndSendPlayer("TestPlayer123", false);
            } else if(s.equals("start game")){
                NetworkController.getInstance().sendStartGame();
            }
            else{
                Message message= new Message(s);
                NetworkController.getInstance().sendMessage(message);
            }
            s= sc.nextLine();
        }
    }
}
