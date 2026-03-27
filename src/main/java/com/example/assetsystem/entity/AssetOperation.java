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

@TableName("asset_operation")
public class AssetOperation {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @NotNull(message = "资产ID不能为空")
    private Long assetId;

    @NotBlank(message = "操作类型不能为空")
    @Size(max = 30, message = "操作类型长度不能超过30")
    private String type;

    @NotBlank(message = "操作人不能为空")
    @Size(max = 50, message = "操作人长度不能超过50")
    private String operator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime time;

    @Size(max = 500, message = "备注长度不能超过500")
    private String remark;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
