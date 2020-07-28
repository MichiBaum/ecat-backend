package com.itensis.ecat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageService {

    public boolean validImageType(MultipartFile image, String[] allowedTypes){
        if(!Arrays.asList(allowedTypes).contains(image.getOriginalFilename().split("\\.")[1])){
            return false;
        }
        return true;
    }

    public void saveImage(MultipartFile image, String imagePath) throws IOException {
        try{
            image.transferTo(new File(imagePath));
        } catch (Exception e){
            throw e;
        }
    }

}
