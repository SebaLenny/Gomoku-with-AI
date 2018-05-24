package gomokuProcessing;

import processing.core.PApplet;

public class Controller {
	private PApplet parent;
	private Stone turn = Stone.BLACK;
	private Board board;
	private Scenarios selfishScenario;
	private Scenarios cautiousScenatio;
	private boolean winLock;
	private Board chosenBoard;
	private int aiDepth;

	public Controller(PApplet pApplet, int size, int cellSize, int aiDepth) {
		this.parent = pApplet;
		board = new Board(pApplet, size, cellSize);
		this.makeRandomStarter(1);
		selfishScenario = new Scenarios(Stone.BLACK);
		cautiousScenatio = new Scenarios(Stone.BLACK, Stone.WHITE);
		chosenBoard = null;
		this.aiDepth = aiDepth;
	}

	public void draw() {
		board.draw();
	}

	public void makeTurnIfAI(Stone AI) {
		setLockIfNecesary();
		if (!winLock && turn == AI) {
			runAlphaBetaCautious(aiDepth);
		}
	}

	public void makeTurnIfAISelfish(Stone AI) {
		setLockIfNecesary();
		if (!winLock && turn == AI) {
			runAlphaBetaSelfish(aiDepth);
		}
	}

	public void makeTurnIfAI() {
		setLockIfNecesary();
		if (!winLock) {
			runAlphaBetaCautious(aiDepth);
		}
	}

	public void makeTurnIfAISelfish() {
		setLockIfNecesary();
		if (!winLock) {
			runAlphaBetaCautious(aiDepth);
		}
	}

	public void makeTurnIfAISelfVSCau() {
		setLockIfNecesary();
		if (!winLock) {
			if (turn == Stone.WHITE) {
				runAlphaBetaCautious(aiDepth);
			} else {
				runAlphaBetaSelfish(aiDepth);
			}
		}
	}

	public int runAlphaBetaSelfish(int depth) {
		int result = this.alphaBeta(board, depth, Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2, turn, selfishScenario);
		aIMove();
		return result;
	}

	public int runAlphaBetaCautious(int depth) {
		int result = this.alphaBeta(board, depth, Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2, turn, cautiousScenatio);
		aIMove();
		return result;
	}

	private void aIMove() {
		if (board != chosenBoard) {
			this.board = chosenBoard;
			this.swithPlayers();
		}
		chosenBoard = null;
	}

	// Based on Wikipedia's pseudocode of alphabeta
	// (only based on general implementation, not game specyfic!!!)
	public int alphaBeta(Board node, int depth, int alpha, int beta, Stone currPlayer, Scenarios scenario) {
		if (node.gameEnded() != Stone.NONE || depth == 0) {
			return node.evaluateBoard(Stone.BLACK, scenario);
		}
		if (currPlayer == Stone.BLACK) {
			int v = Integer.MIN_VALUE / 2;
			for (int i = 0; i < node.getSize() * node.getSize(); i++) {
				Board next = node.clone();
				if (!next.setStone(Stone.BLACK, next.getX(i), next.getY(i))) {
					continue;
				}
				int temp = PApplet.max(v, alphaBeta(next, depth - 1, alpha, beta, Stone.WHITE, scenario));
				if (temp > v && aiDepth == depth) {
					chosenBoard = next;
				}
				v = Math.max(v, temp);
				alpha = PApplet.max(alpha, v);
				if (beta <= alpha) {
					break;
				}
			}
			return v;
		} else {
			int v = Integer.MAX_VALUE / 2;
			for (int i = 0; i < node.getSize() * node.getSize(); i++) {
				Board next = node.clone();
				if (!next.setStone(Stone.WHITE, next.getX(i), next.getY(i))) {
					continue;
				}
				int temp = PApplet.min(v, alphaBeta(next, depth - 1, alpha, beta, Stone.BLACK, scenario));
				if (temp < v && aiDepth == depth) {
					chosenBoard = next;
				}
				v = Math.min(v, temp);
				beta = PApplet.min(beta, v);
				if (beta <= alpha) {
					break;
				}
			}
			return v;
		}
	}

	public boolean makeMove(int x, int y) {
		boolean returnBool = board.setStone(turn, x, y);
		if (returnBool) {
			this.swithPlayers();
		}
		setLockIfNecesary();
		return returnBool;
	}

	public void setLockIfNecesary() {
		if (this.gameEnded() != Stone.NONE || board.isFull()) {
			this.setWinLock();
		}
	}

	public int evaluateBoard() {
		return board.evaluateBoard(Stone.BLACK, cautiousScenatio);
	}

	public Stone gameEnded() {
		return board.gameEnded();
	}

	private void makeRandomStarter(int moves) {
		for (int i = 0; i < moves; i++) {
			if (!this.makeMove(PApplet.floor(parent.random(board.getSize())),
					PApplet.floor(parent.random(board.getSize())))) {
				i--;
			}
		}
	}

	private void swithPlayers() {
		if (turn == Stone.BLACK) {
			turn = Stone.WHITE;
		} else {
			turn = Stone.BLACK;
		}
	}

	public boolean isWinLock() {
		return winLock;
	}

	public void setWinLock() {
		this.winLock = true;
	}
}
