package com.itensis.ecat.controller;

import com.itensis.ecat.converter.ProductGroupConverter;
import com.itensis.ecat.dtos.ReturnProductGroupDto;
import com.itensis.ecat.services.ProductGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductGroupRestController {

	private final ProductGroupConverter productGroupConverter;
	private final ProductGroupService productGroupService;

	@RequestMapping(value = "/api/productsgroups", method = RequestMethod.GET)
	public List<ReturnProductGroupDto> getProductGroups(){
		return productGroupService.getAll().stream().map(productGroupConverter::toDto).collect(Collectors.toList());
	}

}
