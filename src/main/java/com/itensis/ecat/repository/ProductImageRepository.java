package com.itensis.ecat.repository;

import com.itensis.ecat.domain.ProductImage;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductImageRepository extends CustomJpaRepository<ProductImage, Long> {
    List<ProductImage> findAllByProductId(Long productId);

    @Transactional
    void deleteAllByProductId(Long productId);
}
