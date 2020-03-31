package com.itensis.ecat.repository;

import com.itensis.ecat.domain.ProductClass;
import com.itensis.ecat.domain.ProductGroup;

import java.util.List;

public interface ProductClassRepository extends CustomJpaRepository<ProductClass, Long> {
    List<ProductClass> findByProductGroup(ProductGroup productGroup);
}
