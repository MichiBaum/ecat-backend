package com.itensis.ecat.converter;

import com.itensis.ecat.domain.Permission;
import com.itensis.ecat.dtos.ReturnPermissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionConverter {
    public ReturnPermissionDto toDto(Permission permission){
        return new ReturnPermissionDto(
                permission.getName().toString()
        );
    }
}
