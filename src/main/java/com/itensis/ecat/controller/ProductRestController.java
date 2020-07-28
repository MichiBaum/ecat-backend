package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.Character;
import com.itensis.ecat.annotation.Numerus;
import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.ProductConverter;
import com.itensis.ecat.domain.Product;
import com.itensis.ecat.dtos.ProductSearchDto;
import com.itensis.ecat.dtos.ReturnProductDto;
import com.itensis.ecat.dtos.SaveProductDto;
import com.itensis.ecat.services.ProductService;
import com.itensis.ecat.validator.ProductValidator;
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
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(value = "Product Endpoints")
public class ProductRestController {

	private final ProductConverter productConverter;
	private final ProductService productService;
	private final ProductValidator productValidator;

	@Resource
	private Environment environment;

	@InitBinder("saveProductDto")
	public void initSaveProductBinder(WebDataBinder binder) {
		binder.setValidator(productValidator);
	}

	@CrossOrigin
	@PublicEndpoint(character = Character.DIGIT, numerus = Numerus.PLURAL)
	@ApiOperation(value = "GET the Product with the specific ID")
	@RequestMapping(value = "/api/products/{id}", method = RequestMethod.GET)
	public ReturnProductDto getProduct(@PathVariable(value = "id") Product product){
		return productConverter.toDto(product);
	}

	@CrossOrigin
	@PreAuthorize("hasAuthority('ADMINISTRATE')")
	@ApiOperation(value = "DELETE the Product with the specific ID")
	@RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable(value = "id") Product product){
		productService.delete(product);
	}

	@CrossOrigin
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

	@CrossOrigin
	@PreAuthorize("hasAuthority('ADMINISTRATE')")
	@ApiOperation(value = "Update or create a Product")
	@RequestMapping(value = "/api/products/save", method = RequestMethod.POST)
	public ResponseEntity saveProduct(@RequestBody @Valid SaveProductDto saveProductDto){
		Product product;
		if(saveProductDto.getId() == null || saveProductDto.getId() == 0){
			product = productConverter.toEntity(saveProductDto);
			if(product == null){
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
		}else{
			Optional<Product> oldProduct = productService.get(saveProductDto.getId());
			if(oldProduct.isPresent()) {
				product = productService.update(oldProduct.get(), saveProductDto);
			}else {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity(productConverter.toDto(productService.save(product)), HttpStatus.OK);
	}

	@CrossOrigin
	@PreAuthorize("hasAuthority('ADMINISTRATE')")
	@ApiOperation(value = "UPDATE imagepath for product with specific ID")
	@RequestMapping(value = "/api/products/image/{id}", method = RequestMethod.POST)
	public ResponseEntity saveProductImage(@PathVariable(value = "id") Product product, @RequestParam("image")MultipartFile image){
		String imagePath = environment.getRequiredProperty("product.image.path") + image.getOriginalFilename();
		String[] allowedTypes = environment.getRequiredProperty("product.image.types", String[].class);
		if(!Arrays.asList(allowedTypes).contains(image.getOriginalFilename().split("\\.")[1])){
			Map<String, String> responseMap = new HashMap();
			responseMap.put("errorMsg", "product.image.invalidType");
			return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
		}
		try{
			image.transferTo(new File(imagePath));
		} catch (Exception e){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		product.setPictureName(image.getOriginalFilename());
		productService.save(product);
		return new ResponseEntity(HttpStatus.OK);
	}

	@CrossOrigin
	@PublicEndpoint //TODO why public
	@ApiOperation(value = "Get all Products")
	@RequestMapping(value = "/api/products")
	public List<ReturnProductDto> getProducts(){
		return productService.getAll().stream().map(productConverter::toDto).collect(Collectors.toList());
	}

}
