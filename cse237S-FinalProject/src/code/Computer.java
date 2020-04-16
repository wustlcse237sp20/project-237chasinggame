package code;

import doodlepad.Rectangle;

public class Computer {
	private Rectangle r;
	public Computer() {
		r = new Rectangle(1,1,9,9);
		r.setStrokeWidth(0);
		r.setFillColor(0, 0, 255);
	}
	
	public Rectangle getRectangle() {
		return r;
	}
}
