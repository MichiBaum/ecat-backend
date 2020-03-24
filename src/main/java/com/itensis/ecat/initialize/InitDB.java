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

@Component
@RequiredArgsConstructor
public class InitDB {

	@Value("${db.initialize}")
	private boolean initialize;

	@Value("${product.image.path}")
	private String imagePath;

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
		promotionRepository.saveAndFlush(new Promotion("Promotion title", "Promotion description", nowMilis, nowMilis + 604800000L, nowMilis));
	}

	private void initializeProducts() {
		String imageName = CreateImage.createImage(imagePath);
		ProductGroup productGroup = productGroupRepository.saveAndFlush(new ProductGroup("Befestigungsmaterialien"));
		ProductClass productClass = productClassRepository.saveAndFlush(new ProductClass("Schrauben", productGroup));
		ProductFamily productFamily = productFamilyRepository.saveAndFlush(new ProductFamily("Dorks", productClass));
		Product product = productRepository.saveAndFlush(new Product("Schraube XYZ", "1212090909 0990974", imageName, "This is a Descripption", 12.50, new Date().getTime(), productFamily));
	}

	private User initUser(List<Permission> permissions) {
		return userRepository.saveAndFlush(new User("admin", bCryptPasswordEncoder.encode("admin"), permissions, new Date().getTime()));
	}

	private List<Permission> initPermissions() {
		Permission permission1 = permissionRepository.saveAndFlush(new Permission(PermissionName.ADMINISTRATE));
		Permission permission2 = permissionRepository.saveAndFlush(new Permission(PermissionName.ADMINISTRATE_ADMINS));
		return List.of(permission1, permission2);
	}

}
