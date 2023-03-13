package com.example.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Products")
@Entity(name = "ProductModel")
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProductId", columnDefinition = "int")
	private int id;
	
	@Column(name = "ProductName", columnDefinition = "nvarchar(250)")
	private String productName;
	
	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private CategoryModel category;
	
	public ProductModel(int id, String name) {
		this.id = id;
		this.productName = name;
	}
}
