package com.itensis.ecat.validator;

import com.itensis.ecat.domain.ProductFamily;
import com.itensis.ecat.dtos.SaveProductDto;
import com.itensis.ecat.repository.ProductFamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductValidator implements Validator {

    private final ProductFamilyRepository productFamilyRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return SaveProductDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        SaveProductDto saveProductDto = (SaveProductDto) target;
        validateObject(saveProductDto, errors);
    }

    private void validateObject(SaveProductDto saveProductDto, Errors errors) {
        if(saveProductDto.getDescription() == null || saveProductDto.getDescription().isBlank()){
            saveProductDto.setDescription("");
        }

        if(saveProductDto.getArticleNr() == null ) {
            errors.reject("product.articleNr.notSet");
        }

        if(saveProductDto.getName() == null || saveProductDto.getName().isBlank()){
            errors.reject("product.name.notSet");
        }

        if(saveProductDto.getPrice() == null || saveProductDto.getPrice().isNaN()){
            errors.reject("product.price.notSet");
        }

        if(saveProductDto.getProductFamilyId() == null){
            errors.reject("product.family.notSet");
        }else{
            Optional<ProductFamily> optProductFamily = productFamilyRepository.findById(saveProductDto.getProductFamilyId());
            if(optProductFamily.isEmpty()){
                errors.reject("product.family.notValid");
            }
        }

    }
}
