package code;
import java.util.Scanner;
public class RunGame {
	public static void main (String[] args) {
		System.out.println("Starting maze game! Get to the bottom right corner");
		System.out.println("Please enter your desired maze size in the following format: width,height (minimum of 150)");
		boolean flag = false;
		int x = 0;
		int y = 0;
		Scanner in = new Scanner(System.in);
		while (!flag) {
			String [] s = in.nextLine().split(",");
			System.out.println(s[0]);
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
		System.out.println("Player is in BLUE. Computer is in RED");
		
		x = x - (x % 10);
		y = y - (y % 10);

		Maze maze = new Maze(x, y);
	}
}
