package com.itensis.ecat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROMOTION_IMAGE")
public class PromotionImage extends AbstractEntity{

    @Column(nullable = false, name = "IMAGENAME")
    private String imageName;

    @Column(nullable = false, name = "IMAGE_ID")
    private Long imageId;

    @Column(nullable = false, name = "IMAGEINDEX")
    private Long imageIndex;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROMOTION_ID")
    private Promotion promotion;
}
