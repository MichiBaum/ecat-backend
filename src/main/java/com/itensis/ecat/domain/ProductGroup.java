package com.itensis.ecat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PRODUCT_GROUP")
public class ProductGroup extends AbstractEntity {

	@Column(nullable = false, name = "NAME")
	private String name;

	@OneToMany
	private List<ProductClass> productClasses;

}
