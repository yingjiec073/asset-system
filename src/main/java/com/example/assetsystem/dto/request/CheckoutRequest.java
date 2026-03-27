package com.example.assetsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CheckoutRequest {
    @NotNull
    private Long assetId;
    @NotBlank
    private String user;
    @NotBlank
    private String purpose;

    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
