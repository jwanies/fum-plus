package challenges.hackerrank;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class LogValidator {
	
	static String regexPattern = "\"type\":\"([A-Z]+)\",\"userID\":\"([0-9]+)\",\"messageID\":\"([0-9A-Za-z]+)\",\"statusCode\":([0-9]+)";
    static Pattern pattern = Pattern.compile(regexPattern);
    
    static final String ERROR_TYPE = "ERROR";
    static final String[] ALL_LOG_TYPES = {"PERFORMANCE", "INFO", "ERROR"};
    
    static Map<String, List<String>> validateLogs(List<Log> logs) {
    	
    	Map<String, List<String>> messageTypes = new HashMap<String, List<String>>();
    	
    	// create a list for each log type
    	for (String logType : ALL_LOG_TYPES) {
    		messageTypes.put(logType, new ArrayList<String>());
    	}
    	
    	// I coded this in such a way that adding log types in the future would result in no change
    	for (Log log : logs) {
    		// ignore if the message type is null
    		if (log.type == null) {
    			continue;
    		}
    		
    		// do the status code and mod checks, then add the log message ID to the appropriate type list
    		if (log.type.equals(ERROR_TYPE) || log.statusCode >= 400 || 
    				Integer.parseInt(log.userID.substring(0, 2)) % Integer.parseInt(log.userID.substring(log.userID.length() - 1)) != 0) {
    			messageTypes.get(ERROR_TYPE).add(log.messageID);
			} else {
				messageTypes.get(log.type).add(log.messageID);
			}
    	}
    	
    	return messageTypes;
    }

    private static class Log {
        String type;
        String userID;
        String messageID;
        int statusCode;
    }
    
    public static void main(String args[] ) throws Exception {        
        Scanner input = new Scanner(System.in);
        List<Log> logs = new ArrayList<Log>();
        do {
            Log log = new Log();
            Matcher matcher = pattern.matcher(input.nextLine());
            while (matcher.find()) {                
                log.type = matcher.group(1);
                log.userID = matcher.group(2);
                log.messageID = matcher.group(3);
                log.statusCode = Integer.parseInt(matcher.group(4));
            }
            logs.add(log);
        }while(input.hasNext());
        printLogResults(validateLogs(logs));
        input.close();
    }

    public static void printLogResults(Map<String, List<String>> processedLogs) {
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"ERROR\":[\"").append(processedLogs.get("ERROR").stream().collect(Collectors.joining("\",\""))).append("\"],");
        sb.append("\"INFO\":[\"").append(processedLogs.get("INFO").stream().collect(Collectors.joining("\",\""))).append("\"],");
        sb.append("\"PERFORMANCE\":[\"").append(processedLogs.get("PERFORMANCE").stream().collect(Collectors.joining("\",\""))).append("\"]");
        sb.append("}");
        System.out.println(sb.toString());
    }
}
