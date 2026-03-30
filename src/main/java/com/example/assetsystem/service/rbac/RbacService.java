package com.example.assetsystem.service.rbac;

import com.example.assetsystem.entity.rbac.Permission;
import com.example.assetsystem.entity.rbac.Role;

import java.util.List;
import java.util.Set;

public interface RbacService {
    Role createRole(Role role);
    Permission createPermission(Permission permission);
    void bindPermissionToRole(Long roleId, Long permissionId);
    void bindRoleToUser(Long userId, Long roleId);
    Set<String> getUserPermissionCodes(Long userId);
    List<Role> listRoles();
    List<Permission> listPermissions();
}
