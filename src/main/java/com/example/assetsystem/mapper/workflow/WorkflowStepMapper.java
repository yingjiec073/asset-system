package com.example.assetsystem.mapper.workflow;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.assetsystem.entity.workflow.WorkflowStep;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkflowStepMapper extends BaseMapper<WorkflowStep> {
}
