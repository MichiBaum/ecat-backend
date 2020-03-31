package com.itensis.ecat.converter;

import com.itensis.ecat.domain.ProductClass;
import com.itensis.ecat.domain.ProductFamily;
import com.itensis.ecat.dtos.ReturnProductClassDto;
import com.itensis.ecat.repository.ProductFamilyRepository;
import com.itensis.ecat.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductClassConverter {

	private final ProductFamilyConverter productFamilyConverter;
	private final ProductService productService;

	public ReturnProductClassDto toDto(ProductClass productClass){
		List<ProductFamily> productFamilies = productService.findProductFamiliesBy(productClass);
		return new ReturnProductClassDto(
				productClass.getId(),
				productClass.getName(),
				productFamilies.stream().map(productFamilyConverter::toDto).collect(Collectors.toList())
		);
	}

}
