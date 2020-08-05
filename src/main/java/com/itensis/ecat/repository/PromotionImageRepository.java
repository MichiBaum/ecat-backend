package com.itensis.ecat.repository;

import com.itensis.ecat.domain.PromotionImage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PromotionImageRepository extends CustomJpaRepository<PromotionImage, Long> {

    List<PromotionImage> findAllByPromotionId(Long promotionId);

    @Transactional
    void deleteAllByPromotionId(Long promotionId);

}
