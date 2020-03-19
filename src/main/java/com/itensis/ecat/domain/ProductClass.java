package com.itensis.ecat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT_CLASS")
public class ProductClass extends AbstractEntity {

	@Column(nullable = false, name = "NAME")
	private String name;

	@OneToMany
	private List<ProductFamily> productFamilies;

}
