package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.ProductImageConverter;
import com.itensis.ecat.domain.ProductImage;
import com.itensis.ecat.dtos.SaveProductImageDto;
import com.itensis.ecat.services.ProductImageService;
import com.itensis.ecat.validator.ProductImageValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @PreAuthorize("hasAuthority('ADMINISTRATE_PRODUCTS')")
    @ApiOperation(value = "UPDATE index of existing product image")
    @RequestMapping(value = "/api/products/image/{id}", method = RequestMethod.POST)
    public void updateProductImageIndex(@PathVariable(value = "id") ProductImage productImage, @RequestBody Long index){
        productImage.setImageIndex(index);
        productImageService.saveImageObject(productImage);
    }

    @CrossOrigin
    @PreAuthorize("hasAuthority('ADMINISTRATE_PRODUCTS')")
    @ApiOperation(value = "DELETE product image with specific ID")
    @RequestMapping(value = "/api/products/image/{id}", method = RequestMethod.DELETE)
    public void deleteProductImage(@PathVariable(value = "id") ProductImage productImage){ productImageService.deleteImageObject(productImage); }

    @CrossOrigin
    @PreAuthorize("hasAuthority('ADMINISTRATE_PRODUCTS')")
    @ApiOperation(value = "SAVE a new product image")
    @RequestMapping(value = "/api/products/image", method = RequestMethod.POST)
    public ResponseEntity saveProductImage(@ModelAttribute @Valid SaveProductImageDto saveProductImageDto) {
        ProductImage productImageToReturn  = productImageConverter.toEntity(saveProductImageDto);
        if(productImageToReturn.getProduct() == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        productImageToReturn = productImageService.saveWithImage(productImageToReturn, saveProductImageDto.getFile());
        return new ResponseEntity(productImageConverter.toDto(productImageToReturn), HttpStatus.OK);
    }

    @CrossOrigin
    @PublicEndpoint
    @ApiOperation(value = "GET image with specific id")
    @RequestMapping(value = "/api/products/image/{id}", method = RequestMethod.GET)
    public ResponseEntity getProductImage(@PathVariable(value = "id") ProductImage productImage){
        return provideFilePath(productImage.getImageId());
    }

    private ResponseEntity<byte[]> provideFilePath(Long imageId){
        byte[] media = productImageService.getImageBytes(imageId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", productImageService.getImageMimeType(imageId));

        return ResponseEntity.ok().headers(httpHeaders).body(media);
    }

}
