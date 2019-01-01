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

import com.google.gson.Gson;

import fum.FoodUnitManagerService;
import fum.controllers.ProductTypeController;
import fum.data.objects.ProductType;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductTypeController.class)
public class ProductTypeControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private FoodUnitManagerService fums;
	
	List<ProductType> productTypeList;
	private ProductType productTypeA;
	private ProductType productTypeB;
	Gson gson = new Gson();
	
	@Before
	public void setup() {
		
		productTypeA = new ProductType();
		productTypeA.setTypeName("Coffee");
		productTypeA.setAttributes(new HashSet<String>(Arrays.asList("Roast", "Blend")));
		
		productTypeB = new ProductType();
		productTypeB.setTypeName("Milk");
		productTypeB.setAttributes(new HashSet<String>(Arrays.asList("Fat")));
		
		productTypeList = new ArrayList<ProductType>();
		productTypeList.add(productTypeA);
		productTypeList.add(productTypeB);
		when(fums.findAllProductTypes()).thenReturn(productTypeList);
	}
	
	@Test
	public void findAllProductTypes() throws Exception {
		
		MvcResult result = mvc.perform(get("/productTypes").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		assertEquals(gson.toJson(productTypeList), result.getResponse().getContentAsString());
	}
	
	@Test
	public void addProductType( ) throws Exception {
		
		mvc.perform(post("/productTypes").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(productTypeB))).andExpect(status().isOk());
	}
	
}
