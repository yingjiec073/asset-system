package com.example.assetsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.response.DashboardStatsResponse;
import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.entity.AssetCategory;
import com.example.assetsystem.service.AssetCategoryService;
import com.example.assetsystem.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;
    private final AssetCategoryService categoryService;

    public AssetController(AssetService assetService, AssetCategoryService categoryService) {
        this.assetService = assetService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ApiResponse<Page<Asset>> page(@RequestParam(defaultValue = "1") long page,
                                         @RequestParam(defaultValue = "10") long size,
                                         @RequestParam(required = false) String category,
                                         @RequestParam(required = false) String keyword) {
        LambdaQueryWrapper<Asset> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(category), Asset::getCategory, category)
                .and(StringUtils.hasText(keyword), q -> q.like(Asset::getName, keyword)
                        .or().like(Asset::getSerialNumber, keyword)
                        .or().like(Asset::getBrand, keyword))
                .orderByDesc(Asset::getCreatedAt);
        return ApiResponse.ok(assetService.page(new Page<>(page, size), wrapper));
    }

    @GetMapping("/{id}")
    public ApiResponse<Asset> detail(@PathVariable Long id) {
        Asset asset = assetService.getById(id);
        if (asset == null) {
            throw new IllegalArgumentException("资产不存在");
        }
        return ApiResponse.ok(asset);
    }

    @PostMapping
    public ApiResponse<Asset> create(@Valid @RequestBody Asset asset) {
        asset.setId(null);
        if (!StringUtils.hasText(asset.getStatus())) {
            asset.setStatus("在库");
        }
        assetService.save(asset);
        return ApiResponse.ok(asset);
    }

    @PutMapping("/{id}")
    public ApiResponse<Asset> update(@PathVariable Long id, @Valid @RequestBody Asset asset) {
        asset.setId(id);
        assetService.updateById(asset);
        return ApiResponse.ok(assetService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        assetService.removeById(id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/dashboard")
    public ApiResponse<DashboardStatsResponse> dashboard() {
        List<Asset> assets = assetService.list();
        DashboardStatsResponse response = new DashboardStatsResponse();
        response.setTotalAssets(assets.size());
        response.setCategoryStats(assets.stream().collect(Collectors.groupingBy(Asset::getCategory, LinkedHashMap::new, Collectors.counting())));
        response.setStatusStats(assets.stream().collect(Collectors.groupingBy(Asset::getStatus, LinkedHashMap::new, Collectors.counting())));
        response.setDepartmentStats(assets.stream().collect(Collectors.groupingBy(Asset::getDepartmentId, LinkedHashMap::new, Collectors.counting())));
        return ApiResponse.ok(response);
    }

    @GetMapping("/statistics")
    public ApiResponse<Map<String, Object>> statistics() {
        DashboardStatsResponse dashboard = dashboard().getData();
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("total", dashboard.getTotalAssets());
        stats.put("category", dashboard.getCategoryStats());
        stats.put("department", dashboard.getDepartmentStats());
        stats.put("status", dashboard.getStatusStats());
        return ApiResponse.ok(stats);
    }

    @GetMapping("/categories")
    public ApiResponse<List<AssetCategory>> categories() {
        return ApiResponse.ok(categoryService.list());
    }

    @PostMapping("/categories")
    public ApiResponse<AssetCategory> createCategory(@RequestBody AssetCategory category) {
        category.setId(null);
        categoryService.save(category);
        return ApiResponse.ok(category);
    }

    @PutMapping("/categories/{id}")
    public ApiResponse<AssetCategory> updateCategory(@PathVariable Long id, @RequestBody AssetCategory category) {
        category.setId(id);
        categoryService.updateById(category);
        return ApiResponse.ok(categoryService.getById(id));
    }
}
