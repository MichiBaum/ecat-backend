package com.itensis.ecat.controller;

import com.itensis.ecat.converter.PromotionConverter;
import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.dtos.ReturnPromotionDto;
import com.itensis.ecat.services.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PromotionRestController {

	private final PromotionService promotionService;
	private final PromotionConverter promotionConverter;

	@RequestMapping(value = "/api/promotions", method = RequestMethod.GET)
	public List<ReturnPromotionDto> getAllPromotions(){
		return promotionService.getAll().stream()
				.map(promotionConverter::toDto)
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/api/promotions/{promotion}", method = RequestMethod.GET)
	public ReturnPromotionDto getPromotion(@PathVariable Promotion promotion){
		return promotionConverter.toDto(promotion);
	}

}
