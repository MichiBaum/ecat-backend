package com.itensis.ecat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class Product extends AbstractEntity{

	@Column(nullable = false, name = "NAME")
	private String name;

	@Column(nullable = false, name = "ARTICLE_NUMBER", length = 18)
	private String articleNr;

	@Lob
	@Column(nullable = false, name = "DESCRIPTION")
	private String description;

	@Column(nullable = false, name = "PRICE")
	private Double price;

	@Column(nullable = false, name = "CREATION_DATE")
	private Long creationDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_FAMILY")
	private ProductFamily productFamily;

}
