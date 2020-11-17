
// NOTE: X axis and Y axis were considered rotated by -90 degrees by normal Cartesian graph orientation due to GridWorld.class

public class FindingGoal {
	private int actualStep;
	private int minimumStep;
	private boolean goalFound;
	private int X_robotPosition;
	private int Y_robotPosition;
	private int X_freeCellsCoordinates[];
	private int Y_freeCellsCoordinates[];
	private GridWorld grid;
	
	public FindingGoal(GridWorld grid) {
		this.grid = grid;
		this.minimumStep = grid.getMinimumDistanceToTarget();
		X_freeCellsCoordinates = new int[3];
		Y_freeCellsCoordinates = new int[3];
		
	}
	
	// Request robot current position and convert it in two integer values, one for X coordinate and one for Y coordinate
	public void ConvertCurrentRobotCoordinates() {
		String conversion = grid.getCurrentCell().toString();
		String[] parts = conversion.split("(|,|)");
		X_robotPosition = Integer.parseInt(parts[1]);
		Y_robotPosition = Integer.parseInt(parts[3]);
	}
	
	// Request adjacent free cell coordinates and convert in two integer arrays with same length, one for X coordinates and one for Y coordinates
	// Example:	(#coordinate)		  	first	second	third	fourth
	// 			(X coordinate)		X	  2		  1		  0		  1
	// 			(Y coordinate)		Y	  2		  1		  2		  3
	// Adjacent free cells : [2,2] , [1,1] , [0,2] , [1,3]
	public void ConvertAdjacentFreeCells() {
		String conversion = grid.getAdjacentFreeCells().toString();
		String[] parts = conversion.split("[|(|,|)||]");
		int j = 0;
		for (int i = 1; i < parts.length; i++) {
			if (i == 3 || i == 7 || i == 11) {
				i = i+2;
			}
			if (i >= parts.length) {
				break;
			}
			X_freeCellsCoordinates[j] = Integer.parseInt(parts[i]);
			i++;
			Y_freeCellsCoordinates[j] = Integer.parseInt(parts[i]);
			j++;
			
			if (i == 14) {
				i++;
			}
		}
		
	}
	
	public void searchGoal() {;
		
		goalFound = grid.targetReached();
		
		
		
		ConvertCurrentRobotCoordinates();
		
		//System.out.println(X_robotPosition);
		//System.out.println(Y_robotPosition);

		
		ConvertAdjacentFreeCells();
		
		
		/*for (actualStep = 0 ; grid.targetReached() == false; actualStep++ ) {
			
			String a = grid.getAdjacentFreeCells().toString();
			System.out.print(a);
		}*/
	}

}
