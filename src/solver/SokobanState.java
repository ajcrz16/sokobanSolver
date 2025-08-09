package solver;

import java.util.HashSet;
/**
 * This class stores the current positions of boxes and player 
 * @author Airooon
 *
 */
public class SokobanState {
	
	HashSet<Coordinate> boxLocation;
	Coordinate playerLocation;
	
	/*
	 * Constructor for SokobanState
	 */
	public SokobanState(HashSet<Coordinate> boxLocation, Coordinate playerLocation) {
		this.boxLocation = boxLocation;
		this.playerLocation = playerLocation;
	}
		
	
	/*
	 * This checks the state if it is already visited/explored
	 */
	 @Override
	 public int hashCode() {
	    //int prime = 31;
	    int result = 17;
	    for(Coordinate box : boxLocation) {
	    	result = 31 * result + box.hashCode();
	    }
	    result = 31 * result + playerLocation.hashCode();
	    return result;
	  }
	 
	 
	 @Override
	 public boolean equals(Object o) {
		 
		 SokobanState state = (SokobanState)o;
		 if(o == null) {
			 return false;
		 }
		 
		 if(o == this) {
			 return true;
		 }
		 
		 if(this.getClass() != o.getClass()) {
			 return false;
		 }
		 
		 
		 if(this.hashCode() == state.hashCode()) {
			 return true;
		 }
		 
		 if((this.boxLocation == state.boxLocation) && (this.playerLocation == state.playerLocation)) {
			 return true;
		 }
		 
		 return false;
	 }
	
}
