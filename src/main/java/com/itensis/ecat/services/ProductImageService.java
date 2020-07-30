package com.itensis.ecat.services;

import com.itensis.ecat.domain.ProductImage;
import com.itensis.ecat.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Resource
    private final Environment environment;

    public boolean validImageType(MultipartFile image){
        String[] allowedTypes = environment.getRequiredProperty("product.image.mimeTypes", String[].class);
        if(!Arrays.asList(allowedTypes).contains(image.getContentType())){
            return false;

        }
        return true;
    }

    public ProductImage saveProductImage(ProductImage productImage){
        return productImageRepository.saveAndFlush(productImage);
    }

    public void deleteProductImage(ProductImage productImage){ productImageRepository.delete(productImage); }

    public ProductImage saveProductImageWithImage(ProductImage productImage, MultipartFile image) throws IOException {
        this.productImageRepository.saveAndFlush(productImage);
        productImage.setImageId(productImage.getId());
        this.productImageRepository.saveAndFlush(productImage);
        image.transferTo(new File(environment.getRequiredProperty("product.image.path") + productImage.getImageId()));
        return productImage;
    }

    public byte[] getImage(Long imageId){
        File fileToBeReturned = new File(environment.getRequiredProperty("product.image.path") + imageId);
        byte[] arrayToBeReturned = new byte[0];
        try{
           arrayToBeReturned = Files.readAllBytes(fileToBeReturned.toPath());
        } catch (IOException e){
            return arrayToBeReturned;
        }
        return arrayToBeReturned;

    }

}
