package com.itensis.ecat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product extends AbstractEntity{

	@Column(nullable = false, name = "NAME")
	private String name;

	@Column(nullable = false, name = "ARTICLE_NUMBER", length = 18)
	private String articleNr;

	@Column(nullable = false, name = "PICTURE_NAME")
	private String pictureName;

	@Lob
	@Column(nullable = false, name = "DESCRIPTION")
	private String description;

	@Column(nullable = false, name = "PRICE")
	private Double price;

	@Column(nullable = false, name = "CREATION_DATE")
	private Long creationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_FAMILY")
	private ProductFamily productFamily;

	public Product(String name, String articleNr, String pictureName, String description, Double price, Long creationDate, ProductFamily productFamily){
		this.name = name;
		this.articleNr = articleNr;
		this.pictureName = pictureName;
		this.description = description;
		this.price = price;
		this.creationDate = creationDate;
		this.productFamily = productFamily;
	}

}
