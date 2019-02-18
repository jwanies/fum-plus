package challenges.elements.dynamic;

import java.util.Arrays;
import java.util.Scanner;

public class LevenshteinDistance {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		String firstWord = scanner.next();
		String secondWord = scanner.next();
		int[][] grid = new int[firstWord.length() + 1][secondWord.length() + 1];
		
		for (int i = 0; i <= firstWord.length(); i++) {
			for (int j = 0; j <= secondWord.length(); j++) {
				if (i == 0) {
					grid[i][j] = j;
				} else if (j == 0) {
					grid[i][j] = i;
				} else {
					grid[i][j] = min(grid[i-1][j-1] + booleanValue(firstWord.charAt(i-1) == secondWord.charAt(j-1)),
							grid[i-1][j] + 1, grid[i][j-1] + 1);
				}
			}
		}
		
		System.out.println(grid[firstWord.length()][secondWord.length()]);
		
		scanner.close();
	}
	
	/**
	 * Return the min of the numbers provided
	 * @param values
	 * @return
	 */
	private static int min(int... values) {
		return Arrays.stream(values).min().getAsInt();
	}
	
	/**
	 * Convert boolean to int where true = 0 and false = 1
	 * @param value
	 * @return
	 */
	private static int booleanValue(boolean value) {
		return value == true ? 0 : 1;
	}
	
}
