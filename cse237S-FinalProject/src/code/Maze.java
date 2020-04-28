package code;
import java.util.ArrayList;
import java.util.Arrays;

import code.AStar.AStar;
import code.AStar.AStarNode.MOVE;
import doodlepad.*;

public class Maze extends Pad{
	int width;
	int height;
	int playerMovesMade = 0;
	Text score;
	enum Orientation{
		up, right, down, left
	}
	
	Orientation player1Orientation = Orientation.right;
	Rectangle player1;
	//computer also known as player 2

	
	ArrayList<Rectangle> computers = new ArrayList<>(); 
	ArrayList<ArrayList<MOVE>> computerSolutionPaths = new ArrayList<>();
	
	int computerPositionInSolution = 0;
	
	boolean[][] walls;  
	boolean[][] visitedBFS; 
    
	/**
	 * Constructor for a Maze
	 */
	public Maze(int width, int height){
		super(width+300, height+100);
		score =  new Text("Score: 0", width+50, 20);
		this.width = width;
		this.height = height;
		visitedBFS = new boolean[height + 1][width + 1];
		walls = new boolean[height + 1][width + 1];
    	drawMaze(true);
    	this.initializeComputerSolutions(4);
    }
	
	/**
	 * Constructor for a Maze
	 * @param drawMaze indicates whether you should draw maze for the human on the GUI, which would slow down tests by a lot
	 */
    public Maze(int width, int height, boolean drawMaze) {
		super(width+300, height+100);
		score =  new Text("Score: 0", width+50, 20);
    	this.width = width;
		this.height = height;
		visitedBFS = new boolean[height + 1][width + 1];
		walls = new boolean[height + 1][width + 1];
    	drawMaze(drawMaze);
    	
    	this.initializeComputerSolutions(4);

    	//uncomment below for player1 to solve following right wall
//    	rightWallBot();
    }
    
    
    private void initializeComputerSolutions(int numComputers) {
    	for(int i=0; i< numComputers; i++) {
    		AStar computerSolution = new AStar(walls, (int)computers.get(i).getX(), (int)computers.get(i).getY(), (int)player1.getX(), (int)player1.getY());
    		computerSolutionPaths.add(computerSolution.getPath());
    	}

    }
    /**
	 * Draw the maze
	 * @param drawMaze indicates whether you should draw maze for the human on the GUI, which would slow down tests by a lot
	 */
    public void drawMaze(boolean drawMaze) {
    	// put in all walls
    	putInAllWalls();
    	//remove starting wall
    	removeStartWall();
    	removeEndWall();
    	removeWallsToMakeValid(5,5);
    	if (drawMaze) {
    		drawWalls();
    	}
    	this.setupComputers(4);

    	setupPlayer();
    	player1.toFront();
    	}
    
    /**
	 * Puts walls in the maze, adds to walls boolean[][]
	 */
	public void putInAllWalls() {
    	for(int row=0; row<=height; row+= 10) {
    		for(int col = 0; col <= width; col++) {
    			walls[row][col] = true;
    		}
    	}
    	for(int row=0; row<=height; row++) {
    		for(int col = 0; col<=width; col+=10) {
    				walls[row][col] = true;
    			}
    	}
    }
	
	/**
	 * Removes wall at the beginning
	 */
	public void removeStartWall() {
		for(int row = 0; row < 10; row++) {
			walls[row][0] = false;
		}
	}
	
	/**
	 * Removes wall at the end
	 */
	public void removeEndWall() {
		for(int col = 0; col < 10; col++) {
			walls[height - col][width] = false;
		}
	}
	
	/**
	 * Removes wall at specified row and column to make it a valid maze
	 * @param r row
	 * @param c column
	 */
    private void removeWallsToMakeValid(int row, int col) {
    	visitedBFS[row][col] = true;
    	ArrayList<int[]> unv = new ArrayList<int[]>();
    	for(int delta=-1; delta <= 1; delta+=2) {
    		if(row + delta * 10 > -1 && row + delta * 10 < height && !visitedBFS[row + delta * 10][col]) {
    			int [] w = {row + delta*10, col};
    			unv.add(w);
    		}
    		if(col + delta * 10 > -1 && col + delta * 10 < width && !visitedBFS[row][col + delta * 10]) {
    			int [] w = {row, col + delta * 10};
    			unv.add(w);
    		}
    	}
    	while(unv.size() != 0) {
    		int random = (int)(Math.random()*unv.size());
    		int[] cell = unv.remove(random);
    		if(!visitedBFS[cell[0]][cell[1]]) {
    			if(cell[0] == row) {
    				for(int deltaw = -4; deltaw <= 4; deltaw++) {
    					walls[row + deltaw][(cell[1] + col)/2] = false;
    				}
    			}
    			else {
    				for(int deltaw = -4; deltaw <= 4; deltaw++) {
    					if(!walls[(cell[0] + row)/2][col + deltaw]){
    					}
    					walls[(cell[0] + row)/2][col + deltaw] = false;  
    				}    				
    			}
        		removeWallsToMakeValid(cell[0], cell[1]);
    		}
    	}
	}
    
