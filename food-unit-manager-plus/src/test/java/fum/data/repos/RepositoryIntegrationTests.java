package fum.data.repos;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import fum.data.objects.Attributes;
import fum.data.objects.FoodUnit;
import fum.data.objects.Location;
import fum.data.objects.ProductType;
import fum.data.repos.LocationRepository;
import fum.data.repos.ProductTypeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryIntegrationTests {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	private FoodUnit foodUnitA;
	private FoodUnit foodUnitB;
	private ProductType productTypeA;
	private ProductType productTypeB;
	private Location locationA;
	private Location locationB;
	private Date tomorrow;
	
	@Before
	public void instantiateTestObjects() {
		
		Attributes roast = new Attributes();
		roast.setAttribute("Roast");
		roast.setPattern("(?i)(\\\\W|^)(dark|medium|light)(\\\\W|$)");
		Attributes blend = new Attributes();
		blend.setAttribute("Blend");
		blend.setPattern(null);
		Attributes fat = new Attributes();
		fat.setAttribute("Fat");
		fat.setPattern(null);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		tomorrow = calendar.getTime();
		
		productTypeA = new ProductType();
		productTypeA.setTypeName("Coffee");
		productTypeA.setAttributes(new HashSet<Attributes>(Arrays.asList(roast, blend)));
		entityManager.persistAndFlush(productTypeA);
		
		productTypeB = new ProductType();
		productTypeB.setTypeName("Milk");
		productTypeB.setAttributes(new HashSet<Attributes>(Arrays.asList(fat)));
		entityManager.persistAndFlush(productTypeB);
		
		foodUnitA = new FoodUnit();
		foodUnitA.setUnitDescription("Starbucks Coffee Bag");
		foodUnitA.setProductType(productTypeA);
		foodUnitA.setExpiryDate(tomorrow);
		foodUnitA.setOwner("AR Anders");
		foodUnitA.setMass(new Long(350));
		entityManager.persistAndFlush(foodUnitA);
		
		foodUnitB = new FoodUnit();
		foodUnitB.setUnitDescription("Lactose Free Milk");
		foodUnitB.setProductType(productTypeB);
		foodUnitB.setExpiryDate(tomorrow);
		foodUnitB.setOwner("Harvey McMore");
		foodUnitB.setMass(new Long(500));
		entityManager.persistAndFlush(foodUnitB);
		
		locationA = new Location();
		locationA.setLatitude(-77.0364);
		locationA.setLongitude(38.8951);
		locationA.setFoodUnitId(foodUnitA);
		entityManager.persistAndFlush(locationA);
		
		locationB = new Location();
		locationB.setLatitude(-72.9113);
		locationB.setLongitude(38.0059);
		locationB.setFoodUnitId(foodUnitA);
		entityManager.persistAndFlush(locationB);
	}
	
	@Test
	public void testGetProductTypeById() throws Exception {
	
		ProductType productTypeFound = productTypeRepository.getOne(productTypeA.getId());
		assertSame(productTypeFound, productTypeA);
	}
	
	@Test
	public void testGetProductTypeByTypeName() throws Exception {
	
		ProductType productTypeFound = productTypeRepository.findProductTypeByTypeName(productTypeA.getTypeName());
		assertSame(productTypeFound, productTypeA);
	}
	
	@Test
	public void testGetLocation() throws Exception {

		List<Location> locations = locationRepository.findLocationByFoodUnit(foodUnitA);
		assertTrue(locations.contains(locationA));
		assertTrue(locations.contains(locationB));
	}
}
