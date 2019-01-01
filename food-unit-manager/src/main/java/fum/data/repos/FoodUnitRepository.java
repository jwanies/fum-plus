package fum.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import fum.data.objects.FoodUnit;

public interface FoodUnitRepository extends JpaRepository<FoodUnit, Long> {

}
