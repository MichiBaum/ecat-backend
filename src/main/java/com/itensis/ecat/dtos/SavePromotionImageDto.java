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
    private String fileName;
    private Long index;
    private MultipartFile file;
}
