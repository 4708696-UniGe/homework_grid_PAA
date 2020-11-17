
public class Main {

	
	public static void main(String[] args) {

		// Require 3 arguments otherwise exit
		if (args.length != 3) {
			 System.exit(0);
		 }
		
		// Assuming parameters are correct and given in the correct order
		int size = Integer.parseInt(args[0]);
		double density = Double.parseDouble(args[1]);
		long seed = Long.parseLong(args[2]);
		
		// Create new world named "grid"
		GridWorld grid = new GridWorld (size, density, seed);
		
		// Print world for debug
		grid.print();
		
		FindingGoal hunt = new FindingGoal(grid);
		hunt.searchGoal();
		
		/*System.out.println(grid.getMinimumDistanceToTarget());
		
		System.out.println(grid.getAdjacentFreeCells());
		
		System.out.println(grid.getCurrentCell());
		

		

		grid.moveToAdjacentCell(GridWorld.Direction.EAST);
		
		System.out.println(grid.getCurrentCell());
		grid.moveToAdjacentCell(GridWorld.Direction.SOUTH);
		grid.moveToAdjacentCell(GridWorld.Direction.EAST);
		System.out.println(grid.getAdjacentFreeCells());
		*/
	}

}
