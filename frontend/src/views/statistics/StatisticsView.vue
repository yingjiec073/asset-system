<script setup>
import { onMounted, ref } from 'vue'
import { getStats } from '../../api/asset'

const stats = ref({})
onMounted(async () => { stats.value = (await getStats()).data })
</script>

<template>
  <div class="card">
    <h2>统计分析</h2>
    <pre>{{ stats }}</pre>
  </div>
</template>
