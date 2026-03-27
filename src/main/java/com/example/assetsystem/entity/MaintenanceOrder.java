package com.example.assetsystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@TableName("maintenance_order")
public class MaintenanceOrder {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @NotNull(message = "资产ID不能为空")
    private Long assetId;

    @NotBlank(message = "维修状态不能为空")
    @Size(max = 20, message = "维修状态长度不能超过20")
    private String status;

    @Size(max = 500, message = "问题描述长度不能超过500")
    private String issue;

    @Size(max = 500, message = "维修记录长度不能超过500")
    private String record;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getIssue() { return issue; }
    public void setIssue(String issue) { this.issue = issue; }
    public String getRecord() { return record; }
    public void setRecord(String record) { this.record = record; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
