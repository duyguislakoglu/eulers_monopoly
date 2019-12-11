package gui;

import domain.board.tiles.Tile;
import domain.board.tiles.TilePath;
import domain.board.tiles.TilePosition;
import gui.animation.Drawable;
import gui.animation.Path;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;

public class PieceGraphics implements Drawable, Runnable {
    private int pieceID;
    private int x;
    private int y;
    private int xOffset;
    private int yOffset;
    private Image image;
    private boolean movementStopped;
    private Queue<TilePath> pathsToGo = new LinkedList<>();
    private TilePath currentPath;
    private Position currentTarget;
    private long sleepTime = 30;
    private int stepCount;
    private static final int DEFAULT_STEPCOUNT = 8; // TODO: make it like 20

    public ImageIcon[] images = {
            new ImageIcon(getClass().getResource("/p1.png")),
            new ImageIcon(getClass().getResource("/p2.png")),
            new ImageIcon(getClass().getResource("/p3.png")),
            new ImageIcon(getClass().getResource("/p4.png")),
            new ImageIcon(getClass().getResource("/p5.png")),
            new ImageIcon(getClass().getResource("/p6.png")),
            new ImageIcon(getClass().getResource("/p7.png")),
            new ImageIcon(getClass().getResource("/p8.png"))
    };

    public PieceGraphics(int pieceID, int x, int y, int xOffset, int yOffset) {
        this.setPieceID(pieceID);
        this.setImage(pieceID);
        movementStopped = false;
        currentPath = null;
        currentTarget = null;
        stepCount= DEFAULT_STEPCOUNT;

        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.setX(x+xOffset);
        this.setY(y+yOffset);


        // Start Thread for movement
        (new Thread(this)).start();
    }
    public void pauseAnimation(){
        movementStopped=true;
    }
    public void resumeAnimation(){
        movementStopped= false;
    }

    public void setPieceID(int pieceID) {
        this.pieceID = pieceID;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setImage(int pieceID) {
        this.image = images[pieceID].getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
            TilePosition tilePos = currentPath.nextPosition();
           
            // TODO: set currentTarget to Position object that corresponds to the TilePosition. TileLocations class could be modified
            // or we can implement here accordingly. Better practice would be to modify tileLocations to return Position object
            // i.e. TileLocations.getInstance().getPosition(TilePosition)
            System.out.println("Target tilePosition: " + tilePos.toString());
            currentTarget = TileLocations.getInstance().getPosition(tilePos);

            
            // Adds offset to target. TODO: Uncomment when currentTarget assignment is implemented
            currentTarget = new Position(currentTarget.getX()+xOffset,currentTarget.getY()+yOffset);
        }
        x = ((currentTarget.getX() - x)/ stepCount) +x;
        y = ((currentTarget.getY() - y)/ stepCount) +y;
        stepCount--;
        if(stepCount <= 0){
            System.out.println("Reached target of x: " + currentTarget.getX() + ", y: " + currentTarget.getY());
            System.out.println("My x: " + x + ", my y: " + y);
            currentTarget= null;
        }

    }

    protected synchronized void addPath(TilePath tilePath) {
        pathsToGo.add(tilePath);
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public void run() {
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

}
