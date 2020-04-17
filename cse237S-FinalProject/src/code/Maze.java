package code;
import java.util.ArrayList;
import java.util.Arrays;

import code.AStar.AStar;
import code.AStar.AStarNode.MOVE;
import doodlepad.*;

public class Maze extends Pad{
	int width = 500;
	int height = 500;
	enum Orientation{
		up, right, down, left
	}
	
	Orientation player1Orientation = Orientation.right;
	Rectangle player1;
	//computer also known as player 2
	Rectangle computer;
	ArrayList<MOVE> computerSolutionPath;
	int computerPositionInSolution;
	boolean[][] walls = new boolean[height + 1][width + 1];
	boolean[][] visitedBFS = new boolean[height + 1][width + 1];
    public Maze(){
    	super(750, 750);
    	drawMaze(true);
		AStar computerSolution = new AStar(walls, (int)computer.getX(), (int)computer.getY(), 491, 491);
		computerSolutionPath = computerSolution.getPath();
		computerPositionInSolution = 0;
    }
    // bool drawMaze indicates whether you should draw maze for the human on the GUI, which would slow down tests by a lot
    public Maze(boolean drawMaze) {
    	super(750, 750);
    	drawMaze(drawMaze);
		AStar computerSolution = new AStar(walls, (int)computer.getX(), (int)computer.getY(), 491, 491);
		computerSolutionPath = computerSolution.getPath();
		computerPositionInSolution = 0;
    	//uncomment below for player1 to solve following right wall
//    	rightWallBot();
    }
    public void drawMaze(boolean drawMaze) {
    	// put in all walls
    	putInAllWalls();
    	//remove starting wall
    	removeStartWall();
    	removeEndWall();
    	removeWallsToMakeValid(5,5);
    	if (drawMaze) {drawWalls();}
    	setupComputer();
    	computer.toFront();
    	setupPlayer();
    	player1.toFront();
    	}
    //walls are length 10 and begin at (10n,10n)
	public void putInAllWalls() {
    	for(int r=0; r<=height; r+= 10) {
    		for(int c = 0; c <= width; c++) {
    			walls[r][c] = true;
    		}
    	}
    	for(int r=0; r<=height; r++) {
    		for(int c = 0; c<=width; c+=10) {
    				walls[r][c] = true;
    			}
    	}
    }
	public void removeStartWall() {
		for(int r = 0; r < 10; r++) {
			walls[r][0] = false;
		}
	}
	public void removeEndWall() {
		for(int c = 0; c < 10; c++) {
			walls[height - c][width] = false;
		}
	}
    private void removeWallsToMakeValid(int r, int c) {
    	visitedBFS[r][c] = true;
    	ArrayList<int[]> unv = new ArrayList<int[]>();
    	for(int d=-1; d <= 1; d+=2) {
    		if(r + d * 10 > -1 && r + d * 10 < height && !visitedBFS[r + d * 10][c]) {
    			int [] w = {r + d*10, c};
    			unv.add(w);
    		}
    		if(c + d * 10 > -1 && c + d * 10 < width && !visitedBFS[r][c + d * 10]) {
    			int [] w = {r, c + d * 10};
    			unv.add(w);
    		}
    	}
    	while(unv.size() != 0) {
    		int random = (int)(Math.random()*unv.size());
    		int[] cell = unv.remove(random);
    		if(!visitedBFS[cell[0]][cell[1]]) {
    			if(cell[0] == r) {
    				for(int dw = -4; dw <= 4; dw++) {
    					walls[r + dw][(cell[1] + c)/2] = false;
    				}
    			}
    			else {
    				for(int dw = -4; dw <= 4; dw++) {
    					if(!walls[(cell[0] + r)/2][c + dw]){
    					}
    					walls[(cell[0] + r)/2][c + dw] = false;  
    				}    				
    			}
        		removeWallsToMakeValid(cell[0], cell[1]);
    		}
    	}
	}
    private void drawWalls() {
    	//from array
    	for(int r = 0; r <= height; r++) {
    		for(int c = 0; c <= width; c++) {
    			if(walls[r][c])
	    	    		if(r + 1<= height && walls[r + 1][c]) {
	    	    			new Line(c, r, c, r + 1);
	    	    		}
	    	    		if(c + 1 <= width && walls[r][c + 1]) {
	    	    			new Line(c, r, c + 1, r);
	    	    		}    	    	
    		}
    	}
    	
    	
    }
    public void setupPlayer() {
    	player1 = (new Player()).getRectangle();
    }
    public void setupComputer() {
    	computer = (new Computer()).getRectangle();
    }
    public void onKeyPressed(String keyText, String keyModifiers) {
    	moveComputer();
    	if(player1.getX() >= width && player1.getY() >= height - 10) {
    		return;
    	}
    	if(keyText.equals("D")) {
    		moveRight(player1);
    	}
    	else if(keyText.equals("A")){
    		moveLeft(player1);
    	}
    	else if(keyText.equals("W")) {
    		moveUp(player1);
    	}
    	else if(keyText.equals("S")) {
    		moveDown(player1);
    	}
    }
    public boolean moveRight(Rectangle player) {
    	if(!walls[(int)player.getY()][(int)player.getX() + 9]) {
    		double beginX = player.getX();
    		if(player.equals(player1))
    			player1Orientation = Orientation.right;
    		for(double x = beginX + 1; x < beginX + 11; x++)
    			player.setX(x);
    		return true;
    	}
    	return false;
    }
    public boolean moveLeft(Rectangle player) {
    	if(player.getX() - 10 > 0 && !walls[(int)player.getY()][(int)player.getX() - 1]) {
    		double beginX = player.getX();
    		if(player.equals(player1))
    			player1Orientation = Orientation.left;
    		for(double x = beginX - 1; x > beginX  - 11; x--)
    			player.setX(x);
    		return true;
    	}
		return false;    
    }
    public boolean moveDown(Rectangle player) {
    	if(!walls[(int)player.getY()+9][(int)player.getX()]) {
    		double beginY = player.getY();
    		if(player.equals(player1))
    			player1Orientation = Orientation.down;
    		for(double y = beginY + 1; y < beginY + 11; y++)
    			player.setY(y);
    		return true;
    	}

    	return false;
    }
    public boolean moveUp(Rectangle player) {
    	if(player.getY() - 10 > 0 && !walls[(int)player.getY() -1][(int)player.getX()]) {
    		double beginY = player.getY();
    		if(player.equals(player1))
    			player1Orientation = Orientation.up;
    		for(double y = beginY - 1; y > beginY - 11; y--)
    			player.setY(y);
    		return true;
    	}
    	return false;
    }
    public void rightWallBot() {
    	while(player1.getX() != width - 9 || player1.getY() != height - 9)
	    	if(player1Orientation == Orientation.right) {
	    		if(!moveDown(player1)) {
	    			if(!moveRight(player1)) {
	    				if(!moveUp(player1)) {
	    					moveLeft(player1);
	    				}
	    			}
	    		}
	    	}
	    	else if(player1Orientation == Orientation.up) {
	    		if(!moveRight(player1)) {
	    			if(!moveUp(player1)) {
	    				if(!moveLeft(player1)) {
	    					moveDown(player1);
	    				}
	    			}
	    		}
	    	}
	    	else if(player1Orientation == Orientation.left) {
	    		if(!moveUp(player1)) {
	    			if(!moveLeft(player1)) {
	    				if(!moveDown(player1)) {
	    					moveRight(player1);
	    				}
	    			}
	    		}
	    	}
	    	else if(player1Orientation == Orientation.down) {
	    		if(!moveLeft(player1)) {
	    			if(!moveDown(player1)) {
	    				if(!moveRight(player1)) {
	    					moveUp(player1);
	    				}
	    			}
	    		}
	    	}
    }
    public int getMazeHeight() {
    	return this.height;
    }
    public int getMazeWidth() {
    	return this.width;
    }
    public boolean[][] getWalls() {
    	return this.walls;
    }
    public Rectangle getPlayer() {
    	return this.player1;
    }
    public Rectangle getComputer() {
    	return this.computer;
    }
    public boolean moveComputer() {
    	if(computerPositionInSolution < computerSolutionPath.size()) {
    		if(computerSolutionPath.get(computerPositionInSolution) == MOVE.DOWN) {
				moveDown(computer);
			}
    		else if(computerSolutionPath.get(computerPositionInSolution) == MOVE.UP) {
				moveUp(computer);
			}
    		else if(computerSolutionPath.get(computerPositionInSolution) == MOVE.RIGHT) {
				moveRight(computer);
			}
    		else if(computerSolutionPath.get(computerPositionInSolution) == MOVE.LEFT) {
				moveLeft(computer);
			}
    		computerPositionInSolution++;
    		return true;
    	}
    	return false;
    }
}