package com.example.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.Model.ProductModel;

import jakarta.transaction.Transactional;

public interface ProductRepository extends CrudRepository<ProductModel, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "insert into products(product_name, category_id) values (?1, ?2)", nativeQuery = true)
	void insertProduct(String name, int categoryId);
}
