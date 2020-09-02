package com.itensis.ecat.services;

import com.itensis.ecat.domain.PromotionImage;
import com.itensis.ecat.repository.PromotionImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PromotionImageService extends BaseImageService<PromotionImage, PromotionImageRepository>{

    @Autowired
    public PromotionImageService(
            PromotionImageRepository promotionImageRepository,
            @Value("${promotion.image.crop}") boolean cropImages,
            @Value("${promotion.image.path}") String imagePath,
            @Value("${promotion.resizedImage.path}") String resizedImagePath,
            @Value("${promotion.image.mimeTypes}") String[] allowedMimeTypes
    ){
        super(
                cropImages,
                imagePath,
                resizedImagePath,
                allowedMimeTypes,
                promotionImageRepository
        );
    }



}
