package com.example.assetsystem.controller.v1;

import com.example.assetsystem.annotation.RequirePermission;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.entity.rbac.Permission;
import com.example.assetsystem.entity.rbac.Role;
import com.example.assetsystem.service.rbac.RbacService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/rbac")
public class RbacController {

    private final RbacService rbacService;

    public RbacController(RbacService rbacService) {
        this.rbacService = rbacService;
    }

    @PostMapping("/roles")
    @RequirePermission("rbac:manage")
    public ApiResponse<Role> createRole(@RequestBody Role role) {
        return ApiResponse.ok(rbacService.createRole(role));
    }

    @PostMapping("/permissions")
    @RequirePermission("rbac:manage")
    public ApiResponse<Permission> createPermission(@RequestBody Permission permission) {
        return ApiResponse.ok(rbacService.createPermission(permission));
    }

    @PostMapping("/roles/{roleId}/permissions/{permissionId}")
    @RequirePermission("rbac:manage")
    public ApiResponse<Void> bindPermission(@PathVariable Long roleId, @PathVariable Long permissionId) {
        rbacService.bindPermissionToRole(roleId, permissionId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/users/{userId}/roles/{roleId}")
    @RequirePermission("rbac:manage")
    public ApiResponse<Void> bindUserRole(@PathVariable Long userId, @PathVariable Long roleId) {
        rbacService.bindRoleToUser(userId, roleId);
        return ApiResponse.ok(null);
    }

    @GetMapping("/roles")
    public ApiResponse<List<Role>> listRoles() { return ApiResponse.ok(rbacService.listRoles()); }

    @GetMapping("/permissions")
    public ApiResponse<List<Permission>> listPermissions() { return ApiResponse.ok(rbacService.listPermissions()); }

    @GetMapping("/users/{userId}/permission-codes")
    public ApiResponse<Map<String, Set<String>>> userPermissions(@PathVariable Long userId) {
        return ApiResponse.ok(Map.of("permissions", rbacService.getUserPermissionCodes(userId)));
    }
}
