package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.request.CheckoutRequest;
import com.example.assetsystem.dto.request.ReturnRequest;
import com.example.assetsystem.dto.request.TransferRequest;
import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.entity.AssetOperation;
import com.example.assetsystem.service.AssetLifecycleService;
import com.example.assetsystem.service.AssetOperationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-operations")
public class AssetOperationController {

    private final AssetOperationService operationService;
    private final AssetLifecycleService lifecycleService;

    public AssetOperationController(AssetOperationService operationService, AssetLifecycleService lifecycleService) {
        this.operationService = operationService;
        this.lifecycleService = lifecycleService;
    }

    @GetMapping
    public ApiResponse<List<AssetOperation>> list(@RequestParam(required = false) Long assetId) {
        LambdaQueryWrapper<AssetOperation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(assetId != null, AssetOperation::getAssetId, assetId)
                .orderByDesc(AssetOperation::getTime);
        return ApiResponse.ok(operationService.list(wrapper));
    }

    @PostMapping("/checkout")
    public ApiResponse<Void> checkout(@Valid @RequestBody CheckoutRequest request) {
        Asset asset = lifecycleService.requireAsset(request.getAssetId());
        lifecycleService.ensureNotScrapped(asset);
        lifecycleService.changeStatusAndLog(asset, "在用", "领用", request.getUser(), request.getPurpose(), null);
        return ApiResponse.ok(null);
    }

    @PostMapping("/return/{assetId}")
    public ApiResponse<Void> giveBack(@PathVariable Long assetId, @Valid @RequestBody ReturnRequest request) {
        Asset asset = lifecycleService.requireAsset(assetId);
        lifecycleService.ensureNotScrapped(asset);
        lifecycleService.changeStatusAndLog(asset, "在库", "归还", request.getOperator(), request.getRemark(), null);
        return ApiResponse.ok(null);
    }

    @PostMapping("/transfer")
    public ApiResponse<Void> transfer(@Valid @RequestBody TransferRequest request) {
        Asset asset = lifecycleService.requireAsset(request.getAssetId());
        lifecycleService.ensureNotScrapped(asset);
        asset.setDepartmentId(request.getTargetDepartmentId());
        lifecycleService.changeStatusAndLog(asset, asset.getStatus(), "调拨", request.getOperator(), request.getRemark(), String.valueOf(request.getTargetDepartmentId()));
        return ApiResponse.ok(null);
    }
}
