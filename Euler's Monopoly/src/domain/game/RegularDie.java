package domain.game;

import java.util.Random;

public class RegularDie extends Die {

		private int faceValue;
		private Random r = new Random();
		public void rollDie() {
			faceValue= r.nextInt(6)+1;
		}
		public int getFaceValue(){
			return faceValue;
		}



}