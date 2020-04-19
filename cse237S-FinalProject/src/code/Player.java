package code;

import doodlepad.Rectangle;

/**
 * The class which represents the player of the game
 */
public class Player {
	protected Rectangle rect;
	
	/**
	 * Constructor for the player object, represented by a doodlepad rectangle
	 */
	public Player() {
		rect = new Rectangle(121,121,9,9);
		rect.setStrokeWidth(0);
		rect.setFillColor(255, 0, 0);
	}
	
	/**
	 * Returns the player object, represented by a doodlepad rectangle
	 * @return Rectangle object representing player
	 */
	public Rectangle getRectangle() {
		return rect;
	}
}
