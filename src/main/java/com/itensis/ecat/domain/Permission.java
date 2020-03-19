package com.itensis.ecat.domain;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@ToString
@AllArgsConstructor
@Table(name = "PERMISSION")
public class Permission extends AbstractEntity {

	@Enumerated(EnumType.STRING)
	@Column(unique = true, nullable = false, name = "NAME")
	private final PermissionName name;

	protected Permission(){
		this.name = null;
	}

}
