package gui;
import java.util.LinkedList;
import java.util.Queue;

public class EulerPath {
	
	private Queue<Position> road = new LinkedList<Position>();

	public EulerPath() {
		for(int i=0; i<5 ; i++) {
		addPosition(new Position(270,240));
		addPosition(new Position(300,240));
		}
		addPosition(new Position(285,240));
	}
	
	public boolean hasNextPosition() {
		return !road.isEmpty();
	}
	
	public Position nextPosition() {
		return road.remove();
	}
	
	public void addPosition(Position pos) {
		road.add(pos);
	}
	
}
