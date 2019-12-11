package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import domain.network.Message;
import domain.network.NetworkController;
import domain.network.PreGameCommInterface;
import domain.storage.StorageController;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JoinLobby extends JDialog implements PreGameCommInterface{

	private final JPanel contentPanel = new JPanel();
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField idTextField;
	private static JTextArea txtrMessagesHere;
	private static JTextArea txtrListOfPlayers;
	JScrollPane scrollmessages;
	JScrollPane scrollplayers;
	static JoinLobby dialog;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		initializePanel();
	}


	private static void initializePanel() {
		try {
			dialog = new JoinLobby();
			NetworkController.getInstance().setPreGameCommInterface(dialog);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionSuccesful() {
		JOptionPane.showMessageDialog(contentPanel, "Connection successful.");
		dialog.dispose();
		initializeLobbyLogin(ipTextField.getText());
	}

	@Override
	public void connectionFailed() {
		JOptionPane.showMessageDialog(contentPanel, "Connection to ip: "
				+ ipTextField.getText() + " at port:" + portTextField.getText() +" failed.");
	}

	@Override
	public void newMessage(int clientId, String message) {
		txtrMessagesHere.append(clientId + "wrote: " + message + "\n");
	}

	public void updatePlayerClientList(String[] playerClientStrings) {
		String result = "";
		for (String player:playerClientStrings){
			result += (player + "\n");
		}
		txtrListOfPlayers.setText(result);
	}

	private void initializeLobbyLogin(String ip) {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 854, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setBackground(Color.black);
		frame.getContentPane().setBackground(Color.black);

		txtrMessagesHere = new JTextArea();
		txtrMessagesHere.setText("Messages here:\n");
		txtrMessagesHere.setLineWrap(true);
		txtrMessagesHere.setEditable(false);
		txtrMessagesHere.setBounds(339, 137, 444, 225);
		scrollmessages = new JScrollPane(txtrMessagesHere);
		scrollmessages.setBounds(339, 137, 444, 225);
		frame.getContentPane().add(scrollmessages);

		txtrListOfPlayers = new JTextArea();
		txtrListOfPlayers.setText("Add a player to see the player in the game!\n");
		txtrListOfPlayers.append(ip);
		txtrListOfPlayers.setEditable(false);
		txtrListOfPlayers.setLineWrap(true);
		txtrListOfPlayers.setBounds(339, 12, 444, 106);
		scrollplayers = new JScrollPane(txtrListOfPlayers);
		scrollplayers.setBounds(339, 12, 444, 106);
		frame.getContentPane().add(scrollplayers);

		JTextField playertextField = new JTextField();
		playertextField.setBounds(11, 28, 258, 39);
		frame.getContentPane().add(playertextField);
		playertextField.setColumns(10);

		JEditorPane messagesEditorPane = new JEditorPane();
		messagesEditorPane.setBounds(10, 140, 259, 157);
		frame.getContentPane().add(messagesEditorPane);

		JButton btnMessage = new JButton("Send Message");
		btnMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NetworkController.getInstance().sendMessage(new Message(messagesEditorPane.getText()));
				txtrMessagesHere.append(" You wrote: " + messagesEditorPane.getText() + "\n");
			}
		});
		btnMessage.setBounds(11, 309, 258, 39);
		frame.getContentPane().add(btnMessage);

		JButton btnEnterPlayer = new JButton("Enter Player Name");
		btnEnterPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Put the logic in domain.
				NetworkController.getInstance().addAndSendPlayer(playertextField.getText(), false);
			}
		});
		btnEnterPlayer.setBounds(10, 79, 130, 39);
		frame.getContentPane().add(btnEnterPlayer);

		JButton btnEnterBot = new JButton("Enter Bot Name");
		btnEnterBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NetworkController.getInstance().addAndSendPlayer(playertextField.getText(), true);
			}
		});
		btnEnterBot.setBounds(140, 79, 130, 39);
		frame.getContentPane().add(btnEnterBot);
		frame.setVisible(true);

		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NetworkController.getInstance().sendStartGame();
			}
		});
		btnStartGame.setBounds(10, 400, 130, 39);
		JButton loadGameButton = new JButton("Load Game");
		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StorageController.getInstance().loadGame("testSaveGame.save");
			}
		});
		loadGameButton.setBounds(150, 400,130,39);
		frame.getContentPane().add(btnStartGame);
		frame.getContentPane().add(loadGameButton);
		frame.setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	public JoinLobby() {
		setBounds(300, 300, 460, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(Color.black);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblEnterIp = new JLabel("Enter ip");
			lblEnterIp.setForeground(Color.WHITE);
			lblEnterIp.setBounds(13, 13, 92, 33);
			contentPanel.add(lblEnterIp);
		}
		{
			ipTextField = new JTextField();
			ipTextField.setBounds(217, 10, 236, 39);
			contentPanel.add(ipTextField);
			ipTextField.setColumns(10);
		}

		JLabel lblEnterPort = new JLabel("Enter port");
		lblEnterPort.setForeground(Color.WHITE);
		lblEnterPort.setBounds(13, 74, 145, 33);
		contentPanel.add(lblEnterPort);

		portTextField = new JTextField();
		portTextField.setColumns(10);
		portTextField.setBounds(217, 71, 236, 39);
		contentPanel.add(portTextField);

		JLabel enterClientId = new JLabel("Enter clientId as int");
		enterClientId.setForeground(Color.WHITE);
		enterClientId.setBounds(13, 135, 145, 33);
		contentPanel.add(enterClientId);

		idTextField = new JTextField();
		idTextField.setColumns(10);
		idTextField.setBounds(217, 138, 236, 39);
		contentPanel.add(idTextField);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(Color.black);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton hostServerButton = new JButton("Host Server");
				hostServerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						NetworkController.getInstance().createServer(Integer.valueOf(portTextField.getText()));
					}
				});
				hostServerButton.setActionCommand("OK");
				buttonPane.add(hostServerButton);
				getRootPane().setDefaultButton(hostServerButton);
			}
			{
				JButton joinServerButton = new JButton("Join Server");
				joinServerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						NetworkController.getInstance().connectToServer(ipTextField.getText(), Integer.valueOf(portTextField.getText()),Integer.valueOf(idTextField.getText()));

					}
				});
				buttonPane.add(joinServerButton);
			}
		}
	}
}
