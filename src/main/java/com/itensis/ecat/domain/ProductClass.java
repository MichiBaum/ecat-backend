package com.itensis.ecat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class ProductClass extends AbstractEntity {

	@Column(nullable = false)
	private String name;

	@OneToMany
	private List<ProductFamily> productFamilies;

}
