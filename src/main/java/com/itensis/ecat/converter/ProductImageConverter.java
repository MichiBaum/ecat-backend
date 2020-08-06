package com.itensis.ecat.converter;

import com.itensis.ecat.domain.ProductImage;
import com.itensis.ecat.dtos.ReturnProductImageDto;
import com.itensis.ecat.dtos.SaveProductImageDto;
import com.itensis.ecat.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductImageConverter {

    private final ProductService productService;

    public ProductImage toEntity(SaveProductImageDto saveProductImageDto){
        return new ProductImage(
                saveProductImageDto.getFileName(),
                0L,
                saveProductImageDto.getIndex(),
                productService.get(saveProductImageDto.getProductId()).orElse(null)
        );
    }

    public ReturnProductImageDto toDto(ProductImage productImage){
        return new ReturnProductImageDto(
                productImage.getId(),
                productImage.getImageName(),
                productImage.getImageIndex(),
                "http://localhost:8080/api/products/image/" + productImage.getImageId()
        );
    }
}
