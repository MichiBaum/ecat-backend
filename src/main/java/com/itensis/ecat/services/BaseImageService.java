package com.itensis.ecat.services;

import com.itensis.ecat.domain.ImageInterface;
import com.itensis.ecat.domain.PromotionImage;
import com.itensis.ecat.repository.CustomJpaRepository;
import lombok.NoArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@NoArgsConstructor
abstract public class BaseImageService<T extends ImageInterface, R extends CustomJpaRepository<T, Long>> {

    protected String resizedImagePath;
    protected String imagePath;
    protected String[] allowedMimeTypes;
    protected boolean cropImages;
    protected CustomJpaRepository<T, Long> repository;

    public BaseImageService(Boolean cropImages,
                            String imagePath,
                            String resizedImagePath,
                            String[] allowedMimeTypes,
                            CustomJpaRepository<T, Long> repository) {
        this.imagePath = imagePath;
        this.resizedImagePath = resizedImagePath;
        this.allowedMimeTypes = allowedMimeTypes;
        this.cropImages = cropImages;
        this.repository = repository;
    }

    public void deleteImageObject(T imageObject){
        repository.delete(imageObject);
    }

    public T saveImageObject(T imageObject){
        return repository.saveAndFlush(imageObject);
    }

    public T saveWithImage(T imageObject, MultipartFile multipartFile) {
        try{
            this.repository.saveAndFlush(imageObject);
            imageObject.setImageId(imageObject.getId());
            this.repository.saveAndFlush(imageObject);
            File originalFile = new File(imagePath + imageObject.getImageId());
            File resizedFile = new File(resizedImagePath + imageObject.getImageId());
            multipartFile.transferTo(originalFile);
            Files.copy(originalFile.toPath(), resizedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if(cropImages){
                cropImage(imageObject.getImageId());
            }else{
                resizeImage(imageObject.getImageId());
            }
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return imageObject;
    }

    public boolean validImageType(MultipartFile image){
        Tika tika = new Tika();
        try{
            String mimeType = tika.detect(image.getBytes());
            if(!Arrays.asList(allowedMimeTypes).contains(mimeType)){
                return false;
            }
        }catch (IOException e){
            return false;
        }
        return true;
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

    protected void cropImage(Long imageId){
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

    protected void resizeImage(Long imageId){
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
    protected void saveImageAsPng(Long imageId){
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
