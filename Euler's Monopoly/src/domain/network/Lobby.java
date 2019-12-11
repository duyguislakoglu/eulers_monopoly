package domain.network;

import java.util.ArrayList;
import java.util.Random;

public class Lobby {
    private static Lobby instance;
    private ArrayList<LobbyPlayer> players;

    public static synchronized Lobby getInstance(){
        if(instance == null){
            instance = new Lobby();
        }
        return instance;
    }
    private Lobby(){
        players = new ArrayList<>();
    }
    public void removePlayer(){
    }

    public void removeClient(int clientId){

    }
    public synchronized void addPlayer(String playerName,int clientId, boolean isBot){
        int playerId = new Random().nextInt(); // maybe change
        LobbyPlayer player =  new LobbyPlayer(playerName,clientId,isBot,playerId);
        players.add(player);
    }
    public synchronized void addPlayer(LobbyPlayer player){
        players.add(player);
    }

    public ArrayList<LobbyPlayer> getPlayers(){
        return players;
    }

    public String[] getPlayersAsStringArray(){
        String result[] = new String[players.size()];
        for (int i=0; i<players.size();i++){
            result[i] = players.get(i).toString();
        }
        return result;
    }

    public void setPlayers(ArrayList<LobbyPlayer> players) {
        this.players = players;
    }
}
