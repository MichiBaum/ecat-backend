package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.ProductConverter;
import com.itensis.ecat.domain.Product;
import com.itensis.ecat.dtos.ProductSearchDto;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.itensis.ecat.utilities.OptionalUtilities.ifPresentElseThrow;

@RestController
@RequiredArgsConstructor
@Api(value = "Product Endpoints")
public class ProductRestController {

	private final ProductConverter productConverter;
	private final ProductService productService;

	@PublicEndpoint
	@ApiOperation(value = "GET the Product with the specific ID")
	@RequestMapping(value = "/api/products/{id}", method = RequestMethod.GET)
	public ReturnProductDto getProduct(@PathVariable(value = "id") Optional<Product> product){
		return productConverter.toDto(ifPresentElseThrow(product));
	}

	@PreAuthorize("hasAuthority('ADMINISTRATE')")
	@ApiOperation(value = "DELETE the Product with the specific ID")
	@RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable(value = "id") Optional<Product> product){
		productService.delete(ifPresentElseThrow(product));
	}

	@PublicEndpoint
	@ApiOperation(value = "Endpoint to search for Products")
	@RequestMapping(value = "/api/products/search", method = RequestMethod.GET)
	public List<ReturnProductDto> getProduct(ProductSearchDto productSearchDto){
		if(productSearchDto != null){
			return productService
					.search(productSearchDto)
					.stream()
					.map(productConverter::toDto)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

}
