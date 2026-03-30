package com.example.assetsystem.service.audit;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.assetsystem.entity.audit.AuditLog;

public interface AuditLogService extends IService<AuditLog> {
    void log(Long userId, String action, String details);
}
