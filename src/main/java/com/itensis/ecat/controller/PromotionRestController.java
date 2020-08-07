package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.Character;
import com.itensis.ecat.annotation.Numerus;
import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.PromotionConverter;
import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.dtos.ReturnPromotionDto;
import com.itensis.ecat.dtos.SavePromotionDto;
import com.itensis.ecat.services.ProductImageService;
import com.itensis.ecat.services.PromotionService;
import com.itensis.ecat.validator.PromotionValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(value = "Promotion Endpoints")
public class PromotionRestController {

	private final PromotionService promotionService;
	private final PromotionConverter promotionConverter;
	private final PromotionValidator promotionValidator;
	private final ProductImageService productImageService;

	@Resource
	private Environment environment;

	@InitBinder("savePromotionDto")
	public void initSavePromotionBinder(WebDataBinder binder) {
		binder.setValidator(promotionValidator);
	}

	@CrossOrigin
	@PublicEndpoint
	@ApiOperation(value = "GET all Promotions")
	@RequestMapping(value = "/api/promotions", method = RequestMethod.GET)
	public List<ReturnPromotionDto> getAllPromotions(){
		return promotionService.getAll().stream()
				.map(promotionConverter::toDto)
				.collect(Collectors.toList());
	}

	@CrossOrigin
	@PublicEndpoint(character = Character.DIGIT, numerus = Numerus.PLURAL)
	@ApiOperation(value = "GET the Promotions with the specific ID")
	@RequestMapping(value = "/api/promotions/{id}", method = RequestMethod.GET)
	public ReturnPromotionDto getPromotion(@PathVariable(value = "id") Promotion promotion){
		return promotionConverter.toDto(promotion);
	}

	@CrossOrigin
	@PreAuthorize("hasAuthority('ADMINISTRATE_PROMOTIONS')")
	@ApiOperation(value = "DELETE the Promotion with the specific ID")
	@RequestMapping(value = "/api/promotions/{id}", method = RequestMethod.DELETE)
	public void deletePromotion(@PathVariable(value = "id") Promotion promotion){
		promotionService.delete(promotion);
	}

	@CrossOrigin
	@PreAuthorize("hasAuthority('ADMINISTRATE_PROMOTIONS')")
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
