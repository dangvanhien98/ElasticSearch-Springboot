package com.example.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Model.CategoryModel;
import com.example.Model.ProductModel;
import com.example.Service.CategoryService;
import com.example.Service.ProductService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api")
public class TestController {

	@Autowired
	ProductService cr;

	@Autowired
	CategoryService ct;

	@GetMapping(value = "/CrawProduct")
	public ResponseEntity<List<ProductModel>> CrawProduct() {
		String url = "https://tiki.vn/dien-thoai-may-tinh-bang/c1789";
		List<ProductModel> jsonLst = cr.crawProductByLink(url);
		if (jsonLst.isEmpty())
			return new ResponseEntity<List<ProductModel>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<ProductModel>>(jsonLst, HttpStatus.OK);
	}

	@GetMapping(value = "/CrawCategory")
	public ResponseEntity<String> CrawCategory() {
		List<CategoryModel> LstCategory = ct.CrawAllCategoryAndProduct();
		Gson gs = new Gson();
		String CovertLstToJson = gs.toJson(LstCategory);

		if (CovertLstToJson.isEmpty())
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<String>(CovertLstToJson, HttpStatus.OK);
	}

	@PutMapping(value = "/insertCategory")
	public ResponseEntity<String> InsertCategoryToSql() {
		ct.deleteAllCategoryFromSql();
		List<CategoryModel> lst = ct.CrawAllCategory();
		if (lst.isEmpty())
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		for (CategoryModel item : lst) {
			ct.InsertCategoryToSql(item);
		}
		return new ResponseEntity<String>("Insert Category Success!!!", HttpStatus.OK);
	}

	@PutMapping(value = "/insertProduct")
	public ResponseEntity<String> insertProductToSql() {
		cr.deleteAllProductFromSql();
		List<CategoryModel> lst = ct.CrawAllCategoryAndProduct();
		if (lst.isEmpty())
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		for (int i = 0; i < lst.size(); i++) {
			ArrayList<ProductModel> lstProduct = (ArrayList<ProductModel>) lst.get(i).getProducts();
			if (!lstProduct.isEmpty()) {
				for (int j = 0; j < lstProduct.size(); j++) {
					cr.insertProductToSql(lstProduct.get(j).getProductName(), lst.get(i).getId());
				}			
			}
		}
		return new ResponseEntity<String>("Insert Product Success!!!", HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteAllCategory")
	public ResponseEntity<String> deleteAllCategoryFromSql() {
		ct.deleteAllCategoryFromSql();
		return new ResponseEntity<String>("delete All Category Success!!!", HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteAllProduct")
	public ResponseEntity<String> deleteAllProductFromSql() {
		cr.deleteAllProductFromSql();
		return new ResponseEntity<String>("delete All Product Success!!!", HttpStatus.OK);
	}

	@GetMapping(value = "/getCategoryFromSql")
	public ResponseEntity<List<CategoryModel>> getAllCategoryFromSql() {
		List<CategoryModel> lst = ct.getAllCategoryFromSql();
		List<CategoryModel> tempLst = new ArrayList<CategoryModel>();
		for (int i = 0; i < lst.size(); i++) {
			CategoryModel cate = new CategoryModel(lst.get(i).getId(), lst.get(i).getCategoryName(),
					lst.get(i).getCategoryLink());
			tempLst.add(cate);
		}
		if (tempLst.isEmpty())
			return new ResponseEntity<List<CategoryModel>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<CategoryModel>>(tempLst, HttpStatus.OK);
	}
}
