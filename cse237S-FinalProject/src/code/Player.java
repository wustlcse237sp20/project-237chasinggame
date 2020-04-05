package code;

import doodlepad.Rectangle;

public class Player {
	private Rectangle r;
	public Player() {
		r = new Rectangle(1,1,9,9);
		r.setStrokeWidth(0);
		r.setFillColor(255, 0, 0);
	}
	
	public Rectangle getRectangle() {
		return r;
	}
}
