package solver;

import java.util.HashSet;


/*
 * This class computes the total optimal cost from the current node state to the goal state
 */
public class HeuristicFunction {
	
	//int cost;
	private HashSet<Coordinate> goalLocation;
	
	/*
	 * Constructor for HeuristicFunction class
	 */
	public HeuristicFunction(HashSet<Coordinate> goalLocation) {
		this.goalLocation = goalLocation;
		//this.cost = new int[goalLocation.size()][goalLocation.size()];
	}
	
	/*
	 * Helper function calculates the Manhattan distance between two location paremeters
	 */
	public int computeManhattan(Coordinate loc1, Coordinate loc2) {
		
		return Math.abs(loc1.x - loc2.x) + Math.abs(loc1.y - loc2.y);
	}
	
	public int calculateHeuristic(SokobanState sokobanState) {
		HashSet<Coordinate> boxLocation = sokobanState.boxLocation;
		int total = 0 ;
		
		//Compute the distance from the player location to different boxes' locations
		Coordinate playerLocation = sokobanState.playerLocation;
		int minimumPlayerDistance = computeMinDistance(playerLocation, boxLocation);
		total += minimumPlayerDistance;
		
		//Compute the minimum distance from the goals location to different boxes' locations
		for(Coordinate box: boxLocation) {
			int minimumBoxDistance = computeMinDistance(box, goalLocation);
			total += minimumBoxDistance;
		}
		
		return total;
	}
	
	/*
	 * Helper function that returns the minimum Manhattan distance of a given object's coordinate to each elements within the locations set
	 */
	public int computeMinDistance(Coordinate coordinate1, HashSet<Coordinate> locations) {
		
		int minimumDistance = Integer.MAX_VALUE;
		
		// This part calculates the manhattan distance of each coordinate in the locations set and the object location
		for(Coordinate coordinate2 : locations) {
			int distance = computeManhattan(coordinate1, coordinate2);
			
			if(distance < minimumDistance) {
				minimumDistance = distance;
			}
		}
		
		return minimumDistance;
	}
	
	/*
	public int getHeuristic(SokobanState sokobanState) {
		
		int i = 0;
		
		for(Coordinate box : sokobanState.boxLocation) {
			int j = 0;
			int cost = computeManhattan(sokobanState.playerLocation, box);
			for (Coordinate goal : goalLocation) {
				cost[i][j] = manhattan(box, goal);
				cost[i][j] += cost;
				j++;
			}
			
			i++;
		}//end of for loop
		
		return cost;
	}*/
}


