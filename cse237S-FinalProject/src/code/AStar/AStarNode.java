package code.AStar;

import java.util.ArrayList;

/**
 * Class for representing a single AStar node
 */
public class AStarNode {
	public static enum MOVE {
		LEFT, RIGHT, UP, DOWN
	}
	
	private int x, y;
	private int goalx, goaly;
	private ArrayList<MOVE> moves;
	private int cost;
	
	/**
	 * Constructor for an AStar node
	 * @param x x coordinate of AStar node
	 * @param y y coordinate of AStar node
	 * @param goalx x coordinate of goal AStar node
	 * @param goaly y coordinate of goal AStar node
	 */
	public AStarNode(int x, int y, int goalx, int goaly, ArrayList<MOVE> moves) {
		this.x = x;
		this.y = y;
		this.goalx = x;
		this.goaly = y;
		this.moves = moves;
		this.cost = moves.size();
	}
	
	/**
	 * Constructor for an AStar node
	 * @param x x coordinate of AStar node
	 * @param y y coordinate of AStar node
	 * @param previous previous AStarNode in path
	 */
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
	
	/**
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return ArrayList of moves up to this node
	 */
	public ArrayList<MOVE> getMoves() {
		return moves;
	}
	
	/**
	 * @return cost to get to this node
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * @return heuristic approximation to goal from this node
	 */
	public double getHeurisic() {
		return Math.sqrt(Math.pow(this.goalx - this.x, 2) + Math.pow(this.goaly - this.y, 2));
	}
}
