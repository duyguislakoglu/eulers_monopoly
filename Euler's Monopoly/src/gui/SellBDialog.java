package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import domain.board.tiles.Tile;
import domain.game.GameControler;

import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SellBDialog extends JDialog {

	SellBDialog dialog;
	private final JPanel contentPanel = new JPanel();
	JList<String> list;
	ArrayList <String> tileList = (GameControler.getInstance().getPlayerList().get(GameControler.getInstance().getCurrentPlayerOrder())).getOwnedTileListAsStringList();
	DefaultListModel<String> listModel;

	/**
	 * Launch the application.
	 */
	public void run(){
		try {
			if (dialog==null) dialog = new SellBDialog(tileList);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	
	public SellBDialog(ArrayList <String> tileList) {
		this.tileList= tileList;
		setBounds(100, 100, 200, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(Color.black);
		// to enable the sellb when closing with the exit button on the top right hand side
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				ButtonPanel.sellB.setEnabled(true);
			}
		});
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			//TODO: After the implementation is done, uncomment and/or change these
			//ArrayList<Tile> tileA = GameControler.getInstance().getTiles(1); // should be the ID of the current player
			list = new JList<String>();
			listModel = new DefaultListModel<String>();
			//			for (Tile tile:tileA){
			//				listModel.addElement(tile);
			//			}			

			//TODO: Remove after the above implementation is done
			for(int i= 0; i<tileList.size(); i++) {
				listModel.addElement(tileList.get(i));
			}


			//Create the list and put it in a scroll pane.
			list = new JList<String>(listModel);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setSelectedIndex(0);

			list.setVisibleRowCount(5);
			list.setBackground(Color.black);
			list.setForeground(Color.white);
			//JScrollPane listScrollPane = new JScrollPane(list);
			contentPanel.add(list);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton sellButton = new JButton("Sell");
				sellButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//TODO: Notify the domain about the sell of the selected tile
						System.out.println(list.getSelectedValue());
						int index = list.getSelectedIndex();
						if(index >= 0){ //Remove only if a particular item is selected
							listModel.removeElementAt(index);
						}
						ButtonPanel.sellB.setEnabled(true);
						dispose();
					}
				});
				buttonPane.setBackground(Color.BLACK);
				sellButton.setActionCommand("Sell");
				buttonPane.add(sellButton);
				getRootPane().setDefaultButton(sellButton);
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						ButtonPanel.sellB.setEnabled(true);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
