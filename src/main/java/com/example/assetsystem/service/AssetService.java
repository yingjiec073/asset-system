package com.example.assetsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.assetsystem.dto.asset.AssetOperationRequest;
import com.example.assetsystem.dto.asset.AssetQuery;
import com.example.assetsystem.dto.asset.DashboardStats;
import com.example.assetsystem.entity.Asset;

public interface AssetService extends IService<Asset> {
    Page<Asset> pageAssets(AssetQuery query);

    DashboardStats dashboardStats();

    void processOperation(AssetOperationRequest request);
}
