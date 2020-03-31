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

	public ProductGroup(String name){
		this.name = name;
	}

}
