package challenges.bst;

public class BSTNode {

	private BSTNode leftNode;
	private BSTNode rightNode;
	private int value;
	
	public BSTNode(int value) {
		leftNode = null;
		rightNode = null;
		this.value = value;
	}

	public BSTNode getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(BSTNode leftNode) {
		this.leftNode = leftNode;
	}

	public BSTNode getRightNode() {
		return rightNode;
	}

	public void setRightNode(BSTNode rightNode) {
		this.rightNode = rightNode;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString() {
		return String.valueOf(value);
	}
	
}
