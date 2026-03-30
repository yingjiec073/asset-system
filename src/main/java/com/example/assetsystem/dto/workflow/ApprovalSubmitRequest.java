package com.example.assetsystem.dto.workflow;

public class ApprovalSubmitRequest {
    private String workflowCode;
    private Long businessId;
    private String businessType;
    private String reason;

    public String getWorkflowCode() { return workflowCode; }
    public void setWorkflowCode(String workflowCode) { this.workflowCode = workflowCode; }
    public Long getBusinessId() { return businessId; }
    public void setBusinessId(Long businessId) { this.businessId = businessId; }
    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
