package com.itensis.ecat.converter;

import com.itensis.ecat.domain.ProductFamily;
import com.itensis.ecat.dtos.ReturnProductFamilyDto;
import org.springframework.stereotype.Component;

@Component
public class ProductFamilyConverter {

	public ReturnProductFamilyDto toDto(ProductFamily productFamily){
		return new ReturnProductFamilyDto(
				productFamily.getId(),
				productFamily.getName()
		);
	}

}
