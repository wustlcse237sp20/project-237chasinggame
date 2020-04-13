package code;
import java.util.ArrayList;
import java.util.Arrays;

import code.AStar.AStar;
import doodlepad.*;

public class Maze extends Pad{
	int width = 500;
	int height = 500;
	enum Orientation{
		up, right, down, left
	}
	Orientation orientation = Orientation.right;
	Rectangle r;
	boolean[][] walls = new boolean[height + 1][width + 1];
	boolean[][] visitedBFS = new boolean[height + 1][width + 1];
    public Maze(){
    	super(750, 750);
    	drawMaze(true);
    }
    // bool drawMaze indicates whether you should draw maze for the human on the GUI, which would slow down tests by a lot
    public Maze(boolean drawMaze) {
    	super(750, 750);
    	drawMaze(drawMaze);
    }
    public void drawMaze(boolean drawMaze) {
    	// put in all walls
    	putInAllWalls();
    	//remove starting wall
    	removeStartWall();
    	removeEndWall();
    	removeWallsToMakeValid(5,5);
    	if (drawMaze) {drawWalls();}
    	setupCharacter();
    	r.toFront();
    	//uncomment below for bot to solve following right wall
//    	rightWallBot();
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
    public void setupCharacter() {
    	r = (new Player()).getRectangle();
    }
    public void onKeyPressed(String keyText, String keyModifiers) {
    	if(r.getX() >= width && r.getY() >= height - 10) {
    		return;
    	}
    	if(keyText.equals("D")) {
    		movePlayerRight();
    	}
    	else if(keyText.equals("A")){
    		movePlayerLeft();
    	}
    	else if(keyText.equals("W")) {
    		movePlayerUp();
    	}
    	else if(keyText.equals("S")) {
    		movePlayerDown();
    	}
    }
    public boolean movePlayerRight() {
    	if(!walls[(int)r.getY()][(int)r.getX() + 9]) {
    		Rectangle slime = new Rectangle(r.getX(),r.getY(), 9, 9);
    		slime.setFillColor(0, 255, 0);
    		slime.setStrokeWidth(0);
    		slime.toBack();
    		double beginX = r.getX();
    		orientation = Orientation.right;
    		for(double x = beginX + 1; x < beginX + 11; x++)
    			r.setX(x);
    		return true;
    	}
    	return false;
    }
    public boolean movePlayerLeft() {
    	if(r.getX() - 10 > 0 && !walls[(int)r.getY()][(int)r.getX() - 1]) {
    		Rectangle slime = new Rectangle(r.getX(),r.getY(), 9, 9);
    		slime.setFillColor(0, 255, 0);
    		slime.setStrokeWidth(0);
    		slime.toBack();
    		double beginX = r.getX();
    		orientation = Orientation.left;
    		for(double x = beginX - 1; x > beginX  - 11; x--)
    			r.setX(x);
    		return true;
    	}
		return false;    
    }
    public boolean movePlayerDown() {
    	if(!walls[(int)r.getY()+9][(int)r.getX()]) {
    		Rectangle slime = new Rectangle(r.getX(),r.getY(), 9, 9);
    		slime.setFillColor(0, 255, 0);
    		slime.setStrokeWidth(0);
    		slime.toBack();
    		double beginY = r.getY();
    		orientation = Orientation.down;
    		for(double y = beginY + 1; y < beginY + 11; y++)
    			r.setY(y);
    		return true;
    	}
    	return false;
    }
    public boolean movePlayerUp() {
    	if(r.getY() - 10 > 0 && !walls[(int)r.getY() -1][(int)r.getX()]) {
    		Rectangle slime = new Rectangle(r.getX(),r.getY(), 9, 9);
    		slime.setFillColor(0, 255, 0);
    		slime.setStrokeWidth(0);
    		slime.toBack();
    		double beginY = r.getY();
    		orientation = Orientation.up;
    		for(double y = beginY - 1; y > beginY - 11; y--)
    			r.setY(y);
    		return true;
    	}
    	return false;
    }
    public void rightWallBot() {
    	while(r.getX() != width - 9 || r.getY() != height - 9)
	    	if(orientation == Orientation.right) {
	    		if(!movePlayerDown()) {
	    			if(!movePlayerRight()) {
	    				if(!movePlayerUp()) {
	    					movePlayerLeft();
	    				}
	    			}
	    		}
	    	}
	    	else if(orientation == Orientation.up) {
	    		if(!movePlayerRight()) {
	    			if(!movePlayerUp()) {
	    				if(!movePlayerLeft()) {
	    					movePlayerDown();
	    				}
	    			}
	    		}
	    	}
	    	else if(orientation == Orientation.left) {
	    		if(!movePlayerUp()) {
	    			if(!movePlayerLeft()) {
	    				if(!movePlayerDown()) {
	    					movePlayerRight();
	    				}
	    			}
	    		}
	    	}
	    	else if(orientation == Orientation.down) {
	    		if(!movePlayerLeft()) {
	    			if(!movePlayerDown()) {
	    				if(!movePlayerRight()) {
	    					movePlayerUp();
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
    	return this.r;
    }
	public static void main(String[] args) {
		Maze m = new Maze();
		int startx = 1, starty = 1;
		AStar as = new AStar(m.getWalls(), startx, starty, 491, 491);
		AStar.printPath(m.getWalls(), as.getPath(), startx, starty);
	}
}