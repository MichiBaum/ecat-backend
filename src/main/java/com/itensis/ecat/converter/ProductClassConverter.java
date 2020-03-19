package com.itensis.ecat.converter;

import com.itensis.ecat.domain.ProductClass;
import com.itensis.ecat.dtos.ReturnProductClassDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductClassConverter {

	private final ProductFamilyConverter productFamilyConverter;

	public ReturnProductClassDto toDto(ProductClass productClass){
		return new ReturnProductClassDto(
				productClass.getId(),
				productClass.getName(),
				productClass.getProductFamilies().stream().map(productFamilyConverter::toDto).collect(Collectors.toList())
		);
	}

}
