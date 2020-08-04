package com.itensis.ecat.validator;

import com.itensis.ecat.dtos.SavePromotionImageDto;
import com.itensis.ecat.repository.PromotionImageRepository;
import com.itensis.ecat.repository.PromotionRepository;
import com.itensis.ecat.services.PromotionImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PromotionImageValidator implements Validator {

    private final PromotionRepository promotionRepository;
    private final PromotionImageRepository promotionImageRepository;
    private final PromotionImageService promotionImageService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return SavePromotionImageDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        SavePromotionImageDto savePromotionImageDto = (SavePromotionImageDto) target;
        validateObject(savePromotionImageDto, errors);
    }

    private void validateObject(SavePromotionImageDto savePromotionImageDto, Errors errors) {
        if(savePromotionImageDto.getId() != null && savePromotionImageDto.getId() != 0){
            if(promotionImageRepository.findById(savePromotionImageDto.getId()).isEmpty()){
                errors.reject("promotionImage.id.notValid");
            }
        }
        if(savePromotionImageDto.getPromotionId() != null && savePromotionImageDto.getPromotionId() != 0){
            if(promotionRepository.findById(savePromotionImageDto.getPromotionId()).isEmpty()){
                errors.reject("promotionImage.promotionId.notValid");
            }
        }else{
            errors.reject("promotionImage.promotionId.empty");
        }

        if(savePromotionImageDto.getIndex() == null){
            errors.reject("promotionImage.index.empty");
        }
        if(savePromotionImageDto.getFile() == null){
            errors.reject("promotionImage.file.empty");
        }else{
            if(!promotionImageService.validImageType(savePromotionImageDto.getFile())){
                errors.reject("promotionImage.file.invalidType");
            }
            if(savePromotionImageDto.getFileName() == null || savePromotionImageDto.getFileName().isBlank()){
                errors.reject("promotionImage.fileName.notValid");
            }
        }

    }

}
