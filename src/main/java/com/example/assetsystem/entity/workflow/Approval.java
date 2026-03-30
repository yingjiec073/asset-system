package com.example.assetsystem.entity.workflow;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("approval")
public class Approval {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long workflowId;
    private Long businessId;
    private String businessType;
    private Integer currentStepOrder;
    private String status;
    private Long requesterId;
    private String reason;
    private Long actedBy;
    private String actionComment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    public Long getBusinessId() { return businessId; }
    public void setBusinessId(Long businessId) { this.businessId = businessId; }
    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }
    public Integer getCurrentStepOrder() { return currentStepOrder; }
    public void setCurrentStepOrder(Integer currentStepOrder) { this.currentStepOrder = currentStepOrder; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Long getActedBy() { return actedBy; }
    public void setActedBy(Long actedBy) { this.actedBy = actedBy; }
    public String getActionComment() { return actionComment; }
    public void setActionComment(String actionComment) { this.actionComment = actionComment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
