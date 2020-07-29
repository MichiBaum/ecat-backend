package com.itensis.ecat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductImageDto {
    private Long id;
    private Long productId;
    private String imageName;
    private Long index;
    private MultipartFile image;
}
