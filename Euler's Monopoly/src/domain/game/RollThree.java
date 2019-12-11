package domain.game;

import java.util.ArrayList;

public class RollThree extends Cards implements Observable{

	private int id;
	private String name;
	private int[] numbers = {0, 0, 0};
	private ArrayList<Observer> observerList = new ArrayList<Observer>();

	public RollThree(int id, String name) {
		super(id, name);
		char[] name1 = name.toCharArray();
		numbers[0] = name1[0]; numbers[1] = name1[1]; numbers[2] = name1[2];
		
	}


	public int[] getNumbers() {
		return numbers;
	}

	public void applyCard() {

	}


	@Override
	public String cardToString() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		observerList.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observerList.remove(observer);
	}

	@Override
	public void notify(UpdateTypes type, Object obj) {
		// TODO Auto-generated method stub
		if(!observerList.isEmpty())
			observerList.forEach(observer->observer.update(type, obj));
	}

}