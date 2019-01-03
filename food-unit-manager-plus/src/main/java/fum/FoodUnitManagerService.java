package fum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fum.data.objects.*;
import fum.data.repos.*;
import fum.validation.*;

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

	/**
	 * Deletes a specific food unit
	 * 
	 * @param id The id of the food unit to delete
	 */
	public void deleteFoodUnit(long id) {
		foodUnitRepository.deleteById(id);
	}

	/**
	 * Updates the product type identified in the second parameter with the product type in the first parameter.
	 * Creates the product type with the specified ID if one does not already exist.
	 * 
	 * @param newProductType
	 * @param id The id of the productType to update/create
	 * @return The updated or newly created product type
	 */
	public ProductType updateOrCreateProductType(ProductType newProductType, Long id) {
		return productTypeRepository.findById(id)
			.map(productType -> {
				productType.setTypeName(newProductType.getTypeName());
				return productTypeRepository.save(productType);
			})
			.orElseGet(() -> {
				newProductType.setId(id);
				return productTypeRepository.save(newProductType);
			});
	}
}
