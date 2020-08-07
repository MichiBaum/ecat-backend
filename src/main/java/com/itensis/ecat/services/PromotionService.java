package com.itensis.ecat.services;

import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.dtos.SavePromotionDto;
import com.itensis.ecat.repository.PromotionImageRepository;
import com.itensis.ecat.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionService {

	private final PromotionRepository promotionRepository;
	private final PromotionImageRepository promotionImageRepository;

	public List<Promotion> getAll() {
		return promotionRepository.findAll();
	}

	public List<Promotion> getAllNonExpired() {
		List<Promotion> promotions = promotionRepository.findAll();
		Date now = new Date();
		return promotions
				.stream()
				.filter(promotion -> promotion.getEndDate() > now.getTime())
				.collect(Collectors.toList());
	}

	public void delete(Promotion promotion) {
		promotionImageRepository.deleteAllByPromotionId(promotion.getId());
		promotionRepository.delete(promotion);
	}

	public Optional<Promotion> get(Long id) {
		return promotionRepository.findById(id);
	}

	public Promotion save(Promotion promotion) {
		return promotionRepository.saveAndFlush(promotion);
	}

	public Promotion update(Promotion promotion, SavePromotionDto promotionDto) {
		promotion.setTitle(promotionDto.getTitle());
		promotion.setDescription(promotionDto.getDescription());
		promotion.setStartDate(promotionDto.getStartDate());
		promotion.setEndDate(promotionDto.getEndDate());
		return promotion;
	}
}
