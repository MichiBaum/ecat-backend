package com.itensis.ecat.validator;

import com.itensis.ecat.dtos.SavePromotionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PromotionValidator implements Validator {
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return SavePromotionDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        SavePromotionDto savePromotionDto = (SavePromotionDto) target;


    }
}
