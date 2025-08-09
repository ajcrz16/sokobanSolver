package solver;

/**
 * This class stores a Sokoban state, its parent node, the current state's cost, and the move that leads to this state
 * @author Airooon
 *
 */
public class SokobanNode {
	
	public SokobanState sokobanState;
	public SokobanNode parent;
	public int cost;
	public String move;
	
	
	public SokobanNode(SokobanState state, SokobanNode parent, int cost, String move) {
		sokobanState = state;
		this.parent = parent;
		this.cost = cost;
		this.move = move;
	}
	
	@Override
	public boolean equals(Object o) {
		return(this.sokobanState == ((SokobanNode) o).sokobanState);
	}
}
