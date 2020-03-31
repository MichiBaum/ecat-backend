package com.itensis.ecat.repository;

import com.itensis.ecat.domain.ProductClass;
import com.itensis.ecat.domain.ProductFamily;

import java.util.List;

public interface ProductFamilyRepository extends CustomJpaRepository<ProductFamily, Long> {
    List<ProductFamily> findByProductClass(ProductClass productClass);
}
