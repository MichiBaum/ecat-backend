package com.itensis.ecat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnProductImageDto {
    private Long id;
    private Long productId;
    private String imageName;
    private Long index;
    private byte[] file;
    private String mimeType;
}
