package com.itensis.ecat.converter;

import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.dtos.ReturnPromotionDto;
import org.springframework.stereotype.Component;

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

}
