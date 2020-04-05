package tests;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import code.Maze;
import doodlepad.*;

class MazeTest extends Maze {
	
	//removes the need to have BeforeEach code segment to construct new maze objects, as this does it for us
	public MazeTest() {
		super(false);
	}

	@Test
	void testMazeCreation() {
		for(int r = 0; r < 10; r++) {
			if(this.getWalls()[r][0]) {
				fail("Starting walls not removed");
			}
		}
		assertTrue(true); // if I arrive here, no error occurred
	}
	
	@Test
	void boundaryCheck() {
		for(int h = 10; h <= this.getMazeHeight(); h++) {
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
		for(int h = this.getMazeHeight()-10; h >= 0; h--) {
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
		while (!walls[1][(int)r.getX()+9]) {
			assertTrue(this.movePlayerRight());
		}
		assertTrue(false == this.movePlayerRight());
	}
	
	@Test
	void testPlayerMovement() {
		boolean[][] walls = this.getWalls();
		Rectangle r = this.getPlayer();
		int oldX = (int)r.getX();
		int oldY = (int)r.getY();
		if(!walls[1][(int)r.getX()+9]) {
			this.movePlayerRight();
			assert((oldX + 10) == (int)r.getX() && oldY == (int)r.getY());
		}
		else {
			this.movePlayerDown();
			assert(oldX == (int)r.getX() && (oldY + 10) == (int)r.getY());
		}
		
	}
	
	//
	
	@Test
	void testRemoveWallsToMakeValid() {
		//Make sure maze is solvable
		boolean solutionFound = dfs(this.getWalls(), 1, 1);
		Assert.assertTrue(solutionFound);
//		fail("Not yet implemented");
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

}