package challenges.streamlinity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BST that accepts duplicates and returns indexes rather than value when searched
 * 
 * @author John
 *
 */
public class BST {
	
	private static List<Integer> emptyList = new ArrayList<Integer>(Arrays.asList(-1));
	
	private BSTNode root = null;

	public BST(int[] numberArray) {
		
		root = new BSTNode(numberArray[0], 0);
		
		for (int i = 1; i < numberArray.length; i++) {
			insertIntoBst(i, numberArray[i], root);
		}
	}
	
	private void insertIntoBst(int index, int value, BSTNode root) {
		
		if (root == null) {
			
			root = new BSTNode(value, index);
		
		} else if (value == root.getValue()) {
			
			root.addIndex(index);
			
		}else if (value < root.getValue()) {
			
			if (root.left == null) {
				root.left = new BSTNode(value, index);
			} else {
				insertIntoBst(index, value, root.left);
			}
			
		} else if (value > root.getValue()) {
			
			if (root.right == null) {
				root.right = new BSTNode(value, index);
			} else {
				insertIntoBst(index, value, root.right);
			}
		}
		
	}
	
	public List<Integer> search(int number) {
		
		return (binaryTreeSearch(number, root));
		
	}
	
	private List<Integer> binaryTreeSearch(int number, BSTNode root) {
		
		if (root != null) {
			
			if (root.getValue() == number) {
				return root.getIndexes();
			} else if (number < root.getValue()) {
				return binaryTreeSearch(number, root.left);
			} else if (number > root.getValue()) {
				return binaryTreeSearch(number, root.right);
			}
			
		}
		
		return emptyList;
	}
	
}
