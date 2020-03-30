package com.itensis.ecat.converter;

import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.dtos.ReturnPromotionDto;
import com.itensis.ecat.dtos.SavePromotionDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PromotionConverter {

	public ReturnPromotionDto toDto(Promotion promotion) {
		return new ReturnPromotionDto(
				promotion.getId(),
				promotion.getTitle(),
				promotion.getDescription(),
				promotion.getStartDate(),
				promotion.getEndDate()
		);
	}

	public Promotion toEntity(SavePromotionDto savePromotionDto) {
		return new Promotion(
				savePromotionDto.getTitle(),
				savePromotionDto.getDescription(),
				savePromotionDto.getStartdate(),
				savePromotionDto.getEndDate(),
				new Date().getTime()
		);
	}
}
