package com.itensis.ecat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@ToString
public class Permission extends AbstractEntity {

	@Enumerated(EnumType.STRING)
	@Column(unique = true, nullable = false)
	private final PermissionName name;

	protected Permission(){
		this.name = null;
	}

}
