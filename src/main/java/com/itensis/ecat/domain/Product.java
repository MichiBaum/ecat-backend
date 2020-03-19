package com.itensis.ecat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
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

}
