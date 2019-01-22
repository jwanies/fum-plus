package devoted;

/**
 * This interface defines the functions I expect databases to implement.
 *
 */
public interface Database {

	public void set(String key, String value);
	public String get(String key);
	public void delete(String key);
	public Integer count(String value);
	public void begin();
	public void commit();
	public boolean rollback();
	
}
