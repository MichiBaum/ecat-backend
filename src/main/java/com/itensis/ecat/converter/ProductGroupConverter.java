package com.itensis.ecat.converter;

import com.itensis.ecat.domain.ProductGroup;
import com.itensis.ecat.dtos.ReturnProductGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductGroupConverter {

	private final ProductClassConverter productClassConverter;

	public ReturnProductGroupDto toDto(ProductGroup productGroup){
		return new ReturnProductGroupDto(
				productGroup.getId(),
				productGroup.getName(),
				productGroup.getProductClasses().stream().map(productClassConverter::toDto).collect(Collectors.toList())
		);
	}

}
