package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.AssetCategory;
import com.example.assetsystem.mapper.AssetCategoryMapper;
import com.example.assetsystem.service.AssetCategoryService;
import org.springframework.stereotype.Service;

@Service
public class AssetCategoryServiceImpl extends ServiceImpl<AssetCategoryMapper, AssetCategory> implements AssetCategoryService {
}
