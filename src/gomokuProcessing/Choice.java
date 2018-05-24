package gomokuProcessing;

public class Choice {
	private int value = 0;
	private int x = 0;
	private int y = 0;

	public Choice(int value, int x, int y) {
		this.setValue(value);
		this.setX(x);
		this.setY(y);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
