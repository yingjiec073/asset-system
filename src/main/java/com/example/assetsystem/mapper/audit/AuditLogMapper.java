package com.example.assetsystem.mapper.audit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.assetsystem.entity.audit.AuditLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditLogMapper extends BaseMapper<AuditLog> {
}
