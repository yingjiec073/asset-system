package com.example.assetsystem.mapper.rbac;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.assetsystem.entity.rbac.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
