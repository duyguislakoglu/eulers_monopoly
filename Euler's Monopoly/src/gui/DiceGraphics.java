package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;

import domain.board.tiles.TilePath;
import domain.board.tiles.TilePosition;
import gui.animation.Drawable;
import gui.animation.Path;

public class DiceGraphics implements Drawable, Runnable {
    private int x;
    private int y;
    private Image image;
    private boolean movementStopped;
    private Queue<EulerPath> pathsToGo = new LinkedList<>();
    private EulerPath currentPath;
    private Position currentTarget;
    private long sleepTime = 30;
    private int stepCount;
    private static final int DEFAULT_STEPCOUNT = 5; // TODO: make it like 20
    
    public DiceGraphics(int x, int y) {
        this.setImage();
        movementStopped = false;
        currentPath = null;
        currentTarget = null;
        stepCount= DEFAULT_STEPCOUNT;
        this.setX(x);
        this.setY(y);
        // Start Thread for movement
        (new Thread(this)).start();
    }

    
	private void setImage() {
		// TODO Auto-generated method stub
       this.image= new ImageIcon(getClass().getResource("/diceEuler.png")).getImage();
	}

    public Image getImage() {
        return image;
    }
	
	private void setY(int y) {
		// TODO Auto-generated method stub
		this.y=y;
	}

	private void setX(int x) {
		// TODO Auto-generated method stub
		this.x=x;
	}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    


    public void setImage(int pieceID) {
    		new ImageIcon(getClass().getResource(".png")).getImage();
    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		        while (true) {
		            try {
		                synchronized (this) {
		                    if (movementStopped) {
		                        wait();
		                    }
		                }
		                if (!movementStopped)
		                    Thread.sleep(sleepTime);
		            } catch (InterruptedException e) {
		                System.exit(0);
		            }
		            move();
		        }
		    }
	


private synchronized void move() {
    if (currentTarget == null) {
        if (currentPath == null) {
            if (!pathsToGo.isEmpty()) {
                currentPath = pathsToGo.remove();
            } else {
                return;
            }
        }
        stepCount = DEFAULT_STEPCOUNT;
        if(!currentPath.hasNextPosition()){
            currentPath = null;
            return;
        }
        Position tilePos = currentPath.nextPosition();
       
        // TODO: set currentTarget to Position object that corresponds to the TilePosition. TileLocations class could be modified
        // or we can implement here accordingly. Better practice would be to modify tileLocations to return Position object
        // i.e. TileLocations.getInstance().getPosition(TilePosition)
        currentTarget = tilePos;

        
        // Adds offset to target. TODO: Uncomment when currentTarget assignment is implemented
        currentTarget = new Position(currentTarget.getX(),currentTarget.getY());
    }
    x = ((currentTarget.getX() - x)/ stepCount) +x;
    y = ((currentTarget.getY() - y)/ stepCount) +y;
    stepCount--;
    if(stepCount <= 0){
        currentTarget= null;
    }

}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
        g.drawImage(image, x, y, null);
	}


	public void addPath(EulerPath eulerPath) {
		// TODO Auto-generated method stub
        pathsToGo.add(eulerPath);
	}
}
