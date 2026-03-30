package com.example.assetsystem.mapper.rbac;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.assetsystem.entity.rbac.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
