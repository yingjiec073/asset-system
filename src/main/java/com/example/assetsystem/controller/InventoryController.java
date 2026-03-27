package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.asset.AssetOperationRequest;
import com.example.assetsystem.dto.asset.InventoryRecordRequest;
import com.example.assetsystem.entity.InventoryRecord;
import com.example.assetsystem.entity.InventoryTask;
import com.example.assetsystem.service.InventoryRecordService;
import com.example.assetsystem.service.InventoryTaskService;
import com.example.assetsystem.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryTaskService inventoryTaskService;
    private final InventoryRecordService inventoryRecordService;
    private final AssetService assetService;

    public InventoryController(InventoryTaskService inventoryTaskService,
                               InventoryRecordService inventoryRecordService,
                               AssetService assetService) {
        this.inventoryTaskService = inventoryTaskService;
        this.inventoryRecordService = inventoryRecordService;
        this.assetService = assetService;
    }

    @GetMapping("/tasks")
    public ApiResponse<List<InventoryTask>> tasks() {
        return ApiResponse.ok(inventoryTaskService.list(new LambdaQueryWrapper<InventoryTask>()
                .orderByDesc(InventoryTask::getCreatedAt)));
    }

    @PostMapping("/tasks")
    public ApiResponse<InventoryTask> createTask(@Valid @RequestBody InventoryTask task) {
        task.setId(null);
        task.setCreatedAt(null);
        if (task.getStatus() == null) {
            task.setStatus("OPEN");
        }
        inventoryTaskService.save(task);
        return ApiResponse.ok(task);
    }

    @PostMapping("/records")
    public ApiResponse<InventoryRecord> createRecord(@Valid @RequestBody InventoryRecordRequest request) {
        InventoryRecord record = new InventoryRecord();
        record.setTaskId(request.getTaskId());
        record.setAssetId(request.getAssetId());
        record.setResult(request.getResult());
        record.setRemark(request.getRemark());
        inventoryRecordService.save(record);

        AssetOperationRequest op = new AssetOperationRequest();
        op.setAssetId(request.getAssetId());
        op.setType("INVENTORY");
        op.setOperator("inventory-system");
        op.setRemark("盘点结果: " + request.getResult());
        assetService.processOperation(op);
        return ApiResponse.ok(record);
    }

    @GetMapping("/records")
    public ApiResponse<List<InventoryRecord>> records(@RequestParam Long taskId) {
        return ApiResponse.ok(inventoryRecordService.list(new LambdaQueryWrapper<InventoryRecord>()
                .eq(InventoryRecord::getTaskId, taskId)
                .orderByDesc(InventoryRecord::getCreatedAt)));
    }
}
