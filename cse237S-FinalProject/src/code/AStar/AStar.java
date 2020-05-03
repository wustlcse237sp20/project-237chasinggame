package code.AStar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import code.AStar.AStarNode.MOVE;
//The algorithm only works for our maze class, meaning walls may begin only at multiples of 10 and are 10 long.

/**
 * Class for calculating the AStar optimal path between two points on the maze
 */
public class AStar {
	boolean[][] walls;
	int startx, starty, endx, endy;
	
	/**
	 * Constructor for AStar algorithm
	 * @param walls a boolean[][] representing where the walls are in the maze
	 * @param startx the starting x coordinate
	 * @param starty the starting y coordinate
	 * @param endx the ending x coordinate
	 * @param endy the ending y coordinate
	 */
	public AStar(boolean[][] walls, int startx, int starty, int endx, int endy) {
		this.walls = walls;
		this.startx = startx;
		this.starty = starty;
		this.endx = endx;
		this.endy = endy;
	}
	
	/**
	 * @return boolean[][] walls of the maze
	 */
	public boolean[][] getWalls(){
		return this.walls;
	}
	
	/**
	 * @return starting x coordinate
	 */
	public int getStartX() {
		return this.startx;
	}
	
	/**
	 * @return starting y coordinate
	 */
	public int getStartY() {
		return this.starty;
	}
	
	/**
	 * @return ending x coordinate
	 */
	public int getEndX() {
		return this.endx;
	}
	
	/**
	 * @return ending y coordinate
	 */
	public int getEndY() {
		return this.endy;
	}
	
	/**
	 * Gets the optimal path between the two points specified.
	 * @return an ArrayList that contains MOVE enums which specify how to get from start to end
	 */
	public ArrayList<MOVE> getPath() {
		boolean[][] visited = createVisited(walls);
		PriorityQueue<AStarNode> priorityQueue = createAStarPQ();
		AStarNode start = new AStarNode(startx, starty, endx, endy, new ArrayList<MOVE>());
		priorityQueue.add(start);
		while(!priorityQueue.isEmpty()) {
			AStarNode current = priorityQueue.poll();
			
			//don't return to a previously visited location
			if(visited[current.getY()][current.getX()]) {
				continue;
			}
			visited[current.getY()][current.getX()] = true;
			
			if(current.getX() == endx && current.getY() == endy) {
				return current.getMoves();
			}
			
			//attempt to visit all four directions from current position, adding it to priority queue if possible
			if(current.getX() + 10 < visited[0].length && !visited[current.getY()][current.getX() + 10] && !visited[current.getY()][current.getX() + 9] )
				priorityQueue.add(new AStarNode(current.getX() + 10, current.getY(), current));
			if(current.getX() - 10 > -1 &&!visited[current.getY()][current.getX() - 10] && !visited[current.getY()][current.getX() - 1])
				priorityQueue.add(new AStarNode(current.getX() - 10, current.getY(), current));
			if(current.getY() + 10 < visited.length && !visited[current.getY() + 10][current.getX()] && !visited[current.getY() + 9][current.getX()] )
				priorityQueue.add(new AStarNode(current.getX(), current.getY() + 10, current));
			if(current.getY() - 10 > -1 && !visited[current.getY() - 10][current.getX()] && !visited[current.getY() - 1][current.getX()])
				priorityQueue.add(new AStarNode(current.getX(), current.getY() - 10, current));
		}
		return null;
	}
	
	/**
	 * Prints out the path found using AStar
	 * @param walls boolean[][] which represents the maze
	 * @param path an ArrayList of MOVE enums which represent optimal path
	 * @param startx starting x coordinate
	 * @param starty starting y coordinate
	 * @return points visited
	 */
	public static ArrayList<int[]> printPath(boolean[][] walls, ArrayList<MOVE> path, int startx, int starty) {
		ArrayList<int[]> visitedPoints = new ArrayList<>();
		int x = startx;
		int y = starty;
		boolean[][] trail = createTrail(walls);
		trail[y][x] = true;
		for(int i = 0; i < path.size(); i++) {
			if(path.get(i) == MOVE.DOWN) {
				System.out.println("DOWN"); y+=10;
			}
			if(path.get(i) == MOVE.UP) {
				System.out.println("UP"); y-=10;
			}
			if(path.get(i) == MOVE.RIGHT) {
				System.out.println("RIGHT"); x+=10;
			}
			if(path.get(i) == MOVE.LEFT) {
				System.out.println("LEFT"); x-=10;
			}
			int [] points = {x, y};
			visitedPoints.add(points);
			System.out.println(x + " " + y);
			trail[y][x] = true;
		}
		
		for(int row = 0; row < walls.length; row++) {
			for(int col = 0; col < walls[0].length; col++) {
				if(walls[row][col]) System.out.print("x ");
				else if(trail[row][col]) System.out.print("o ");
				else System.out.print("  ");
			}
			System.out.println();
		}

		return visitedPoints;
	}
	
	/**
	 * Creates the PriorityQueue of AStar Nodes necessary for AStar
	 * @return PriorityQueue necessary for AStar
	 */
	private static PriorityQueue<AStarNode> createAStarPQ() {
		//creates modified comparator that can determine priority based on calculated path distance between enemy and player locations
		Comparator<AStarNode> nodeComparator = new Comparator<AStarNode>() {
			@Override
			public int compare(AStarNode a1, AStarNode a2) {
				return (int) Math.round(a1.getCost() + a1.getHeurisic() - a2.getCost() - a2.getHeurisic());
			}
		};
		PriorityQueue<AStarNode> priorityQueue = new PriorityQueue<AStarNode>(nodeComparator);
		return priorityQueue;
	}
	
	/**
	 * Creates the boolean[][] of visited nodes based on the walls of the maze
	 * @param walls boolean[][] of the maze
	 * @return boolean[][] of visited nodes
	 */
	public static boolean[][] createVisited(boolean[][] walls) {
		boolean[][] visited = new boolean[walls.length][walls[0].length];
		for(int row = 0; row < walls.length; row++) {
			for(int col = 0; col < walls[0].length; col++) {
				visited[row][col] = walls[row][col];
			}
		}
		return visited;
	}
	
	/**
	 * Creates the boolean[][] of trailing nodes based on the walls of the maze
	 * @param walls boolean[][] of the maze
	 * @return boolean[][] of trailing nodes
	 */
	public static boolean[][] createTrail(boolean[][] walls) {
		boolean[][] trail = new boolean[walls.length][walls[0].length];
		for(int row = 0; row < walls.length; row++) {
			for(int col = 0; col < walls[0].length; col++) {
				trail[row][col] = false;
			}
		}
		return trail;
	}
	
}
