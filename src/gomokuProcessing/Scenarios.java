package gomokuProcessing;

import java.util.ArrayList;

public class Scenarios {
	private int allyPoints = 2;
	private int enemyPoints = -2;
	private ArrayList<Scenario> scenarios = new ArrayList<Scenario>();

	public Scenarios(Stone stoneType) {
		this.arrangeScenarios(stoneType, allyPoints);
	}

	public Scenarios(Stone stoneType, Stone enemyType) {
		this.arrangeScenarios(stoneType, allyPoints);
		this.arrangeScenarios(enemyType, enemyPoints);
	}

	private void arrangeScenarios(Stone stoneType, double pointsBase) {
		// -----------2--------------
		scenarios.add(new Scenario(new Stone[] { Stone.NONE, Stone.NONE, Stone.NONE, stoneType, stoneType },
				(int) Math.pow(pointsBase, 1)));
		scenarios.add(new Scenario(new Stone[] { stoneType, stoneType, Stone.NONE, Stone.NONE, Stone.NONE },
				(int) Math.pow(pointsBase, 1)));
		scenarios.add(new Scenario(new Stone[] { stoneType, Stone.NONE, Stone.NONE, Stone.NONE, stoneType },
				(int) Math.pow(pointsBase, 1)));
		// -----------32--------------
		scenarios.add(new Scenario(new Stone[] { stoneType, stoneType, stoneType, Stone.NONE, Stone.NONE },
				(int) Math.pow(pointsBase, 5)));
		scenarios.add(new Scenario(new Stone[] { stoneType, stoneType, Stone.NONE, Stone.NONE, stoneType },
				(int) Math.pow(pointsBase, 5)));
		scenarios.add(new Scenario(new Stone[] { stoneType, Stone.NONE, Stone.NONE, stoneType, stoneType },
				(int) Math.pow(pointsBase, 5)));
		scenarios.add(new Scenario(new Stone[] { Stone.NONE, Stone.NONE, stoneType, stoneType, stoneType },
				(int) Math.pow(pointsBase, 5)));
		scenarios.add(new Scenario(new Stone[] { Stone.NONE, stoneType, stoneType, stoneType, Stone.NONE },
				(int) Math.pow(pointsBase, 5)));
		// -----------128--------------
		scenarios.add(new Scenario(new Stone[] { stoneType, stoneType, stoneType, stoneType, Stone.NONE },
				(int) Math.pow(pointsBase, 7)));
		scenarios.add(new Scenario(new Stone[] { stoneType, stoneType, stoneType, Stone.NONE, stoneType },
				(int) Math.pow(pointsBase, 7)));
		scenarios.add(new Scenario(new Stone[] { stoneType, stoneType, Stone.NONE, stoneType, stoneType },
				(int) Math.pow(pointsBase, 7)));
		scenarios.add(new Scenario(new Stone[] { stoneType, Stone.NONE, stoneType, stoneType, stoneType },
				(int) Math.pow(pointsBase, 7)));
		scenarios.add(new Scenario(new Stone[] { Stone.NONE, stoneType, stoneType, stoneType, stoneType },
				(int) Math.pow(pointsBase, 7)));
		// -----------2048--------------
		scenarios.add(new Scenario(new Stone[] { stoneType, stoneType, stoneType, stoneType, stoneType },
				(int) Math.pow(pointsBase, 11)));
	}

	public int checkArray(Stone[] toCheck) {
		int sum = 0;
		for (Scenario scenario : scenarios) {
			sum += scenario.checkArray(toCheck);
		}
		return sum;
	}

}
