package fum.driver;

import java.io.IOException;

import fum.driver.FumHelper.HttpMethod;

public class FumDriver {
	
	//private static final String requestUrl="http://ec2-54-165-38-60.compute-1.amazonaws.com:8080/";
	private static final String requestUrl="http://localhost:8080/";
	
	private static final String createCoffeeProductType = "{\"typeName\" : \"Coffee\", \"attributes\" : [{\"attribute\":\"Roast\", \"pattern\":\"(?i)(\\\\W|^)(dark|medium|light)(\\\\W|$)\"}, {\"attribute\":\"Blend\"}]}";
	private static final String createMilkProductType = "{\"typeName\" : \"Milk\", \"attributes\" : [{\"attribute\":\"Fat\"}] }";
	private static final String updateMilkProductType1 = "{\"typeName\" : \"Milke\", \"attributes\" : [{\"attribute\":\"Fat\"}] }";
	private static final String updateMilkProductType2 = "{\"typeName\" : \"Milk\", \"attributes\" : [{\"attribute\":\"Fat\"}] }";
	private static final String productTypesPath = "productTypes";
	
	private static final String createCoffeeFoodUnit = "{\"owner\": \"John W\", \"unitDescription\": \"Kona Island Coffee\", \"mass\": 16, \"expiryDate\": \"2025-09-17\", \"productType\" : {\"typeName\" : \"Coffee\"}, \"typeSpecificAttributes\" : {\"Roast\" : \"Light\", \"Blend\" : \"Kona\"}}";
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
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void populateDb() {
		String request = "Request: ";
		String response = "Response: ";
		
		System.out.println(request + login);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + login, "", HttpMethod.POST));
		
		System.out.println(request + createCoffeeProductType);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath, createCoffeeProductType, HttpMethod.POST));
		System.out.println(request + createMilkProductType);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath, createMilkProductType, HttpMethod.POST));
		System.out.println(request + updateMilkProductType1);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath + "/4", updateMilkProductType1, HttpMethod.PUT));
		System.out.println(request + updateMilkProductType2);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath + "/4", updateMilkProductType2, HttpMethod.PUT));
		System.out.println(request + "GET ALL");
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath, "", HttpMethod.GET));
		
		System.out.println(request + createCoffeeFoodUnit);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + foodUnitsPath, createCoffeeFoodUnit, HttpMethod.POST));
		System.out.println(request + createMilkFoodUnit);
		String responseString = FumHelper.sendHttpRequest(requestUrl + foodUnitsPath, createMilkFoodUnit, HttpMethod.POST);
		System.out.println(response + responseString);
		System.out.println(request + " delete FoodUnit");
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + foodUnitsPath + "/16", "", HttpMethod.DELETE));
		
		System.out.println(request + createLocationA);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + locationsPath, createLocationA, HttpMethod.POST));
		System.out.println(request + createLocationB);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + locationsPath, createLocationB, HttpMethod.POST));
	}
	
}

