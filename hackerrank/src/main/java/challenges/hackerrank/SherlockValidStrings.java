package challenges.hackerrank;

import java.io.*;
import java.util.*;

public class SherlockValidStrings {
	
    // Complete the isValid function below.
    static String isValid(String s) {
        
        Map<String, Integer> letterCounts = new HashMap<String, Integer>();
        //boolean deletionUsed = false;

        for (String letter : s.split("")) {
            letterCounts.put(letter, letterCounts.get(letter) == null ? 0 : letterCounts.get(letter) + 1);
        }
        
        //letterCounts.values().stream().

        return "TRUE";
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = scanner.nextLine();

        String result = isValid(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

