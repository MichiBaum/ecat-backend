package com.itensis.ecat.converter;

import com.itensis.ecat.domain.ProductClass;
import com.itensis.ecat.domain.ProductGroup;
import com.itensis.ecat.dtos.ReturnProductGroupDto;
import com.itensis.ecat.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductGroupConverter {

	private final ProductClassConverter productClassConverter;
	private final ProductService productService;

	public ReturnProductGroupDto toDto(ProductGroup productGroup){
		List<ProductClass> productClasses = productService.findProductClassesBy(productGroup);
		return new ReturnProductGroupDto(
				productGroup.getId(),
				productGroup.getName(),
				productClasses.stream().map(productClassConverter::toDto).collect(Collectors.toList())
		);
	}

}
