package com.itensis.ecat.domain;

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
@Table(name = "PRODUCT_CLASS")
public class ProductClass extends AbstractEntity {

	@Column(nullable = false, name = "NAME")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_GROUP")
	private ProductGroup productGroup;

	public ProductClass(String name, ProductGroup productGroup){
		this.name = name;
		this.productGroup = productGroup;
	}

}
