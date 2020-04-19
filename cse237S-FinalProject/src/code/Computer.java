package code;

import doodlepad.Rectangle;

/**
 * The class which represents an enemy in the game
 */
public class Computer extends Player {

	/**
	 * Constructor for the computer object, represented by a doodlepad rectangle
	 */
	public Computer() {
		rect = new Rectangle(1,1,9,9);
		rect.setStrokeWidth(0);
		rect.setFillColor(0, 0, 255);
	}

}
