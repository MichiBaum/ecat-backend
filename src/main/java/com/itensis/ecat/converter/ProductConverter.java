package com.itensis.ecat.converter;

import com.itensis.ecat.domain.Product;
import com.itensis.ecat.dtos.ReturnProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

	public ReturnProductDto toDto(Product product){
		return new ReturnProductDto(
				product.getId(),
				product.getName(),
				product.getArticleNr(),
				product.getPictureName(),
				product.getDescription(),
				product.getPrice(),
				product.getCreationDate()
		);
	}

}
