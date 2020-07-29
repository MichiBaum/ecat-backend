package com.itensis.ecat.controller;

import com.itensis.ecat.converter.ProductImageConverter;
import com.itensis.ecat.dtos.SaveProductImageDto;
import com.itensis.ecat.services.ProductImageService;
import com.itensis.ecat.validator.ProductImageValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(value = "Product Image Endpoints")
public class ProductImageRestController {

    private final ProductImageService productImageService;
    private final ProductImageConverter productImageConverter;
    private final ProductImageValidator productImageValidator;

    @InitBinder("saveProductImageDto")
    public void initSaveProductBinder(WebDataBinder binder) {
        binder.setValidator(productImageValidator);
    }

    @CrossOrigin
    @PreAuthorize("hasAuthority('ADMINISTRATE')")
    @ApiOperation(value = "UPDATE imagepath for product with specific ID")
    @RequestMapping(value = "/api/products/image", method = RequestMethod.POST)
    public ResponseEntity saveProductImage(@ModelAttribute @Valid SaveProductImageDto saveProductImageDto){
        try{
            productImageService.saveProductImage(productImageConverter.toEntity(saveProductImageDto), saveProductImageDto.getImage());
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
