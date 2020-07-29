package com.itensis.ecat.repository;

import com.itensis.ecat.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductImageRepository extends CustomJpaRepository<ProductImage, Long> {
    List<ProductImage> findAllByProductId(Long productId);

}
