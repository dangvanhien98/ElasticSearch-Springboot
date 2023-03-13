package com.example.EsRepository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Model.CategoryModel;

@Repository
public interface CategoryEsRepository extends ElasticsearchRepository<CategoryModel, Integer> {
	void save(List<CategoryModel> lst);
	
	List<CategoryModel> findByCategoryName(String categoryName);
	
	
	List<CategoryModel> findByProducts(String searchType);
}
