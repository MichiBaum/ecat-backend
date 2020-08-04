package com.itensis.ecat.controller;

import com.itensis.ecat.converter.ProductImageConverter;
import com.itensis.ecat.domain.ProductImage;
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

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;

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
    @ApiOperation(value = "UPDATE index of existing product image")
    @RequestMapping(value = "/api/products/image/{id}", method = RequestMethod.POST)
    public void updateProductImageIndex(@PathVariable(value = "id") ProductImage productImage, @RequestBody Long index){
        productImage.setImageIndex(index);
        productImageService.saveProductImage(productImage);
    }

    @CrossOrigin
    @PreAuthorize("hasAuthority('ADMINISTRATE')")
    @ApiOperation(value = "DELETE product image with specific ID")
    @RequestMapping(value = "/api/products/image/{id}", method = RequestMethod.DELETE)
    public void deleteProductImage(@PathVariable(value = "id") ProductImage productImage){ productImageService.deleteProductImage(productImage); }

    @CrossOrigin
    @PreAuthorize("hasAuthority('ADMINISTRATE')")
    @ApiOperation(value = "SAVE a new product image")
    @RequestMapping(value = "/api/products/image", method = RequestMethod.POST)
    public ResponseEntity saveProductImage(@ModelAttribute @Valid SaveProductImageDto saveProductImageDto) throws IOException {
        ProductImage productImageToReturn;
        productImageToReturn = productImageService.saveProductImageWithImage(productImageConverter.toEntity(saveProductImageDto), saveProductImageDto.getImage());
        return new ResponseEntity(productImageConverter.toDto(productImageToReturn), HttpStatus.OK);
    }

}
