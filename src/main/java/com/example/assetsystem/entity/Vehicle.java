package com.example.assetsystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("vehicle")
public class Vehicle {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long assetId;

    @NotBlank(message = "车牌号不能为空")
    @Size(max = 20, message = "车牌号长度不能超过20")
    private String plateNumber;

    @NotBlank(message = "车架号不能为空")
    @Size(max = 50, message = "车架号长度不能超过50")
    private String vin;

    @NotBlank(message = "品牌不能为空")
    @Size(max = 50, message = "品牌长度不能超过50")
    private String brand;

    @NotBlank(message = "型号不能为空")
    @Size(max = 50, message = "型号长度不能超过50")
    private String model;

    @NotNull(message = "购买日期不能为空")
    private LocalDate purchaseDate;

    @NotNull(message = "购买价格不能为空")
    @DecimalMin(value = "0.00", message = "购买价格不能小于0")
    private BigDecimal purchasePrice;

    @NotBlank(message = "状态不能为空")
    @Size(max = 20, message = "状态长度不能超过20")
    private String status;

    @NotNull(message = "部门ID不能为空")
    private Long departmentId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
