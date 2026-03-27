package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.common.AssetStatus;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.entity.MaintenanceOrder;
import com.example.assetsystem.service.AssetService;
import com.example.assetsystem.service.MaintenanceOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceOrderService maintenanceOrderService;
    private final AssetService assetService;

    public MaintenanceController(MaintenanceOrderService maintenanceOrderService, AssetService assetService) {
        this.maintenanceOrderService = maintenanceOrderService;
        this.assetService = assetService;
    }

    @GetMapping
    public ApiResponse<List<MaintenanceOrder>> list(@RequestParam(required = false) Long assetId) {
        LambdaQueryWrapper<MaintenanceOrder> wrapper = new LambdaQueryWrapper<MaintenanceOrder>()
                .orderByDesc(MaintenanceOrder::getCreatedAt);
        if (assetId != null) {
            wrapper.eq(MaintenanceOrder::getAssetId, assetId);
        }
        return ApiResponse.ok(maintenanceOrderService.list(wrapper));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MaintenanceOrder>> create(@Valid @RequestBody MaintenanceOrder order) {
        Asset asset = assetService.getById(order.getAssetId());
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail("资产不存在"));
        }
        order.setId(null);
        order.setCreatedAt(null);
        maintenanceOrderService.save(order);
        asset.setStatus(AssetStatus.IN_MAINTENANCE.name());
        assetService.updateById(asset);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MaintenanceOrder>> update(@PathVariable Long id,
                                                                @Valid @RequestBody MaintenanceOrder order) {
        MaintenanceOrder existing = maintenanceOrderService.getById(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail("维修单不存在"));
        }
        order.setId(id);
        order.setCreatedAt(null);
        maintenanceOrderService.updateById(order);

        Asset asset = assetService.getById(order.getAssetId());
        if (asset != null && "COMPLETED".equals(order.getStatus())) {
            asset.setStatus(AssetStatus.IN_STOCK.name());
            assetService.updateById(asset);
        }
        return ResponseEntity.ok(ApiResponse.ok(maintenanceOrderService.getById(id)));
    }
}
