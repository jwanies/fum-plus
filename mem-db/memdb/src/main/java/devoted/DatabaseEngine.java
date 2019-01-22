package devoted;

import java.util.Scanner;

/**
 * The database executes functions against a Database based on command line instructions
 *
 */
public class DatabaseEngine {

	private Database db;
	private static final String TERMINATE = "END";
	private static final String KEY_NOT_FOUND = "NULL";
	private static final String INVALID_COMMAND = "Invalid Command";
	private static final String INVALID_PARAMETER = "Invalid number of parameters provided";
	private static final String NOTHING_TO_ROLLBACK = "TRANSACTION NOT FOUND";
	
	/**
	 * Read in lines from the command prompt and send them off for parsing/processing.
	 * Display results back on the command prompt.
	 */
	public void run() {
				
		Scanner scanner = new Scanner(System.in);
    	String command = scanner.nextLine();
    	
    	 while (!command.equalsIgnoreCase(TERMINATE)) {
    		
    		String response = this.executeCommand(command);
    		if (response != null) {
    			System.out.println(response);
    		}
    		command = scanner.nextLine();
    	}
    	
    	scanner.close();
	}

	/**
	 * Parses the provided String command and calls the appropriate function on the database
	 * @param command The command defining what the database should do
	 * @return The response from the database if one was provided
	 */
	public String executeCommand(String command) {
		
		String[] commandArray = command.split(" ");
		String returnVal = null;
		
		try {
			
			switch (DatabaseCommands.valueOf(commandArray[0].toUpperCase())) {
			
				case BEGIN:
					db.begin();
					break;
				case COMMIT:
					db.commit();
					break;
				case COUNT:
					returnVal = db.count(commandArray[1]).toString();
				case DELETE:
					db.delete(commandArray[1]);
					break;
				case GET:
					String response = db.get(commandArray[1]);
					returnVal = response == null ? KEY_NOT_FOUND : response;
					break;
				case ROLLBACK:
					if (!db.rollback()) {
						returnVal = NOTHING_TO_ROLLBACK;
					}
					break;
				case SET:
					db.set(commandArray[1], commandArray[2]);
					break;
			}
			
		} 
		// I admit to sacrificing a proper validation layer in exchange for spending more time developing the database
		catch (IllegalArgumentException ex) {
			returnVal = INVALID_COMMAND;
		} catch (IndexOutOfBoundsException ex) {
			returnVal = INVALID_PARAMETER;
		}

		return returnVal;
	}
	
	public void setDatabase(Database db) {
		this.db = db;
	}
}
