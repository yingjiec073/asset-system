package com.example.assetsystem.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.annotation.RequirePermission;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.entity.audit.AuditLog;
import com.example.assetsystem.service.audit.AuditLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    @RequirePermission("audit:view")
    public ApiResponse<List<AuditLog>> list() {
        return ApiResponse.ok(auditLogService.list(new LambdaQueryWrapper<AuditLog>().orderByDesc(AuditLog::getCreatedAt)));
    }
}
