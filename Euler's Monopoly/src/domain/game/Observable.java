package domain.game;

public interface Observable {
	public void addObserver(Observer observer);
	public void removeObserver(Observer observer);
	public void notify(UpdateTypes type, Object obj);
}
