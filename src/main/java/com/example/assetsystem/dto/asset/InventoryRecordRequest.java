package com.example.assetsystem.dto.asset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InventoryRecordRequest {
    @NotNull(message = "盘点任务ID不能为空")
    private Long taskId;

    @NotNull(message = "资产ID不能为空")
    private Long assetId;

    @NotBlank(message = "盘点结果不能为空")
    private String result;

    @Size(max = 255, message = "盘点备注长度不能超过255")
    private String remark;

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
