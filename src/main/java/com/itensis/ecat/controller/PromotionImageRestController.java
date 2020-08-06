package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.PromotionImageConverter;
import com.itensis.ecat.domain.PromotionImage;
import com.itensis.ecat.dtos.SavePromotionImageDto;
import com.itensis.ecat.services.PromotionImageService;
import com.itensis.ecat.validator.PromotionImageValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Api(value = "Promotion Image Endpoints")
public class PromotionImageRestController {

    private final PromotionImageService promotionImageService;
    private final PromotionImageConverter promotionImageConverter;
    private final PromotionImageValidator promotionImageValidator;

    @InitBinder("savePromotionImageDto")
    public void initSavePromotionBinder(WebDataBinder binder) {
        binder.setValidator(promotionImageValidator);
    }

    @CrossOrigin
    @PreAuthorize("hasAuthority('ADMINISTRATE')")
    @ApiOperation(value = "UPDATE index of existing promotion image")
    @RequestMapping(value = "/api/promotions/image/{id}", method = RequestMethod.POST)
    public void updateProductImageIndex(@PathVariable(value = "id") PromotionImage promotionImage, @RequestBody Long index){
        promotionImage.setImageIndex(index);
        promotionImageService.savePromotionImage(promotionImage);
    }

    @CrossOrigin
    @PreAuthorize("hasAuthority('ADMINISTRATE')")
    @ApiOperation(value = "DELETE promotion image with specific ID")
    @RequestMapping(value = "/api/promotions/image/{id}", method = RequestMethod.DELETE)
    public void deleteProductImage(@PathVariable(value = "id") PromotionImage promotionImage){ promotionImageService.deletePromotionImage(promotionImage); }

    @CrossOrigin
    @PreAuthorize("hasAuthority('ADMINISTRATE')")
    @ApiOperation(value = "UPDATE imagepath for promotion with specific ID")
    @RequestMapping(value = "/api/promotions/image", method = RequestMethod.POST)
    public ResponseEntity savePromotionImage(@ModelAttribute @Valid SavePromotionImageDto savePromotionImageDto){
        PromotionImage promotionImageToReturn = promotionImageConverter.toEntity(savePromotionImageDto);
        if(promotionImageToReturn.getPromotion() == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        promotionImageToReturn = promotionImageService.savePromotionImageWithImage(promotionImageToReturn, savePromotionImageDto.getFile());
        return new ResponseEntity(promotionImageConverter.toDto(promotionImageToReturn), HttpStatus.OK);
    }

    @CrossOrigin
    @PublicEndpoint
    @ApiOperation(value = "GET image with specific id")
    @RequestMapping(value = "/api/promotions/image/{id}", method = RequestMethod.GET, produces = {"image/jpeg", "image/png"})
    public ResponseEntity getPromotionImages(@PathVariable(value = "id") PromotionImage promotionImage){
        return provideFilePath(promotionImage.getImageId());
    }

    private ResponseEntity<byte[]> provideFilePath(Long imageId){
        byte[] media = promotionImageService.getImageBytes(imageId);

        return new ResponseEntity<>(media, HttpStatus.OK);
    }
}
