<template>
  <div class="dashboard-grid" v-loading="loading">
    <el-card>
      <template #header>资产总数</template>
      <div class="total">{{ stats.totalAssets }}</div>
    </el-card>
    <el-card>
      <template #header>状态分布</template>
      <div ref="statusChartRef" class="chart"></div>
    </el-card>
    <el-card>
      <template #header>资产类型占比</template>
      <div ref="categoryChartRef" class="chart"></div>
    </el-card>
    <el-card class="span-2">
      <template #header>部门资产统计</template>
      <div ref="departmentChartRef" class="chart wide"></div>
    </el-card>
  </div>
</template>

<script setup>
import { nextTick, onMounted, onUnmounted, reactive, ref } from 'vue'
import * as echarts from 'echarts'
import { getAssetStats } from '../../api/asset'

const loading = ref(false)
const statusChartRef = ref(null)
const categoryChartRef = ref(null)
const departmentChartRef = ref(null)
const stats = reactive({
  totalAssets: 0,
  statusStats: [],
  categoryStats: [],
  departmentStats: []
})

let statusChart
let categoryChart
let departmentChart

const initCharts = () => {
  if (statusChartRef.value) statusChart = echarts.init(statusChartRef.value)
  if (categoryChartRef.value) categoryChart = echarts.init(categoryChartRef.value)
  if (departmentChartRef.value) departmentChart = echarts.init(departmentChartRef.value)
}

const renderCharts = () => {
  statusChart?.setOption({
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: '65%', data: stats.statusStats.map((i) => ({ name: i.name, value: i.value })) }]
  })

  categoryChart?.setOption({
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: ['35%', '68%'], data: stats.categoryStats.map((i) => ({ name: i.name, value: i.value })) }]
  })

  departmentChart?.setOption({
    xAxis: { type: 'category', data: stats.departmentStats.map((i) => i.name) },
    yAxis: { type: 'value' },
    tooltip: { trigger: 'axis' },
    series: [{ type: 'bar', data: stats.departmentStats.map((i) => i.value), barWidth: 28 }]
  })
}

const fetchStats = async () => {
  loading.value = true
  try {
    const { data } = await getAssetStats()
    stats.totalAssets = data?.totalAssets ?? 0
    stats.statusStats = data?.statusStats ?? []
    stats.categoryStats = data?.categoryStats ?? []
    stats.departmentStats = data?.departmentStats ?? []
    await nextTick()
    initCharts()
    renderCharts()
  } finally {
    loading.value = false
  }
}

const handleResize = () => {
  statusChart?.resize()
  categoryChart?.resize()
  departmentChart?.resize()
}

onMounted(() => {
  fetchStats()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  statusChart?.dispose()
  categoryChart?.dispose()
  departmentChart?.dispose()
})
</script>

<style scoped>
.dashboard-grid {
  display: grid;
  gap: 16px;
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.span-2 {
  grid-column: span 2;
}

.total {
  font-size: 40px;
  font-weight: 700;
  color: #409eff;
  line-height: 200px;
  text-align: center;
}

.chart {
  width: 100%;
  height: 260px;
}

.wide {
  height: 320px;
}
</style>
