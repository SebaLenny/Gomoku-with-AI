package gomokuProcessing;

import processing.core.PApplet;

public class Main extends PApplet {

	Controller gameController;
	int boardSize = 10;
	int cellSize = 40;
	int aiDepth = 3;

	@Override
	public void settings() {
		size(boardSize * cellSize, boardSize * cellSize);
	}

	@Override
	public void setup() {
		noStroke();
		fill(0);
		gameController = new Controller(this, boardSize, cellSize, aiDepth);
	}

	@Override
	public void draw() {
		background(0);
		translate(cellSize / 2, cellSize / 2);
		gameController.draw();
		gameController.makeTurnIfAI();
		// gameController.makeTurnIfAI(Stone.BLACK);
		// gameController.makeTurnIfAISelfVSCau();
		// gameController.makeTurnIfAISelfish();
		// gameController.makeTurnIfAISelfish(Stone.BLACK);
	}

	@Override
	public void keyPressed() {

	}

	@Override
	public void mousePressed() {
		if (mouseButton == LEFT && !gameController.isWinLock()) {
			int x = floor(mouseX / cellSize);
			int y = floor(mouseY / cellSize);
			gameController.makeMove(x, y);
			System.out.println("Value of board now: " + gameController.evaluateBoard());
			System.out.println("--------------------------");
		}
	}

	public static void main(String[] args) {
		PApplet.main("gomokuProcessing.Main");
	}

}
