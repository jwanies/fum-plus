package challenges.streamlinity;

import java.util.List;

public class App {

	public static void main(String[] args) {
		
		int[] numberArray = {23,36,98,333,23,23,15,16,39,25};
		
		BST binarySearchTree = new BST(numberArray);
		
		List<Integer> results = binarySearchTree.search(232);
		
		System.out.println(results.toString());
		
	}

}
