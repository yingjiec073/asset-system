package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.Vehicle;
import com.example.assetsystem.mapper.VehicleMapper;
import com.example.assetsystem.service.VehicleService;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements VehicleService {
}
