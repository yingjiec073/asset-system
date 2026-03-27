package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.InventoryRecord;
import com.example.assetsystem.mapper.InventoryRecordMapper;
import com.example.assetsystem.service.InventoryRecordService;
import org.springframework.stereotype.Service;

@Service
public class InventoryRecordServiceImpl extends ServiceImpl<InventoryRecordMapper, InventoryRecord>
        implements InventoryRecordService {
}
