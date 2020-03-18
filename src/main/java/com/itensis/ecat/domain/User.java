package com.itensis.ecat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "LOGIN_USER")
public class User extends AbstractEntity{

	@Column(nullable = false, name = "NAME")
	private String name;

	@Column(nullable = false, name = "PASSWORD")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Permission> permissions;

	@Column(nullable = false, name = "CREATION_DATE")
	private Long creationDate;

	protected User() {
		this.permissions = null;
	}

}
