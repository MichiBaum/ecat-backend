package com.itensis.ecat.services;

import com.itensis.ecat.domain.Product;
import com.itensis.ecat.domain.ProductClass;
import com.itensis.ecat.domain.ProductGroup;
import com.itensis.ecat.repository.ProductClassRepository;
import com.itensis.ecat.repository.ProductFamilyRepository;
import com.itensis.ecat.repository.ProductGroupRepository;
import com.itensis.ecat.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductClassService {

	private final ProductFamilyRepository productFamilyRepository;
	private final ProductRepository productRepository;

	public List<Product> findByProductClass(ProductClass productClass) {
		return productFamilyRepository.findByProductClass(productClass)
				.stream()
				.map(productRepository::findByProductFamily)
				.flatMap(Collection::parallelStream)
				.collect(Collectors.toList());
	}

}
