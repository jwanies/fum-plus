package challenges.elements.dynamic;

import java.util.Scanner;

public class ScoreCombinations {

	public static void main(String[] args) {
		
		// get the score outcome we are calculating combinations for from the console
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		
		// this table will hold our running total combinations for each outcome
		int[] table = new int[input+1];
		
		// these are the possible points we can score in a play
		int[] scores = {2,3,7};
		
		// base case
		table[0] = 1;
		
		// keep track of the number of ways we can reach each score
		for (int score : scores) {
			for (int i = score; i <= input; i++) {
				table[i] += table[i - score];
			}
		}
		
		System.out.println(table[input]);
		scanner.close();
	}
	
}
