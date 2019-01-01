package fum.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fum.data.objects.FoodUnit;
import fum.data.objects.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
	
	List<Location> findLocationByFoodUnit(FoodUnit fu);
	
}
