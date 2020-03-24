package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.ProductGroupConverter;
import com.itensis.ecat.dtos.ReturnProductGroupDto;
import com.itensis.ecat.services.ProductGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(value = "Product Group Endpoints")
public class ProductGroupRestController {

	private final ProductGroupConverter productGroupConverter;
	private final ProductGroupService productGroupService;

	@PublicEndpoint
	@ApiOperation(value = "GET all Productgroups with ProductClass and ProductFamily")
	@RequestMapping(value = "/api/productsgroups", method = RequestMethod.GET)
	public List<ReturnProductGroupDto> getProductGroups(){
		return productGroupService.getAll().stream().map(productGroupConverter::toDto).collect(Collectors.toList());
	}

}
