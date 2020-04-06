package com.itensis.ecat.repository;

import com.itensis.ecat.domain.Product;
import com.itensis.ecat.domain.ProductFamily;

import java.util.List;

public interface ProductRepository extends CustomJpaRepository<Product, Long> {
    List<Product> findByProductFamily(ProductFamily productFamily);
}
