package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.common.AssetStatus;
import com.example.assetsystem.common.OperationType;
import com.example.assetsystem.dto.asset.AssetOperationRequest;
import com.example.assetsystem.dto.asset.AssetQuery;
import com.example.assetsystem.dto.asset.DashboardStats;
import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.entity.AssetOperation;
import com.example.assetsystem.mapper.AssetMapper;
import com.example.assetsystem.service.AssetOperationService;
import com.example.assetsystem.service.AssetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements AssetService {

    private final AssetOperationService assetOperationService;

    public AssetServiceImpl(AssetOperationService assetOperationService) {
        this.assetOperationService = assetOperationService;
    }

    @Override
    public Page<Asset> pageAssets(AssetQuery query) {
        LambdaQueryWrapper<Asset> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getCategory())) {
            wrapper.eq(Asset::getCategory, query.getCategory());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.and(w -> w.like(Asset::getName, query.getKeyword())
                    .or().like(Asset::getSerialNumber, query.getKeyword())
                    .or().like(Asset::getBrand, query.getKeyword()));
        }
        wrapper.orderByDesc(Asset::getCreatedAt);
        return this.page(new Page<>(query.getPageNum(), query.getPageSize()), wrapper);
    }

    @Override
    public DashboardStats dashboardStats() {
        List<Asset> allAssets = this.list();
        DashboardStats stats = new DashboardStats();
        stats.setTotalAssets(allAssets.size());
        stats.setCategoryDistribution(groupCount(allAssets, Asset::getCategory));
        stats.setStatusDistribution(groupCount(allAssets, Asset::getStatus));
        stats.setDepartmentDistribution(allAssets.stream().collect(Collectors.groupingBy(
                Asset::getDepartmentId,
                LinkedHashMap::new,
                Collectors.counting())));
        return stats;
    }

    @Override
    @Transactional
    public void processOperation(AssetOperationRequest request) {
        Asset asset = this.getById(request.getAssetId());
        if (asset == null) {
            throw new IllegalArgumentException("资产不存在");
        }

        OperationType type = OperationType.valueOf(request.getType());
        if (AssetStatus.SCRAPPED.name().equals(asset.getStatus()) && type != OperationType.INVENTORY) {
            throw new IllegalStateException("已报废资产不可继续业务操作");
        }

        switch (type) {
            case CHECKOUT -> {
                requireStatus(asset, AssetStatus.IN_STOCK);
                asset.setStatus(AssetStatus.IN_USE.name());
            }
            case RETURN -> {
                requireStatus(asset, AssetStatus.IN_USE);
                asset.setStatus(AssetStatus.IN_STOCK.name());
            }
            case TRANSFER -> {
                if (request.getTargetDepartmentId() == null) {
                    throw new IllegalArgumentException("调拨必须提供目标部门");
                }
                asset.setDepartmentId(request.getTargetDepartmentId());
            }
            case MAINTENANCE -> asset.setStatus(AssetStatus.IN_MAINTENANCE.name());
            case SCRAP -> asset.setStatus(AssetStatus.SCRAPPED.name());
            case INVENTORY -> {
                // 盘点仅记录日志
            }
            default -> throw new IllegalArgumentException("不支持的操作类型");
        }

        this.updateById(asset);

        AssetOperation operation = new AssetOperation();
        operation.setAssetId(asset.getId());
        operation.setType(type.name());
        operation.setOperator(request.getOperator());
        operation.setRemark(request.getPurpose() == null ? request.getRemark() : request.getPurpose());
        assetOperationService.save(operation);
    }

    private void requireStatus(Asset asset, AssetStatus expected) {
        if (!expected.name().equals(asset.getStatus())) {
            throw new IllegalStateException("资产状态不正确，当前状态: " + asset.getStatus());
        }
    }

    private <T> Map<T, Long> groupCount(List<Asset> assets, Function<Asset, T> key) {
        return assets.stream().collect(Collectors.groupingBy(key, LinkedHashMap::new, Collectors.counting()));
    }
}
