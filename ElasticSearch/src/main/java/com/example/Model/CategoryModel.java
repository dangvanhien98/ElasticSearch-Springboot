package com.example.Model;

import java.util.Collection;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Categories")
@Entity(name = "CategoryModel")
@Document(indexName = "categories")
public class CategoryModel {
	
	@Id
	@Column(name = "CategoryId", columnDefinition = "int")
	private int id;
	
	@Field(type = FieldType.Text, name = "categoryname")
	@Column(name = "CategoryName", columnDefinition = "nvarchar(50)")
	private String categoryName;
	
	@Field(type = FieldType.Text, name = "categorylink")
	@Column(name = "CategoryLink", columnDefinition = "nvarchar(100)")
	private String categoryLink;
	
	@Field(type = FieldType.Text, name = "products")
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Collection<ProductModel> products;
	
	public CategoryModel(int id, String name, String link) {
		this.id = id;
		this.categoryName = name;
		this.categoryLink = link;
	}
	
}
