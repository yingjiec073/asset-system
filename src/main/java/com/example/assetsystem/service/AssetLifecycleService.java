package com.example.assetsystem.service;

import com.example.assetsystem.entity.Asset;

public interface AssetLifecycleService {
    Asset requireAsset(Long assetId);
    void ensureNotScrapped(Asset asset);
    void changeStatusAndLog(Asset asset, String status, String type, String operator, String remark, String targetDepartment);
}
