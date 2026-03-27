package com.example.assetsystem.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ReturnRequest {
    @NotBlank
    private String operator;
    private String remark;

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
