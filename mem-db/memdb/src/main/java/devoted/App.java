package devoted;

/**
 * The entry point for our application - simply launches a database engine
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	DatabaseEngine engine = new DatabaseEngine();
    	engine.setDatabase(new BasicDatabase());
    	engine.run();
    }
}
