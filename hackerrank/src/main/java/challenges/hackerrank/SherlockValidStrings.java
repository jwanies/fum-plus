package challenges.hackerrank;

import java.io.*;
import java.util.*;

public class SherlockValidStrings {
	
    // Complete the isValid function below.
    static String isValid(String s) {
        
    	 Map<String, Integer> letterCounts = new HashMap<String, Integer>();
         boolean deletionUsed = false;

         // get a count of each letter and put it in a map
         for (String letter : s.split("")) {
             letterCounts.put(letter, letterCounts.get(letter) == null ? 1 : letterCounts.get(letter) + 1);
         }
         
         Integer[] values = letterCounts.values().toArray(new Integer[0]);
         Arrays.sort(values);
         for (int i = 1; i < values.length; i++) {        	 
        	 Integer difference = values[i] - values[i-1];
             if (difference == 1 && !deletionUsed) {
                 deletionUsed = true;
                 values[i]--;
             } else if (difference > 1 && values[i-1] == 1 && !deletionUsed) {
            	 deletionUsed = true; 
             }else if (difference != 0) {
                 return "NO";
             }
         }

         return "YES";
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String s = scanner.nextLine();

        String result = isValid(s);

        System.out.println(result);

        scanner.close();
    }
}

