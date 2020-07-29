package com.itensis.ecat.services;

import com.itensis.ecat.domain.PromotionImage;
import com.itensis.ecat.repository.PromotionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PromotionImageService {

    private final PromotionImageRepository promotionImageRepository;

    @Resource
    private final Environment environment;

    public boolean validImageType(MultipartFile image){
        String[] allowedTypes = environment.getRequiredProperty("promotion.image.mimeTypes", String[].class);
        if(!Arrays.asList(allowedTypes).contains(image.getContentType())){
            return false;

        }
        return true;
    }

    public void savePromotionImage(PromotionImage promotionImage, MultipartFile image) throws IOException {
        this.promotionImageRepository.saveAndFlush(promotionImage);
        promotionImage.setImageId(promotionImage.getId());
        this.promotionImageRepository.saveAndFlush(promotionImage);
        image.transferTo(new File(environment.getRequiredProperty("promotion.image.path") + promotionImage.getImageId()));
    }

}
