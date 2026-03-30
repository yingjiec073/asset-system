package com.example.assetsystem.entity.workflow;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("workflow_step")
public class WorkflowStep {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long workflowId;
    private Integer stepOrder;
    private String approverRoleCode;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    public Integer getStepOrder() { return stepOrder; }
    public void setStepOrder(Integer stepOrder) { this.stepOrder = stepOrder; }
    public String getApproverRoleCode() { return approverRoleCode; }
    public void setApproverRoleCode(String approverRoleCode) { this.approverRoleCode = approverRoleCode; }
}
