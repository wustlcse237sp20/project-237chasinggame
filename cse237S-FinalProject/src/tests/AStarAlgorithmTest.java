package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import code.AStar.AStar;

class AStarAlgorithmTest {

	@Test
	void test() {
		boolean[][] walls = AStar.createWalls();
		int startx = 3, starty = 3;
		AStar as = new AStar(walls, startx, starty, 8, 8);
		AStar.printPath(walls, as.getPath(), startx, starty);
		
	}

}
