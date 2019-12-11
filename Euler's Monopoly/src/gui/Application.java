package gui;
import javax.swing.JFrame;
import domain.game.*;
import domain.network.Lobby;
import domain.network.LobbyPlayer;

public class Application {
	public static void main(String[] args) { // to test implementations quickly
		Lobby.getInstance().addPlayer(new LobbyPlayer("arda",999,false,123));
		Lobby.getInstance().addPlayer(new LobbyPlayer("duygu",999,false,1234));
		GameControler.getInstance().startGame();
}
	public static void initializeApplication(){
		GameFrame frame = new GameFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}