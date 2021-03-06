package fum.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fum.FoodUnitManagerService;
import fum.data.objects.FoodUnit;
import fum.validation.FoodUnitValidator;

/**
 * FoodUnitController handles requests to and response from /foodUnits/*
 * 
 * @author jwanies
 *
 */
@RestController
public class FoodUnitController {
	
	@Autowired
	private FoodUnitValidator validator; 
	
	@Autowired
	private FoodUnitManagerService foodUnitManagerService;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
	}
	
	@GetMapping("/foodUnits")
	public List<FoodUnit> all() {
		return foodUnitManagerService.findAllFoodUnits();
	}
	
	@GetMapping("/foodUnits/{id}")
	public FoodUnit one(@PathVariable Long id) {
		return foodUnitManagerService.findFoodUnitById(id);
	}
	
	@DeleteMapping("/foodUnits/{id}")
	public void deleteFoodUnit(@PathVariable long id) {
		foodUnitManagerService.deleteFoodUnit(id);
	}
	
	@PostMapping("/foodUnits")
	public FoodUnit addFoodUnit(@Valid @RequestBody FoodUnit foodUnit) {
		return foodUnitManagerService.saveFoodUnit(foodUnit);
	}
}
