package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.board.tiles.TileInformation;
import domain.board.tiles.TilePath;
import domain.board.tiles.TilePosition;
import domain.game.*;
import gui.animation.Animator;

public class GameFrame extends JFrame implements Observer {
	/**
	 *
	 */
	private static final long serialVersionUID = -7653214116309351866L;
	Image boardImage;
	JPanel contentPane = new JPanel();
	HashMap<Integer,PieceGraphics> pieces = new HashMap<>() ;
	ArrayList<DiceGraphics> dices = new ArrayList<DiceGraphics>();

	private LeftPanel leftP;
	private RightPanel rightP;
	private Animator animator;
	public  EulerPath eulerPath;

	public GameFrame() {
		super("Monopoly Game");
		contentPane.setLayout(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(1300, 700));
		leftP = new LeftPanel();
		contentPane.add(leftP, BorderLayout.EAST);
		animator= new Animator();
		contentPane.add(animator,BorderLayout.EAST);
		rightP = new RightPanel();
		contentPane.add(rightP, BorderLayout.EAST);
		initializePieceGraphics();
		initializeDiceGraphics();
		animator.setVisible(true);

		leftP.updateCurrentPlayer();
		leftP.updatePool();
		GameControler.addObserverToAll(this);
	}

	private void initializePieceGraphics(){ 
		int offsetX=0;
		int offsetY=0;
		ArrayList<Player> playerList= GameControler.getInstance().getPlayerList();
		for (int i = 0; i < GameControler.getInstance().getPlayerNumber(); i++) {
			Piece piece= playerList.get(i).getPiece();
			TilePosition tilePos = piece.getCurrentPosition();
			Position pos =TileLocations.getInstance().getPosition(tilePos);
			int initialX = pos.getX();
			int initialY = pos.getY();
			if (i%2==0) offsetX=-3;
			else offsetX=3;
			if (i<=1) offsetY=-9;
			else if (i<=3) offsetY=-3;
			else if (i<=5) offsetY=3;
			else offsetY=9;
			int playerId = playerList.get(i).getID();
			PieceGraphics pieceGraphics = new PieceGraphics(i,initialX,initialY,offsetX,offsetY);
			
			this.pieces.put(playerId,pieceGraphics);
			animator.addDrawable(pieceGraphics);
			System.out.println("Added new piece at location: " + initialX + ", " + initialY);
		}
	}
	

	private void initializeDiceGraphics(){ 
		int initialX = 285;
		int initialY = 240;
		DiceGraphics diceGraphics = new DiceGraphics(initialX,initialY);
		dices.add(diceGraphics);
		animator.addDrawable(diceGraphics);
		System.out.println("Added Euler at location: " + initialX + ", " + initialY);
		}
	
	

	@Override
	public void update(UpdateTypes type, Object obj) {
		// TODO Auto-generated method stub
		if(type == UpdateTypes.DICE_RESULT) {
			moveEulerByPath(new EulerPath());
			int[] diceResult = (int[]) obj;
			leftP.updateResults(diceResult);
		}	
		else if(type == UpdateTypes.PLAYER_MONEY) {
			rightP.updateTextAreas();
		}	
		else if(type == UpdateTypes.PLAYER_ASSETS) {
			rightP.updateTextAreas();
		}	
		else if(type == UpdateTypes.CARD) {
			leftP.cardPanel.updateCard((String)obj);
		}	
		else if(type == UpdateTypes.PLAYER_TILE) {
			rightP.updateTextAreas();
			
		}else if(type == UpdateTypes.PIECE_POSITION) {
			rightP.updateTextAreas();
			
		}else if(type == UpdateTypes.CURRENT_PLAYER) {
			leftP.setDefaultCard();
			leftP.updateCurrentPlayer();
			
		}else if(type == UpdateTypes.POOL) {
			leftP.updatePool();
			
		}else if(type == UpdateTypes.PATH){
			// TODO: make it so that we know which piece to move rather than moving the current player. Not urgent
			int id =GameControler.getInstance().getCurrentPlayerId();
			System.out.println("current player id: " + id);
			movePieceByPath(id, (TilePath) obj);
		}else if(type == UpdateTypes.UPDATE_LEGALACTIONS){
			List<ActionTypes> legalActions = (List<ActionTypes>) obj;
			leftP.buttonPanel.updateIllegalButtons(legalActions);
		}else if(type == UpdateTypes.GAME_PAUSED){
			String gamePausedByPlayerName = (String) obj;
			pausePieces();
			//pauseEuler();
			pauseAnimator();
		}else if(type == UpdateTypes.GAME_RESUMED){
			resumePieces();
			resumeAnimator();
		}
	}

	private void pausePieces(){
		for(Integer i : pieces.keySet()){
			pieces.get(i).pauseAnimation();
		}
	}
	private void resumePieces(){
		for(Integer i : pieces.keySet()){
			PieceGraphics piece= pieces.get(i);
			synchronized (piece){
				piece.resumeAnimation();
				piece.notify();
			}
		}
	}
	private void pauseAnimator(){
		animator.pauseAnimator();
	}
	private void resumeAnimator(){
		synchronized (animator){
			animator.resumeAnimator();
			animator.notify();
		}
	}

	private void movePieceByPath(int playerId, TilePath tilePath){
		pieces.get(playerId).addPath(tilePath);
	}
	
	private void moveEulerByPath(EulerPath eulerPath){
		dices.get(0).addPath(eulerPath);
	}
}
