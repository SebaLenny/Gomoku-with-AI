package gomokuProcessing;

import processing.core.PApplet;
import processing.core.PVector;

public class Cell {
	private PApplet parent;
	private int colour;
	private PVector position;
	private int size;
	private Stone stone = Stone.NONE;

	public Cell(PApplet pApplet, PVector position, int colour, int size) {
		this.parent = pApplet;
		this.position = position;
		this.colour = colour;
		this.size = size;
	}

	private Cell(PApplet pApplet, PVector position, int colour, int size, Stone stone) {
		this.parent = pApplet;
		this.position = position;
		this.colour = colour;
		this.size = size;
		this.stone = stone;
	}

	public boolean setStone(Stone stone) {
		if (this.stone == Stone.NONE) {
			this.stone = stone;
			return true;
		}
		return false;
	}

	public Stone getStone() {
		return stone;
	}

	public void draw() {
		parent.pushMatrix();
		parent.fill(colour);
		parent.translate(position.x, position.y);
		parent.rectMode(PApplet.CENTER);
		parent.rect(0, 0, size, size);
		if (stone == Stone.BLACK) {
			parent.fill(25);
		}
		if (stone == Stone.WHITE) {
			parent.fill(200);
		}
		parent.ellipse(0, 0, size, size);
		parent.popMatrix();
	}

	@Override
	public Cell clone() {
		return new Cell(parent, position.copy(), colour, size, stone);
	}
}