    /**
	 * Draws walls on the maze
	 */
    private void drawWalls() {
    	//from array
    	for(int row = 0; row <= height; row++) {
    		for(int col = 0; col <= width; col++) {
    			if(walls[row][col])
	    	    		if(row + 1<= height && walls[row + 1][col]) {
	    	    			new Line(col, row, col, row + 1);
	    	    		}
	    	    		if(col + 1 <= width && walls[row][col + 1]) {
	    	    			new Line(col, row, col + 1, row);
	    	    		}    	    	
    		}
    	}
    	
    	
    }
    
    /**
	 * Sets up player rectangle
	 */
    public void setupPlayer() {
    	player1 = (new Player(0,0,255,121,121)).getRectangle();
    }
    
    /**
	 * Sets up computers rectangle
	 */
    
    public void setupComputers(int numComputers) {
    	for(int i =1; i<=numComputers; i++) {
    		Rectangle c = (new Computer(i*10+1,i*10+1)).getRectangle();
    		computers.add(c);
    		c.toFront();
    	}
    }
    
    /**
	 * Reads input from keyboard
	 */
    public void onKeyPressed(String keyText, String keyModifiers) {
    	this.moveComputers();
    	playerMovesMade++;
    	score.setText("Score: "+ playerMovesMade);
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
    
    /**
	 * Moves the specified player right
	 * @param player player to be moved
	 * @return boolean of whether this is a valid action for the player
	 */
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
    
    /**
	 * Moves the specified player left
	 * @param player player to be moved
	 * @return boolean of whether this is a valid action for the player
	 */
    public boolean moveLeft(Rectangle player) {
    	if(player.getX() - 10 > 0 && !walls[(int)player.getY()][(int)player.getX() - 1]) {
    		double beginX = player.getX();
    		if(player.equals(player1)) {
    			player1Orientation = Orientation.left;
    		}
    		for(double x = beginX - 1; x > beginX  - 11; x--) {
    			player.setX(x);
    		}
    		return true;
    	}
		return false;    
    }
    
    /**
	 * Moves the specified player down
	 * @param player player to be moved
	 * @return boolean of whether this is a valid action for the player
	 */
    public boolean moveDown(Rectangle player) {
    	if(!walls[(int)player.getY()+9][(int)player.getX()]) {
    		double beginY = player.getY();
    		if(player.equals(player1)) {
    			player1Orientation = Orientation.down;
    		}
    		for(double y = beginY + 1; y < beginY + 11; y++) {
    			player.setY(y);
    		}
    		return true;
    	}

    	return false;
    }
    
    /**
	 * Moves the specified player up
	 * @param player player to be moved
	 * @return boolean of whether this is a valid action for the player
	 */
    public boolean moveUp(Rectangle player) {
    	if(player.getY() - 10 > 0 && !walls[(int)player.getY() -1][(int)player.getX()]) {
    		double beginY = player.getY();
    		if(player.equals(player1)) {
    			player1Orientation = Orientation.up;
    		}
    		for(double y = beginY - 1; y > beginY - 11; y--) {
    			player.setY(y);
    		}
    		return true;
    	}
    	return false;
    }
    
    /**
	 * Bot for a strategy to follow the right wall
	 */
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
    
    /**
	 * Returns maze height
	 * @return maze height
	 */
    public int getMazeHeight() {
    	return this.height;
    }
    
    /**
	 * Returns maze width
	 * @return maze width
	 */
    public int getMazeWidth() {
    	return this.width;
    }
    
    /**
	 * Returns maze walls
	 * @return maze walls boolean[][]
	 */
    public boolean[][] getWalls() {
    	return this.walls;
    }
    
    /**
	 * Returns main player rectangle
	 * @return player rectangle
	 */
    public Rectangle getPlayer() {
    	return this.player1;
    }
    
    /**
	 * Returns computer player rectangle
	 * @return computer rectangle
	 */
    public ArrayList<Rectangle> getComputers() {
    	return this.computers;
    }
    
    /**
	 * Runs the computer through the optimal path
	 * @return if the computer has reached the solution
	 */
    
    public boolean[] moveComputers() {
    	boolean []flags = new boolean[computers.size()];
    	for(int i=0; i<computers.size(); i++) {
    		Rectangle computer = computers.get(i);
    		AStar computerSolution = new AStar(walls, (int)computer.getX(), (int)computer.getY(), (int)player1.getX(), (int)player1.getY());
    		computerSolutionPaths.set(i, computerSolution.getPath()); 
    		
    		ArrayList<MOVE> computerSolutionPath = computerSolutionPaths.get(i);
    		
    		
        	if(computerPositionInSolution < computerSolutionPath.size()) {
        		MOVE compMove = computerSolutionPath.get(0); // Getting the next move
        		if(compMove == MOVE.DOWN) {
    				moveDown(computer);
    			}
        		else if(compMove == MOVE.UP) {
    				moveUp(computer);
    			}
        		else if(compMove == MOVE.RIGHT) {
    				moveRight(computer);
    			}
        		else if(compMove == MOVE.LEFT) {
    				moveLeft(computer);
    			}
        		flags[i] = true;
        	} else {
        		flags[i] = false;
        	}
    	}
    	return flags;
    }
}
