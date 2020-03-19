package com.itensis.ecat.initialize;

import com.itensis.ecat.domain.*;
import com.itensis.ecat.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
			User admin = initUser(permissions);
			initializeProducts();
		}
	}

	private void initializeProducts() {
		String imageName = CreateImage.createImage(imagePath);
		Product product = productRepository.save(new Product("Schraube XYZ", "1212090909 0990974", imageName, "This is a Descripption", 12.50, new Date().getTime()));
		ProductFamily productFamily = productFamilyRepository.save(new ProductFamily("Dorks", List.of(product)));
		ProductClass productClass = productClassRepository.save(new ProductClass("Schrauben", List.of(productFamily)));
		ProductGroup productGroup = productGroupRepository.save(new ProductGroup("Befestigungsmaterialien", List.of(productClass)));
	}

	private User initUser(List<Permission> permissions) {
		return userRepository.save(new User("admin", bCryptPasswordEncoder.encode("admin"), permissions, new Date().getTime()));
	}

	private List<Permission> initPermissions() {
		Permission permission1 = permissionRepository.save(new Permission(PermissionName.ADMINISTRATE));
		Permission permission2 = permissionRepository.save(new Permission(PermissionName.ADMINISTRATE_ADMINS));
		return List.of(permission1, permission2);
	}

}
