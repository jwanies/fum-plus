package fum.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import fum.data.objects.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

	ProductType findProductTypeByTypeName(String typeName);
	boolean existsByTypeName(String typename);
}
