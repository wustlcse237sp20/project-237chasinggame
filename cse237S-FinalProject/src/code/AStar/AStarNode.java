package code.AStar;

import java.util.ArrayList;

public class AStarNode {
	public static enum MOVE {
		LEFT, RIGHT, UP, DOWN
	}
	
	private int x, y;
	private int goalx, goaly;
	private ArrayList<MOVE> moves;
	private int cost;
	
	public AStarNode(int x, int y, int goalx, int goaly, ArrayList<MOVE> moves) {
		this.x = x;
		this.y = y;
		this.goalx = x;
		this.goaly = y;
		this.moves = moves;
		this.cost = moves.size();
		
	}
	
	public AStarNode(int x, int y, AStarNode previous) {
		this.x = x;
		this.y = y;
		this.moves = new ArrayList<MOVE>(previous.getMoves());
		if(previous.x < this.x && previous.y == this.y) this.moves.add(MOVE.RIGHT);
		if(previous.x > this.x && previous.y == this.y) this.moves.add(MOVE.LEFT);
		if(previous.x == this.x && previous.y > this.y) this.moves.add(MOVE.UP);
		if(previous.x == this.x && previous.y < this.y) this.moves.add(MOVE.DOWN);
		this.cost = previous.getCost() + 1;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ArrayList<MOVE> getMoves() {
		return moves;
	}
	
	public int getCost() {
		return cost;
	}
	
	public double getHeurisic() {
		return Math.sqrt(Math.pow(this.goalx - this.x, 2) + Math.pow(this.goaly - this.y, 2));
	}
}
