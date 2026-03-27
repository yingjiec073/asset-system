<template>
  <section>
    <h2>仪表盘</h2>
    <p>资产总数：{{ stats.totalAssets }}</p>
    <pre>分类占比: {{ stats.categoryDistribution }}</pre>
    <pre>状态分布: {{ stats.statusDistribution }}</pre>
    <pre>部门统计: {{ stats.departmentDistribution }}</pre>
  </section>
</template>

<script setup>
import { onMounted, reactive } from 'vue';
import { fetchDashboard } from '@/api/asset';

const stats = reactive({ totalAssets: 0, categoryDistribution: {}, statusDistribution: {}, departmentDistribution: {} });

onMounted(async () => {
  const res = await fetchDashboard();
  Object.assign(stats, res.data || {});
});
</script>
