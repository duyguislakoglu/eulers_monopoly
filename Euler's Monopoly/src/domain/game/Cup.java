package domain.game;

import java.util.ArrayList;


public class Cup implements Observable{
	// OVERVIEW: Cup contains dies and holds the result of the dieRolls

	private static Cup instance;
	private ArrayList<Observer> observerList;
	private RegularDie regularDie1 = new RegularDie();
	private RegularDie regularDie2 = new RegularDie();
	private RegularDie regularDie3= new RegularDie();
	private SpeedDie speedDie=  new SpeedDie();

	private SpeedDie speed=  new SpeedDie();
	private int [] result  = new int[3];

	
	/**
	 * The constructor for cup
	 * @EFFECTS creates a cup object containing result array and observerlist. Initializes all results to zero
	 */
	private Cup() {
		// EFFECTS: Initializes the results to 0 and creates a new observerList
		result[0] = 0;
		result[1] = 0;
		result[2] = 0;
		observerList = new ArrayList<Observer>();
	}
	/**
	 * @EFFECTS creates a cup object if there is none and returns the singleton
	 * @RETURN the cup instance
	 */
	public static Cup getInstance(){
		if(instance == null) instance=new Cup();
		return instance;
	}
	
	/**
	 * @MODIFIES result array
	 * @EFFECTS updates the result array with the new rolled results of the three dice and notifies the observer
	 */
	public void roll() {
		regularDie1.rollDie();
		regularDie2.rollDie();
		regularDie3.rollDie();
		//speedDie.rollDie();
		result[0] = regularDie1.getFaceValue();
		result[1] = regularDie2.getFaceValue();
		result[2] = regularDie3.getFaceValue();
		notify(UpdateTypes.DICE_RESULT,result);
		System.out.print("Observer is notified.");
	}
	
	/**
	 * @RETURN result array
	 */
	public int[] getDiceResult() {
		return result;
	}

	/**
	 * @RETURN the total of the first two dice rolls
	 */
	public int getTotal(){
		return result[0]+ result[1]+ result[2];
	}
	/**
	 * @RETURN whether there is a double (whether the two dice faces are the same)
	 */
	public boolean isDouble(){
		return (result[0] == result[1]);
	}

	


	/**
	 * @param observer the observer object to add
	 * @MODIFIES observerList
	 * @EFFECTS the parameter is added to the observer list 
	 */
	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		observerList.add(observer);
	}
	
	/**
	 * @param observer, the observer object to remove
	 * @MODIFIES observerList
	 * @EFFECTS the parameter is removed from the observer list 
	 */
	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observerList.remove(observer);
	}
	
	public ArrayList<Observer> getObserverList() {
		return observerList;
	}

	public boolean repOk() {
		if(!observerList.isEmpty() && result.length == 2)
			return true;
		else return false;
	}
	
	@Override
	public String toString() {
		return "Cup [regularDie= " + regularDie1.toString() + "\n speedDie= " + speed.toString() +
				"\n result= " + result.toString();
	}
	@Override
	public void notify(UpdateTypes type, Object obj) {
		if(!observerList.isEmpty())
		observerList.forEach(observer->observer.update(type, obj));
	}


}
