<script setup>
import { onMounted, ref } from 'vue'
import { getDashboard } from '../../api/asset'

const stats = ref({ totalAssets: 0, categoryStats: {}, statusStats: {}, departmentStats: {} })
const loading = ref(false)

async function load() {
  loading.value = true
  try {
    const res = await getDashboard()
    stats.value = res.data
  } finally {
    loading.value = false
  }
}
onMounted(load)
</script>

<template>
  <div class="card">
    <h2>仪表盘</h2>
    <p v-if="loading">loading...</p>
    <div v-else>
      <p>资产总数：{{ stats.totalAssets }}</p>
      <p>类型占比：{{ stats.categoryStats }}</p>
      <p>状态分布：{{ stats.statusStats }}</p>
      <p>部门统计：{{ stats.departmentStats }}</p>
    </div>
  </div>
</template>
