package com.itensis.ecat.controller;

import com.itensis.ecat.converter.ProductConverter;
import com.itensis.ecat.domain.Product;
import com.itensis.ecat.dtos.ReturnProductDto;
import com.itensis.ecat.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

	private final ProductConverter productCoonverter;
	private final ProductService productService;

	@RequestMapping(value = "/api/products/{product}", method = RequestMethod.GET)
	public ReturnProductDto getProduct(@PathVariable Product product){
		return productCoonverter.toDto(product);
	}

}
