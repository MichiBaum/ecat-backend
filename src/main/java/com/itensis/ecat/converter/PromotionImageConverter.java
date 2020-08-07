package com.itensis.ecat.converter;

import com.itensis.ecat.domain.PromotionImage;
import com.itensis.ecat.dtos.ReturnPromotionImageDto;
import com.itensis.ecat.dtos.SavePromotionImageDto;
import com.itensis.ecat.services.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RequiredArgsConstructor
public class PromotionImageConverter {

    @Resource
    private final Environment environment;

    private final PromotionService promotionService;

    public PromotionImage toEntity(SavePromotionImageDto savepromotionImageDto){
        return new PromotionImage(
                savepromotionImageDto.getFileName(),
                0L,
                savepromotionImageDto.getIndex(),
                promotionService.get(savepromotionImageDto.getPromotionId()).orElse(null)
        );
    }

    public ReturnPromotionImageDto toDto(PromotionImage promotionImage){
        return new ReturnPromotionImageDto(
                promotionImage.getId(),
                promotionImage.getImageName(),
                promotionImage.getImageIndex(),
                environment.getRequiredProperty("server.url") + "/api/promotions/image/" + promotionImage.getImageId()
        );
    }
}
