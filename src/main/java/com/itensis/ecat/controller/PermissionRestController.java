package com.itensis.ecat.controller;

import com.itensis.ecat.converter.PermissionConverter;
import com.itensis.ecat.dtos.ReturnPermissionDto;
import com.itensis.ecat.services.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(value = "Permission Endpoints")
public class PermissionRestController {

    private final PermissionService permissionService;
    private final PermissionConverter permissionConverter;

    @CrossOrigin
    @PreAuthorize("hasAuthority('ADMINISTRATE_USERS')")
    @ApiOperation(value = "GET all Users")
    @RequestMapping(value = "/api/permissions", method = RequestMethod.GET)
    public List<ReturnPermissionDto> getUsers(){
        return permissionService.getAll().stream().map(permissionConverter::toDto).collect(Collectors.toList());
    }
}
