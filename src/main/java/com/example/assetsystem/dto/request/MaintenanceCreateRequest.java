package com.example.assetsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MaintenanceCreateRequest {
    @NotNull
    private Long assetId;
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String operator;

    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
}
