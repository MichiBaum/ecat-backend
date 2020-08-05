package com.itensis.ecat.converter;

import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.dtos.ReturnPromotionDto;
import com.itensis.ecat.dtos.SavePromotionDto;
import com.itensis.ecat.repository.PromotionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PromotionConverter {

	private final PromotionImageRepository promotionImageRepository;
	private final PromotionImageConverter promotionImageConverter;

	public ReturnPromotionDto toDto(Promotion promotion) {
		return new ReturnPromotionDto(
				promotion.getId(),
				promotion.getTitle(),
				promotion.getDescription(),
				promotion.getStartDate(),
				promotion.getEndDate(),
				promotionImageRepository.findAllByPromotionId(promotion.getId()).stream().map(promotionImageConverter::toDto).collect(Collectors.toList())
		);
	}

	public Promotion toEntity(SavePromotionDto savePromotionDto) {
		return new Promotion(
				savePromotionDto.getTitle(),
				savePromotionDto.getDescription(),
				savePromotionDto.getStartDate(),
				savePromotionDto.getEndDate(),
				new Date().getTime()
		);
	}
}
