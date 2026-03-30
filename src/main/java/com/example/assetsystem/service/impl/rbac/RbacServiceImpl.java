package com.example.assetsystem.service.impl.rbac;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.entity.rbac.*;
import com.example.assetsystem.mapper.rbac.*;
import com.example.assetsystem.service.rbac.RbacService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RbacServiceImpl implements RbacService {

    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final UserRoleMapper userRoleMapper;

    public RbacServiceImpl(RoleMapper roleMapper, PermissionMapper permissionMapper,
                           RolePermissionMapper rolePermissionMapper, UserRoleMapper userRoleMapper) {
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public Role createRole(Role role) {
        role.setId(null);
        roleMapper.insert(role);
        return role;
    }

    @Override
    public Permission createPermission(Permission permission) {
        permission.setId(null);
        permissionMapper.insert(permission);
        return permission;
    }

    @Override
    @Transactional
    public void bindPermissionToRole(Long roleId, Long permissionId) {
        RolePermission rp = new RolePermission();
        rp.setRoleId(roleId);
        rp.setPermissionId(permissionId);
        rolePermissionMapper.insert(rp);
    }

    @Override
    @Transactional
    public void bindRoleToUser(Long userId, Long roleId) {
        UserRole ur = new UserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        userRoleMapper.insert(ur);
    }

    @Override
    public Set<String> getUserPermissionCodes(Long userId) {
        List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        if (userRoles.isEmpty()) return Collections.emptySet();
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).toList();
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId, roleIds));
        if (rolePermissions.isEmpty()) return Collections.emptySet();
        List<Long> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).toList();
        return permissionMapper.selectBatchIds(permissionIds).stream().map(Permission::getCode).collect(Collectors.toSet());
    }

    @Override
    public List<Role> listRoles() { return roleMapper.selectList(null); }

    @Override
    public List<Permission> listPermissions() { return permissionMapper.selectList(null); }
}
