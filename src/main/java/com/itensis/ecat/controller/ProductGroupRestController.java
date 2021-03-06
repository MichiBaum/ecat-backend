package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.Character;
import com.itensis.ecat.annotation.Numerus;
import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.ProductConverter;
import com.itensis.ecat.converter.ProductGroupConverter;
import com.itensis.ecat.domain.ProductGroup;
import com.itensis.ecat.dtos.ReturnProductDto;
import com.itensis.ecat.dtos.ReturnProductGroupDto;
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
public class ProductGroupRestController {

	private final ProductGroupConverter productGroupConverter;
	private final ProductGroupService productGroupService;
	private final ProductConverter productConverter;

	@CrossOrigin
	@PublicEndpoint
	@ApiOperation(value = "GET all Productgroups with ProductClass and ProductFamily")
	@RequestMapping(value = "/api/productsgroups", method = RequestMethod.GET)
	public List<ReturnProductGroupDto> getProductGroups(){
		return productGroupService.getAll().stream().map(productGroupConverter::toDto).collect(Collectors.toList());
	}

	@CrossOrigin
	@PublicEndpoint(character = Character.DIGIT, numerus = Numerus.PLURAL)
	@ApiOperation(value = "Get all Products from this productgroup")
	@RequestMapping(value = "/api/productgroups/{id}", method = RequestMethod.GET)
	public List<ReturnProductDto> getProductsFromProductGroup(@PathVariable(value = "id") ProductGroup productGroup){
		if(productGroup != null){
			return productGroupService.findByProductGroup(productGroup).stream()
					.map(productConverter::toDto)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

}
