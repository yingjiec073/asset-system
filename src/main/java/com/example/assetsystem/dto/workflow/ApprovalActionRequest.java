package com.example.assetsystem.dto.workflow;

public class ApprovalActionRequest {
    private boolean approve;
    private String comment;

    public boolean isApprove() { return approve; }
    public void setApprove(boolean approve) { this.approve = approve; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
