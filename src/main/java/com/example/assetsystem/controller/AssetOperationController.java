package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.asset.AssetOperationRequest;
import com.example.assetsystem.entity.AssetOperation;
import com.example.assetsystem.service.AssetOperationService;
import com.example.assetsystem.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-operations")
public class AssetOperationController {

    private final AssetService assetService;
    private final AssetOperationService assetOperationService;

    public AssetOperationController(AssetService assetService, AssetOperationService assetOperationService) {
        this.assetService = assetService;
        this.assetOperationService = assetOperationService;
    }

    @PostMapping
    public ApiResponse<Void> operate(@Valid @RequestBody AssetOperationRequest request) {
        assetService.processOperation(request);
        return ApiResponse.ok(null);
    }

    @GetMapping
    public ApiResponse<List<AssetOperation>> list(@RequestParam(required = false) Long assetId) {
        LambdaQueryWrapper<AssetOperation> wrapper = new LambdaQueryWrapper<AssetOperation>()
                .orderByDesc(AssetOperation::getTime);
        if (assetId != null) {
            wrapper.eq(AssetOperation::getAssetId, assetId);
        }
        return ApiResponse.ok(assetOperationService.list(wrapper));
    }
}
