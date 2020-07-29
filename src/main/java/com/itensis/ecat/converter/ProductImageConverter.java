package com.itensis.ecat.converter;

import com.itensis.ecat.domain.ProductImage;
import com.itensis.ecat.dtos.SaveProductImageDto;
import com.itensis.ecat.services.ProductImageService;
import com.itensis.ecat.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductImageConverter {

    private final ProductImageService productImageService;
    private final ProductService productService;

    public ProductImage toEntity(SaveProductImageDto saveProductImageDto){
        return new ProductImage(
                saveProductImageDto.getImageName(),
                0L,
                saveProductImageDto.getIndex(),
                productService.get(saveProductImageDto.getProductId()).get()
        );
    }
}
