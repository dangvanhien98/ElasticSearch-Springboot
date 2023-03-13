package com.example.Service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import com.example.EsRepository.CategoryEsRepository;
import com.example.Model.CategoryModel;
import com.example.Model.ProductModel;
import com.example.Repository.CategoryReposiroty;

@Service
public class CategoryService {

	@Autowired
	ProductService cr;

	@Autowired
	CategoryReposiroty categoryReposiroty;

	public List<CategoryModel> CrawAllCategoryAndProduct() {
		try {
			List<CategoryModel> lst = new ArrayList<CategoryModel>();
			String url = "https://tiki.vn/";
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements elements = doc.getElementsByClass("styles__StyledItem-sc-oho8ay-0 bzmzGe");
			for (int i = 9; i < elements.size() - 1; i++) {
				String categoryName = elements.get(i).text();
				Elements tagA = elements.get(i).getElementsByTag("a");
				String href = tagA.get(0).absUrl("href");
				CategoryModel cm = new CategoryModel();
				cm.setId(i);
				cm.setCategoryName(categoryName);
				cm.setCategoryLink(href);
				List<ProductModel> lstProduct = cr.crawProductByLink(href);
				cm.setProducts(lstProduct);
				lst.add(cm);
			}
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<CategoryModel> CrawAllCategory() {
		try {
			List<CategoryModel> lst = new ArrayList<CategoryModel>();
			String url = "https://tiki.vn/";
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements elements = doc.getElementsByClass("styles__StyledItem-sc-oho8ay-0 bzmzGe");
			for (int i = 9; i < elements.size() - 1; i++) {
				String categoryName = elements.get(i).text();
				Elements tagA = elements.get(i).getElementsByTag("a");
				String href = tagA.get(0).absUrl("href");
				CategoryModel cm = new CategoryModel();
				cm.setId(i);
				cm.setCategoryName(categoryName);
				cm.setCategoryLink(href);
				lst.add(cm);
			}
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void InsertCategoryToSql(CategoryModel ca) {
		categoryReposiroty.save(ca);
	}

	public void deleteAllCategoryFromSql() {
		categoryReposiroty.deleteAll();
	}

	public List<CategoryModel> getAllCategoryFromSql() {
		return categoryReposiroty.findAll();
	}

	// Elasticsearch
	@Autowired
	CategoryEsRepository categoryEsRepository;

	public void insertAllCategory(List<CategoryModel> lst) {
		categoryEsRepository.save(lst);
	}

	public List<CategoryModel> findByName(String categoryName) {
		return categoryEsRepository.findByCategoryName(categoryName);
	}

	public List<CategoryModel> findByProducts(String searchType) {
		return categoryEsRepository.findByProducts(searchType);
	}

	@Autowired
	ElasticsearchOperations elasticsearchOperations;

	public Object findByProductName(String productName) {
		Query searchQuery = new StringQuery("{\"match\":{\"products.productName\":{\"query\":\"N'"+ productName +"' \"}}}\"");
		SearchHits<CategoryModel> categories = elasticsearchOperations.search(searchQuery, CategoryModel.class,
				IndexCoordinates.of("categories"));
		Object lst = categories.getSearchHits();
		return lst;
	}
}
