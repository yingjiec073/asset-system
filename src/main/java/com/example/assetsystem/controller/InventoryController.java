package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.request.InventoryRecordRequest;
import com.example.assetsystem.dto.request.InventoryTaskRequest;
import com.example.assetsystem.entity.InventoryRecord;
import com.example.assetsystem.entity.InventoryTask;
import com.example.assetsystem.service.InventoryRecordService;
import com.example.assetsystem.service.InventoryTaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryTaskService taskService;
    private final InventoryRecordService recordService;

    public InventoryController(InventoryTaskService taskService, InventoryRecordService recordService) {
        this.taskService = taskService;
        this.recordService = recordService;
    }

    @GetMapping("/tasks")
    public ApiResponse<List<InventoryTask>> tasks() {
        return ApiResponse.ok(taskService.list(new LambdaQueryWrapper<InventoryTask>().orderByDesc(InventoryTask::getCreatedAt)));
    }

    @PostMapping("/tasks")
    public ApiResponse<InventoryTask> createTask(@Valid @RequestBody InventoryTaskRequest request) {
        InventoryTask task = new InventoryTask();
        task.setName(request.getName());
        task.setDepartmentId(request.getDepartmentId());
        task.setStatus("进行中");
        taskService.save(task);
        return ApiResponse.ok(task);
    }

    @PostMapping("/tasks/{taskId}/records")
    public ApiResponse<InventoryRecord> record(@PathVariable Long taskId, @Valid @RequestBody InventoryRecordRequest request) {
        InventoryRecord record = new InventoryRecord();
        record.setTaskId(taskId);
        record.setAssetId(request.getAssetId());
        record.setResult(request.getResult());
        record.setRemark(request.getRemark());
        recordService.save(record);
        return ApiResponse.ok(record);
    }

    @GetMapping("/tasks/{taskId}/diffs")
    public ApiResponse<List<InventoryRecord>> diffs(@PathVariable Long taskId) {
        return ApiResponse.ok(recordService.list(new LambdaQueryWrapper<InventoryRecord>()
                .eq(InventoryRecord::getTaskId, taskId)
                .in(InventoryRecord::getResult, "丢失", "异常")));
    }
}
