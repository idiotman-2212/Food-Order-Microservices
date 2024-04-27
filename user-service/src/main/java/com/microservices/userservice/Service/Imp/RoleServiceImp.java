package com.microservices.userservice.Service.Imp;

import com.microservices.userservice.Entity.RoleEntity;
import com.microservices.userservice.Payload.Response.RoleResponse;

import java.util.List;

public interface RoleServiceImp {
    List<RoleResponse> getAllRoles();

    RoleEntity getRoleById(Integer idRole);
}
