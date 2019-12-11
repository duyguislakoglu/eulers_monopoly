package domain.network;

public interface PreGameCommInterface {
    void connectionSuccesful();
    void connectionFailed();
    void updatePlayerClientList(String[] playerClientStrings);
    void newMessage(int clientId, String message);
}
