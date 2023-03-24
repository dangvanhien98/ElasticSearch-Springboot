package com.example.Model;

import org.hibernate.validator.constraints.Length;

import com.example.Constraint.NameConstraint;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Customers")
@Entity(name = "CustomerModel")
public class CustomerModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	
	@Email(message = "Email không hợp lệ")
	private String email;
	
	@NotNull(message = "không được để trống")
	@Length(min = 9, max = 16 , message = "password phải từ 9-16 kí tự trở lên")
	private String passWord;
	
	@NameConstraint
	@Column(name = "firstname", columnDefinition = "nvarchar(25)")
	private String firstName;
	
	@NameConstraint
	@Column(name = "lastname", columnDefinition = "nvarchar(25)")
	private String lastName;
	
	@Column(name = "phonenumber", columnDefinition = "nvarchar(15)")
	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "authprovider")
	private AuthenticationProvider authProvider;
	
}
