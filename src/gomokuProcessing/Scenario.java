package gomokuProcessing;

import java.util.Arrays;

public class Scenario {
	private int points;
	private Stone[] sequence;

	public Scenario(Stone[] sequence, int points) {
		this.points = points;
		this.sequence = sequence;
	}

	public int checkArray(Stone[] toCheck) {
		if (Arrays.equals(this.sequence, toCheck)) {
			return this.points;
		} else {
			return 0;
		}
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Stone[] getSequence() {
		return sequence;
	}

	public void setSequence(Stone[] sequence) {
		this.sequence = sequence;
	}
}
