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
		promotionRepository.saveAndFlush(new Promotion("AB SOFORT: ALLE ONLINE-BESTELLUNGEN VERSANDKOSTENFREI!", "VERSANDKOSTEN WERDEN AUTOMATISCH ABGEZOGEN", nowMilis, nowMilis + 604800000L, nowMilis));
	}

	private void initializeProducts() {
		String imageName = CreateImage.createImage(imagePath);
		ProductGroup productGroup = productGroupRepository.saveAndFlush(new ProductGroup("Befestigungsmaterialien"));
		ProductClass productClass = productClassRepository.saveAndFlush(new ProductClass("Schrauben", productGroup));
		ProductFamily productFamily = productFamilyRepository.saveAndFlush(new ProductFamily("Spanplattenschrauben", productClass));
		productRepository.saveAndFlush(new Product("ASSY® 3.0 BLAU VERZINKT SPANPLATTENSCHRAUBE", "1212090909 0990874", imageName, "Für den universellen Einsatz im Möbelbau, Ladenbau, Innenausbau, Dachbereich und Holzbau", 12.50, new Date().getTime(), productFamily));
		productRepository.saveAndFlush(new Product("ASSY® 3.0 P SPANPLATTENSCHRAUBE", "1212093909 0940974", imageName, "Die Schraube mit Zusammenpresseffekt. Optimal für Plattenwerkstoffe. Besonders geeignet für den Innenausbau und für den Möbelbau.", 12.50, new Date().getTime(), productFamily));
		productRepository.saveAndFlush(new Product("ASSY® 3.0 KOPFLOCHGEBOHRT SPANPLATTENSCHRAUBE", "1212190909 0980974", imageName, "Speziell für die Verschraubung Holz mit Holz im Möbel und Ladenbau. Der Kopf kann mit passenden farblich abgestimmten Kappen abgedeckt werden.", 12.50, new Date().getTime(), productFamily));
		productRepository.saveAndFlush(new Product("ASSY® 3.0 GELB VERZINKT SPANPLATTENSCHRAUBE", "1212090909 0950944", imageName, "Für den universellen Einsatz im Möbel- und Innenausbau sowie im Holzbau.", 12.50, new Date().getTime(), productFamily));
		productRepository.saveAndFlush(new Product("ASSY® 3.0 GOLD VERZINKT SPANPLATTENSCHRAUBE", "1212290909 0920974", imageName, "Für den universellen Einsatz im Möbel- und Innenausbau sowie im Holzbau.", 12.50, new Date().getTime(), productFamily));

		ProductFamily productFamily2 = productFamilyRepository.saveAndFlush(new ProductFamily("Trockenbauschrauben", productClass));
		productRepository.saveAndFlush(new Product("SCHNELLBAUSCHRAUBE MIT DOPPELGANGGEWINDE", "1212090909 0995874", imageName, "Für die Verschraubung auf Metall-Unterkonstruktionen bis max. 1 mm", 12.50, new Date().getTime(), productFamily2));
		productRepository.saveAndFlush(new Product("GIPSKARTONPLATTENSCHRAUBE", "1212793909 0940974", imageName, "Zum Verschrauben von Gipskartonplatten aufeinander", 12.50, new Date().getTime(), productFamily2));
		productRepository.saveAndFlush(new Product("SCHNELLBAUSCHRAUBE MIT BOHRSPITZE", "1212197779 0980974", imageName, "Mit Bohrspitze", 12.50, new Date().getTime(), productFamily2));
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
