package fum;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

import fum.FoodUnitManagerService;
import fum.data.objects.FoodUnit;
import fum.data.objects.Location;
import fum.data.objects.ProductType;
import fum.data.repos.FoodUnitRepository;
import fum.data.repos.LocationRepository;
import fum.data.repos.ProductTypeRepository;
import fum.exceptions.DuplicateException;
import fum.exceptions.EntityNotFoundException;
import fum.exceptions.MissingAttributeException;
import fum.exceptions.UnexpectedAttributeException;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class FoodUnitManagerServiceTest {

	List<ProductType> productTypeList;
	private ProductType productTypeA;
	private ProductType productTypeB;
	List<FoodUnit> foodUnitList;
	private FoodUnit foodUnitA;
	private FoodUnit foodUnitB;
	List<Location> locationList;
	private Location locationA;
	private Location locationB;
	private Date tomorrow;
	
	@MockBean
	private FoodUnitRepository foodUnitRepository;
	
	@MockBean
	private ProductTypeRepository productTypeRepository;
	
	@MockBean
	private LocationRepository locationRepository;
	
	@TestConfiguration
    static class FoodUnitManagerServiceTestContextConfiguration {
  
        @Bean
        public FoodUnitManagerService foodUnitManagerService() {
            return new FoodUnitManagerService();
        }
    }
	
	@Autowired
	private FoodUnitManagerService fums;
	
	@Before
	public void setup() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		tomorrow = calendar.getTime();
		
		productTypeA = new ProductType();
		productTypeA.setId(new Long(1));
		productTypeA.setTypeName("Coffee");
		productTypeA.setAttributes(new HashSet<String>(Arrays.asList("Roast", "Blend")));
		when(productTypeRepository.findProductTypeByTypeName(productTypeA.getTypeName())).thenReturn(productTypeA);
		when(productTypeRepository.save(productTypeA)).thenReturn(productTypeA);
		
		productTypeB = new ProductType();
		productTypeB.setTypeName("Milk");
		productTypeB.setAttributes(new HashSet<String>(Arrays.asList("Fat")));
		when(productTypeRepository.findProductTypeByTypeName(productTypeB.getTypeName())).thenReturn(productTypeB);		
		when(productTypeRepository.existsByTypeName(productTypeB.getTypeName())).thenReturn(true);
		
		productTypeList = new ArrayList<ProductType>();
		productTypeList.add(productTypeA);
		productTypeList.add(productTypeB);
		when(productTypeRepository.findAll()).thenReturn(productTypeList);
		
		foodUnitA = new FoodUnit();
		foodUnitA.setId(new Long(1));
		foodUnitA.setUnitDescription("Starbucks Coffee Bag");
		foodUnitA.setProductType(productTypeA);
		foodUnitA.setExpiryDate(tomorrow);
		foodUnitA.setOwner("AR Anders");
		foodUnitA.setMass(new Long(350));
		when(foodUnitRepository.findById(foodUnitA.getId())).thenReturn(Optional.of(foodUnitA));
		
		Map<String, String> typeSpecificAttributes = new HashMap<String,String>();
		typeSpecificAttributes.put("Fat", "2%");
		foodUnitB = new FoodUnit();
		foodUnitB.setUnitDescription("Lactose Free Milk");
		foodUnitB.setProductType(productTypeB);
		foodUnitB.setExpiryDate(tomorrow);
		foodUnitB.setOwner("Harvey McMore");
		foodUnitB.setTypeSpecificAttributes(typeSpecificAttributes);
		foodUnitB.setMass(new Long(500));
		when(foodUnitRepository.save(foodUnitB)).thenReturn(foodUnitB);
		
		foodUnitList = new ArrayList<FoodUnit>();
		foodUnitList.add(foodUnitA);
		foodUnitList.add(foodUnitB);
		when(foodUnitRepository.findAll()).thenReturn(foodUnitList);
		
		locationA = new Location();
		locationA.setLatitude(-77.0364);
		locationA.setLongitude(38.8951);
		locationA.setFoodUnitId(foodUnitA);
		when(locationRepository.save(locationA)).thenReturn(locationA);
		
		locationB = new Location();
		locationB.setLatitude(-72.9113);
		locationB.setLongitude(38.0059);
		locationB.setFoodUnitId(foodUnitA);
		
		locationList = new ArrayList<Location>();
		locationList.add(locationA);
		locationList.add(locationB);
		when(locationRepository.findLocationByFoodUnit(foodUnitA)).thenReturn(locationList);
	}
	
	@Test
	public void findAllProductTypes() throws Exception {
				
		List<ProductType> productTypeListFound = fums.findAllProductTypes();
		assertSame(productTypeList, productTypeListFound);
	}
	
	@Test
	public void saveProductType() {
		ProductType productTypeFound = fums.saveProductType(productTypeA);
		assertSame(productTypeA, productTypeFound);
	}
	
	@Test
	public void findAllFoodUnits() throws Exception {
				
		List<FoodUnit> foodUnitListFound = fums.findAllFoodUnits();
		assertSame(foodUnitList, foodUnitListFound);
	}
	
	@Test
	public void findFoodUnitById() {
		FoodUnit foodUnitFound = fums.findFoodUnitById(foodUnitA.getId());
		assertSame(foodUnitA, foodUnitFound);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void findFoodUnitByIdEntityNotFoundException() {
		fums.findFoodUnitById(new Long(-1));
	}
	
	@Test
	public void saveFoodUnit() throws JsonProcessingException {
		FoodUnit foodUnitFound = fums.saveFoodUnit(foodUnitB);
		System.out.println(TestHelper.mapToJson(foodUnitB));
		assertSame(foodUnitB, foodUnitFound);
	}
	
	@Test(expected = MissingAttributeException.class)
	public void saveFoodUnitMissingAttributeException() {
		try {
			fums.saveFoodUnit(foodUnitA);
		} catch (MissingAttributeException ex) {
			assertTrue(ex.getMessage().equals("The following attributes need to be provided: [Roast, Blend]"));
			throw ex;
		}
	}
	
	@Test(expected = DuplicateException.class)
	public void saveProductTypeDuplicateException() {
		fums.saveProductType(productTypeB);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void saveFoodUnitProductTypeEntityNotFoundException() {
		fums.saveFoodUnit(getAnotherFoodUnit());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void saveFoodUnitProductTypeNameEntityNotFoundException() {
		ProductType productTypeC = new ProductType();
		productTypeC.setTypeName("Water");
		FoodUnit anotherFoodUnit = getAnotherFoodUnit();
		anotherFoodUnit.setProductType(productTypeC);
		fums.saveFoodUnit(anotherFoodUnit);
	}
	
	@Test(expected = UnexpectedAttributeException.class)
	public void saveFoodUnitUnexpectedAttributeException() {
		Map<String, String> typeSpecificAttributes = new HashMap<String,String>();
		typeSpecificAttributes.put("Fat", "2%");
		ProductType productTypeC = new ProductType();
		productTypeC.setTypeName("Water");
		FoodUnit anotherFoodUnit = getAnotherFoodUnit();
		anotherFoodUnit.setProductType(productTypeC);
		anotherFoodUnit.setTypeSpecificAttributes(typeSpecificAttributes);
		when(productTypeRepository.findProductTypeByTypeName(productTypeC.getTypeName())).thenReturn(productTypeC);
		fums.saveFoodUnit(anotherFoodUnit);
	}
	
	@Test
	public void findAllLocations() throws Exception {
				
		List<Location> locationListFound = fums.findLocationsByFoodUnitId(foodUnitA.getId());
		assertSame(locationList, locationListFound);
	}
	
	@Test
	public void moveFoodUnit() {
		Location locationFound = fums.moveFoodUnit(locationA);
		assertSame(locationA, locationFound);
	}
	
	public FoodUnit getAnotherFoodUnit() {
		FoodUnit anotherFoodUnit = new FoodUnit();
		anotherFoodUnit.setUnitDescription("Keurig French Vanilla");
		anotherFoodUnit.setExpiryDate(tomorrow);
		anotherFoodUnit.setOwner("Bobby Bob");
		anotherFoodUnit.setMass(new Long(12));
		return anotherFoodUnit;
	}
}
