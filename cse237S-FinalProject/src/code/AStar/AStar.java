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
		PriorityQueue<AStarNode> pq = createAStarPQ();
		AStarNode start = new AStarNode(startx, starty, endx, endy, new ArrayList<MOVE>());
		pq.add(start);
		while(!pq.isEmpty()) {
			AStarNode current = pq.poll();
			if(visited[current.getY()][current.getX()]) {
				continue;
			}
			visited[current.getY()][current.getX()] = true;
			
			if(current.getX() == endx && current.getY() == endy) {
				return current.getMoves();
			}
			
			if(current.getX() + 10 < visited[0].length && !visited[current.getY()][current.getX() + 10] && !visited[current.getY()][current.getX() + 9] )
				pq.add(new AStarNode(current.getX() + 10, current.getY(), current));
			if(current.getX() - 10 > -1 &&!visited[current.getY()][current.getX() - 10] && !visited[current.getY()][current.getX() - 1])
				pq.add(new AStarNode(current.getX() - 10, current.getY(), current));
			if(current.getY() + 10 < visited.length && !visited[current.getY() + 10][current.getX()] && !visited[current.getY() + 9][current.getX()] )
				pq.add(new AStarNode(current.getX(), current.getY() + 10, current));
			if(current.getY() - 10 > -1 && !visited[current.getY() - 10][current.getX()] && !visited[current.getY() - 1][current.getX()])
				pq.add(new AStarNode(current.getX(), current.getY() - 10, current));
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
		
		for(int i = 0; i < walls.length; i++) {
			for(int j = 0; j < walls[0].length; j++) {
				if(walls[i][j]) System.out.print("x ");
				else if(trail[i][j]) System.out.print("o ");
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
		Comparator<AStarNode> nodeComparator = new Comparator<AStarNode>() {
			@Override
			public int compare(AStarNode a1, AStarNode a2) {
				return (int) Math.round(a1.getCost() + a1.getHeurisic() - a2.getCost() - a2.getHeurisic());
			}
		};
		PriorityQueue<AStarNode> pq = new PriorityQueue<AStarNode>(nodeComparator);
		return pq;
	}
	
	/**
	 * Creates the boolean[][] of visited nodes based on the walls of the maze
	 * @param walls boolean[][] of the maze
	 * @return boolean[][] of visited nodes
	 */
	public static boolean[][] createVisited(boolean[][] walls) {
		boolean[][] visited = new boolean[walls.length][walls[0].length];
		for(int i = 0; i < walls.length; i++) {
			for(int j = 0; j < walls[0].length; j++) {
				visited[i][j] = walls[i][j];
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
		for(int i = 0; i < walls.length; i++) {
			for(int j = 0; j < walls[0].length; j++) {
				trail[i][j] = false;
			}
		}
		return trail;
	}
	
}
