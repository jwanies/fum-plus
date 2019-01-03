package fum.driver;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fum.data.objects.FoodUnit;
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
	private static final String createMilkFoodUnit = "{\"owner\": \"John W\", \"unitDescription\": \"Heritage Farms Milk\", \"mass\": 1, \"expiryDate\": \"2019-09-17\", \"productType\" : {\"typeName\" : \"Milk\"}, \"typeSpecificAttributes\" : {\"Fat\" : \"2%\"}}";
	private static final String foodUnitsPath = "foodUnits";
	
	private static final String createLocationA = "{\"longitude\": \"-77.0364\", \"latitude\": \"38.8951\", \"foodUnit\": {\"id\" : ";
	private static final String createLocationB = "{\"longitude\": \"-71.1122\", \"latitude\": \"42.8435\", \"foodUnit\": {\"id\" : ";
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
	public static void populateDb() throws JsonParseException, JsonMappingException, IOException {
		String request = "Request: ";
		String response = "Response: ";
		
		// authenticate
		System.out.println(request + login);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + login, "", HttpMethod.POST));
		
		// create some product types (if they don't exist)
		System.out.println(request + createCoffeeProductType);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath, createCoffeeProductType, HttpMethod.POST));
		System.out.println(request + createMilkProductType);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath, createMilkProductType, HttpMethod.POST));
		
		// update a product type
		System.out.println(request + updateMilkProductType1);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath + "/4", updateMilkProductType1, HttpMethod.PUT));
		System.out.println(request + updateMilkProductType2);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath + "/4", updateMilkProductType2, HttpMethod.PUT));
		
		// get list of product types
		System.out.println(request + "GET ALL");
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + productTypesPath, "", HttpMethod.GET));
		
		// create some food units
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(request + createCoffeeFoodUnit);
		String responseString = FumHelper.sendHttpRequest(requestUrl + foodUnitsPath, createCoffeeFoodUnit, HttpMethod.POST);
		System.out.println(response + responseString);
		FoodUnit newCoffeeFoodUnit = mapper.readValue(responseString, FoodUnit.class);
		System.out.println(request + createMilkFoodUnit);
		responseString = FumHelper.sendHttpRequest(requestUrl + foodUnitsPath, createMilkFoodUnit, HttpMethod.POST);
		System.out.println(response + responseString);
		FoodUnit newMilkFoodUnit = mapper.readValue(responseString, FoodUnit.class);
		
		// delete a food unit
		System.out.println(request + " delete FoodUnit");
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + foodUnitsPath + "/" + newMilkFoodUnit.getId(), "", HttpMethod.DELETE));
		
		// move a food unit twice
		System.out.println(request + createLocationA);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + locationsPath, createLocationA + newCoffeeFoodUnit.getId() + "}}", HttpMethod.POST));
		System.out.println(request + createLocationB);
		System.out.println(response + FumHelper.sendHttpRequest(requestUrl + locationsPath, createLocationB + newCoffeeFoodUnit.getId() + "}}", HttpMethod.POST));
	}
	
}

