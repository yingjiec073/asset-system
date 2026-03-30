package com.example.assetsystem.service.impl.audit;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.audit.AuditLog;
import com.example.assetsystem.mapper.audit.AuditLogMapper;
import com.example.assetsystem.service.audit.AuditLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLog> implements AuditLogService {
    @Override
    public void log(Long userId, String action, String details) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setDetails(details);
        log.setCreatedAt(LocalDateTime.now());
        save(log);
    }
}
