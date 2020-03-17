package com.itensis.ecat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class User extends AbstractEntity{

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Permission> permissions;

	private Long creationDate;

	protected User() {
		this.permissions = null;
	}

}
