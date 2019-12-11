package domain.game;

import java.util.Random;

public class SpeedDie extends Die{

	private int faceValue;
	private Random r = new Random();
	public void rollDie() {
		faceValue= r.nextInt(5)+1;
	}
	public int getFaceValue(){
		return faceValue;
	}
}