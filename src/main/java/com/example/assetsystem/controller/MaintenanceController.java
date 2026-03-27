package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.request.MaintenanceCreateRequest;
import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.entity.MaintenanceRecord;
import com.example.assetsystem.service.AssetLifecycleService;
import com.example.assetsystem.service.MaintenanceRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceRecordService maintenanceService;
    private final AssetLifecycleService lifecycleService;

    public MaintenanceController(MaintenanceRecordService maintenanceService, AssetLifecycleService lifecycleService) {
        this.maintenanceService = maintenanceService;
        this.lifecycleService = lifecycleService;
    }

    @GetMapping
    public ApiResponse<List<MaintenanceRecord>> list(@RequestParam(required = false) String status) {
        LambdaQueryWrapper<MaintenanceRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(status != null && !status.isBlank(), MaintenanceRecord::getStatus, status)
                .orderByDesc(MaintenanceRecord::getCreatedAt);
        return ApiResponse.ok(maintenanceService.list(wrapper));
    }

    @PostMapping
    public ApiResponse<MaintenanceRecord> create(@Valid @RequestBody MaintenanceCreateRequest request) {
        Asset asset = lifecycleService.requireAsset(request.getAssetId());
        lifecycleService.ensureNotScrapped(asset);
        lifecycleService.changeStatusAndLog(asset, "维修", "报修", request.getOperator(), request.getDescription(), null);

        MaintenanceRecord record = new MaintenanceRecord();
        record.setAssetId(request.getAssetId());
        record.setTitle(request.getTitle());
        record.setDescription(request.getDescription());
        record.setStatus("待维修");
        maintenanceService.save(record);
        return ApiResponse.ok(record);
    }

    @PutMapping("/{id}/start")
    public ApiResponse<Void> start(@PathVariable Long id) {
        MaintenanceRecord record = maintenanceService.getById(id);
        if (record == null) throw new IllegalArgumentException("维修单不存在");
        record.setStatus("维修中");
        maintenanceService.updateById(record);
        return ApiResponse.ok(null);
    }

    @PutMapping("/{id}/complete")
    public ApiResponse<Void> complete(@PathVariable Long id, @RequestParam String operator) {
        MaintenanceRecord record = maintenanceService.getById(id);
        if (record == null) throw new IllegalArgumentException("维修单不存在");
        record.setStatus("完成");
        record.setFinishedAt(LocalDateTime.now());
        maintenanceService.updateById(record);

        Asset asset = lifecycleService.requireAsset(record.getAssetId());
        lifecycleService.changeStatusAndLog(asset, "在库", "维修完成", operator, record.getTitle(), null);
        return ApiResponse.ok(null);
    }
}
