package com.itensis.ecat.converter;

import com.itensis.ecat.domain.Product;
import com.itensis.ecat.domain.ProductFamily;
import com.itensis.ecat.dtos.ReturnProductDto;
import com.itensis.ecat.dtos.ReturnProductImageDto;
import com.itensis.ecat.dtos.SaveProductDto;
import com.itensis.ecat.repository.ProductFamilyRepository;
import com.itensis.ecat.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductConverter {

	private final ProductFamilyRepository productFamilyRepository;
	private final ProductImageRepository productImageRepository;
	private final ProductImageConverter productImageConverter;

	public ReturnProductDto toDto(Product product){
		return new ReturnProductDto(
				product.getId(),
				product.getName(),
				product.getArticleNr(),
				product.getDescription(),
				product.getPrice(),
				product.getCreationDate(),
				product.getProductFamily().getId(),
				productImageRepository.
						findAllByProductId(product.getId())
						.stream()
						.map(productImageConverter::toDto)
						.sorted(Comparator.comparing(ReturnProductImageDto::getIndex))
						.collect(Collectors.toList())

		);
	}

	public Product toEntity(SaveProductDto saveProductDto) {
		Optional<ProductFamily> optProductFamily = productFamilyRepository.findById(saveProductDto.getProductFamilyId());
		return optProductFamily.map(
				productFamily -> new Product(
						saveProductDto.getName(),
						saveProductDto.getArticleNr(),
						saveProductDto.getDescription(),
						saveProductDto.getPrice(),
						new Date().getTime(),
						productFamily)
		).orElse(null);
	}
}
