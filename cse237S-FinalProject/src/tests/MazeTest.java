package tests;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import code.Maze;
import code.AStar.AStar;
import code.AStar.AStarNode;
import code.AStar.AStarNode.MOVE;
import doodlepad.*;

class MazeTest extends Maze {
	
	//removes the need to have BeforeEach code segment to construct new maze objects, as this does it for us
	public MazeTest() {
		super(250, 250, false);
	}
	
	@Test
	void boundaryCheck() {
		for(int h = 0; h <= this.getMazeHeight(); h++) {
			if(!this.getWalls()[h][0]) {
				fail("Erroneous left boundary walls removed");
			}
		}
		for(int w = 1; w <= this.getMazeWidth(); w++) {
			if(!this.getWalls()[0][w]) {
				fail("Erroneous top boundary walls removed");
			}
		}
		for(int w = 0; w <= this.getMazeWidth() - 1; w++) {
			if(!this.getWalls()[this.getMazeHeight()][w]) {
				fail("Erroneous bottom boundary walls removed");
			}
		}
		for(int h = this.getMazeHeight()-1; h >= 0; h--) {
			if(!this.getWalls()[h][this.getMazeWidth()]) {
				fail("Erroneous right boundary walls removed");
			}
		}
		assertTrue(true); // if I arrive here, no failures
	}
	
	@Test
	void testWallBarrier() {
		boolean[][] walls = this.getWalls();
		Rectangle r = this.getPlayer();
		while (!walls[(int)r.getY()][(int)r.getX()+9]) {
			assertTrue(this.moveRight(r));
		}
		assertTrue(false == this.moveRight(r));
	}
	
	@Test
	void testSetupComputers() {
		this.setupComputers(5);
		assertTrue(this.getComputers().size() == (4+5));
	}
	
	@Test
	void testPlayerMovesCount() {
		for (int i = 0; i < 10; i++) {
			onKeyPressed("W","");
		}
		assertTrue(this.getPlayerMovesMade() == 10);
	}
	
	@Test
	void testSimpleGetters() {
		assertTrue(this.getMazeWidth() == this.getMazeHeight() && this.getMazeHeight() == 250);
	}
	
	@Test
	void testPlayerMovement() {
		boolean[][] walls = this.getWalls();
		Rectangle r = this.getPlayer();
		int oldX = (int)r.getX();
		int oldY = (int)r.getY();
		if(!walls[(int)r.getY()][(int)r.getX()+9]) {
			this.moveRight(r);
			assert((oldX + 10) == (int)r.getX() && oldY == (int)r.getY());
		}
		else if (!walls[(int)r.getY()][(int)r.getX()-1]) {
			this.moveLeft(r);
			assert((oldX-10) == (int)r.getX() && oldY == (int)r.getY());
		}
		else if (!walls[(int)r.getY()+9][(int)r.getX()]) {
			this.moveDown(r);
			assert(oldX == (int)r.getX() && (oldY+10) == (int)r.getY());
		}
		else {
			this.moveUp(r);
			assert(oldX == (int)r.getX() && (oldY-10) == (int)r.getY());
		}
		
	}
	
	
	@Test
	void testRemoveWallsToMakeValid() {
		//Make sure maze is solvable
		boolean solutionFound = dfs(this.getWalls(), 1, 1);
		Assert.assertTrue(solutionFound);
//		fail("Not yet implemented");
	}
	@Test
	void testMoveComputerSolvesMaze() {
		boolean flag = true;
		while(flag) {
			flag = false;
			boolean [] moves = moveComputers();
			for(int i =0; i< moves.length; i++) {
				if(moves[i])
					flag = true;
			}
		}
		ArrayList<Rectangle> computers = this.getComputers();
		Rectangle player = this.getPlayer();
		for(Rectangle computer: computers) {
			assert((int)computer.getX() == (int)player.getX() && (int)computer.getY() == (int)player.getY());
		}
		
	}
	boolean dfs(boolean[][] w, int r, int c) {
		boolean [][] visited = new boolean[w.length][w.length];
		return dfsHelp(w,visited,r,c);
	}
	boolean dfsHelp(boolean[][] w, boolean[][] visited, int r, int c) {
		if(r == this.getMazeHeight() - 9 && c == this.getMazeWidth() - 9) {
			return true;
		}
		visited[r][c] = true;
    	for(int d=-1; d <= 1; d+=2) {
    		if(r + d * 10 > -1 && r + d * 10 < this.getMazeHeight() && !visited[r + d * 10][c]) {
    			if(dfsHelp(w, visited, r+d*10, c)) {
    				return true;
    			}
    		}
    		if(c + d * 10 > -1 && c + d * 10 < this.getMazeWidth() && !visited[r][c + d * 10]) {
    			if(dfsHelp(w, visited, r, c + d * 10)) {
    				return true;
    			}
    		}
    	}
    	visited[r][c] = false;
    	return false;
	}
	
	@Test
	void testGameOver() {
		boolean flag = false;
		while(!flag) {
			boolean [] moves = moveComputers();
			for(int i =0; i< moves.length; i++) {
				if(!moves[i])
					flag = true;
			}
		}
		onKeyPressed("W","");
		Assert.assertTrue(getGameOver());
		
	}

	@Test
	void testPathLength() {
		AStar sol = new AStar(this.getWalls(), 11, 11, 121, 121);
		ArrayList<MOVE> path = sol.getPath();
		if (path.size() < 22) {
			fail("Path length is less than shortest possible Manhattan distance (22)");
		}
	}
}