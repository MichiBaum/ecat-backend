package com.itensis.ecat.controller;

import com.itensis.ecat.converter.ProductConverter;
import com.itensis.ecat.domain.Product;
import com.itensis.ecat.dtos.ReturnProductDto;
import com.itensis.ecat.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(value = "Product Endpoints")
public class ProductRestController {

	private final ProductConverter productConverter;
	private final ProductService productService;

	@ApiOperation(value = "GET the Product with the specific ID")
	@RequestMapping(value = "/api/products/{id}", method = RequestMethod.GET)
	public ReturnProductDto getProduct(@PathVariable(value = "id") Product product){
		return productConverter.toDto(product);
	}

	@PreAuthorize("hasAuthority('ADMINISTRATE')")
	@ApiOperation(value = "DELETE the Product with the specific ID")
	@RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable(value = "id") Product product){
		productService.delete(product);
	}

}
