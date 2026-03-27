package com.example.assetsystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@TableName("asset_category")
public class AssetCategoryConfig {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @NotBlank(message = "分类编码不能为空")
    @Size(max = 30, message = "分类编码长度不能超过30")
    private String code;

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 60, message = "分类名称长度不能超过60")
    private String name;

    @Size(max = 255, message = "分类描述长度不能超过255")
    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
