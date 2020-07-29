package com.itensis.ecat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavePromotionImageDto {
    private Long id;
    private Long promotionId;
    private String imageName;
    private Long index;
    private MultipartFile image;
}
