package com.itensis.ecat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "PRODUCT_FAMILY")
public class ProductFamily extends AbstractEntity{

	@Column(nullable = false, name = "NAME")
	private String name;

	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Product> products;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProductClass productClass;

	public ProductFamily(String name, List<Product> products, ProductClass productClass){
		this.name = name;
		this.products = products;
		this.productClass = productClass;
	}

}
