package challenges.streamlinity;

import java.util.ArrayList;
import java.util.List;

public class BSTNode {

	public BSTNode left;
	public BSTNode right;
	public int value;
	public List<Integer> indexes;
	
	public BSTNode(int value, int index) {
		this.value = value;
		indexes = new ArrayList<>();
		indexes.add(index);
	}
	
	public int getValue() {
		return value;
	}
	
	public void addIndex(int index) {
		indexes.add(index);
	}
	
	public List<Integer> getIndexes() {
		return indexes;
	}
	
}
