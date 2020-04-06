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
@Table(name = "PRODUCT_FAMILY")
public class ProductFamily extends AbstractEntity{

	@Column(nullable = false, name = "NAME")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_CLASS")
	private ProductClass productClass;

	public ProductFamily(String name, ProductClass productClass){
		this.name = name;
		this.productClass = productClass;
	}

}
