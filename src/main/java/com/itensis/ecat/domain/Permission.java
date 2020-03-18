package com.itensis.ecat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@ToString
@Table(name = "PERMISSION")
public class Permission extends AbstractEntity {

	@Enumerated(EnumType.STRING)
	@Column(unique = true, nullable = false, name = "NAME")
	private final PermissionName name;

	protected Permission(){
		this.name = null;
	}

}
