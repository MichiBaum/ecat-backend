package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.Character;
import com.itensis.ecat.annotation.Numerus;
import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.ProductConverter;
import com.itensis.ecat.converter.ProductFamilyConverter;
import com.itensis.ecat.domain.ProductFamily;
import com.itensis.ecat.dtos.ReturnProductDto;
import com.itensis.ecat.dtos.ReturnProductFamilyDto;
import com.itensis.ecat.services.ProductFamilyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(value = "Product Group Endpoints")
public class ProductFamilyRestController {

	private final ProductConverter productConverter;
	private final ProductFamilyService productFamilyService;
	private final ProductFamilyConverter productFamilyConverter;

	@CrossOrigin
	@PublicEndpoint(character = Character.DIGIT, numerus = Numerus.PLURAL)
	@ApiOperation(value = "Get all Products from this productfamily")
	@RequestMapping(value = "/api/productfamilies/{id}", method = RequestMethod.GET)
	public List<ReturnProductDto> getProductsFromProductFamily(@PathVariable(value = "id") ProductFamily productFamily){
		if(productFamily != null){
			return productFamilyService.findByProductFamily(productFamily).stream()
					.map(productConverter::toDto)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@CrossOrigin
	@PublicEndpoint //TODO why public
	@ApiOperation(value = "Get all Product families")
	@RequestMapping(value = "/api/productfamilies")
	public List<ReturnProductFamilyDto> getProductFamilies(){
		return this.productFamilyService.getAll().stream().map(productFamilyConverter::toDto).collect(Collectors.toList());
	}

}
