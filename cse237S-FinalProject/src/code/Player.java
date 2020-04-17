package code;

import doodlepad.Rectangle;

/**
 * The class which represents the player of the game
 */
public class Player {
	private Rectangle r;
	
	/**
	 * Constructor for the player object, represented by a doodlepad rectangle
	 */
	public Player() {
		r = new Rectangle(121,121,9,9);
		r.setStrokeWidth(0);
		r.setFillColor(255, 0, 0);
	}
	
	/**
	 * Returns the player object, represented by a doodlepad rectangle
	 * @return Rectangle object representing player
	 */
	public Rectangle getRectangle() {
		return r;
	}
}
