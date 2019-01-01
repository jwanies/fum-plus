package fum.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import fum.FoodUnitManagerService;
import fum.controllers.FoodUnitController;
import fum.data.objects.FoodUnit;
import fum.data.objects.ProductType;
import fum.validation.DuplicateException;
import fum.validation.EntityNotFoundException;
import fum.validation.FoodUnitValidator;
import fum.validation.MissingAttributeException;
import fum.validation.UnexpectedAttributeException;
import fum.data.objects.Attributes;

@RunWith(SpringRunner.class)
@WebMvcTest(FoodUnitController.class)
public class FoodUnitControllerTest {

	private String expectedJsonList = "[{\"id\":1,\"owner\":\"AR Anders\",\"productType\":\"Coffee\",\"unitDescription\":\"Starbucks Coffee Bag\",\"createdDate\":null,\"mass\":350,\"expiryDate\":\"2100-01-31T16:10:10.258+0000\",\"typeSpecificAttributes\":{}},{\"id\":null,\"owner\":\"Harvey McMore\",\"productType\":\"Coffee\",\"unitDescription\":\"Cafe Du Monde\",\"createdDate\":null,\"mass\":500,\"expiryDate\":\"2100-01-31T16:10:10.258+0000\",\"typeSpecificAttributes\":{}}]";
	private String expectedJsonFoodUnit = "{\"id\":1,\"owner\":\"AR Anders\",\"productType\":\"Coffee\",\"unitDescription\":\"Starbucks Coffee Bag\",\"createdDate\":null,\"mass\":350,\"expiryDate\":\"2100-01-31T16:10:10.258+0000\",\"typeSpecificAttributes\":{}}";
	private String jsonCreateFoodUnit = "{\"owner\": \"John W\", \"unitDescription\": \"Kona Island Coffee\", \"mass\": 16, \"expiryDate\": \"2025-09-17\", \"productType\" : {\"typeName\" : \"Coffee\"}, \"typeSpecificAttributes\" : {\"Roast\" : \"Light\", \"Blend\" : \"Kona\"}}";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private FoodUnitManagerService fums;
	
	@MockBean
	private FoodUnitValidator validator;

	private ProductType productTypeA;
	List<FoodUnit> foodUnitList;
	private FoodUnit foodUnitA;
	private FoodUnit foodUnitB;
	private Date expiryDate;
	
	@Before
	public void setup() {
		
		Attributes roast = new Attributes();
		roast.setAttribute("Roast");
		roast.setPattern("(?i)(\\\\W|^)(dark|medium|light)(\\\\W|$)");
		Attributes blend = new Attributes();
		blend.setAttribute("Blend");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Long("4105095010258"));
		expiryDate = calendar.getTime();
		productTypeA = new ProductType();
		productTypeA.setTypeName("Coffee");
		productTypeA.setAttributes(new HashSet<Attributes>(Arrays.asList(roast, blend)));
		
		foodUnitA = new FoodUnit();
		foodUnitA.setId(new Long(1));
		foodUnitA.setUnitDescription("Starbucks Coffee Bag");
		foodUnitA.setExpiryDate(expiryDate);
		foodUnitA.setProductType(productTypeA);
		foodUnitA.setOwner("AR Anders");
		foodUnitA.setMass(new Long(350));
		when(fums.findFoodUnitById(new Long(1))).thenReturn(foodUnitA);
		when(fums.findFoodUnitById(new Long(4))).thenThrow(new EntityNotFoundException(new Long(4)));
		
		when(fums.findFoodUnitById(new Long(5))).thenThrow(new MissingAttributeException("Attribute1"));
		when(fums.findFoodUnitById(new Long(6))).thenThrow(new UnexpectedAttributeException(Arrays.asList("Attribute1", "Attribute2")));
		when(fums.findFoodUnitById(new Long(7))).thenThrow(new DuplicateException(productTypeA.getTypeName()));
		
		foodUnitB = new FoodUnit();
		foodUnitB.setUnitDescription("Cafe Du Monde");
		foodUnitB.setProductType(productTypeA);
		foodUnitB.setExpiryDate(expiryDate);
		foodUnitB.setOwner("Harvey McMore");
		foodUnitB.setMass(new Long(500));
		
		foodUnitList = new ArrayList<FoodUnit>();
		foodUnitList.add(foodUnitA);
		foodUnitList.add(foodUnitB);
		when(fums.findAllFoodUnits()).thenReturn(foodUnitList);
		
	}
	
	@Test
	public void findAllFoodUnits() throws Exception {
		
		MvcResult result = mvc.perform(get("/foodUnits")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		
		assertEquals(expectedJsonList, result.getResponse().getContentAsString());
	}
	
	@Test
	public void findFoodUnit() throws Exception {
		
		MvcResult result = mvc.perform(get("/foodUnits/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		
		assertEquals(expectedJsonFoodUnit, result.getResponse().getContentAsString());
	}
	
	@Test
	public void findFoodUnitEntityNotFoundException() throws Exception {
		
		mvc.perform(get("/foodUnits/4")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void findFoodUnitMissingAttributeException() throws Exception {
		
		mvc.perform(get("/foodUnits/5")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void findFoodUnitUnexpectedAttributeException() throws Exception {
		
		mvc.perform(get("/foodUnits/6")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void addFoodUnit() throws Exception {
		when(validator.supports(ArgumentMatchers.any())).thenReturn(true);
		mvc.perform(post("/foodUnits").contentType(MediaType.APPLICATION_JSON)
			.content(jsonCreateFoodUnit))
			.andExpect(status().isOk());
	}
	
	@Test
	public void throwDuplicateException( ) throws Exception {
		
		mvc.perform(get("/foodUnits/7")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
}
