package fum.validation;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fum.data.objects.Attributes;
import fum.data.objects.FoodUnit;
import fum.data.objects.ProductType;
import fum.data.repos.ProductTypeRepository;

@Component
public class FoodUnitValidator implements Validator {

	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return FoodUnit.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FoodUnit newFoodUnit = (FoodUnit) target;
		
		// retrieve the product type if it exists
		String productName = Optional.ofNullable(newFoodUnit.getProductType()).orElseThrow(() -> new EntityNotFoundException("productName")).getTypeName();
		ProductType productType = productTypeRepository.findProductTypeByTypeName(productName);
		
		// if it does not exist, throw an exception since valid product type must be provided
		if (productType == null) {
			throw new EntityNotFoundException(productName);
		}
		
		newFoodUnit.setProductType(productType);
		
		// validate the type specific attributes
		validateTypeSpecificAttributes(productType.getAttributes(), newFoodUnit.getTypeSpecificAttributes());
	}

	private void validateTypeSpecificAttributes(Set<Attributes> expectedAttributes, Map<String, String> typeSpecificAttributes) {
		
		ArrayList<String> providedAttributeList = new ArrayList<String>(typeSpecificAttributes.keySet());
		
		for (Attributes attribute : expectedAttributes) {
			
			String providedAttributeValue = typeSpecificAttributes.get(attribute.getAttribute());
			
			// if an expected attribute was not provided...
			if (providedAttributeValue == null) {
				throw new MissingAttributeException(attribute.getAttribute());
			}
			
			// make sure the attribute value meets requirements specified by the attribute validation regex
			if (attribute.getPattern() != null && !providedAttributeValue.matches(attribute.getPattern())) {
				throw new InvalidAttributeValueException(attribute);
			}
			
			providedAttributeList.remove(attribute.getAttribute());
		}
		
		// make sure no unexpected attributes were provided
		if (!providedAttributeList.isEmpty()) {
			throw new UnexpectedAttributeException(providedAttributeList);
		}
	}

}
