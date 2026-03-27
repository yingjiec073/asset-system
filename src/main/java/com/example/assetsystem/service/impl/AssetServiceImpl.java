package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.Asset;
import com.example.assetsystem.mapper.AssetMapper;
import com.example.assetsystem.service.AssetService;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements AssetService {
}
