package code;

import doodlepad.Rectangle;

/**
 * The class which represents an enemy in the game
 */
public class Computer {
	private Rectangle r;
	
	/**
	 * Constructor for the computer object, represented by a doodlepad rectangle
	 */
	public Computer() {
		r = new Rectangle(1,1,9,9);
		r.setStrokeWidth(0);
		r.setFillColor(0, 0, 255);
	}
	
	/**
	 * Returns the computer object, represented by a doodlepad rectangle
	 * @return Rectangle object representing computer
	 */
	public Rectangle getRectangle() {
		return r;
	}
}
