package com.itensis.ecat.converter;

import com.itensis.ecat.domain.Product;
import com.itensis.ecat.domain.ProductFamily;
import com.itensis.ecat.dtos.ReturnProductDto;
import com.itensis.ecat.dtos.SaveProductDto;
import com.itensis.ecat.repository.ProductFamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductConverter {

	private final ProductFamilyRepository productFamilyRepository;

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

	public Product toEntity(SaveProductDto saveProductDto) {
		Optional<ProductFamily> optProductFamily = productFamilyRepository.findById(saveProductDto.getProductFamilyId());
		return optProductFamily.map(
				productFamily -> new Product(
						saveProductDto.getName(),
						saveProductDto.getArticleNr(),
						"",
						saveProductDto.getDescription(),
						saveProductDto.getPrice(),
						new Date().getTime(),
						productFamily)
		).orElse(null);
	}
}
