package code;
import java.util.Scanner;
public class RunGame {
	
	public static Maze runGame(int level, Maze prevMaze) {
		
		if(prevMaze == null) {
		System.out.println("Starting maze game! Avoid the enemies");
		System.out.println("Please enter your desired maze size in the following format: width,height (minimum of 150) ex: 170,180");
		boolean flag = false;
		int x = 0;
		int y = 0;
		Scanner in = new Scanner(System.in);
		while (!flag) {
			String [] s = in.nextLine().split(",");
			try {
				if (Integer.parseInt(s[0]) < 150 || Integer.parseInt(s[1]) < 150) {
					System.out.println("Error, please try again with the correct format.");
					continue;
				}
				else {
					x = Integer.parseInt(s[0]);
					y = Integer.parseInt(s[1]);
					flag = true;
				}
			}
			catch (NumberFormatException nfe) {
				System.out.println("Error, please try again with the correct format.");
			}
		}
		
		System.out.println("Use WASD to move player/square. Make sure you've selected window before trying to move player.");
		System.out.println("Player is in BLUE. Enemies are in RED");
		
		x = x - (x % 10);
		y = y - (y % 10);
		
		return new Maze(x, y, true, level);
		} else {
			return new Maze(prevMaze.getMazeWidth(), prevMaze.getMazeHeight(), true, level);
		}
	}
	
	
	public static void main (String[] args) {
		int level = 1;
		Maze maze = runGame(level, null);
		while(true) {
			
			if(maze.getGameOver()) {
				if(maze.getPlayerMovesMade() > Math.min(maze.getMazeHeight(), maze.getMazeWidth())/5) {
					if(level >= 11 ) {
						break;
					}
					level++;
					maze = runGame(level,maze);
					
				}else {
					break;
				}
			}
		}
		if(level == 10) {
			System.out.println("You have won the game!!");
		}else {
			System.out.println("The game ended you reached level "+level);
		}
		System.out.println("Nice WORK!!!!");
		
	}
}
