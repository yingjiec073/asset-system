package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.AssetCategoryConfig;
import com.example.assetsystem.mapper.AssetCategoryConfigMapper;
import com.example.assetsystem.service.AssetCategoryConfigService;
import org.springframework.stereotype.Service;

@Service
public class AssetCategoryConfigServiceImpl extends ServiceImpl<AssetCategoryConfigMapper, AssetCategoryConfig>
        implements AssetCategoryConfigService {
}
