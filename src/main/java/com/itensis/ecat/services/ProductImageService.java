package com.itensis.ecat.services;

import com.itensis.ecat.domain.ProductImage;
import com.itensis.ecat.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
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

    public ProductImage saveProductImageWithImage(ProductImage productImage, MultipartFile image) {
        try{
            productImageRepository.saveAndFlush(productImage);
            productImage.setImageId(productImage.getId());
            productImageRepository.saveAndFlush(productImage);
            image.transferTo(new File(environment.getRequiredProperty("product.image.path") + productImage.getImageId()));
            resizeImage(productImage.getImageId());
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return productImage;
    }

    public byte[] getImageBytes(Long imageId){
        File file = new File(environment.getRequiredProperty("product.image.path") + imageId);
        byte[] imageBytes;
        try{
           imageBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return imageBytes;
    }

    public String getImageMimeType(Long imageId){
        File file = new File(environment.getRequiredProperty("product.image.path") + imageId);
        String mimeType;
        try {
            URLConnection urlConnection = file.toURI().toURL().openConnection();
            mimeType = urlConnection.getContentType();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return mimeType;
    }

    private void resizeImage(Long imageId){
        try{
            String mimeType = getImageMimeType(imageId);
            File file = new File(environment.getRequiredProperty("product.image.path") + imageId);
            BufferedImage bufferedImage = ImageIO.read(file);
            double aspectRatio = 4D/3D;
            int croppedHeight = bufferedImage.getHeight();
            int croppedWidth = (int) Math.round(bufferedImage.getHeight() * aspectRatio);

            if(croppedWidth > bufferedImage.getWidth()){
                croppedHeight = (int) Math.round(bufferedImage.getWidth() / aspectRatio);
                croppedWidth = bufferedImage.getWidth();
            }

            BufferedImage croppedImage = bufferedImage.getSubimage((bufferedImage.getWidth() - croppedWidth) / 2, (bufferedImage.getHeight() - croppedHeight) / 2, croppedWidth, croppedHeight);
            ImageIO.write(croppedImage, mimeType.substring(mimeType.lastIndexOf("/") + 1), file);
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
