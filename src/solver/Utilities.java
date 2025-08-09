package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;


/**
 * This class stores the location for all map elements, this also implements the utility functions for testing of goal and deadlock states.
 * @author Airooon
 *
 */
public class Utilities {
	
	SokobanState initial;
	HashMap<Coordinate, Coordinate> blockedLocation;
	HashSet<Coordinate> wallLocation;
	HashSet<Coordinate> goalLocation;
	
	/*
	 * Constructor for Utilities class
	 */
	public Utilities(HashSet<Coordinate> wallLocation, SokobanState initial, HashSet<Coordinate> goalLocation) {
		this.wallLocation = wallLocation;
		this.initial = initial;
		this.goalLocation = goalLocation;
	}
	
	/*
	 * Helper function that check whether the current box locations are all situated in the goal positions
	 */
	public boolean isGoal(SokobanState state) {
		//int count = 0;
		for(Coordinate box : state.boxLocation) {
			if(!goalLocation.contains(box)) {
				//count++;
				return false;
			}
		}

		
		return true; //false; //(count == goalLocation.size());
	}
	
	
	/*
	 * Helper function that returns the available actions for the player
	 */
	public ArrayList<String> playerActions(SokobanState sokobanState){
		ArrayList<String> actions = new ArrayList<>();
		int x = sokobanState.playerLocation.x;
		int y = sokobanState.playerLocation.y;
		HashSet<Coordinate> boxLocation = sokobanState.boxLocation;
		
		//Create new instance of player and box locations for up direction
		Coordinate upPlayer = new Coordinate(x-1, y);
		Coordinate upBox = new Coordinate(x-2, y);
		if(!wallLocation.contains(upPlayer)) {
			if(!(boxLocation.contains(upPlayer) && (boxLocation.contains(upBox) || wallLocation.contains(upBox)))) {
				actions.add("u");
			}
			
		}
		
		//Create new instance of player and box locations for down direction
		Coordinate downPlayer = new Coordinate(x+1, y);
		Coordinate downBox = new Coordinate(x+2, y);
		if(!wallLocation.contains(downPlayer)) {
			if(!(boxLocation.contains(downPlayer) && (boxLocation.contains(downBox) || wallLocation.contains(downBox)))) {
				actions.add("d");
			}
		
		}
		
		//Create new instance of player and box locations for left direction
		Coordinate leftPlayer = new Coordinate(x, y-1);
		Coordinate leftBox = new Coordinate(x, y-2);
		if(!wallLocation.contains(leftPlayer)) {
			if(!(boxLocation.contains(leftPlayer) && (boxLocation.contains(leftBox) || wallLocation.contains(leftBox)))) {
				actions.add("l");
			}
			
		}
		
		//Create new instance of player and box locations for left direction
		Coordinate rightPlayer = new Coordinate(x, y+1);
		Coordinate rightBox = new Coordinate(x, y+2);
		if(!wallLocation.contains(rightPlayer)) {
			if(!(boxLocation.contains(rightPlayer) && (boxLocation.contains(rightBox) || wallLocation.contains(rightBox)))) {
				actions.add("r");
			}
		
		}
	
	
		return actions;
	}
	
	
	public boolean isDeadlock(SokobanState sokobanState) {
		for(Coordinate box : sokobanState.boxLocation) {
			
			int x = box.x;
			int y = box.y;
			
			if(!coordinatePointsTo(goalLocation, x, y)) {
				
				// CORNER DEADLOCK
				if(coordinatePointsTo(wallLocation, x-1, y) && coordinatePointsTo(wallLocation, x, y-1)) {
					//checking for top and left sides
					return true;
				}
				if(coordinatePointsTo(wallLocation, x-1, y) && coordinatePointsTo(wallLocation, x, y+1)) {
					//checking for top and right sides
					return true;
				}
				if(coordinatePointsTo(wallLocation, x+1, y) && coordinatePointsTo(wallLocation, x, y-1)) {
					//checking for bottom and left sides
					return true;
				}
				if(coordinatePointsTo(wallLocation, x+1, y) && coordinatePointsTo(wallLocation, x, y+1)) {
					//checking for bottom and right sides
					return true;
				}
				
				//FREEZE DEADLOCK
				
				if(coordinatePointsTo(wallLocation, x-1, y-1) && coordinatePointsTo(wallLocation, x-1, y) &&
						coordinatePointsTo(wallLocation, x-1, y+1) && coordinatePointsTo(wallLocation, x, y-2) &&
						coordinatePointsTo(wallLocation, x, y+2) && (!coordinatePointsTo(goalLocation, x, y-1)) &&
								(!coordinatePointsTo(goalLocation, x, y+1))) {
					//checking for the top side its adjacent spaces
					return true;
				}
				if(coordinatePointsTo(wallLocation, x+1, y-1) && coordinatePointsTo(wallLocation, x+1, y) &&
						coordinatePointsTo(wallLocation, x+1, y+1) && coordinatePointsTo(wallLocation, x, y-2) &&
						coordinatePointsTo(wallLocation, x, y+2) && (!coordinatePointsTo(goalLocation, x, y-1)) &&
								(!coordinatePointsTo(goalLocation, x, y+1))) {
					//checking for the bottom side its adjacent spaces
					return true;
				}
				if(coordinatePointsTo(wallLocation, x-1, y-1) && coordinatePointsTo(wallLocation, x, y-1) &&
						coordinatePointsTo(wallLocation, x+1, y-1) && coordinatePointsTo(wallLocation, x-2, y) &&
						coordinatePointsTo(wallLocation, x+2, y) && (!coordinatePointsTo(goalLocation, x-1, y)) &&
								(!coordinatePointsTo(goalLocation, x+1, y))) {
					//checking for the left side and its adjacent vertical space
					return true;
				}
				if(coordinatePointsTo(wallLocation, x-1, y+1) && coordinatePointsTo(wallLocation, x, y+1) &&
						coordinatePointsTo(wallLocation, x+1, y+1) && coordinatePointsTo(wallLocation, x-2, y) &&
						coordinatePointsTo(wallLocation, x+2, y) && (!coordinatePointsTo(goalLocation, x-1, y)) &&
								(!coordinatePointsTo(goalLocation, x+1, y))) {
					//checking for the right side and adjacent spaces
					return true;
				}
				
			}//end of outer if-statement
		} // end of for-loop
		
		return false; //no deadlock
	}
	
	
	/*
	 * Helper function that checks if a map element is present within the given coordinate;
	 */
	private boolean coordinatePointsTo(HashSet<Coordinate> set, int x, int y) {
		return(set.contains(new Coordinate(x, y)));
		
	}
	
	
}

