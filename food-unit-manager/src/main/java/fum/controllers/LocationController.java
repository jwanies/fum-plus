package fum.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fum.FoodUnitManagerService;
import fum.data.objects.Location;

/**
 * FoodUnitController handles requests to and response from /locations/*
 * 
 * @author jwanies
 *
 */
@RestController
public class LocationController {
	
	@Autowired
	private FoodUnitManagerService foodUnitManagerService;
		
	@PostMapping("/locations")
	public Location addLocation(@Valid @RequestBody Location newLocation)
	{
		return foodUnitManagerService.moveFoodUnit(newLocation);
	}
	
	@GetMapping("/locations")
	public List<Location> byFoodUnitId(@RequestParam("foodUnitId") Long foodUnitId)
	{
		return foodUnitManagerService.findLocationsByFoodUnitId(foodUnitId);
	}
	
	
}
