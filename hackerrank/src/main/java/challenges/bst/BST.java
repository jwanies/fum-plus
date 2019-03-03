package challenges.bst;

public class BST {

	private BSTNode root = null;
	
	public BST(BSTNode root) {
		this.root = root;
	}
	
	public BSTNode getRoot() {
		return root;
	}

	public void setRoot(BSTNode root) {
		this.root = root;
	}
	
	public void insert(BSTNode nodeToInsert) {
		this.insert(nodeToInsert, root);
	}
	
	private void insert(BSTNode nodeToInsert, BSTNode root) {
		
		if (root == null) {
			root = nodeToInsert;
			return;
		}

		if (nodeToInsert.getValue() < root.getValue()) {
			if (root.getLeftNode() == null)
				root.setLeftNode(nodeToInsert);
			else
				insert(nodeToInsert, root.getLeftNode());
		} else {
			
			if (root.getRightNode() == null)
				root.setRightNode(nodeToInsert);
			else
				insert(nodeToInsert, root.getRightNode());
		}
		
	}
	
	public BSTNode get(int valueToGet) {
		return this.get(valueToGet, root);
	}
	
	private BSTNode get(int valueToGet, BSTNode root) {
		
		if(root != null) {
			
			if (root.getValue() == valueToGet)
				return root;
			else if (valueToGet < root.getValue())
				return get(valueToGet, root.getLeftNode());
			else if (valueToGet > root.getValue())
				return get(valueToGet, root.getRightNode());
			
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return toStringHelper(this.root);
	}
	
	private String toStringHelper(BSTNode root) {
		
		if (root == null)
			return " ";
		else {
			return toStringHelper(root.getLeftNode()) + root + toStringHelper(root.getRightNode());
		}
	}
	
}
