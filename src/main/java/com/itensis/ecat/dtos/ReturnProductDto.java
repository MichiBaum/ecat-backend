package com.itensis.ecat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnProductDto {
	private Long id;
	private String name;
	private String articleNr;
	private String description;
	private Double price;
	private Long creationDate;
	private Long productFamilyId;
}
