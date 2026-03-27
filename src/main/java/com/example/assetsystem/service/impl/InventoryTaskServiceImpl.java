package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.InventoryTask;
import com.example.assetsystem.mapper.InventoryTaskMapper;
import com.example.assetsystem.service.InventoryTaskService;
import org.springframework.stereotype.Service;

@Service
public class InventoryTaskServiceImpl extends ServiceImpl<InventoryTaskMapper, InventoryTask> implements InventoryTaskService {
}
