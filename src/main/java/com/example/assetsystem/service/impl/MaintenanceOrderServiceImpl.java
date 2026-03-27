package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.MaintenanceOrder;
import com.example.assetsystem.mapper.MaintenanceOrderMapper;
import com.example.assetsystem.service.MaintenanceOrderService;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceOrderServiceImpl extends ServiceImpl<MaintenanceOrderMapper, MaintenanceOrder>
        implements MaintenanceOrderService {
}
