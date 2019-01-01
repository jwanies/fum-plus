package fum.driver;

public class FumDriver {
	
	//private static final String requestUrl="http://ec2-54-165-38-60.compute-1.amazonaws.com:8080/";
	private static final String requestUrl="http://localhost:8080/";
	
	private static final String createCoffeeProductType = "{\"typeName\" : \"Coffee\", \"attributes\" : [{\"attribute\":\"Roast\", \"pattern\":\"(?i)(\\\\W|^)(dark|medium|light)(\\\\W|$)\"}, {\"attribute\":\"Blend\"}]}";
	private static final String createMilkProductType = "{\"typeName\" : \"Milk\", \"attributes\" : [{\"attribute\":\"Fat\"}] }";
	private static final String productTypesPath = "productTypes";
	
	private static final String createCoffeeFoodUnit = "{\"owner\": \"John W\", \"unitDescription\": \"Kona Island Coffee\", \"mass\": -16, \"expiryDate\": \"2025-09-17\", \"productType\" : {\"typeName\" : \"Coffee\"}, \"typeSpecificAttributes\" : {\"Roast\" : \"Light\", \"Blend\" : \"Kona\"}}";
	private static final String createMilkFoodUnit = "{\"owner\": \"John W\", \"unitDescription\": \"Heritage Farms Milk\", \"mass\": 1, \"expiryDate\": \"2019-09-17\", \"productType\" : {\"typeName\" : \"Milk\"}, \"typeSpecificAttributes\" : {\"Fat\" : \"2%\", \"Roast\" : \"KindaLight\"}}";
	private static final String foodUnitsPath = "foodUnits";
	
	private static final String createLocationA = "{\"longitude\": \"-77.0364\", \"latitude\": \"38.8951\", \"foodUnit\": {\"id\" : 7 }}";
	private static final String createLocationB = "{\"longitude\": \"-71.1122\", \"latitude\": \"42.8435\", \"foodUnit\": {\"id\" : 7 }}";
	private static final String locationsPath = "locations";
	
	private static final String login = "login?username=motiionAdmin&password=m0t11onAdm1nPaSsWoRd";
	
	/**
	 * Intended to be a driver for live testing the food-unit-manager REST API
	 */
	public static void main(String[] args) throws Exception
	{
		populateDb();
	}
	
	/**
	 * Populates the DB with a little bit of data
	 */
	public static void populateDb() {
		String request = "Request: ";
		String response = "Response: ";
		
		System.out.println(request + login);
		System.out.println(response + FumHelper.sendPostRequest(requestUrl + login, ""));
		
		System.out.println(request + createCoffeeProductType);
		System.out.println(response + FumHelper.sendPostRequest(requestUrl + productTypesPath, createCoffeeProductType));
		System.out.println(request + createMilkProductType);
		System.out.println(response + FumHelper.sendPostRequest(requestUrl + productTypesPath, createMilkProductType));
		
		System.out.println(request + createCoffeeFoodUnit);
		System.out.println(response + FumHelper.sendPostRequest(requestUrl + foodUnitsPath, createCoffeeFoodUnit));
		System.out.println(request + createMilkFoodUnit);
		System.out.println(response + FumHelper.sendPostRequest(requestUrl + foodUnitsPath, createMilkFoodUnit));
		
		System.out.println(request + createLocationA);
		System.out.println(response + FumHelper.sendPostRequest(requestUrl + locationsPath, createLocationA));
		System.out.println(request + createLocationB);
		System.out.println(response + FumHelper.sendPostRequest(requestUrl + locationsPath, createLocationB));
	}
	
}

