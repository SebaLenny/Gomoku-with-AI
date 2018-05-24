package gomokuProcessing;

import processing.core.PApplet;
import processing.core.PVector;

public class Board {
	private PApplet parent;
	private int size = 10;
	private int cellSize = 50;
	private Cell[] cells;
	private int winNO = 5;

	public Board(PApplet pApplet, int size, int cellSize) {
		this.parent = pApplet;
		this.cellSize = cellSize;
		this.size = size;
		this.cells = new Cell[size * size];
		this.prepereCells();
	}

	private Board(PApplet pApplet, int size, int cellSize, Cell[] cells) {
		this.parent = pApplet;
		this.cellSize = cellSize;
		this.size = size;
		this.cells = copyCells(cells);
	}

	private Cell[] copyCells(Cell[] cells) {
		Cell[] reurnCells = new Cell[cells.length];
		for (int i = 0; i < cells.length; i++) {
			reurnCells[i] = cells[i].clone();
		}
		return reurnCells;
	}

	private void prepereCells() {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				cells[toIndex(x, y)] = new Cell(parent, new PVector(x * cellSize, y * cellSize),
						(x + y) % 2 == 0 ? parent.color(0, 70, 180) : parent.color(0, 50, 150), cellSize);
			}
		}
	}

	public int evaluateBoard(Stone stone, Scenarios scenarios) {
		int sum = 0;
		for (int subSquareY = 0; subSquareY < size - winNO + 1; subSquareY++) {
			for (int subSquareX = 0; subSquareX < size - winNO + 1; subSquareX++) {
				sum += rateSquareDiagonal(subSquareX, subSquareY, scenarios);
			}
		}
		for (int subSquareY = 0; subSquareY < size - winNO + 1; subSquareY++) {
			for (int subSquareX = 0; subSquareX < size; subSquareX++) {
				sum += rateSquareVertical(subSquareX, subSquareY, scenarios);
			}
		}
		for (int subSquareY = 0; subSquareY < size; subSquareY++) {
			for (int subSquareX = 0; subSquareX < size - winNO + 1; subSquareX++) {
				sum += rateSquareHorizontal(subSquareX, subSquareY, scenarios);
			}
		}
		return sum;
	}

	public int rateSquareHorizontal(int startX, int startY, Scenarios scenarios) {
		Stone[] horizontalStones = new Stone[5];
		int sum = 0;
		for (int i = 0; i < winNO; i++) {
			horizontalStones[i] = cells[toIndex(startX + i, startY)].getStone();
		}
		sum += scenarios.checkArray(horizontalStones);
		return sum;
	}

	public int rateSquareVertical(int startX, int startY, Scenarios scenarios) {
		Stone[] verticalStones = new Stone[5];
		int sum = 0;
		for (int i = 0; i < winNO; i++) {
			verticalStones[i] = cells[toIndex(startX, startY + i)].getStone();
		}
		sum += scenarios.checkArray(verticalStones);
		return sum;
	}

	public int rateSquareDiagonal(int startX, int startY, Scenarios scenarios) {
		Stone[] diagonalLeftStones = new Stone[5];
		Stone[] diagonalRightStones = new Stone[5];
		int sum = 0;
		for (int i = 0; i < winNO; i++) {
			diagonalLeftStones[i] = cells[toIndex(i + startX, i + startY)].getStone();
			diagonalRightStones[i] = cells[toIndex(i + startX, startY + winNO - i - 1)].getStone();
		}
		sum += scenarios.checkArray(diagonalLeftStones);
		sum += scenarios.checkArray(diagonalRightStones);
		return sum;
	}

	public int rateSquare(int startX, int startY, Scenarios scenarios) {
		Stone[] horizontalStones = new Stone[5];
		Stone[] verticalStones = new Stone[5];
		Stone[] diagonalLeftStones = new Stone[5];
		Stone[] diagonalRightStones = new Stone[5];
		int sum = 0;
		for (int i = 0; i < winNO; i++) {
			horizontalStones[i] = cells[toIndex(startX + i, startY)].getStone();
			verticalStones[i] = cells[toIndex(startX, startY + i)].getStone();
			diagonalLeftStones[i] = cells[toIndex(i + startX, i + startY)].getStone();
			diagonalRightStones[i] = cells[toIndex(i + startX, startY + winNO - i - 1)].getStone();
		}
		sum += scenarios.checkArray(horizontalStones);
		sum += scenarios.checkArray(verticalStones);
		sum += scenarios.checkArray(diagonalLeftStones);
		sum += scenarios.checkArray(diagonalRightStones);
		return sum;
	}

	public Stone gameEnded() {
		Stone retVal = checkDiagonalBoxSpaces();
		if (retVal != Stone.NONE) {
			return retVal;
		}
		retVal = checkHorizontalBoxSpaces();
		if (retVal != Stone.NONE) {
			return retVal;
		}
		retVal = checkVerticalBoxSpaces();
		if (retVal != Stone.NONE) {
			return retVal;
		}
		return Stone.NONE;
	}

	private Stone checkVerticalBoxSpaces() {
		for (int subSquareY = 0; subSquareY < size - winNO + 1; subSquareY++) {
			for (int subSquareX = 0; subSquareX < size; subSquareX++) {
				Stone stoneB = checkVertical(subSquareX, subSquareY);
				if (stoneB != Stone.NONE) {
					return stoneB;
				}
			}
		}
		return Stone.NONE;
	}

	private Stone checkHorizontalBoxSpaces() {
		for (int subSquareY = 0; subSquareY < size; subSquareY++) {
			for (int subSquareX = 0; subSquareX < size - winNO + 1; subSquareX++) {
				Stone stoneA = checkHorizontal(subSquareX, subSquareY);
				if (stoneA != Stone.NONE) {
					return stoneA;
				}
			}
		}
		return Stone.NONE;
	}

	private Stone checkDiagonalBoxSpaces() {
		for (int subSquareY = 0; subSquareY < size - winNO + 1; subSquareY++) {
			for (int subSquareX = 0; subSquareX < size - winNO + 1; subSquareX++) {
				Stone stoneC = checkDiagonalLeft(subSquareX, subSquareY);
				Stone stoneD = checkDiagonalRight(subSquareX, subSquareY);
				if (stoneC != Stone.NONE) {
					return stoneC;
				}
				if (stoneD != Stone.NONE) {
					return stoneD;
				}
			}
		}
		return Stone.NONE;
	}

	private Stone checkHorizontal(int startX, int startY) {
		Stone checker = cells[toIndex(startX, startY)].getStone();
		if (checker == Stone.NONE) {
			return Stone.NONE;
		}
		for (int i = 1; i < winNO; i++) {
			Stone temp = cells[toIndex(startX + i, startY)].getStone();
			if (temp != checker) {
				return Stone.NONE;
			}
		}
		return checker;
	}

	private Stone checkVertical(int startX, int startY) {
		Stone checker = cells[toIndex(startX, startY)].getStone();
		if (checker == Stone.NONE) {
			return Stone.NONE;
		}
		for (int i = 1; i < winNO; i++) {
			Stone temp = cells[toIndex(startX, startY + i)].getStone();
			if (temp != checker) {
				return Stone.NONE;
			}
		}
		return checker;
	}

	private Stone checkDiagonalLeft(int startX, int startY) {
		Stone checker = cells[toIndex(startX, startY)].getStone();
		if (checker == Stone.NONE) {
			return Stone.NONE;
		}
		for (int i = 1; i < winNO; i++) {
			Stone temp = cells[toIndex(i + startX, i + startY)].getStone();
			if (temp != checker) {
				return Stone.NONE;
			}
		}
		return checker;
	}

	private Stone checkDiagonalRight(int startX, int startY) {
		Stone checker = cells[toIndex(startX, startY + winNO - 1)].getStone();
		if (checker == Stone.NONE) {
			return Stone.NONE;
		}
		for (int i = 1; i < winNO; i++) {
			Stone temp = cells[toIndex(i + startX, startY + winNO - i - 1)].getStone();
			if (temp != checker) {
				return Stone.NONE;
			}
		}
		return checker;
	}

	public boolean setStone(Stone stone, int x, int y) {
		return cells[toIndex(x, y)].setStone(stone);
	}

	public boolean isFull() {
		for (Cell cell : cells) {
			if (cell.getStone() == Stone.NONE) {
				return false;
			}
		}
		return true;
	}

	public int getSize() {
		return size;
	}

	public void draw() {
		for (Cell cell : cells) {
			cell.draw();
		}
	}

	private int toIndex(int x, int y) {
		return x + y * size;
	}

	public int getX(int index) {
		return index % size;
	}

	public int getY(int index) {
		return index / size;
	}

	@Override
	public Board clone() {
		return new Board(parent, size, cellSize, cells);
	}
}
