package devoted;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Stack;

/**
 * This is where the magic happens. This basic database implements all the functions mentioned in
 * the Devoted assignment specification and should meet all requirements - performance and otherwise.
 *
 */
public class BasicDatabase implements Database {

	// I went with a HashMap, which has a worst case of O(logn) in Java 8+ (what I'm using). Of course, even that would only be
	// in the case of a catastrophic hash collision. O(1) is average (and what really matters for HashMap).
	Map<String, String> database = new HashMap<String, String>();
	
	// Since count also needed to have runtime of O(logn) or better, I created a separate HashMap to cache the counts over 
	// calculating in real-time.
	Map<String, Integer> valueCount = new HashMap<String, Integer>();
	
	// I decided to keep track of rollbacks rather than transactions. I went with a stack of maps since I'm only ever working 
	// with the top layer. Each map represents a transaction to rollback. 
	Stack<Map<String, String>> rollbacks = new Stack<Map<String, String>>();
	
	@Override
	public void set(String key, String value) {
		
		String oldVal = database.put(key, value); 
		decrementCountIfNecessary(oldVal);
		
		// keep track of the old value the first time this is changed in a transaction (so we can rollback if need be)
		if (!rollbacks.isEmpty() && !rollbacks.peek().containsKey(key)) {
			rollbacks.peek().put(key, oldVal);
		}
		
		incrementCount(value);
	}

	@Override
	public String get(String key) {
		return database.get(key);
	}

	@Override
	public void delete(String key) {
		
		String oldVal = database.remove(key);
		decrementCountIfNecessary(oldVal);
		
		// keep track of this delete for a rollback (if needed)
		if (oldVal != null && !rollbacks.isEmpty() && !rollbacks.peek().containsKey(key)) {
			rollbacks.peek().put(key, oldVal);
		}
	}
	
	@Override
	public Integer count(String value) {
		return Optional.ofNullable(valueCount.get(value)).orElse(0);
	}

	@Override
	public void begin() {
		rollbacks.push(new HashMap<String, String>());
	}

	@Override
	public void commit() {
		
		if (rollbacks.isEmpty()) {
			return;
		}
		
		// remove all transactions, since we are only really keeping track of changes in case of rollback and the spec says a commit
		// applies to ALL open transactions
		rollbacks.clear();
	}

	@Override
	public boolean rollback() {
		
		// return false if there are no transactions to rollback
		if (rollbacks.isEmpty()) {
			return false;
		}
		
		// reverse everything we did in the last transaction
		for (Entry<String,String> entry : rollbacks.pop().entrySet()) {
			// if an entry was added, delete it
			if (entry.getValue() == null) {
				decrementCountIfNecessary(database.remove(entry.getKey()));
			} 
			// if an entry was deleted, re-add it
			else {
				if (!entry.getValue().equals(database.put(entry.getKey(), entry.getValue()))) {
					incrementCount(entry.getValue());
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Helper method to decrement the counter for the provided value
	 */
	private void decrementCountIfNecessary(String value) {
		if (value != null) {
			valueCount.put(value, valueCount.get(value) - 1);
		}
	}

	/**
	 * Helper method to increment the counter for the provided value
	 */
	private void incrementCount(String value) {
		int count = valueCount.get(value) == null ? 0 : valueCount.get(value);
		valueCount.put(value, count + 1);
	}
}
