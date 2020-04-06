package com.itensis.ecat.services;

import com.itensis.ecat.domain.*;
import com.itensis.ecat.dtos.ProductSearchDto;
import com.itensis.ecat.dtos.SaveProductDto;
import com.itensis.ecat.repository.ProductClassRepository;
import com.itensis.ecat.repository.ProductFamilyRepository;
import com.itensis.ecat.repository.ProductRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

	@PersistenceContext
	private final EntityManager em;

	private final ProductRepository productRepository;
	private final ProductFamilyRepository productFamilyRepository;
	private final ProductClassRepository productClassRepository;

	public void delete(Product product) {
		productRepository.delete(product);
	}

	public List<Product> search(ProductSearchDto productSearchDto) {
		QProduct product = QProduct.product;

		BooleanExpression nameFilter = product.name.contains(productSearchDto.getSearchtext());

		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		return queryFactory
				.selectFrom(product)
				.where(nameFilter)
				.fetch();
	}

	public Optional<Product> get(Long id) {
		return productRepository.findById(id);
	}

	public Product save(Product product) {
		return productRepository.saveAndFlush(product);
	}

	public Product update(Product product, SaveProductDto saveProductDto) {
		product.setName(saveProductDto.getName());
		product.setDescription(saveProductDto.getDescription());
		product.setArticleNr(saveProductDto.getArticleNr());
		product.setPrice(saveProductDto.getPrice());
		Optional<ProductFamily> optProductFamily = productFamilyRepository.findById(saveProductDto.getProductFamilyId());
		if(optProductFamily.isPresent()){
			product.setProductFamily(optProductFamily.get());
		}
		return product;
	}

	public List<ProductFamily> findProductFamiliesBy(ProductClass productClass) {
		return productFamilyRepository.findByProductClass(productClass);
	}

	public List<ProductClass> findProductClassesBy(ProductGroup productGroup) {
		return productClassRepository.findByProductGroup(productGroup);
	}

	public List<Product> getAll(){return productRepository.findAll();}

}
