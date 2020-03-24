package com.itensis.ecat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "PRODUCT_GROUP")
public class ProductGroup extends AbstractEntity {

	@Column(nullable = false, name = "NAME")
	private String name;

	@OneToMany(
			fetch = FetchType.LAZY,
			cascade= {CascadeType.ALL},
			mappedBy = "id"
	)
	private List<ProductClass> productClasses;

	public ProductGroup(String name){
		this.name = name;
		this.productClasses = new ArrayList<>();
	}

}
