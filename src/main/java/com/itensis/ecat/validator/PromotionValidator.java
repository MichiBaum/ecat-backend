package com.itensis.ecat.validator;

import com.itensis.ecat.dtos.SavePromotionDto;
import com.itensis.ecat.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PromotionValidator implements Validator {

    private final PromotionRepository promotionRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return SavePromotionDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        SavePromotionDto savePromotionDto = (SavePromotionDto) target;

        validateObject(savePromotionDto, errors);
    }

    private void validateObject(SavePromotionDto savePromotionDto, Errors errors) {
        if(savePromotionDto.getId() != null && savePromotionDto.getId() != 0){
            if(promotionRepository.findById(savePromotionDto.getId()).isEmpty()){
                errors.reject("promotion.id.notValid");
            }
        }

        if(savePromotionDto.getStartDate() == null || savePromotionDto.getStartDate() == 0){
            errors.reject("promotion.startdate.notSet");
        }

        if(savePromotionDto.getEndDate() == null || savePromotionDto.getEndDate() == 0){
            errors.reject("promotion.enddate.notSet");
        }

        if(savePromotionDto.getEndDate() < savePromotionDto.getStartDate()){
            errors.reject("promotion.startdate.biggerThanEnd");
        }

        if(savePromotionDto.getDescription() == null || savePromotionDto.getDescription().isBlank()){
            savePromotionDto.setDescription("");
        }

        if(savePromotionDto.getTitle() == null || savePromotionDto.getTitle().isBlank()){
            errors.reject("promotion.title.notSet");
        }
    }
}
