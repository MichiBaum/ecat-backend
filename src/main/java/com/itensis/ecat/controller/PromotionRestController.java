package com.itensis.ecat.controller;

import com.itensis.ecat.converter.PromotionConverter;
import com.itensis.ecat.dtos.PromotionDto;
import com.itensis.ecat.services.PromotionService;
import lombok.RequiredArgsConstructor;
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
	public List<PromotionDto> getAllPromotions(){
		return promotionService.getAll().stream()
				.map(promotionConverter::toDto)
				.collect(Collectors.toList());
	}

}
