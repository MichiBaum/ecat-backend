package com.itensis.ecat.services;

import com.itensis.ecat.domain.Product;
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
public class ProductGroupService {

	private final ProductGroupRepository productGroupRepository;
	private final ProductClassRepository productClassRepository;
	private final ProductFamilyRepository productFamilyRepository;
	private final ProductRepository productRepository;

	public List<ProductGroup> getAll() {
		return productGroupRepository.findAll();
	}

	public List<Product> findByProductGroup(ProductGroup productGroup) {
		return productClassRepository.findByProductGroup(productGroup)
				.stream()
				.map(productFamilyRepository::findByProductClass)
				.flatMap(Collection::parallelStream)
				.map(productRepository::findByProductFamily)
				.flatMap(Collection::parallelStream)
				.collect(Collectors.toList());
	}

}
