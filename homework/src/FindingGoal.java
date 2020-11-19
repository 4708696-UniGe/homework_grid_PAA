import java.util.*;
// NOTE: X axis and Y axis were considered rotated by -90 degrees by normal Cartesian graph orientation due to GridWorld.class

public class FindingGoal {
	private int actualStep;
	private int minimumStep;
	private int X_robotPosition;
	private int Y_robotPosition;
	private int X_freeCellsCoordinates[];
	private int Y_freeCellsCoordinates[];
	private GridWorld grid;
	private boolean SOUTH;
	private boolean EAST;
	private boolean NORTH;
	private boolean WEST;
	private boolean IMPASSE;
	private boolean EXCLUDE;
	private String prevDirection;
	
	public FindingGoal(GridWorld grid) {
		this.actualStep = 0;
		this.grid = grid;
		this.minimumStep = grid.getMinimumDistanceToTarget();
		X_freeCellsCoordinates = new int[4];
		Y_freeCellsCoordinates = new int[4];
		
	}
	
	// Request robot current position and convert it in two integer values, one for X coordinate and one for Y coordinate
	public void GetAndConvertCurrentRobotCoordinates() {
		//String conversion = grid.getCurrentCell().toString();
		//String[] parts = conversion.split("(|,|)");
		X_robotPosition = grid.getCurrentCell().row;
		Y_robotPosition = grid.getCurrentCell().col;
	}
	
	// Request adjacent free cell coordinates and convert in two integer arrays with same length, one for X coordinates and one for Y coordinates
	// Example:	(#coordinate)		  	first	second	third	fourth
	// 			(X coordinate)		X	  2		  1		  0		  1
	// 			(Y coordinate)		Y	  2		  1		  2		  3
	// Adjacent free cells : [(2,2)] , (1,1) , (0,2) , (1,3)]
	public void GetAndConvertAdjacentFreeCells() {
		Iterator<GridWorld.Coordinate> conv = grid.getAdjacentFreeCells().iterator();
		for(int i = 0; conv.hasNext(); i++) {
			GridWorld.Coordinate coord = conv.next();
			X_freeCellsCoordinates[i] = coord.row;
			Y_freeCellsCoordinates[i] = coord.col;
			//System.out.println("x.coo"+X_freeCellsCoordinates[i]);
			//System.out.println("x.coo"+Y_freeCellsCoordinates[i]);
		}
	}
	
	// Swap array elements for give priorities
	public void Swap (int[] array, int indexA, int indexB ) {
		int temp;
		temp = array[indexA];
		array[indexA] = array[indexB];
		array[indexB] = temp;
	}
	
	// Sort free cells coordinates array giving priorities to SOUTH and EAST direction
	public void GiveChoicePriorities() {
	sort:	for (int j = 0; j < X_freeCellsCoordinates.length; j++) {
				if (j != (X_freeCellsCoordinates.length - 1) && X_freeCellsCoordinates[j] < X_freeCellsCoordinates[j+1]) {
					Swap(X_freeCellsCoordinates, j, j+1);
					Swap(Y_freeCellsCoordinates, j, j+1);
					continue sort;
				}
				else if (j != (Y_freeCellsCoordinates.length - 1) && Y_freeCellsCoordinates[j] < Y_freeCellsCoordinates[j+1]) {
					Swap(X_freeCellsCoordinates, j, j+1);
					Swap(Y_freeCellsCoordinates, j, j+1); 
					continue sort;
				}
				/*else if (j != (X_freeCellsCoordinates.length - 1) && X_freeCellsCoordinates[j] > X_freeCellsCoordinates[j+1]) {
					Swap(X_freeCellsCoordinates, j, j+1);
					Swap(Y_freeCellsCoordinates, j, j+1); 
					continue sort;
				}
				else if (j != (Y_freeCellsCoordinates.length - 1) && Y_freeCellsCoordinates[j] > Y_freeCellsCoordinates[j+1]) {
					Swap(X_freeCellsCoordinates, j, j+1);
					Swap(Y_freeCellsCoordinates, j, j+1); 
					continue sort;
				}*/
				//System.out.println("x.coo"+X_freeCellsCoordinates[j]);
				//System.out.println("x.coo"+Y_freeCellsCoordinates[j]);
			}
		/*for (int j = 0; j < X_freeCellsCoordinates.length; j++) {
			System.out.println("x.coo"+X_freeCellsCoordinates[j]);
			System.out.println("x.coo"+Y_freeCellsCoordinates[j]);
		}*/
	}
	
	// Scan free adjacent free cells and select the best direction 
	public void ChoiceDirection() {
		for (int j = 0; j < X_freeCellsCoordinates.length; j++) {
			if (X_freeCellsCoordinates[j] > X_robotPosition && (prevDirection != "NORTH" || (IMPASSE == true && EXCLUDE == false))) {
				SOUTH = true;
				EAST = false;
				NORTH = false;
				WEST = false;
				break;
			}
			else if (Y_freeCellsCoordinates[j] > Y_robotPosition && (prevDirection != "WEST" || IMPASSE == true && EXCLUDE == false)) {
				SOUTH = false;
				EAST = true;
				NORTH = false;
				WEST = false;
				break;
			}
			else if (X_freeCellsCoordinates[j] < X_robotPosition && (prevDirection != "SOUTH" || IMPASSE == true && EXCLUDE == false)) {
				SOUTH = false;
				EAST = false;
				NORTH = true;
				WEST = false;
				break;
			}
			else if (Y_freeCellsCoordinates[j] < Y_robotPosition && (prevDirection != "EAST" || IMPASSE == true && EXCLUDE == false)) {
				SOUTH = false;
				EAST = false;
				NORTH = false;
				WEST = true; 
				break;
			}
		}
	}
	
