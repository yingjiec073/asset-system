package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.asset.AssetQuery;
import com.example.assetsystem.dto.asset.DashboardStats;
import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.entity.AssetCategoryConfig;
import com.example.assetsystem.service.AssetCategoryConfigService;
import com.example.assetsystem.service.AssetService;
import com.example.assetsystem.service.audit.AuditLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetService assetService;
    private final AssetCategoryConfigService categoryConfigService;
    private final AuditLogService auditLogService;

    public AssetController(AssetService assetService, AssetCategoryConfigService categoryConfigService,
                           AuditLogService auditLogService) {
        this.assetService = assetService;
        this.categoryConfigService = categoryConfigService;
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public ApiResponse<Page<Asset>> list(AssetQuery query) {
        return ApiResponse.ok(assetService.pageAssets(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Asset>> detail(@PathVariable Long id) {
        Asset asset = assetService.getById(id);
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(404, "asset not found"));
        }
        return ResponseEntity.ok(ApiResponse.ok(asset));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Asset>> create(@Valid @RequestBody Asset asset,
                                                      @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        asset.setId(null);
        asset.setCreatedAt(null);
        boolean saved = assetService.save(asset);
        if (!saved) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(500, "create asset failed"));
        }
        auditLogService.log(userId, "asset_create", "assetId=" + asset.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(asset));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Asset>> update(@PathVariable Long id, @Valid @RequestBody Asset asset,
                                                      @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        if (assetService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(404, "asset not found"));
        }
        asset.setId(id);
        asset.setCreatedAt(null);
        boolean updated = assetService.updateById(asset);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(500, "update asset failed"));
        }
        auditLogService.log(userId, "asset_update", "assetId=" + id);
        return ResponseEntity.ok(ApiResponse.ok(assetService.getById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id,
                                                     @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        boolean removed = assetService.removeById(id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(404, "asset not found"));
        }
        auditLogService.log(userId, "asset_delete", "assetId=" + id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @GetMapping("/dashboard")
    public ApiResponse<DashboardStats> dashboard() {
        return ApiResponse.ok(assetService.dashboardStats());
    }

    @GetMapping("/categories")
    public ApiResponse<List<AssetCategoryConfig>> categories() {
        return ApiResponse.ok(categoryConfigService.list());
    }

    @PostMapping("/categories")
    public ApiResponse<AssetCategoryConfig> createCategory(@Valid @RequestBody AssetCategoryConfig category) {
        category.setId(null);
        categoryConfigService.save(category);
        return ApiResponse.ok(category);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<AssetCategoryConfig>> updateCategory(@PathVariable Long id,
                                                                           @Valid @RequestBody AssetCategoryConfig category) {
        if (categoryConfigService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(404, "category not found"));
        }
        category.setId(id);
        category.setCreatedAt(null);
        categoryConfigService.updateById(category);
        return ResponseEntity.ok(ApiResponse.ok(categoryConfigService.getById(id)));
    }
}
