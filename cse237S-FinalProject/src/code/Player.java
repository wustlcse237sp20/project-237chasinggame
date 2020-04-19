package code;

import doodlepad.Rectangle;

/**
 * The class which represents the player of the game
 */
public class Player {
	private Rectangle rect;
	
	/**
	 * Constructor for the player object, represented by a doodlepad rectangle
	 */
	public Player(int red, int green, int blue, int posX, int posY) {
		rect = new Rectangle(posX,posY,9,9);
		rect.setStrokeWidth(0);
		rect.setFillColor(red, green, blue);
	}
	
	/**
	 * Returns the player object, represented by a doodlepad rectangle
	 * @return Rectangle object representing player
	 */
	public Rectangle getRectangle() {
		return rect;
	}
}
