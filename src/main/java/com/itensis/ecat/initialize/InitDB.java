package com.itensis.ecat.initialize;

import com.itensis.ecat.domain.*;
import com.itensis.ecat.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class InitDB {

	@Value("${db.initialize}")
	private boolean initialize;

	@Value("${product.image.path}")
	private String productImagePath;

	@Value("${promotion.image.path}")
	private String promotionImagePath;

	private final UserRepository userRepository;
	private final PermissionRepository permissionRepository;
	private final ProductRepository productRepository;
	private final ProductClassRepository productClassRepository;
	private final ProductFamilyRepository productFamilyRepository;
	private final ProductGroupRepository productGroupRepository;
	private final PromotionRepository promotionRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	private void initialize(){
		if(initialize){
			List<Permission> permissions = initPermissions();
			initUser(permissions);
			initializeProducts();
			initPromotion();
		}
	}

	private void initPromotion() {
		Long nowMilis = new Date().getTime();
		for(int i = 0; i < 10; i++){
			promotionRepository.saveAndFlush(new Promotion("AB SOFORT: ALLE ONLINE-BESTELLUNGEN VERSANDKOSTENFREI!", "VERSANDKOSTEN WERDEN AUTOMATISCH ABGEZOGEN", nowMilis + i, nowMilis + 604800000L, nowMilis));
		}
	}

	private void initializeProducts() {
		List<ProductFamily> productFamilies = productFamilyRepository.findAll();
		for(ProductFamily productFamily1: productFamilies) {
			for (int i = 0; i < 8; i++) {
				productRepository.saveAndFlush(new Product("Product", Integer.toString(ThreadLocalRandom.current().nextInt(1000000, 4000000 + 1)), "Speziell für die Verschraubung Holz mit Holz im Möbel und Ladenbau. Der Kopf kann mit passenden farblich abgestimmten Kappen abgedeckt werden.", 12.50, new Date().getTime(), productFamily1));
			}
		}
	}

	private void initUser(List<Permission> permissions) {
		for(int i = 0; i < 10; i++){
			userRepository.saveAndFlush(new User("admin" + i, bCryptPasswordEncoder.encode("admin"), permissions, new Date().getTime()));
		}
	}

	private List<Permission> initPermissions() {
		Permission permission1 = permissionRepository.saveAndFlush(new Permission(PermissionName.ADMINISTRATE));
		Permission permission2 = permissionRepository.saveAndFlush(new Permission(PermissionName.ADMINISTRATE_ADMINS));
		return List.of(permission1, permission2);
	}

}
