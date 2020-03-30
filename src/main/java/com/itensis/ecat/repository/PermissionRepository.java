package com.itensis.ecat.repository;

import com.itensis.ecat.domain.Permission;
import com.itensis.ecat.domain.PermissionName;
import com.itensis.ecat.domain.User;

import java.util.Optional;

public interface PermissionRepository extends CustomJpaRepository<Permission, Long> {

    Optional<Permission> findByName(PermissionName name);

}
