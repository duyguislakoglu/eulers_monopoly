package domain.game;

public interface Observer {
	public void update(UpdateTypes type, Object obj);
}
