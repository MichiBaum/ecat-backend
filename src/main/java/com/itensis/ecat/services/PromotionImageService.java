package com.itensis.ecat.services;

import com.itensis.ecat.domain.PromotionImage;
import com.itensis.ecat.repository.PromotionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
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

    @Value("${promotion.image.mimeTypes}")
    private String[] allowedTypes;

    @Value("${promotion.image.crop}")
    private boolean cropImages;

    @Value("${promotion.image.path}")
    private String imagePath;

    @Value("${promotion.resizedImage.path}")
    private String resizedImagePath;

    public boolean validImageType(MultipartFile image){
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
            File originalFile = new File(imagePath + promotionImage.getImageId());
            File resizedFile = new File(resizedImagePath + promotionImage.getImageId());
            image.transferTo(originalFile);
            Files.copy(originalFile.toPath(), resizedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if(cropImages){
                cropImage(promotionImage.getImageId());
            }else{
                resizeImage(promotionImage.getImageId());
            }
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return promotionImage;
    }

    public byte[] getImageBytes(Long imageId){
        File file = new File(resizedImagePath + imageId);
        byte[] imageBytes;
        try{
            imageBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return imageBytes;

    }

    public String getImageMimeType(Long imageId){
        File file = new File(resizedImagePath + imageId);
        String mimeType;
        try {
            URLConnection urlConnection = file.toURI().toURL().openConnection();
            mimeType = urlConnection.getContentType();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return mimeType;
    }

    private void cropImage(Long imageId){
        try{
            String mimeType = getImageMimeType(imageId);
            File file = new File(resizedImagePath + imageId);
            BufferedImage bufferedImage = ImageIO.read(file);
            double aspectRatio = 4D/3D;
            int croppedHeight = bufferedImage.getHeight();
            int croppedWidth = (int) Math.round(bufferedImage.getHeight() * aspectRatio);

            if(croppedWidth > bufferedImage.getWidth()){
                croppedHeight = (int) Math.round(bufferedImage.getWidth() / aspectRatio);
                croppedWidth = bufferedImage.getWidth();
            }
            int x = (bufferedImage.getWidth() -  croppedWidth) / 2;
            int y = (bufferedImage.getHeight() - croppedHeight) / 2;
            BufferedImage croppedImage = bufferedImage.getSubimage(x, y, croppedWidth, croppedHeight);
            boolean result = ImageIO.write(croppedImage, mimeType.substring(mimeType.lastIndexOf("/") + 1), file);
            if(!result){
                throw new RuntimeException("Could Not Crop Image");
            }
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void resizeImage(Long imageId){
        try{
            String mimeType = getImageMimeType(imageId);
            if(!mimeType.equals("image/png")){
                saveImageAsPng(imageId);
            }
            File file = new File(resizedImagePath + imageId);
            BufferedImage inputImage = ImageIO.read(file);
            double aspectRatio = 4D/3D;
            int newHeight = (int) Math.round(inputImage.getWidth() / aspectRatio);
            int newWidth = inputImage.getWidth();
            if(newHeight < inputImage.getHeight()){
                newHeight = inputImage.getHeight();
                newWidth = (int) Math.round(inputImage.getHeight() * aspectRatio);
            }
            int x = (newWidth - inputImage.getWidth()) / 2;
            int y = (newHeight - inputImage.getHeight()) / 2;
            BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2D = outputImage.createGraphics();
            g2D.drawImage(inputImage, x, y, inputImage.getWidth(), inputImage.getHeight(), null);
            g2D.dispose();
            boolean result = ImageIO.write(outputImage, "png", file);
            if(!result){
                throw new RuntimeException("Could Not Resize Image");
            }

        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

    }
    private void saveImageAsPng(Long imageId){
        try{
            File file = new File(resizedImagePath + imageId);
            BufferedImage bufferedImage = ImageIO.read(file);
            boolean result = ImageIO.write(bufferedImage, "png", file);
            if(!result){
                throw new RuntimeException("Could Not Convert Image");
            }
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
