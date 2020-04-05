package code;


import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MazeTest extends Maze {
	private Maze maze;
	
	@BeforeEach
	void setupTestingObjects() {
		maze = new Maze();
	}
	
	@Test
//	void 
	//existence of maze
	
	//no boundaries
	
	//rectangle moved
	
	//check wall boundary
	
	//
	
	@Test
	void testRemoveWallsToMakeValid() {
		//Make sure maze is solvable
		boolean solutionFound = dfs(walls, 1, 1);
		Assert.assertTrue(solutionFound);
//		fail("Not yet implemented");
	}
	boolean dfs(boolean[][] w, int r, int c) {
		boolean [][] visited = new boolean[w.length][w.length];
		return dfsHelp(w,visited,r,c);
	}
	boolean dfsHelp(boolean[][] w, boolean[][] visited, int r, int c) {
		if(r == height - 9 && c == width - 9) {
			return true;
		}
		visited[r][c] = true;
    	for(int d=-1; d <= 1; d+=2) {
    		if(r + d * 10 > -1 && r + d * 10 < height && !visited[r + d * 10][c]) {
    			if(dfsHelp(w, visited, r+d*10, c)) {
    				return true;
    			}
    		}
    		if(c + d * 10 > -1 && c + d * 10 < width && !visited[r][c + d * 10]) {
    			if(dfsHelp(w, visited, r, c + d * 10)) {
    				return true;
    			}
    		}
    	}
    	visited[r][c] = false;
    	return false;
	}

}
