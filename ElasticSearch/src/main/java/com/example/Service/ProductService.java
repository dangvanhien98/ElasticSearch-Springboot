package com.example.Service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Model.ProductModel;
import com.example.Repository.ProductRepository;

@Service
public class ProductService {
	@Autowired ProductRepository productRepository;
	
	public List<ProductModel> crawProductByLink(String url) {
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements elements = doc.getElementsByClass("name");
			List<ProductModel> listObjects = new ArrayList<ProductModel>();
			for (int i = 0; i < elements.size(); i++) {
				String str = elements.get(i).text();
				ProductModel cr = new ProductModel();
				cr.setId(i+1);
				cr.setProductName(str);
				listObjects.add(cr);
			}
			return listObjects;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAllProductFromSql() {
		productRepository.deleteAll();
	}
	
	public void insertProductToSql(String productName, int categoryId) {
		productRepository.insertProduct(productName, categoryId);
	}
	
}
