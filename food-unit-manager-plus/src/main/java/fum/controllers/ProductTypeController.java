package fum.controllers;

/**
 * FoodUnitController handles requests to and response from /productTypes/*
 * 
 * @author jwanies
 *
 */
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fum.FoodUnitManagerService;
import fum.data.objects.ProductType;

@RestController
public class ProductTypeController {
	
	@Autowired
	private FoodUnitManagerService foodUnitManagerService;
	
	@GetMapping("/productTypes")
	public List<ProductType> all() {
		return foodUnitManagerService.findAllProductTypes();
	}
	
	@PutMapping("/productTypes/{id}")
	public ProductType replaceProductType(@RequestBody ProductType newProductType, @PathVariable Long id) {
		return foodUnitManagerService.updateOrCreateProductType(newProductType, id);
	}
	
	@PostMapping("/productTypes")
	public ProductType addProductType(@Valid @RequestBody ProductType productType) {
		return foodUnitManagerService.saveProductType(productType);
	}
}
