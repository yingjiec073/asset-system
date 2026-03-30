package com.example.assetsystem.mapper.rbac;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.assetsystem.entity.rbac.RolePermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
