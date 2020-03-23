package com.itensis.ecat.services;

import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

	private final PromotionRepository promotionRepository;

	public List<Promotion> getAll() {
		return promotionRepository.findAll();
	}

	public void delete(Promotion promotion) {
		promotionRepository.delete(promotion);
	}
}
