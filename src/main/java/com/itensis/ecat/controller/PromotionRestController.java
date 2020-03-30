package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.PromotionConverter;
import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.dtos.ReturnPromotionDto;
import com.itensis.ecat.dtos.SavePromotionDto;
import com.itensis.ecat.services.PromotionService;
import com.itensis.ecat.validator.PromotionValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
	private final PromotionValidator promotionValidator;

	@InitBinder("savePromotionDto")
	public void initSavePromotionBinder(WebDataBinder binder) {
		binder.setValidator(promotionValidator);
	}

	@PublicEndpoint
	@ApiOperation(value = "GET all Promotions")
	@RequestMapping(value = "/api/promotions", method = RequestMethod.GET)
	public List<ReturnPromotionDto> getAllPromotions(){
		return promotionService.getAll().stream()
				.map(promotionConverter::toDto)
				.collect(Collectors.toList());
	}

	@PublicEndpoint
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

	@PreAuthorize("hasAuthority('ADMINISTRATE')")
	@ApiOperation(value = "Update or create a Promotion")
	@RequestMapping(value = "/api/promotions/save", method = RequestMethod.POST)
	public ResponseEntity savePromotion(@RequestBody @Valid SavePromotionDto savePromotionDto){
		Promotion promotion;
		if(savePromotionDto.getId() == null || savePromotionDto.getId() == 0){
			promotion = promotionConverter.toEntity(savePromotionDto);
		}else{
			Optional<Promotion> oldPromotion = promotionService.get(savePromotionDto.getId());
			if(oldPromotion.isPresent()) {
				promotion = promotionService.update(oldPromotion.get(), savePromotionDto);
			}else {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity(promotionConverter.toDto(promotionService.save(promotion)), HttpStatus.OK);
	}

}
