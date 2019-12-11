package domain.game;

import java.util.ArrayList;

public class Pool implements Observable {
	private ArrayList<Observer> observerList =new ArrayList<Observer> () ;
	private int money = 0;
	private static Pool instance;
	
	public int getMoney() {
		return money;
	}

	public void addMoney(int amount) {
		this.money += amount;
		notify(UpdateTypes.POOL, this.money);
	}
	
	public void decrementMoney(int amount) {
		this.money -= amount;
		notify(UpdateTypes.POOL, this.money);
	}
	
	public static Pool getInstance() {
		if (null == instance) {
			instance = new Pool();
			return instance;
		} else return instance;
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