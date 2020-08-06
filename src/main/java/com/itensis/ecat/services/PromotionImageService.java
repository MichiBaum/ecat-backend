package com.itensis.ecat.services;

import com.itensis.ecat.domain.PromotionImage;
import com.itensis.ecat.repository.PromotionImageRepository;
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
import java.nio.file.StandardCopyOption;
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

    public void deletePromotionImage(PromotionImage promotionImage){
        promotionImageRepository.delete(promotionImage);
    }

    public PromotionImage savePromotionImage(PromotionImage promotionImage){
        return promotionImageRepository.saveAndFlush(promotionImage);
    }

    public PromotionImage savePromotionImageWithImage(PromotionImage promotionImage, MultipartFile image) {
        try{
            this.promotionImageRepository.saveAndFlush(promotionImage);
            promotionImage.setImageId(promotionImage.getId());
            this.promotionImageRepository.saveAndFlush(promotionImage);
            File originalFile = new File(environment.getRequiredProperty("promotion.image.path") + promotionImage.getImageId());
            File resizedFile = new File(environment.getRequiredProperty("promotion.resizedImage.path") + promotionImage.getImageId());
            image.transferTo(originalFile);
            Files.copy(originalFile.toPath(), resizedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            resizeImage(promotionImage.getImageId());
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return promotionImage;
    }

    public byte[] getImageBytes(Long imageId){
        File file = new File(environment.getRequiredProperty("promotion.resizedImage.path") + imageId);
        byte[] imageBytes;
        try{
            imageBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return imageBytes;

    }

    public String getImageMimeType(Long imageId){
        File file = new File(environment.getRequiredProperty("promotion.resizedImage.path") + imageId);
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
            File file = new File(environment.getRequiredProperty("promotion.resizedImage.path") + imageId);
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
