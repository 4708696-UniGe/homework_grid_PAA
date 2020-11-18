
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
	private boolean DEADEND;
	
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
			}
	}
	
	// Scan free adjacent free cells and select the best direction 
	public void ChoiceDirection() {
		for (int j = 0; j < X_freeCellsCoordinates.length; j++) {
			if (X_freeCellsCoordinates[j] > X_robotPosition && NORTH != true) {
				SOUTH = true;
				EAST = false;
				NORTH = false;
				WEST = false;
				break;
			}
			else if (Y_freeCellsCoordinates[j] > Y_robotPosition && WEST != true) {
				SOUTH = false;
				EAST = true;
				NORTH = false;
				WEST = false;
				break;
			}
			else if (X_freeCellsCoordinates[j] < X_robotPosition && SOUTH != true) {
				SOUTH = false;
				EAST = false;
				NORTH = true;
				WEST = false;
				break;
			}
			else if (Y_freeCellsCoordinates[j] < Y_robotPosition && EAST != true) {
				SOUTH = false;
				EAST = false;
				NORTH = false;
				WEST = true; 
				break;
			}
		}
	}
	
	// Report dead ends if the only free adjacent cell is where it came from and call method GoBack
	public void DetectDeadEnds() {
		DEADEND = false;
		if (SOUTH == true) {
			ChoiceDirection();
			if (EAST == false && NORTH == false && WEST == false) {
				DEADEND = true;
			}
		}
		if (EAST == true) {
			ChoiceDirection();
			if (SOUTH == false && NORTH == false && WEST == false) {
				DEADEND = true;
			}
		}
		if (NORTH == true) {
			ChoiceDirection();
			if (SOUTH == false && EAST == false && WEST == false) {
				DEADEND = true;
			}
		}
		if (WEST == true) {
			ChoiceDirection();
			if (SOUTH == false && EAST == false && NORTH == false) {
				DEADEND = true;
			}
		}
		if (DEADEND == true) {
			GoBack();
		}
	}
	
	// Go back after dead ends detection
	public void GoBack() {
		if (SOUTH == true) {
			
		}
		if (EAST == true) {
			
		}
		if (NORTH == true) {
			
		}
		if (WEST == true) {
			
		}
	}
	
	
	public void searchGoal() {
		
		GetAndConvertCurrentRobotCoordinates();
		
		// Shows minimum step needed for reach target
		System.out.println("Minimum step needed: "+minimumStep);
		
		// Print initial robot position
		//System.out.print("("+X_robotPosition+",");
		//System.out.print(" "+Y_robotPosition+"), ");
		
		// 
		
discover:	while (grid.targetReached() == false) {
				GetAndConvertCurrentRobotCoordinates();
				//DetectDeadEnds();
				GetAndConvertAdjacentFreeCells();
				GiveChoicePriorities();
				ChoiceDirection();
				System.out.print("("+X_robotPosition+",");
				System.out.print(" "+Y_robotPosition+")");
				System.out.print(" ");
			
				
				if (SOUTH == true) {
					grid.moveToAdjacentCell(GridWorld.Direction.SOUTH);
					actualStep++;
					continue discover;
					//break;
				}
				
				else if (EAST == true) {
					grid.moveToAdjacentCell(GridWorld.Direction.EAST);
					actualStep++;
					continue discover;
					//break;
				}
				
				else if (NORTH == true) {
					grid.moveToAdjacentCell(GridWorld.Direction.NORTH);
					actualStep++;
					continue discover;
					//break;
				}
				
				else if (WEST == true) {
					grid.moveToAdjacentCell(GridWorld.Direction.WEST);
					actualStep++;
					//continue discover;
					break;
				}
				
				
		}
		System.out.println("");
		System.out.println("Step made: "+actualStep);
		System.out.println("Target reached: "+grid.targetReached());
	}
		
}
