package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.AssetOperation;
import com.example.assetsystem.mapper.AssetOperationMapper;
import com.example.assetsystem.service.AssetOperationService;
import org.springframework.stereotype.Service;

@Service
public class AssetOperationServiceImpl extends ServiceImpl<AssetOperationMapper, AssetOperation> implements AssetOperationService {
}
