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
@Table(name = "PRODUCT_CLASS")
public class ProductClass extends AbstractEntity {

	@Column(nullable = false, name = "NAME")
	private String name;

	@OneToMany(
			fetch = FetchType.LAZY,
			cascade= {CascadeType.ALL},
			mappedBy = "id"
	)
	private List<ProductFamily> productFamilies;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_GROUP")
	private ProductGroup productGroup;

	public ProductClass(String name, List<ProductFamily> productFamilies, ProductGroup productGroup){
		this.name = name;
		this.productFamilies = productFamilies;
		this.productGroup = productGroup;
	}

}
