package com.example.assetsystem.controller.v1;

import com.example.assetsystem.annotation.RequirePermission;
import com.example.assetsystem.context.UserContext;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.workflow.ApprovalActionRequest;
import com.example.assetsystem.dto.workflow.ApprovalSubmitRequest;
import com.example.assetsystem.entity.workflow.Approval;
import com.example.assetsystem.service.audit.AuditLogService;
import com.example.assetsystem.service.workflow.WorkflowApprovalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/approvals")
public class ApprovalController {

    private final WorkflowApprovalService workflowApprovalService;
    private final AuditLogService auditLogService;

    public ApprovalController(WorkflowApprovalService workflowApprovalService, AuditLogService auditLogService) {
        this.workflowApprovalService = workflowApprovalService;
        this.auditLogService = auditLogService;
    }

    @PostMapping
    @RequirePermission("approval:submit")
    public ApiResponse<Approval> submit(@RequestBody ApprovalSubmitRequest request) {
        Long userId = UserContext.getUserId();
        Approval approval = workflowApprovalService.submit(
                request.getWorkflowCode(), request.getBusinessId(), request.getBusinessType(), userId, request.getReason());
        auditLogService.log(userId, "approval_submit", "approvalId=" + approval.getId());
        return ApiResponse.ok(approval);
    }

    @PostMapping("/{id}/actions")
    @RequirePermission("approval:act")
    public ApiResponse<Approval> action(@PathVariable Long id, @RequestBody ApprovalActionRequest request) {
        Long userId = UserContext.getUserId();
        Approval approval = workflowApprovalService.act(id, userId, request.isApprove(), request.getComment());
        auditLogService.log(userId, "approval_action", "approvalId=" + id + ",approve=" + request.isApprove());
        return ApiResponse.ok(approval);
    }

    @GetMapping
    @RequirePermission("approval:view")
    public ApiResponse<List<Approval>> list() {
        return ApiResponse.ok(workflowApprovalService.list());
    }
}
