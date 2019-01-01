package fum.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
import fum.controllers.ProductTypeController;
import fum.data.objects.Attributes;
import fum.data.objects.ProductType;
import fum.validation.FoodUnitValidator;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductTypeController.class)
public class ProductTypeControllerTest {

	private String expectedJsonProductTypeList = "[{\"typeName\":\"Coffee\",\"attributes\":[{\"attribute\":\"Blend\",\"pattern\":null},{\"attribute\":\"Roast\",\"pattern\":\"(?i)(\\\\\\\\W|^)(dark|medium|light)(\\\\\\\\W|$)\"}]},{\"typeName\":\"Milk\",\"attributes\":[{\"attribute\":\"Fat\",\"pattern\":null}]}]";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private FoodUnitManagerService fums;
	
	@MockBean
	private FoodUnitValidator validator;
	
	List<ProductType> productTypeList;
	private ProductType productTypeA;
	private ProductType productTypeB;
	
	@Before
	public void setup() {
		
		Attributes roast = new Attributes();
		roast.setAttribute("Roast");
		roast.setPattern("(?i)(\\\\W|^)(dark|medium|light)(\\\\W|$)");
		Attributes blend = new Attributes();
		blend.setAttribute("Blend");
		blend.setPattern(null);
		Attributes fat = new Attributes();
		fat.setAttribute("Fat");
		fat.setPattern(null);
		
		productTypeA = new ProductType();
		productTypeA.setTypeName("Coffee");
		productTypeA.setAttributes(new HashSet<Attributes>(Arrays.asList(roast, blend)));
		
		productTypeB = new ProductType();
		productTypeB.setTypeName("Milk");
		productTypeB.setAttributes(new HashSet<Attributes>(Arrays.asList(fat)));
		
		productTypeList = new ArrayList<ProductType>();
		productTypeList.add(productTypeA);
		productTypeList.add(productTypeB);
		when(fums.findAllProductTypes()).thenReturn(productTypeList);
	}
	
	@Test
	public void findAllProductTypes() throws Exception {
		
		MvcResult result = mvc.perform(get("/productTypes").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		assertEquals(expectedJsonProductTypeList, result.getResponse().getContentAsString());
	}
	
	@Test
	public void addProductType( ) throws Exception {
		
		mvc.perform(post("/productTypes").contentType(MediaType.APPLICATION_JSON)
				.content(TestHelper.mapToJson(productTypeB))).andExpect(status().isOk());
	}
	
}
