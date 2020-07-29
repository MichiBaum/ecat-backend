package com.itensis.ecat.validator;

import com.itensis.ecat.domain.ProductFamily;
import com.itensis.ecat.dtos.SaveProductDto;
import com.itensis.ecat.dtos.SaveProductImageDto;
import com.itensis.ecat.repository.ProductImageRepository;
import com.itensis.ecat.repository.ProductRepository;
import com.itensis.ecat.services.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductImageValidator implements Validator {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductImageService productImageService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return SaveProductImageDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        SaveProductImageDto saveProductImageDto = (SaveProductImageDto) target;
        validateObject(saveProductImageDto, errors);
    }

    private void validateObject(SaveProductImageDto saveProductImageDto, Errors errors) {
        if(saveProductImageDto.getId() != null && saveProductImageDto.getId() != 0){
            if(productImageRepository.findById(saveProductImageDto.getId()).isEmpty()){
                errors.reject("productImage.id.notValid");
            }
        }
        if(saveProductImageDto.getProductId() != null && saveProductImageDto.getProductId() != 0){
            if(productRepository.findById(saveProductImageDto.getProductId()).isEmpty()){
                errors.reject("productImage.productId.notValid");
            }
        }else{
            errors.reject("productImage.productId.empty");
        }

        if(saveProductImageDto.getIndex() == null){
            errors.reject("productImage.index.empty");
        }
        if(saveProductImageDto.getImage() == null){
            errors.reject("productImage.image.empty");
        }else{
            if(!productImageService.validImageType(saveProductImageDto.getImage())){
                errors.reject("productImage.image.invalidType");
            }
            if(saveProductImageDto.getImageName() == null || saveProductImageDto.getImageName().isBlank()){
                errors.reject("productImage.imageName.notValid");
            }
        }

    }

}
