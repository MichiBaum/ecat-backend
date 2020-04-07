package com.itensis.ecat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROMOTION")
public class Promotion extends AbstractEntity {

	@Column(nullable = false, name = "TITLE")
	private String title;

	@Column(nullable = false, name = "DESCRIPTION")
	private String description;

	@Column(nullable = false, name = "PICTURE_NAME")
	private String pictureName;

	@Column(nullable = false, name = "START_DATE")
	private Long startDate;

	@Column(nullable = false, name = "END_DATE")
	private Long endDate;

	@Column(nullable = false, name = "CREATION_DATE")
	private Long creationDate;

}
