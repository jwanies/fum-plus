package fum;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fum.data.objects.*;
import fum.data.repos.*;
import fum.exceptions.*;

/**
 * This service class was created to keep any business logic out of the controllers
 * 
 * @author jwanies
 *
 */
@Service
public class FoodUnitManagerService {

	@Autowired
	private FoodUnitRepository foodUnitRepository;
	
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	/**
	 * Returns a list of all the food units currently stored in our DB
	 */
	public List<FoodUnit> findAllFoodUnits() {
		return foodUnitRepository.findAll();
	}
	
	/**
	 * Returns a specific food unit
	 * 
	 * @param id The id of the food unit to return
	 * @return The newly created food unit
	 * @throws EntityNotFoundException if one does not exists
	 */
	public FoodUnit findFoodUnitById(Long id) {
		return foodUnitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
	}
	
	/**
	 * Persists the provided food unit to the DB
	 * 
	 * @param foodUnit
	 * @throws EntityNotFoundException If the productName was not provided or does not exist in the DB
	 * @throws MissingAttributeException If an EXPECTED product type specific attribute WAS NOT provided
	 * @throws UnexpectedAttributeException If an UNEXPECTED product type specific attribute WAS provided
	 */ 
	public FoodUnit saveFoodUnit(FoodUnit foodUnit) {
		
		// retrieve the product type if it exists
		String productName = Optional.ofNullable(foodUnit.getProductType()).orElseThrow(() -> new EntityNotFoundException("productName")).getTypeName();
		ProductType productType = productTypeRepository.findProductTypeByTypeName(productName);
		
		if (productType == null) {
			throw new EntityNotFoundException(productName);
		}
		
		foodUnit.setProductType(productType);
	
		Set<String> expectedAttributes = productType.getAttributes();
		Set<String> providedAttributes = foodUnit.getTypeSpecificAttributes().keySet();
		
		// make sure all expected productType specific attributes were provided
		if (!expectedAttributes.isEmpty() && !providedAttributes.containsAll(expectedAttributes))
		{
			expectedAttributes.removeAll(providedAttributes);
			throw new MissingAttributeException(expectedAttributes);
		}
		
		// make sure no unexpected attributes were provided
		if (providedAttributes.size() != expectedAttributes.size()) {
			providedAttributes.removeAll(expectedAttributes);
			throw new UnexpectedAttributeException(providedAttributes);
		}
		
		return foodUnitRepository.save(foodUnit);
	}
	
	/**
	 * Persists the provided product type to the DB
	 * 
	 * @param productType The unique name of the product type
	 * @return The newly created product type
	 * @throws DuplicateException if the product type already exists
	 */
	public ProductType saveProductType(ProductType productType) {
		
		if (productTypeRepository.existsByTypeName(productType.getTypeName())) {
			throw new DuplicateException(productType.getTypeName());
		}
		return productTypeRepository.save(productType);
	}
	
	/**
	 * Returns a list of all the product types currently stored in our DB
	 */
	public List<ProductType> findAllProductTypes() {
		return productTypeRepository.findAll();
	}
	
	/**
	 * Returns a list of all the locations associated with the provided foodUnitId that are currently stored in our DB
	 */
	public List<Location> findLocationsByFoodUnitId(Long foodUnitId) {
		return locationRepository.findLocationByFoodUnit(this.findFoodUnitById(foodUnitId));
	}
	
	/**
	 * When the food unit moves, creates a new location object to track food unit's current location
	 * 
	 * @param newLocation The location to create
	 * @return The newly created location
	 */
	public Location moveFoodUnit(Location newLocation)
	{
		return locationRepository.save(newLocation);
	}
}
