package com.itensis.ecat.validator;

import com.itensis.ecat.domain.ProductFamily;
import com.itensis.ecat.dtos.SaveProductDto;
import com.itensis.ecat.repository.ProductFamilyRepository;
import com.itensis.ecat.repository.ProductRepository;
import com.itensis.ecat.repository.PromotionRepository;
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
    private final ProductRepository productRepository;

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
        if(saveProductDto.getId() != null && saveProductDto.getId() != 0){
            if(productRepository.findById(saveProductDto.getId()).isEmpty()){
                errors.reject("product.id.notValid");
            }
        }

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

        if(saveProductDto.getProductFamilyId() == null || saveProductDto.getProductFamilyId() == 0){
            errors.reject("product.family.notSet");
        }else{
            Optional<ProductFamily> optProductFamily = productFamilyRepository.findById(saveProductDto.getProductFamilyId());
            if(optProductFamily.isEmpty()){
                errors.reject("product.family.notValid");
            }
        }

    }
}
