package gui.animation;

import gui.BoardPanel;
import gui.PieceGraphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

public class Animator extends BoardPanel implements Runnable {
    private boolean animatorStopped= false, firstTime= true;
    private long sleepTime= 50;
    private Vector elementsToDraw = new Vector();

    public Animator(){
        super();
    }
    public void paint(Graphics g) {
        super.paint(g);
        Enumeration e = elementsToDraw.elements();
        while (e.hasMoreElements())
            ((Drawable) e.nextElement()).draw(g);
        // TODO
    }
    public void setVisible(boolean display) {
        if (display) {
            if (firstTime) {
                firstTime = false;

                // Show the animator.  This starts the GUI thread.
                setVisible(true);

                // Put the animator in another thread so that the
                // calling object can continue.
                (new Thread(this)).start();
            }
        }
    }
    public void addDrawable(Drawable d) {
        elementsToDraw.addElement(d);
    }

    public void pauseAnimator(){
        animatorStopped= true;
    }
    public void resumeAnimator(){
        animatorStopped= false;
    }

    public void run() {
        while (true) {
            try {
                synchronized(this) {
                    if (animatorStopped) {
                        wait();
                    }
                }
                if (!animatorStopped)
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println("Program Interrupted");
                System.exit(0);
            }
            repaint();
        }
    }
}
