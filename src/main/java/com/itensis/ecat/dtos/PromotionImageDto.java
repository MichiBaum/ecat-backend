package com.itensis.ecat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionImageDto {
    private Long id;
    private Long promotionId;
    private String imageName;
    private Long index;
    private byte[] file;
    private String mimeType;
}
