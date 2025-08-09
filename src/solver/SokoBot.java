package solver;

import java.util.HashSet;

/**
 * This class collates and assembles all the classes within the 'solver' package to create the solution for the puzzle
 * @author Airooon
 *
 */
public class SokoBot {

private HashSet<Coordinate> boxLocation = new HashSet<>();
private HashSet<Coordinate> goalLocation = new HashSet<>();
private HashSet<Coordinate> wallLocation = new HashSet<>();
private Coordinate playerLocation;
private HeuristicFunction heurFunc;
private Utilities util;
private SearchTreeSimulator aStar;


  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
	
	for(int i=0; i<mapData.length; i++) {
		for(int j =0 ; j<mapData[i].length; j++) {
			if(mapData[i][j] == '#') {//add instance of wall
				wallLocation.add(new Coordinate(i,j));
			}
			if(mapData[i][j] == '.') {//add instance of goal
				goalLocation.add(new Coordinate(i,j));
			}
			if(itemsData[i][j] == '$') {//add instance of box
				boxLocation.add(new Coordinate(i,j));
			}
			if(itemsData[i][j] == '@') {//add initial player location
				playerLocation = new Coordinate(i,j);
			}
		}
	}
	
	util = new Utilities(wallLocation, new SokobanState(boxLocation, playerLocation), goalLocation);
	heurFunc = new HeuristicFunction(goalLocation);
	aStar = new SearchTreeSimulator(heurFunc);
	String solution = aStar.aStarSearch(util);
	System.out.println(solution); //sanity check
    return solution;
  }
}
  
  


