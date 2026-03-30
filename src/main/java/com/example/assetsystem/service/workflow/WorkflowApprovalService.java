package com.example.assetsystem.service.workflow;

import com.example.assetsystem.entity.workflow.Approval;

import java.util.List;

public interface WorkflowApprovalService {
    Approval submit(String workflowCode, Long businessId, String businessType, Long requesterId, String reason);
    Approval act(Long approvalId, Long userId, boolean approve, String comment);
    List<Approval> list();
}
