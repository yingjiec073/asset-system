package com.example.assetsystem.service.impl.workflow;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.entity.workflow.Approval;
import com.example.assetsystem.entity.workflow.Workflow;
import com.example.assetsystem.entity.workflow.WorkflowStep;
import com.example.assetsystem.mapper.workflow.ApprovalMapper;
import com.example.assetsystem.mapper.workflow.WorkflowMapper;
import com.example.assetsystem.mapper.workflow.WorkflowStepMapper;
import com.example.assetsystem.service.workflow.WorkflowApprovalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class WorkflowApprovalServiceImpl implements WorkflowApprovalService {

    private final WorkflowMapper workflowMapper;
    private final WorkflowStepMapper workflowStepMapper;
    private final ApprovalMapper approvalMapper;

    public WorkflowApprovalServiceImpl(WorkflowMapper workflowMapper,
                                       WorkflowStepMapper workflowStepMapper,
                                       ApprovalMapper approvalMapper) {
        this.workflowMapper = workflowMapper;
        this.workflowStepMapper = workflowStepMapper;
        this.approvalMapper = approvalMapper;
    }

    @Override
    @Transactional
    public Approval submit(String workflowCode, Long businessId, String businessType, Long requesterId, String reason) {
        Workflow workflow = workflowMapper.selectOne(new LambdaQueryWrapper<Workflow>().eq(Workflow::getCode, workflowCode));
        if (workflow == null) throw new IllegalArgumentException("workflow not found: " + workflowCode);
        List<WorkflowStep> steps = workflowStepMapper.selectList(new LambdaQueryWrapper<WorkflowStep>()
                .eq(WorkflowStep::getWorkflowId, workflow.getId()));
        int firstStep = steps.stream().min(Comparator.comparing(WorkflowStep::getStepOrder))
                .map(WorkflowStep::getStepOrder).orElse(1);

        Approval approval = new Approval();
        approval.setWorkflowId(workflow.getId());
        approval.setBusinessId(businessId);
        approval.setBusinessType(businessType);
        approval.setRequesterId(requesterId);
        approval.setCurrentStepOrder(firstStep);
        approval.setStatus("PENDING");
        approval.setReason(reason);
        approval.setCreatedAt(LocalDateTime.now());
        approval.setUpdatedAt(LocalDateTime.now());
        approvalMapper.insert(approval);
        return approval;
    }

    @Override
    @Transactional
    public Approval act(Long approvalId, Long userId, boolean approve, String comment) {
        Approval approval = approvalMapper.selectById(approvalId);
        if (approval == null) throw new IllegalArgumentException("approval not found");
        if (!"PENDING".equals(approval.getStatus())) throw new IllegalStateException("approval already finalized");

        approval.setActedBy(userId);
        approval.setActionComment(comment);
        if (!approve) {
            approval.setStatus("REJECTED");
        } else {
            List<WorkflowStep> steps = workflowStepMapper.selectList(new LambdaQueryWrapper<WorkflowStep>()
                    .eq(WorkflowStep::getWorkflowId, approval.getWorkflowId()));
            int maxOrder = steps.stream().map(WorkflowStep::getStepOrder).max(Integer::compareTo).orElse(approval.getCurrentStepOrder());
            if (approval.getCurrentStepOrder() >= maxOrder) {
                approval.setStatus("APPROVED");
            } else {
                approval.setCurrentStepOrder(approval.getCurrentStepOrder() + 1);
            }
        }
        approval.setUpdatedAt(LocalDateTime.now());
        approvalMapper.updateById(approval);
        return approval;
    }

    @Override
    public List<Approval> list() {
        return approvalMapper.selectList(new LambdaQueryWrapper<Approval>().orderByDesc(Approval::getCreatedAt));
    }
}
