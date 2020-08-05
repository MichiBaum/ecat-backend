package com.itensis.ecat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPromotionDto {
	private Long id;
	private String title;
	private String description;
	private Long startDate;
	private Long endDate;
	private List<ReturnPromotionImageDto> returnPromotionImageDtos;
}
