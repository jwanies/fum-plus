package challenges.bst;

public class Test {

	public static void main(String[] args) {
		searchBST();
	}
	
	public static void searchBST() {
        
		BST tree = new BST(new BSTNode(7));
		tree.insert(new BSTNode(2));
		tree.insert(new BSTNode(4));
		tree.insert(new BSTNode(9));
		tree.insert(new BSTNode(1));
		tree.insert(new BSTNode(8));
		tree.insert(new BSTNode(10));
		System.out.println(tree.toString());
		System.out.println(tree.get(4));
		System.out.println(tree.get(3));
    }
	
}
