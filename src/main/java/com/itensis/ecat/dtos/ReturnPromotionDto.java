package com.itensis.ecat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPromotionDto {
	private Long id;
	private String title;
	private String description;
	private String pictureName;
	private Long startDate;
	private Long endDate;
}
