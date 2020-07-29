package com.itensis.ecat.controller;

import com.itensis.ecat.converter.PromotionImageConverter;
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
    @ApiOperation(value = "UPDATE imagepath for promotion with specific ID")
    @RequestMapping(value = "/api/promotions/image", method = RequestMethod.POST)
    public ResponseEntity savePromotionImage(@ModelAttribute @Valid SavePromotionImageDto savePromotionImageDto){
        try{
            promotionImageService.savePromotionImage(promotionImageConverter.toEntity(savePromotionImageDto), savePromotionImageDto.getImage());
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
