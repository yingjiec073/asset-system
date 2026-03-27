<template>
  <el-table :data="data" border stripe v-loading="loading" style="width: 100%">
    <el-table-column prop="name" label="资产名称" min-width="140" />
    <el-table-column prop="category" label="类型" min-width="110" />
    <el-table-column prop="brand" label="品牌" min-width="100" />
    <el-table-column prop="model" label="型号" min-width="120" />
    <el-table-column prop="serialNumber" label="编号" min-width="160" />
    <el-table-column prop="status" label="状态" min-width="120">
      <template #default="{ row }">
        <asset-status-tag :status="row.status" />
      </template>
    </el-table-column>
    <el-table-column prop="departmentId" label="部门ID" min-width="100" />
    <el-table-column prop="location" label="位置" min-width="140" />
    <el-table-column prop="createdAt" label="创建时间" min-width="180" />
    <el-table-column v-if="showActions" label="操作" width="180" fixed="right">
      <template #default="{ row }">
        <el-button type="primary" link @click="$emit('edit', row)">编辑</el-button>
        <el-button type="danger" link @click="$emit('delete', row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import AssetStatusTag from './AssetStatusTag.vue'

defineProps({
  data: {
    type: Array,
    default: () => []
  },
  loading: Boolean,
  showActions: {
    type: Boolean,
    default: true
  }
})

defineEmits(['edit', 'delete'])
</script>
