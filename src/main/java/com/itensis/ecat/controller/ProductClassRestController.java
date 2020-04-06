package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.Character;
import com.itensis.ecat.annotation.Numerus;
import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.ProductConverter;
import com.itensis.ecat.converter.ProductGroupConverter;
import com.itensis.ecat.domain.ProductClass;
import com.itensis.ecat.domain.ProductGroup;
import com.itensis.ecat.dtos.ReturnProductDto;
import com.itensis.ecat.dtos.ReturnProductGroupDto;
import com.itensis.ecat.services.ProductClassService;
import com.itensis.ecat.services.ProductGroupService;
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
public class ProductClassRestController {

	private final ProductConverter productConverter;
	private final ProductClassService productClassService;

	@CrossOrigin
	@PublicEndpoint(character = Character.DIGIT, numerus = Numerus.PLURAL)
	@ApiOperation(value = "Get all Products from this productclass")
	@RequestMapping(value = "/api/productclasses/{id}", method = RequestMethod.GET)
	public List<ReturnProductDto> getProductsFromProductClass(@PathVariable(value = "id") ProductClass productClass){
		if(productClass != null){
			return productClassService.findByProductClass(productClass).stream()
					.map(productConverter::toDto)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

}
