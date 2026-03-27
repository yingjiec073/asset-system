package com.example.assetsystem.service.impl;

import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.entity.AssetOperation;
import com.example.assetsystem.service.AssetLifecycleService;
import com.example.assetsystem.service.AssetOperationService;
import com.example.assetsystem.service.AssetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetLifecycleServiceImpl implements AssetLifecycleService {

    private final AssetService assetService;
    private final AssetOperationService operationService;

    public AssetLifecycleServiceImpl(AssetService assetService, AssetOperationService operationService) {
        this.assetService = assetService;
        this.operationService = operationService;
    }

    @Override
    public Asset requireAsset(Long assetId) {
        Asset asset = assetService.getById(assetId);
        if (asset == null) {
            throw new IllegalArgumentException("资产不存在");
        }
        return asset;
    }

    @Override
    public void ensureNotScrapped(Asset asset) {
        if ("报废".equals(asset.getStatus())) {
            throw new IllegalStateException("资产已报废，不能继续操作");
        }
    }

    @Override
    @Transactional
    public void changeStatusAndLog(Asset asset, String status, String type, String operator, String remark, String targetDepartment) {
        asset.setStatus(status);
        assetService.updateById(asset);

        AssetOperation operation = new AssetOperation();
        operation.setAssetId(asset.getId());
        operation.setType(type);
        operation.setOperator(operator);
        operation.setRemark(remark);
        operation.setTargetDepartment(targetDepartment);
        operationService.save(operation);
    }
}
