package fum.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import fum.FoodUnitManagerService;
import fum.TestHelper;
import fum.controllers.LocationController;
import fum.data.objects.Attributes;
import fum.data.objects.FoodUnit;
import fum.data.objects.Location;
import fum.data.objects.ProductType;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
public class LocationControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private FoodUnitManagerService fums;
	
	List<Location> locationList;
	private Location locationA;
	private Location locationB;
	private FoodUnit foodUnitA;
	private ProductType productTypeA;
	
	@Before
	public void setup() {
		
		Attributes roast = new Attributes();
		roast.setAttribute("Roast");
		roast.setPattern("(?i)(\\\\W|^)(dark|medium|light)(\\\\W|$)");
		Attributes blend = new Attributes();
		blend.setAttribute("Blend");
		blend.setPattern(null);
		
		productTypeA = new ProductType();
		productTypeA.setTypeName("Coffee");
		productTypeA.setAttributes(new HashSet<Attributes>(Arrays.asList(roast, blend)));
		
		Map<String, String> typeSpecificAttributes = new HashMap<String,String>();
		typeSpecificAttributes.put("Roast", "Medium");
		typeSpecificAttributes.put("Blend", "Kona");
		foodUnitA = new FoodUnit();
		foodUnitA.setId(new Long(1));
		foodUnitA.setTypeSpecificAttributes(typeSpecificAttributes);
		foodUnitA.setUnitDescription("Starbucks Coffee Bag");
		foodUnitA.setExpiryDate(Date.valueOf(LocalDate.of(2099, 12, 30)));
		foodUnitA.setProductType(productTypeA);
		foodUnitA.setOwner("AR Anders");
		foodUnitA.setMass(new Long(350));
		
		locationA = new Location();
		locationA.setId(new Long(1));
		locationA.setLatitude(-77.0364);
		locationA.setLongitude(38.8951);
		locationA.setFoodUnitId(foodUnitA);
		
		locationB = new Location();
		locationB.setId(new Long(2));
		locationB.setLatitude(-72.9113);
		locationB.setLongitude(38.0059);
		locationB.setFoodUnitId(foodUnitA);
		
		locationList = new ArrayList<Location>();
		locationList.add(locationA);
		locationList.add(locationB);
		when(fums.findLocationsByFoodUnitId(foodUnitA.getId())).thenReturn(locationList);
	}
	
	@Test
	public void findLocations() throws Exception {
		
		MvcResult result = mvc.perform(get("/locations")
			.contentType(MediaType.APPLICATION_JSON).param("foodUnitId", foodUnitA.getId().toString()))
			.andExpect(status().isOk()).andReturn();
		
		assertEquals(TestHelper.mapToJson(locationList), result.getResponse().getContentAsString());
	}
	
	@Test
	public void addLocation() throws Exception {
		
		mvc.perform(post("/locations").contentType(MediaType.APPLICATION_JSON)
				.content("{\"longitude\": \"-77.0364\", \"latitude\": \"38.8951\", \"foodUnit\": { \"id\" : 2 }}")).andExpect(status().isOk());
	}
}
