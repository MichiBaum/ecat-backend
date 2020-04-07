package com.itensis.ecat.services;

import com.itensis.ecat.domain.Product;
import com.itensis.ecat.domain.ProductClass;
import com.itensis.ecat.domain.ProductFamily;
import com.itensis.ecat.repository.ProductFamilyRepository;
import com.itensis.ecat.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductFamilyService {

	private final ProductRepository productRepository;
	private final ProductFamilyRepository productFamilyRepository;

	public List<Product> findByProductFamily(ProductFamily productFamily) {
		return productRepository.findByProductFamily(productFamily);
	}

	public List<ProductFamily> getAll() {
		return productFamilyRepository.findAll();
	}
}
