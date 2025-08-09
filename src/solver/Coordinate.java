package solver;

/**
 * This class stores the abscissa and ordinate values for boxes, player, walls, and goals location
 * @author Airooon
 *
 */
public class Coordinate {
	
	public int x;
	public int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * This returns the hashcode for each coordinates, this ensures that each coordinate is unique
	 */
	@Override
	public int hashCode() {
		int hashConstant = 1000;
		return x * hashConstant + y;
	}
	
	@Override
	public boolean equals(Object o) {
		
		Coordinate coordinate = (Coordinate) o;
		
		if(o == null) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		if(this.getClass() != o.getClass()) {
			return false;
		}
		
		if(this.hashCode() == coordinate.hashCode()) {
			return true;
		}
		
		
		return ((this.x == coordinate.x) && (this.y == coordinate.y));
	}
}
