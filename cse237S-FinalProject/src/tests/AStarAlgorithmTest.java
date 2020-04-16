	package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import code.Maze;
import code.AStar.AStar;

class AStarAlgorithmTest {

	@Test
	void positionStartSetTest() {
		Maze m = new Maze(false);
		boolean[][] walls = m.getWalls();
		int width = walls.length, height = walls[0].length;
		
		for(int i=1; i<width-1; i++) {
			for(int j=1; j<height-1; j++) {
				int startx = i, starty = j;
				AStar as = new AStar(walls, startx, starty, 8, 8);
				assertEquals(as.getStartX(), startx);
				assertEquals(as.getStartY(), starty);
			}	
		}
	}

	@Test
	void positionEndSetTest() {
		Maze m = new Maze(false);
		boolean[][] walls = m.getWalls();
		int width = walls.length, height = walls[0].length;
		
		for(int i =1; i<width-1; i++) {
			for(int j =1; j<height-1; j++) {
				int endx = i, endy = j;
				AStar as = new AStar(walls, 1, 1, endx, endy);
				assertEquals(as.getEndX(), endx);
				assertEquals(as.getEndY(), endy);
			}	
		}
	}
	
	@Test
	void findsEndTest() {
		Maze m = new Maze(false);
		boolean[][] walls = m.getWalls();
		int width = walls.length, height = walls[0].length;
		int startx = 4, starty = 4;
		
		for(int i=1; i<width-1; i++) {
			for(int j =1; j<height-1; j++) {
				if((i>=3 || i<=5) && (j>=3 || j<=5)) {
					continue;
				}
				int endx = i, endy = j;
				AStar as = new AStar(walls, startx, starty, endx, endy);
				ArrayList<int[]> visitedPoints = AStar.printPath(walls, as.getPath(), startx, starty);
				int [] lastPoint = visitedPoints.get(visitedPoints.size()-1);
				assertEquals(lastPoint[0], endx);
				assertEquals(lastPoint[1], endy);
			}
			
		}

	}
	
	@Test
	void numberOfMoves() {
		Maze m = new Maze(false);
		boolean[][] walls = m.getWalls();
		int width = walls.length, height = walls[0].length;
		int startx = 1, starty = 1;
		
		for(int i=1; i<width; i+=10) {
			for(int j =1; j<height; j+=10) {
				int endx = i, endy = j;
				int minMoves = (Math.abs(startx-endx)+Math.abs(starty-endy))/10;
				AStar as = new AStar(walls, startx, starty, endx, endy);
				int numMoves = as.getPath().size();
				assertTrue(numMoves>=minMoves);
			
			}
			
		}

	}

}
