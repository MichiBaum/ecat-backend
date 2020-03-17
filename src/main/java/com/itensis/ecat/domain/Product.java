package com.itensis.ecat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Product extends AbstractEntity{

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, name = "article_number")
	private String articleNr;

	@Column(nullable = false)
	private String pictureName;

	@Lob
	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false)
	private Long creationDate;

}
