package com.itensis.ecat.services;

import com.itensis.ecat.domain.ProductGroup;
import com.itensis.ecat.repository.ProductGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductGroupService {

	private final ProductGroupRepository productGroupRepository;

	public List<ProductGroup> getAll() {
		return productGroupRepository.findAll();
	}

}
