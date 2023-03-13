package com.example.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Model.CategoryModel;
import com.example.Service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@PostMapping(value = "/insertCategories")
	public ResponseEntity<String> insertCategories(@RequestBody List<CategoryModel> lst) {
		categoryService.insertAllCategory(lst);
		return new ResponseEntity<String>("insert success", HttpStatus.OK);
	}

	@GetMapping(value = "/findByName/{categoryName}")
	public ResponseEntity<List<CategoryModel>> findByName(@PathVariable String categoryName) {
		var lst = categoryService.findByName(categoryName);
		return new ResponseEntity<List<CategoryModel>>(lst, HttpStatus.OK);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<Object> findProductByproducts(@RequestParam String productName) {
		Object lst = categoryService.findByProductName(productName);
		return new ResponseEntity<Object>(lst, HttpStatus.OK);
	}
}
