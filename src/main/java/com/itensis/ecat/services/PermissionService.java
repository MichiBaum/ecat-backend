package com.itensis.ecat.services;

import com.itensis.ecat.domain.Permission;
import com.itensis.ecat.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<Permission> getAll(){
        return permissionRepository.findAll();
    }
}
