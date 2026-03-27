package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.request.ScrapRequest;
import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.entity.ScrapRecord;
import com.example.assetsystem.service.AssetLifecycleService;
import com.example.assetsystem.service.ScrapRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scrap")
public class ScrapController {

    private final ScrapRecordService scrapService;
    private final AssetLifecycleService lifecycleService;

    public ScrapController(ScrapRecordService scrapService, AssetLifecycleService lifecycleService) {
        this.scrapService = scrapService;
        this.lifecycleService = lifecycleService;
    }

    @GetMapping
    public ApiResponse<List<ScrapRecord>> list() {
        return ApiResponse.ok(scrapService.list(new LambdaQueryWrapper<ScrapRecord>().orderByDesc(ScrapRecord::getCreatedAt)));
    }

    @PostMapping
    public ApiResponse<ScrapRecord> scrap(@Valid @RequestBody ScrapRequest request) {
        Asset asset = lifecycleService.requireAsset(request.getAssetId());
        lifecycleService.changeStatusAndLog(asset, "报废", "报废", request.getOperator(), request.getReason(), null);

        ScrapRecord record = new ScrapRecord();
        record.setAssetId(request.getAssetId());
        record.setOperator(request.getOperator());
        record.setReason(request.getReason());
        scrapService.save(record);
        return ApiResponse.ok(record);
    }
}
