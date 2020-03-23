package com.itensis.ecat.services;

import com.itensis.ecat.domain.Product;
import com.itensis.ecat.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public void delete(Product product) {
		productRepository.delete(product);
	}

}
