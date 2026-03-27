package com.example.assetsystem.dto.asset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AssetOperationRequest {
    @NotNull(message = "资产ID不能为空")
    private Long assetId;

    @NotBlank(message = "操作类型不能为空")
    private String type;

    @NotBlank(message = "操作人不能为空")
    private String operator;

    @Size(max = 500, message = "备注长度不能超过500")
    private String remark;

    private Long targetDepartmentId;

    @Size(max = 255, message = "用途长度不能超过255")
    private String purpose;

    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public Long getTargetDepartmentId() { return targetDepartmentId; }
    public void setTargetDepartmentId(Long targetDepartmentId) { this.targetDepartmentId = targetDepartmentId; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
