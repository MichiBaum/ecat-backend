package com.itensis.ecat.converter;

import com.itensis.ecat.domain.PromotionImage;
import com.itensis.ecat.dtos.ReturnPromotionImageDto;
import com.itensis.ecat.dtos.SavePromotionImageDto;
import com.itensis.ecat.services.PromotionImageService;
import com.itensis.ecat.services.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionImageConverter {

    private final PromotionService promotionService;
    private final PromotionImageService promotionImageService;

    public PromotionImage toEntity(SavePromotionImageDto savepromotionImageDto){
        return new PromotionImage(
                savepromotionImageDto.getFileName(),
                0L,
                savepromotionImageDto.getIndex(),
                promotionService.get(savepromotionImageDto.getPromotionId()).get()
        );
    }

    public ReturnPromotionImageDto toDto(PromotionImage promotionImage){
        return new ReturnPromotionImageDto(
                promotionImage.getId(),
                promotionImage.getImageName(),
                promotionImage.getImageIndex()
        );
    }
}
