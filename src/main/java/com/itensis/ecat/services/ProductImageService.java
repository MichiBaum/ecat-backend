package com.itensis.ecat.services;

import com.itensis.ecat.domain.ProductImage;
import com.itensis.ecat.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductImageService extends BaseImageService<ProductImage, ProductImageRepository>{

    @Autowired
    public ProductImageService(
            ProductImageRepository productImageRepository,
            @Value("${product.image.crop}") boolean cropImages,
            @Value("${product.image.path}") String imagePath,
            @Value("${product.resizedImage.path}") String resizedImagePath,
            @Value("${product.image.mimeTypes}") String[] allowedMimeTypes
    ){
        super(
                cropImages,
                imagePath,
                resizedImagePath,
                allowedMimeTypes,
                productImageRepository
        );
    }
}
