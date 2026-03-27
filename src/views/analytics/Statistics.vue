<template>
  <el-card>
    <template #header>统计分析</template>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="资产总量">{{ summary.totalAssets }}</el-descriptions-item>
      <el-descriptions-item label="分类数">{{ summary.categoryCount }}</el-descriptions-item>
      <el-descriptions-item label="部门数">{{ summary.departmentCount }}</el-descriptions-item>
      <el-descriptions-item label="在用资产">{{ summary.inUseCount }}</el-descriptions-item>
    </el-descriptions>

    <el-divider />

    <el-table :data="categoryStats" border stripe>
      <el-table-column prop="name" label="分类" />
      <el-table-column prop="value" label="数量" />
    </el-table>
  </el-card>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { getAssetStats } from '../../api/asset'

const summary = reactive({ totalAssets: 0, categoryCount: 0, departmentCount: 0, inUseCount: 0 })
const categoryStats = ref([])

const fetchData = async () => {
  const { data } = await getAssetStats()
  summary.totalAssets = data?.totalAssets ?? 0
  summary.categoryCount = data?.categoryStats?.length ?? 0
  summary.departmentCount = data?.departmentStats?.length ?? 0
  summary.inUseCount = data?.statusStats?.find((item) => item.name === 'IN_USE')?.value ?? 0
  categoryStats.value = data?.categoryStats ?? []
}

fetchData()
</script>