	// Report impasse if the only free adjacent cell is where it came from and call method GoBack
	public void DetectImpasse() {
		IMPASSE = false;
		EXCLUDE = false;
		GiveChoicePriorities();
		if (prevDirection == "SOUTH") {
			for (int j = 0; j < X_freeCellsCoordinates.length; j++) {
				if (X_freeCellsCoordinates[j] > X_robotPosition) {
					break;
				}
				else if (Y_freeCellsCoordinates[j] > Y_robotPosition) {
					break;
				}
				else if (Y_freeCellsCoordinates[j] < Y_robotPosition) {
					break;
				}
				IMPASSE = true;
			}
		}
		if (prevDirection == "EAST") {
			for (int j = 0; j < X_freeCellsCoordinates.length; j++) {
				if (X_freeCellsCoordinates[j] > X_robotPosition) {
					break;
				}
				else if (Y_freeCellsCoordinates[j] > Y_robotPosition) {
					break;
				}
				for (int i = 0; i < X_freeCellsCoordinates.length; i++) {
					System.out.println("x.coo"+X_freeCellsCoordinates[i]);
					System.out.println("x.coo"+Y_freeCellsCoordinates[i]);
				}
				if (X_freeCellsCoordinates[j] < X_robotPosition) {
					break;
				}
				IMPASSE = true;
			}
		}
		if (prevDirection == "NORTH") {
			for (int j = 0; j < X_freeCellsCoordinates.length; j++) {
				if (Y_freeCellsCoordinates[j] > Y_robotPosition) {
					break;
				}
				else if (X_freeCellsCoordinates[j] < X_robotPosition) {
					break;
				}
				else if (Y_freeCellsCoordinates[j] < Y_robotPosition) {
					break;
				}
				IMPASSE = true;
			}
		}
		if (prevDirection == "WEST") {
			for (int j = 0; j < X_freeCellsCoordinates.length; j++) {
				if (X_freeCellsCoordinates[j] > X_robotPosition) {
					break;
				}
				else if (X_freeCellsCoordinates[j] < X_robotPosition) {
					break;
				}
				else if (Y_freeCellsCoordinates[j] < Y_robotPosition) {
					break;
				}
				IMPASSE = true;
			}
		}
	}
	
	// Go back after impasse detection
	public void GoBack() {
		if (prevDirection == "SOUTH") {
			grid.moveToAdjacentCell(GridWorld.Direction.NORTH);
			NORTH = true;
			EXCLUDE = true;
		}
		if (prevDirection == "EAST") {
			grid.moveToAdjacentCell(GridWorld.Direction.WEST);
			WEST = true;
			EXCLUDE = true;
		}
		if (prevDirection == "NORD") {
			grid.moveToAdjacentCell(GridWorld.Direction.SOUTH);
			SOUTH = true;
			EXCLUDE = true;
		}
		if (prevDirection == "WEST") {
			grid.moveToAdjacentCell(GridWorld.Direction.EAST);
			EAST = true;
			EXCLUDE = true;
		}
	}
	
	
	public void searchGoal() {
		
		// Shows minimum step needed for reach target
		System.out.println("Minimum step needed: "+minimumStep);
		
		// Algorithm for finding target
discover:	while (grid.targetReached() == false) {
				
				GetAndConvertCurrentRobotCoordinates();
				GetAndConvertAdjacentFreeCells();
				GiveChoicePriorities();
				ChoiceDirection();
				DetectImpasse();
				
				System.out.print("("+X_robotPosition+",");
				System.out.print(" "+Y_robotPosition+")");
				System.out.print(" ");
			
				if (IMPASSE == true) {
					GoBack();
					GetAndConvertCurrentRobotCoordinates();
					System.out.print("("+X_robotPosition+",");
					System.out.print(" "+Y_robotPosition+")");
					System.out.print(" ");
					actualStep++;
					//continue discover;
					break;
				}
				if (SOUTH == true) {
					prevDirection = "SOUTH";
					grid.moveToAdjacentCell(GridWorld.Direction.SOUTH);
					actualStep++;
					continue discover;
					//break;
				}
				
				else if (EAST == true) {
					prevDirection = "EAST";
					grid.moveToAdjacentCell(GridWorld.Direction.EAST);
					actualStep++;
					continue discover;
					//break;
				}
				
				else if (NORTH == true) {
					prevDirection = "NORTH";
					grid.moveToAdjacentCell(GridWorld.Direction.NORTH);
					actualStep++;
					continue discover;
					//break;
				}
				
				else if (WEST == true) {
					prevDirection = "WEST";
					grid.moveToAdjacentCell(GridWorld.Direction.WEST);
					actualStep++;
					break;
				}
				
				
		}
		System.out.println("");
		System.out.println("Step made: "+actualStep);
		System.out.println("Target reached: "+grid.targetReached());
	}
		
}
