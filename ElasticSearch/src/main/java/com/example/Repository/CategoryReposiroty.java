package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Model.CategoryModel;

@Repository
public interface CategoryReposiroty extends CrudRepository<CategoryModel, Integer>{
	@Query(value = "select * from categories c", nativeQuery = true)
	List<CategoryModel> findAll();
}
