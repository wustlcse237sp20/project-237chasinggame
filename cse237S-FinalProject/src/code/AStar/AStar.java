package code.AStar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import code.AStar.AStarNode.MOVE;

public class AStar {
	public static void main(String[] args) {
		boolean[][] walls = createWalls();
		int startx = 4, starty = 4;
		AStar as = new AStar(walls, startx, starty, 8, 8);
		printPath(walls, as.getPath(), startx, starty);
	}
	
	boolean[][] walls;
	int startx, starty, endx, endy;
	
	public AStar(boolean[][] walls, int startx, int starty, int endx, int endy) {
		this.walls = walls;
		this.startx = startx;
		this.starty = starty;
		this.endx = endx;
		this.endy = endy;
	}
	
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
			
			if(current.getX() + 1 < visited[0].length && !visited[current.getY()][current.getX() + 1])
				pq.add(new AStarNode(current.getX() + 1, current.getY(), current));
			if(current.getX() - 1 > -1 &&!visited[current.getY()][current.getX() - 1])
				pq.add(new AStarNode(current.getX() - 1, current.getY(), current));
			if(current.getY() + 1 < visited.length && !visited[current.getY() + 1][current.getX()])
				pq.add(new AStarNode(current.getX(), current.getY() + 1, current));
			if(current.getY() - 1 > -1 && !visited[current.getY() - 1][current.getX()])
				pq.add(new AStarNode(current.getX(), current.getY() - 1, current));
		}
		return null;
	}
	
	public static void printPath(boolean[][] walls, ArrayList<MOVE> path, int startx, int starty) {
		int x = startx;
		int y = starty;
		boolean[][] trail = createTrail(walls);
		trail[y][x] = true;
		for(int i = 0; i < path.size(); i++) {
			if(path.get(i) == MOVE.DOWN) {
				System.out.println("DOWN"); y++;
			}
			if(path.get(i) == MOVE.UP) {
				System.out.println("UP"); y--;
			}
			if(path.get(i) == MOVE.RIGHT) {
				System.out.println("RIGHT"); x++;
			}
			if(path.get(i) == MOVE.LEFT) {
				System.out.println("LEFT"); x--;
			}
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
	}
	
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
	
	public static boolean[][] createVisited(boolean[][] walls) {
		boolean[][] visited = new boolean[walls.length][walls[0].length];
		for(int i = 0; i < walls.length; i++) {
			for(int j = 0; j < walls[0].length; j++) {
				visited[i][j] = walls[i][j];
			}
		}
		return visited;
	}
	
	public static boolean[][] createTrail(boolean[][] walls) {
		boolean[][] trail = new boolean[walls.length][walls[0].length];
		for(int i = 0; i < walls.length; i++) {
			for(int j = 0; j < walls[0].length; j++) {
				trail[i][j] = false;
			}
		}
		return trail;
	}
	
	public static boolean[][] createWalls() {
		boolean[][] walls = new boolean[10][10];
		for(int i = 0; i < walls.length; i++) {
			for(int j = 0; j < walls[0].length; j++) {
				walls[i][j] = false;
			}
		}
		for(int i = 0; i < walls.length; i++) {
			for(int j = 0; j < walls[0].length; j++) {
				if(i == 0 || i == walls.length - 1) walls[i][j] = true;
				if(j == 0 || j == walls[0].length - 1) walls[i][j] = true;
			}
		}
		
		walls[5][5] = true;
		walls[5][4] = true;
		walls[5][3] = true;
		walls[4][5] = true;
		walls[3][5] = true;
		
		return walls;
	}
}
