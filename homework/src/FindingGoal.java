
// NOTE: X axis and Y axis were considered rotated by -90 degrees by normal Cartesian graph orientation due to GridWorld.class

public class FindingGoal {
	private int actualStep;
	private int minimumStep;
	private int X_robotPosition;
	private int Y_robotPosition;
	private int X_freeCellsCoordinates[];
	private int Y_freeCellsCoordinates[];
	private GridWorld grid;
	
	public FindingGoal(GridWorld grid) {
		this.actualStep = 0;
		this.grid = grid;
		this.minimumStep = grid.getMinimumDistanceToTarget();
		X_freeCellsCoordinates = new int[3];
		Y_freeCellsCoordinates = new int[3];
		
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
		for (int i = 0; i < X_freeCellsCoordinates.length; i++) {
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
				/*else if (j != (Y_freeCellsCoordinates.length - 1) && X_freeCellsCoordinates[j] > X_freeCellsCoordinates[j+1]) {
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
	}
	
	
	public void searchGoal() {
		
		GetAndConvertCurrentRobotCoordinates();
		
		// Shows minimum step needed for reach target
		System.out.println("Minimum step needed: "+minimumStep);
		
		// Print initial robot position
		System.out.print("("+X_robotPosition+",");
		System.out.print(" "+Y_robotPosition+"), ");
		
		// 
		
discover:	while (grid.targetReached() == false) {
				GetAndConvertCurrentRobotCoordinates();
				GetAndConvertAdjacentFreeCells();
				GiveChoicePriorities();
			
				
		SOUTH:	if (X_freeCellsCoordinates[0] > X_robotPosition) {
					grid.moveToAdjacentCell(GridWorld.Direction.SOUTH);
					GetAndConvertCurrentRobotCoordinates();
					GetAndConvertAdjacentFreeCells();
					GiveChoicePriorities();
					System.out.print("("+X_robotPosition+",");
					System.out.print(" "+Y_robotPosition+"), ");
					actualStep++;
					break SOUTH;
				}
				
		 EAST:	 if (Y_freeCellsCoordinates[0] > Y_robotPosition) {
					grid.moveToAdjacentCell(GridWorld.Direction.EAST);
					GetAndConvertCurrentRobotCoordinates();
					GetAndConvertAdjacentFreeCells(); 
					GiveChoicePriorities();
					System.out.print("("+X_robotPosition+",");
					System.out.print(" "+Y_robotPosition+"), ");
					actualStep++;
					if (X_freeCellsCoordinates[0] > X_robotPosition) {
						continue discover;
					}
						else if (Y_freeCellsCoordinates[0] > Y_robotPosition) {
							break EAST;
						}
				}
				
		 else if (X_freeCellsCoordinates[1] < X_robotPosition) {
					grid.moveToAdjacentCell(GridWorld.Direction.NORTH);
					GetAndConvertCurrentRobotCoordinates();
					GetAndConvertAdjacentFreeCells();
					GiveChoicePriorities();
					System.out.print("("+X_robotPosition+",");
					System.out.print(" "+Y_robotPosition+"), ");
					actualStep++;
					break discover;
					}
				
		 else if (Y_freeCellsCoordinates[1] < Y_robotPosition) {
					grid.moveToAdjacentCell(GridWorld.Direction.WEST);
					GetAndConvertCurrentRobotCoordinates();
					GetAndConvertAdjacentFreeCells();
					GiveChoicePriorities();
					System.out.print("("+X_robotPosition+",");
					System.out.print(" "+Y_robotPosition+"), ");
					actualStep++;
					break discover;
					}
				for(int k = 0; k < 10000; k++) {
				System.out.print("("+X_robotPosition+",");
				System.out.print(" "+Y_robotPosition+"), ");
				}
				/*for (int j = 0; j < X_freeCellsCoordinates.length; ) {	
					System.out.println("TEST");
					if (X_freeCellsCoordinates[j] < X_robotPosition) {
						grid.moveToAdjacentCell(GridWorld.Direction.NORTH);
						GetAndConvertCurrentRobotCoordinates();
						GetAndConvertAdjacentFreeCells();
						GiveChoicePriorities();
						System.out.print("("+X_robotPosition+",");
						System.out.print(" "+Y_robotPosition+"), ");
						actualStep++;
					}
					j++;
						//continue discover;
				}
				
				/*for (int j = 0; j < X_freeCellsCoordinates.length; j++) {	
					if (X_freeCellsCoordinates[j] < X_robotPosition) {
						grid.moveToAdjacentCell(GridWorld.Direction.WEST);
						GetAndConvertCurrentRobotCoordinates();
						GetAndConvertAdjacentFreeCells();
						GiveChoicePriorities();
						System.out.print("("+X_robotPosition+",");
						System.out.print(" "+Y_robotPosition+"), ");
						actualStep++;
						continue discover;
					}
				}/*
						
						/*for (int i = 0;i < X_freeCellsCoordinates.length && X_freeCellsCoordinates[i] < X_robotPosition; i++) {
							grid.moveToAdjacentCell(GridWorld.Direction.NORTH);
							System.out.print("("+X_robotPosition+",");
							System.out.print(" "+Y_robotPosition+"), ");
						}
						//continue discover;
					}
				
				
				if (Y_freeCellsCoordinates[0] < Y_robotPosition) {
					grid.moveToAdjacentCell(GridWorld.Direction.WEST);
					GetAndConvertCurrentRobotCoordinates();
					GetAndConvertAdjacentFreeCells();
					GiveChoicePriorities();
					System.out.print("("+X_robotPosition+",");
					System.out.print(" "+Y_robotPosition+"), ");
					actualStep++;
					/*for (int i = 0; i < X_freeCellsCoordinates.length && Y_freeCellsCoordinates[i] < Y_robotPosition; i++) {
						grid.moveToAdjacentCell(GridWorld.Direction.WEST);
						System.out.print("("+X_robotPosition+",");
						System.out.print(" "+Y_robotPosition+"), ");
					}
					continue discover;
				}
				*/
		}
		
		System.out.println("Step made: "+actualStep);
		System.out.println("Target reached: "+grid.targetReached());
	}
		
}
