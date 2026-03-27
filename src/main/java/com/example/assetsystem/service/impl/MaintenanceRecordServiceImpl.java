package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.MaintenanceRecord;
import com.example.assetsystem.mapper.MaintenanceRecordMapper;
import com.example.assetsystem.service.MaintenanceRecordService;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceRecordServiceImpl extends ServiceImpl<MaintenanceRecordMapper, MaintenanceRecord> implements MaintenanceRecordService {
}
