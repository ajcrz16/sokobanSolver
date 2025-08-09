package solver;


import java.util.PriorityQueue;
import java.util.Comparator;
//import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
//import java.util.Stack;


/**
 * This class implements the A-star search algorithm to solve the sokoban puzzle
 * @author Airooon
 *
 */

public class SearchTreeSimulator {
	
	private static HeuristicFunction heuristic;
	
	public SearchTreeSimulator(HeuristicFunction hf) {
		heuristic = hf;
	}
	
	/*
	 * This function is responsible for the A-star search implementation
	 */
	public String aStarSearch(Utilities util) {
		SokobanNode initialNode = new SokobanNode(util.initial, null, 0, "");
		Set<SokobanState> exploredNodes = new HashSet<>();
		PriorityQueue<SokobanNode> frontier = new PriorityQueue<>(11, astar_comparator);
		int traversal = 0;
		//add the initial node to the frontier 
		frontier.add(initialNode);
		
		//Continue until all the nodes within the frontier are visited/explored
		while(!frontier.isEmpty()) {
			
			//retrieve the head of the queue
			SokobanNode currentNode = frontier.remove();
			
			//Check if the current node's state leads to a goal state
			if(util.isGoal(currentNode.sokobanState)) {
				return solution(currentNode);
			}
			
			//Check for any instances of deadlock for the current node state
			if(!util.isDeadlock(currentNode.sokobanState)) {
				
				//add to the explored sets
				exploredNodes.add(currentNode.sokobanState);
				
				//Get the possible or valid player actions given the current state of the node
				ArrayList<String> playerActions = util.playerActions(currentNode.sokobanState);
				
				//iterate through the valid moves
				for(int i=0; i<playerActions.size(); i++) {
					
					//create an instance of child node
					SokobanNode childNode = getChildNode(util, currentNode, playerActions.get(i));
					
					if((childNode != null) && (childNode.sokobanState != null)) {
						//check for the existence of child node 
						if((!exploredNodes.contains(childNode.sokobanState)) && (!frontier.contains(childNode))) {
							frontier.add(childNode);
						}
						else { //redundant node
							//Compare the redundant node with its previous cost, if the recent cost is lower, update its cost
							for(SokobanNode nextNode : frontier) {
								if(nextNode == childNode) {
									if(childNode.cost < nextNode.cost) {
										nextNode = childNode; //update the node
									}
								}
							}
						}
					}
				}
			}
			traversal++;
		}//end of while loop
		//return solution(null); // no solution
		System.out.println(traversal);
		return solution(null);
	}
	
	/**
	 * This function constructs the solution path of the puzzle 
	 * @param sokobanNode
	 * @return
	 */
	private String solution(SokobanNode sokobanNode) {
		String solutionPath = "";
		
		if(sokobanNode != null) {
			while(sokobanNode.parent != null) {
				solutionPath = sokobanNode.move + solutionPath;
				sokobanNode = sokobanNode.parent;
			}
		}//end of if-statement
		else {
			// if there's no solution
			solutionPath = "[PROGRAM FAILURE]: CANNOT SOLVE THE PUZZLE";
		}
		
		return solutionPath;
	}	
	
	
	private SokobanNode getChildNode(Utilities util, SokobanNode sokobanNode, String playerAction) {
		@SuppressWarnings("unchecked")
		HashSet<Coordinate> boxLocation = (HashSet<Coordinate>) sokobanNode.sokobanState.boxLocation.clone();	
		int x = sokobanNode.sokobanState.playerLocation.x;
		int y = sokobanNode.sokobanState.playerLocation.y;
		int newCost = sokobanNode.cost+1;
		Coordinate newPlayerLocation = sokobanNode.sokobanState.playerLocation;
		char action = playerAction.charAt(0);
		
		switch(action) {
		//upward movement
		case 'u':
			//update the player's location
			newPlayerLocation = new Coordinate(x-1, y);
			
			//Check if there is a box along the way
			if(boxLocation.contains(newPlayerLocation)) {
				Coordinate newBoxLocation = new Coordinate(x-2, y);
				boxLocation.remove(newPlayerLocation); //remove the instance of box's previous coordinate
				boxLocation.add(newBoxLocation); //add the new box location 
			}
			break;
		
		//downward movement
		case 'd':
			//update the player's location
			newPlayerLocation = new Coordinate(x+1, y);
			
			//Check if there is a box along the way
			if(boxLocation.contains(newPlayerLocation)) {
				Coordinate newBoxLocation = new Coordinate(x+2, y);
				boxLocation.remove(newPlayerLocation); //remove the instance of box's previous coordinate
				boxLocation.add(newBoxLocation); //add the new box location 
			}
			break;
		
		//leftward movement
		case 'l':
			//update the player's location
			newPlayerLocation = new Coordinate(x, y-1);
			
			//Check if there is a box along the way
			if(boxLocation.contains(newPlayerLocation)) {
				Coordinate newBoxLocation = new Coordinate(x, y-2);
				boxLocation.remove(newPlayerLocation); //remove the instance of box's previous coordinate
				boxLocation.add(newBoxLocation); //add the new box location 
			}
			break;
			
		//rightward movement
		case 'r':
			//update the player's location
			newPlayerLocation = new Coordinate(x, y+1);
			
			//Check if there is a box along the way
			if(boxLocation.contains(newPlayerLocation)) {
				Coordinate newBoxLocation = new Coordinate(x, y+2);
				boxLocation.remove(newPlayerLocation); //remove the instance of box's previous coordinate
				boxLocation.add(newBoxLocation); //add the new box location 
			}
			break;
		}
		
		return new SokobanNode(new SokobanState(boxLocation, newPlayerLocation), sokobanNode, newCost, Character.toString(action));
	}
	
	
	public static Comparator<SokobanNode> astar_comparator = new Comparator<SokobanNode>() {
		
		
		@Override
		public int compare(SokobanNode node1, SokobanNode node2) {
			return (int) ((node1.cost + heuristic.calculateHeuristic(node1.sokobanState)) - (node2.cost + heuristic.calculateHeuristic(node2.sokobanState)));
		}
	};
}