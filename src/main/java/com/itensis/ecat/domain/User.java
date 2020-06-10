package com.itensis.ecat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LOGIN_USER")
public class User extends AbstractEntity{

	@Column(nullable = false, unique = true, name = "NAME")
	private String name;

	@Column(nullable = false, name = "PASSWORD")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Permission> permissions;

	@Column(nullable = false, name = "CREATION_DATE")
	private Long creationDate;

	public boolean hasPermission(PermissionName permissionName){
		return permissions.stream()
				.anyMatch(permission -> permission.getName().equals(permissionName));
	}
}
