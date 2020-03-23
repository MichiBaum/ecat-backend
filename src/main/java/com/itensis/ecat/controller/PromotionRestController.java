package com.itensis.ecat.controller;

import com.itensis.ecat.converter.PromotionConverter;
import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.dtos.ReturnPromotionDto;
import com.itensis.ecat.services.PromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.itensis.ecat.utilities.OptionalUtilities.ifPresentElseThrow;

@RestController
@RequiredArgsConstructor
@Api(value = "Promotion Endpoints")
public class PromotionRestController {

	private final PromotionService promotionService;
	private final PromotionConverter promotionConverter;

	@ApiOperation(value = "GET all Promotions")
	@RequestMapping(value = "/api/promotions", method = RequestMethod.GET)
	public List<ReturnPromotionDto> getAllPromotions(){
		return promotionService.getAll().stream()
				.map(promotionConverter::toDto)
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "GET the Promotions with the specific ID")
	@RequestMapping(value = "/api/promotions/{id}", method = RequestMethod.GET)
	public ReturnPromotionDto getPromotion(@PathVariable(value = "id") Optional<Promotion> promotion){
		return promotionConverter.toDto(ifPresentElseThrow(promotion));
	}

	@PreAuthorize("hasAuthority('ADMINISTRATE')")
	@ApiOperation(value = "DELETE the Promotion with the specific ID")
	@RequestMapping(value = "/api/promotions/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable(value = "id") Optional<Promotion> promotion){
		promotionService.delete(ifPresentElseThrow(promotion));
	}

}
