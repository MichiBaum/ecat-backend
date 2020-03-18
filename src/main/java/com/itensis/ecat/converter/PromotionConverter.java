package com.itensis.ecat.converter;

import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.dtos.PromotionDto;
import org.springframework.stereotype.Component;

@Component
public class PromotionConverter {

	public PromotionDto toDto(Promotion promotion) {
		return new PromotionDto(
				promotion.getId(),
				promotion.getTitle(),
				promotion.getDescription(),
				promotion.getStartDate(),
				promotion.getEndDate()
		);
	}

}
