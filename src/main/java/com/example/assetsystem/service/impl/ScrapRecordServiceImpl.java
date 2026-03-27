package com.example.assetsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.assetsystem.entity.ScrapRecord;
import com.example.assetsystem.mapper.ScrapRecordMapper;
import com.example.assetsystem.service.ScrapRecordService;
import org.springframework.stereotype.Service;

@Service
public class ScrapRecordServiceImpl extends ServiceImpl<ScrapRecordMapper, ScrapRecord> implements ScrapRecordService {
}
